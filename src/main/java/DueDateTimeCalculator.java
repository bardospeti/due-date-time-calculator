import entities.*;
import java.time.*;
import java.util.Objects;

public class DueDateTimeCalculator {

    private Issue issue;

    public DueDateTimeCalculator(Issue issue) {
        Objects.requireNonNull(issue,"Issue must not be null!");
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
        Objects.requireNonNull(issue,"Issue must not be null!");
    }

    private void validateIssue(Issue issue) {
        Objects.requireNonNull(issue,"Issue must not be null");
        DueDateTimeCalculator dueDateTimeCalculator = new DueDateTimeCalculator(issue);
        assert dueDateTimeCalculator.isWorkingTime(issue.getSubmitDateTime()) : "A problem can only be reported during working hours!";
    }

    protected DueDateTime calculateDueDateTime(Issue issue) {
        validateIssue(issue);
        while (issue.getTurnaroundTimeInHours() > WorkingTime.WEEKLY_WORKING_HOURS) {
            issue = leapWeek(issue);
        }
        while (issue.getTurnaroundTimeInHours() > WorkingTime.DAILY_WORKING_HOURS) {
            issue = leapDay(issue);
        }
        if (isDueToday(issue)) {
            issue.setSubmitDateTime(new SubmitDateTime(issue.getSubmitDateTime().plusSeconds(issue.getTurnaroundTimeInSeconds())));
        } else {
            setIssueDueTomorrow(issue);
        }
        return new DueDateTime(issue.getSubmitDateTime());
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isWorkingTime(LocalDateTime localDateTime) {
        if (isWeekend(localDateTime)) {
            return false;
        }
        LocalTime localTime = localDateTime.toLocalTime();
        return !localTime.isBefore(WorkingTime.START_OF_WORKING_TIME) &&
                !localTime.isAfter(WorkingTime.END_OF_WORKING_TIME);
    }

    private boolean isDueToday(Issue issue) {
        return (issue.getTurnaroundTimeInMillis() <=
                Duration.between(issue.getSubmitDateTime(),
                        LocalDateTime.of(issue.getSubmitDateTime().toLocalDate(),
                                WorkingTime.END_OF_WORKING_TIME)).toMillis());
    }

    private SubmitDateTime calculateSubmitDateTimeOfMorningOfNextWorkday(LocalDateTime localDateTime) {
        localDateTime = localDateTime.plusDays(1).withHour(WorkingTime.START_OF_WORKING_TIME.getHour()).
                withMinute(WorkingTime.START_OF_WORKING_TIME.getMinute());
        switch (localDateTime.getDayOfWeek()) {
            case SATURDAY -> localDateTime = localDateTime.plusDays(2);
            case SUNDAY -> localDateTime = localDateTime.plusDays(1);
        }
        return new SubmitDateTime(localDateTime);
    }

    private Issue leapWeek(Issue issue) {
        LocalDateTime localDateTime = issue.getSubmitDateTime();
        double turnaroundTimeInHours = issue.getTurnaroundTimeInHours();
        localDateTime = localDateTime.plusWeeks(1);
        turnaroundTimeInHours -= WorkingTime.WEEKLY_WORKING_HOURS;
        issue.setSubmitDateTime(new SubmitDateTime(localDateTime));
        issue.setTurnaroundTime(new TurnaroundTime(turnaroundTimeInHours));
        return issue;
    }

    private Issue leapDay(Issue issue) {
        LocalDateTime localDateTime = issue.getSubmitDateTime();
        double turnaroundTimeInHours = issue.getTurnaroundTimeInHours();
        localDateTime = localDateTime.plusDays(1);
        while (isWeekend(localDateTime)) {
            localDateTime = localDateTime.plusDays(1);
        }
        turnaroundTimeInHours -= WorkingTime.DAILY_WORKING_HOURS;
        issue.setSubmitDateTime(new SubmitDateTime(localDateTime));
        issue.setTurnaroundTime(new TurnaroundTime(turnaroundTimeInHours));
        return issue;
    }

    private Issue setIssueDueTomorrow(Issue issue) {
        double remainingTurnaroundTimeInMillis = issue.getTurnaroundTimeInMillis();
        remainingTurnaroundTimeInMillis -= Duration.between(issue.getSubmitDateTime(),
                LocalDateTime.of(issue.getSubmitDateTime().toLocalDate(),WorkingTime.END_OF_WORKING_TIME)).toMillis();
        issue.setSubmitDateTime(calculateSubmitDateTimeOfMorningOfNextWorkday(issue.getSubmitDateTime()));
        double remainingTurnaroundTimeInSeconds = remainingTurnaroundTimeInMillis / 1000;
        issue.setSubmitDateTime(new SubmitDateTime(issue.getSubmitDateTime().plusSeconds((long) remainingTurnaroundTimeInSeconds)));
        return issue;
    }
}
