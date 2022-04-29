package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Issue {

    private SubmitDateTime submitDateTime;

    private TurnAroundTime turnAroundTime;

    public Issue(SubmitDateTime submitDateTime, TurnAroundTime turnAroundTime) {
        Objects.requireNonNull(submitDateTime,"Submit date/time must not be null!");
        Objects.requireNonNull(turnAroundTime,"Turnaround time must not be null!");
        this.submitDateTime = submitDateTime;
        this.turnAroundTime = turnAroundTime;
    }

    public LocalDateTime getSubmitDateTime() {
        return submitDateTime.getSubmitDateTime();
    }

    public void setSubmitDateTime(SubmitDateTime submitDateTime) {
        this.submitDateTime = submitDateTime;
    }

    public double getTurnAroundTimeInHours() {
        return turnAroundTime.getTurnaroundTimeInHours();
    }

    public long getTurnAroundTimeInSeconds() {
        return turnAroundTime.getTurnaroundTimeInSeconds();
    }

    public long getTurnAroundTimeInMillis() {
        return turnAroundTime.getTurnaroundTimeInMillis();
    }

    public void setTurnAroundTimeInHours(TurnAroundTime turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setTurnAroundTimeInMillis(TurnAroundTime turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    @Override
    public String toString() {
        return submitDateTime.toString() + "\n" + turnAroundTime.toString();
    }
}
