package entities;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DueDateTest {

    public LocalDateTime localDateTime = LocalDateTime.of(2022, Month.APRIL,27,16,30);

    @Test
    public void testCreate() {
        DueDate dueDate = new DueDate(localDateTime);
        assertThat(dueDate.getDueDate().getYear(),equalTo(2022));
        assertThat(dueDate.getDueDate().getMonth(),equalTo(Month.APRIL));
        assertThat(dueDate.getDueDate().getDayOfMonth(),equalTo(27));
        assertThat(dueDate.getDueDate().getHour(),equalTo(16));
        assertThat(dueDate.getDueDate().getMinute(),equalTo(30));
        assertThat(dueDate.getDueDate().getSecond(),equalTo(0));
    }

    @Test
    public void testCreateNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new DueDate(null));
        assertEquals(exception.getMessage(),"Due date must not be null!");
    }
}
