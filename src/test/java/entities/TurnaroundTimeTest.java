package entities;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TurnaroundTimeTest {

    //@Before
    TurnaroundTime turnaroundTime = new TurnaroundTime(2.5);

    @Test
    public void testCreate() {
        assertThat(turnaroundTime.getTurnaroundTimeInHours(),equalTo(2.5));
    }

    @Test
    public void testCreateZero() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> new TurnaroundTime(0));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testCreateNegative() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> new TurnaroundTime(-1));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testSetToNegative1() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> turnaroundTime.setTurnaroundTimeInHours(-3));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testSetToNegative2() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> turnaroundTime.setTurnaroundTimeInMillis(-60_000_000));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testSetToZero1() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> turnaroundTime.setTurnaroundTimeInHours(0));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testSetToZero2() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> turnaroundTime.setTurnaroundTimeInMillis(0));
        assertEquals(assertionError.getMessage(),"Turnaround time must be positive!");
    }

    @Test
    public void testGetterConvert() {
        assertThat(turnaroundTime.getTurnaroundTimeInSeconds(),equalTo(9000L));
        assertThat(turnaroundTime.getTurnaroundTimeInMillis(),equalTo(9_000_000L));
    }

    @Test
    public void testSetterConvert() {
        turnaroundTime.setTurnaroundTimeInMillis(3_600_000);
        assertThat(turnaroundTime.getTurnaroundTimeInHours(),equalTo(1.0));
    }
}
