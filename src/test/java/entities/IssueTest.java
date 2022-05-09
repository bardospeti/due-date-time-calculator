package entities;

import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class IssueTest {

    SubmitDateTime submitDateTime = new SubmitDateTime(LocalDateTime.of(2022, Month.APRIL,28,16,49));
    TurnaroundTime turnaroundTime = new TurnaroundTime(1);

    @Test
    public void testCreate() {
        Issue issue = new Issue(submitDateTime, turnaroundTime);
        assertThat(issue.getSubmitDateTime().getYear(),equalTo(2022));
        assertThat(issue.getSubmitDateTime().getMonth(),equalTo(Month.APRIL));
        assertThat(issue.getSubmitDateTime().getDayOfMonth(),equalTo(28));
        assertThat(issue.getSubmitDateTime().getHour(),equalTo(16));
        assertThat(issue.getSubmitDateTime().getMinute(),equalTo(49));
        assertThat(issue.getTurnaroundTimeInHours(),equalTo(1.0));
    }

    @Test
    public void testCreateNull1() {
        Exception exception = assertThrows(NullPointerException.class, () -> new Issue(submitDateTime,null));
        assertEquals(exception.getMessage(),"Turnaround time must not be null!");
    }
    @Test
    public void testCreateNull2() {
        Exception exception = assertThrows(NullPointerException.class, () -> new Issue(null, turnaroundTime));
        assertEquals(exception.getMessage(),"Submit date/time must not be null!");
    }

    @Test
    public void testSetToNull1() {
        Issue issue = new Issue(submitDateTime, turnaroundTime);
        Exception exception = assertThrows(NullPointerException.class, () -> issue.setSubmitDateTime(null));
        assertEquals(exception.getMessage(),"Submit date/time must not be null!");
    }

    @Test
    public void testSetToNull2() {
        Issue issue = new Issue(submitDateTime, turnaroundTime);
        Exception exception = assertThrows(NullPointerException.class, () -> issue.setTurnaroundTime(null));
        assertEquals(exception.getMessage(),"Turnaround time must not be null!");
    }
}
