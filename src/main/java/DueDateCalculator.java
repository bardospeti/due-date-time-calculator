import entities.*;
import java.time.*;
import java.util.Objects;

public class DueDateCalculator {

    private Issue issue;

    public DueDateCalculator(Issue issue) {
        Objects.requireNonNull(issue,"Issue must not be null");
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
        Objects.requireNonNull(issue,"Issue must not be null");
    }

    protected DueDate calculateDueDate(Issue issue) {
        IssueValidator issueValidator = new IssueValidator(issue);
        issueValidator.validateIssue(issue);
        while (issue.getTurnAroundTimeInHours() > WorkingTime.WEEKLY_WORKING_HOURS) {
            issue = leapWeek(issue);
        }
        while (issue.getTurnAroundTimeInHours() > WorkingTime.DAILY_WORKING_HOURS) {
            issue = leapDay(issue);
        }
        if (isDueToday(issue)) {
            issue.setSubmitDateTime(new SubmitDateTime(issue.getSubmitDateTime().plusSeconds(issue.getTurnAroundTimeInSeconds())));
        } else {
            setIssueDueTomorrow(issue);
        }
        return new DueDate(issue.getSubmitDateTime());
    }

    protected boolean isWeekend(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    protected boolean isWorkingTime(LocalDateTime localDateTime) {
        if (isWeekend(localDateTime)) {
            return false;
        }
        LocalTime localTime = localDateTime.toLocalTime();
        return !localTime.isBefore(WorkingTime.START_OF_WORKING_TIME) &&
                !localTime.isAfter(WorkingTime.END_OF_WORKING_TIME);
    }

    protected boolean isDueToday(Issue issue) {
        return (issue.getTurnAroundTimeInMillis() <=
                Duration.between(issue.getSubmitDateTime(),
                        LocalDateTime.of(issue.getSubmitDateTime().toLocalDate(),
                                WorkingTime.END_OF_WORKING_TIME)).toMillis());
    }

    protected SubmitDateTime calculateSubmitDateTimeOfMorningOfNextWorkday(LocalDateTime localDateTime) {
        localDateTime = localDateTime.plusDays(1).withHour(WorkingTime.START_OF_WORKING_TIME.getHour()).
                withMinute(WorkingTime.START_OF_WORKING_TIME.getMinute());
        switch (localDateTime.getDayOfWeek()) {
            case SATURDAY -> localDateTime = localDateTime.plusDays(2);
            case SUNDAY -> localDateTime = localDateTime.plusDays(1);
        }
        return new SubmitDateTime(localDateTime);
    }

    protected Issue leapWeek(Issue issue) {
        LocalDateTime localDateTime = issue.getSubmitDateTime();
        double turnAroundTimeInHours = issue.getTurnAroundTimeInHours();
        localDateTime = localDateTime.plusWeeks(1);
        turnAroundTimeInHours -= WorkingTime.WEEKLY_WORKING_HOURS;
        return new Issue(new SubmitDateTime(localDateTime), new TurnAroundTime(turnAroundTimeInHours));
    }

    protected Issue leapDay(Issue issue) {
        LocalDateTime localDateTime = issue.getSubmitDateTime();
        double turnAroundTimeInHours = issue.getTurnAroundTimeInHours();
        localDateTime = localDateTime.plusDays(1);
        turnAroundTimeInHours -= WorkingTime.DAILY_WORKING_HOURS;
        return new Issue(new SubmitDateTime(localDateTime), new TurnAroundTime(turnAroundTimeInHours));
    }

    protected Issue setIssueDueTomorrow(Issue issue) {
        double remainingTurnaroundTimeInMillis = issue.getTurnAroundTimeInMillis();
        remainingTurnaroundTimeInMillis -= Duration.between(issue.getSubmitDateTime(),
                LocalDateTime.of(issue.getSubmitDateTime().toLocalDate(),WorkingTime.END_OF_WORKING_TIME)).toMillis();
        issue.setSubmitDateTime(calculateSubmitDateTimeOfMorningOfNextWorkday(issue.getSubmitDateTime()));
        double remainingTurnaroundTimeInSeconds = remainingTurnaroundTimeInMillis / 1000;
        issue.setSubmitDateTime(new SubmitDateTime(issue.getSubmitDateTime().plusSeconds((long) remainingTurnaroundTimeInSeconds)));
        return issue;
    }

    public static void main(String[] args) {
        SubmitDateTime submitDateTime = new SubmitDateTime(LocalDateTime.of
                (2022,Month.APRIL,26,14,21));
        TurnAroundTime turnAroundTime = new TurnAroundTime(2.5);
        Issue issue = new Issue(submitDateTime, turnAroundTime);
        //System.out.println(issue.getSubmitDateTime().toString());
        //System.out.println(issue.getTurnAroundTimeInHours().toString());
        System.out.println(issue);
        DueDateCalculator dueDateCalculator = new DueDateCalculator(issue);
        System.out.println(dueDateCalculator.calculateDueDate(issue));
        System.out.println(dueDateCalculator.isWeekend(issue.getSubmitDateTime()));
        System.out.println(dueDateCalculator.isWorkingTime(issue.getSubmitDateTime()));
        System.out.println(dueDateCalculator.isWorkingTime(LocalDateTime.now()));
        System.out.println(issue.getSubmitDateTime());
        System.out.println(Duration.between(WorkingTime.START_OF_WORKING_TIME,WorkingTime.END_OF_WORKING_TIME).toMillis());
        System.out.println(Duration.ofMillis(issue.getTurnAroundTimeInMillis()).toMillis());
        System.out.println(dueDateCalculator.isDueToday(issue));
        System.out.println(dueDateCalculator.calculateDueDate(issue));
        System.out.println(dueDateCalculator.calculateSubmitDateTimeOfMorningOfNextWorkday(issue.getSubmitDateTime()));
    }

}
