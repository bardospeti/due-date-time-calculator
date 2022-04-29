package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class DueDate {

    private LocalDateTime dueDate;

    public DueDate(LocalDateTime dueDate) {
        Objects.requireNonNull(dueDate, "Due date must not be null!");
        this.dueDate = dueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "due date: "+ dueDate;
    }
}
