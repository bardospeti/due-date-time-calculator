import entities.Issue;
import entities.SubmitDateTime;
import entities.TurnAroundTime;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class IssueValidatorTest {

    private SubmitDateTime submitDateTime1 = new SubmitDateTime(LocalDateTime.of(2022, Month.APRIL,28,16,34));
    private SubmitDateTime submitDateTime2 = new SubmitDateTime(LocalDateTime.of(2022, Month.APRIL,28,18,34));
    private TurnAroundTime turnAroundTime1 = new TurnAroundTime(1);

    @Test
    public void testCreate() {
        Issue issue = new Issue(submitDateTime1, turnAroundTime1);
        IssueValidator issueValidator = new IssueValidator(issue);
        assertTrue(issueValidator.validateIssue(issue));
    }

    @Test
    public void testOutsideWorkingHours() {
        Issue issue = new Issue(submitDateTime2, turnAroundTime1);
        IssueValidator issueValidator = new IssueValidator(issue);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> issueValidator.validateIssue(issue));
        assertEquals(assertionError.getMessage(),"A problem can only be reported during working hours!");
    }

    @Test
    public void testNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new IssueValidator(null));
        assertEquals(exception.getMessage(),"Issue must not be null");
    }
}
