package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubmitDateTime {

    private LocalDateTime submitDateTime;

    public SubmitDateTime(LocalDateTime submitDateTime) {
        Objects.requireNonNull(submitDateTime,"Submit date/time must not be null!");
        this.submitDateTime = submitDateTime;
    }

    public LocalDateTime getSubmitDateTime() {
        return submitDateTime;
    }

    public void setSubmitDateTime(LocalDateTime submitDateTime) {
        this.submitDateTime = submitDateTime;
    }

    @Override
    public String toString() {
        return "submit date/time: " + submitDateTime;
    }
}
