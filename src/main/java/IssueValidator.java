import entities.Issue;
import java.util.Objects;

public class IssueValidator {

    private Issue issue;

    public IssueValidator(Issue issue) {
        Objects.requireNonNull(issue,"Issue must not be null");
        this.issue = issue;
    }

    protected boolean validateIssue(Issue issue) {
        Objects.requireNonNull(issue,"Issue must not be null");
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        assert dueDateTimeCalculator.isWorkingTime(issue.getSubmitDateTime()) : "A problem can only be reported during working hours!";
        return true;
    }
}
