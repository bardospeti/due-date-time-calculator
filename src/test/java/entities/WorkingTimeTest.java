package entities;

import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkingTimeTest {

    @Test
    public void testStart() {
        LocalTime startTime = WorkingTime.START_OF_WORKING_TIME;
        assertThat(startTime.getHour(),equalTo(9));
        assertThat(startTime.getMinute(),equalTo(0));
    }

    @Test
    public void testEnd() {
        LocalTime endTime = WorkingTime.END_OF_WORKING_TIME;
        assertThat(endTime.getHour(), equalTo(17));
        assertThat(endTime.getMinute(), equalTo(0));
    }

    @Test
    public void testDuration() {
        assertThat(WorkingTime.DURATION_OF_WORKDAY.toMillis(),equalTo(8 * 3600 * 1000L));
    }

    @Test
    public void testDailyWorkingHours() {
        assertThat(WorkingTime.DAILY_WORKING_HOURS,equalTo(8));
    }

    @Test
    public void testWeeklyWorkingHours() {
        assertThat(WorkingTime.WEEKLY_WORKING_HOURS,equalTo(40));
    }
}
