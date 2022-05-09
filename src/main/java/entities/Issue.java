package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Issue {

    private SubmitDateTime submitDateTime;

    private TurnaroundTime turnaroundTime;

    public Issue(SubmitDateTime submitDateTime, TurnaroundTime turnaroundTime) {
        Objects.requireNonNull(submitDateTime,"Submit date/time must not be null!");
        Objects.requireNonNull(turnaroundTime,"Turnaround time must not be null!");
        this.submitDateTime = submitDateTime;
        this.turnaroundTime = turnaroundTime;
    }

    public LocalDateTime getSubmitDateTime() {
        return submitDateTime.getSubmitDateTime();
    }

    public void setSubmitDateTime(SubmitDateTime submitDateTime) {
        Objects.requireNonNull(submitDateTime,"Submit date/time must not be null!");
        this.submitDateTime = submitDateTime;
    }

    public double getTurnaroundTimeInHours() {
        return turnaroundTime.getTurnaroundTimeInHours();
    }

    public long getTurnaroundTimeInSeconds() {
        return turnaroundTime.getTurnaroundTimeInSeconds();
    }

    public long getTurnaroundTimeInMillis() {
        return turnaroundTime.getTurnaroundTimeInMillis();
    }

    public void setTurnaroundTime(TurnaroundTime turnaroundTime) {
        Objects.requireNonNull(turnaroundTime,"Turnaround time must not be null!");
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public String toString() {
        return submitDateTime.toString() + "\n" + turnaroundTime.toString();
    }
}
