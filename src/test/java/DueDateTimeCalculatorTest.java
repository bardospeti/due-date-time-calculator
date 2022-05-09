import entities.DueDateTime;
import entities.Issue;
import entities.SubmitDateTime;
import entities.TurnAroundTime;
import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class DueDateTimeCalculatorTest {

    public SubmitDateTime submitDateTime = new SubmitDateTime(LocalDateTime.of
            (2022, Month.APRIL,26,15,21));
    public TurnAroundTime turnAroundTime1 = new TurnAroundTime(1);
    public TurnAroundTime turnAroundTime2 = new TurnAroundTime(2);

    @Test
    public void testCreateAndConvert() {
        Issue issue = new Issue(submitDateTime, turnAroundTime1);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        assertThat(dueDateTimeCalculator.getIssue().getSubmitDateTime().getHour(),equalTo(15));
        assertThat(dueDateTimeCalculator.getIssue().getTurnAroundTimeInSeconds(),equalTo(3600L));
    }

    @Test
    public void testCreateNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new DueDateTimeCalculator(null));
        assertEquals(exception.getMessage(),"Issue must not be null!");
    }

    @Test
    public void testSetToNull() {
        Issue issue = new Issue(submitDateTime, turnAroundTime1);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        Exception exception = assertThrows(NullPointerException.class, () -> dueDateTimeCalculator.setIssue(null));
        assertEquals(exception.getMessage(),"Issue must not be null!");
    }

    @Test
    public void testSubmitDateTimeOutsideWorkingHours() {
        submitDateTime = new SubmitDateTime(submitDateTime.getSubmitDateTime().plusHours(2));
        Issue issue = new Issue(submitDateTime, turnAroundTime1);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> dueDateTimeCalculator.calculateDueDateTime(issue));
        assertEquals(assertionError.getMessage(),"A problem can only be reported during working hours!");
    }

    @Test
    public void testDueToday() {
        Issue issue = new Issue(submitDateTime, turnAroundTime1);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusHours(1)));
    }

    @Test
    public void testDueTomorrow() {
        Issue issue = new Issue(submitDateTime, turnAroundTime2);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusHours(18)));
    }

    @Test
    public void testDueFriday() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(25);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusDays(3).plusHours(1)));
    }

    @Test
    public void testDueNextWeek1() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(39);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusWeeks(1).plusHours(-1)));
    }

    @Test
    public void testDueNextWeek2() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(41);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusWeeks(1).plusHours(1)));
    }

    @Test
    public void testDueNextWeek3() {
        submitDateTime = new SubmitDateTime(submitDateTime.getSubmitDateTime().plusDays(3));
        TurnAroundTime turnAroundTime = new TurnAroundTime(8);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusDays(3)));
    }

    @Test
    public void testLongWork() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(97);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusWeeks(2).plusDays(2).plusHours(1)));
    }

    @Test
    public void testThirtyMinutes() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(0.5);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusMinutes(30)));
    }

    @Test
    public void testTenMinutes() {
        TurnAroundTime turnAroundTime = new TurnAroundTime(1.0 / 6);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        DueDateTime dueDateTime = dueDateTimeCalculator.calculateDueDateTime(issue);
        assertThat(dueDateTime.getDueDate(),equalTo(submitDateTime.getSubmitDateTime().plusMinutes(10)));
    }
}
