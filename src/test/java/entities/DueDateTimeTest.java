package entities;

import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DueDateTimeTest {

    public LocalDateTime localDateTime = LocalDateTime.of(2022, Month.APRIL,27,16,30);

    @Test
    public void testCreate() {
        DueDateTime dueDateTime = new DueDateTime(localDateTime);
        assertThat(dueDateTime.getDueDate().getYear(),equalTo(2022));
        assertThat(dueDateTime.getDueDate().getMonth(),equalTo(Month.APRIL));
        assertThat(dueDateTime.getDueDate().getDayOfMonth(),equalTo(27));
        assertThat(dueDateTime.getDueDate().getHour(),equalTo(16));
        assertThat(dueDateTime.getDueDate().getMinute(),equalTo(30));
        assertThat(dueDateTime.getDueDate().getSecond(),equalTo(0));
    }

    @Test
    public void testCreateNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new DueDateTime(null));
        assertEquals(exception.getMessage(),"Due date must not be null!");
    }

    @Test
    public void testSetToNull() {
        DueDateTime dueDateTime = new DueDateTime(localDateTime);
        Exception exception = assertThrows(NullPointerException.class, () -> dueDateTime.setDueDate(null));
        assertEquals(exception.getMessage(),"Due date must not be null!");
    }
}
