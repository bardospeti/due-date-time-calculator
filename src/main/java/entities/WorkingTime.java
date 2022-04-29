package entities;

import java.time.Duration;
import java.time.LocalTime;

public class WorkingTime {
    public final static LocalTime START_OF_WORKING_TIME = LocalTime.of(9,0);
    public final static LocalTime END_OF_WORKING_TIME = LocalTime.of(17,0);
    public final static Duration DURATION_OF_WORKDAY = Duration.between(START_OF_WORKING_TIME,END_OF_WORKING_TIME);
    public final static int DAILY_WORKING_HOURS = (int) (WorkingTime.DURATION_OF_WORKDAY.toHours());
    public final static int NUMBER_OF_WEEKDAYS = 5;
    public final static int WEEKLY_WORKING_HOURS = WorkingTime.NUMBER_OF_WEEKDAYS * DAILY_WORKING_HOURS;
}
