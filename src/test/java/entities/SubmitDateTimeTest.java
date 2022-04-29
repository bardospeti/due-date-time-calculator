package entities;

import org.junit.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SubmitDateTimeTest {

    @Test
    public void testCreate() {
        SubmitDateTime submitDateTime = new SubmitDateTime(LocalDateTime.of
                (2022, Month.APRIL,26,16,20));
        assertThat(submitDateTime.getSubmitDateTime().getYear(),equalTo(2022));
        assertThat(submitDateTime.getSubmitDateTime().getMonth(),equalTo(Month.APRIL));
        assertThat(submitDateTime.getSubmitDateTime().getDayOfMonth(),equalTo(26));
        assertThat(submitDateTime.getSubmitDateTime().getDayOfWeek(),equalTo(DayOfWeek.TUESDAY));
        assertThat(submitDateTime.getSubmitDateTime().getHour(),equalTo(16));
        assertThat(submitDateTime.getSubmitDateTime().getMinute(),equalTo(20));
    }

    @Test
    public void testCreateNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new SubmitDateTime(null));
        assertEquals(exception.getMessage(),"Submit date/time must not be null!");
    }
}
