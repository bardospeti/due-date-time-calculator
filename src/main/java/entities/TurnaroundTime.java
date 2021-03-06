package entities;

public class TurnaroundTime {

    private double turnaroundTime;

    public TurnaroundTime(double turnaroundTime) {
        assert !(turnaroundTime <= 0) : "Turnaround time must be positive!";
        this.turnaroundTime = turnaroundTime;
    }

    public double getTurnaroundTimeInHours() {
        return turnaroundTime;
    }

    public long getTurnaroundTimeInSeconds() {
        return (long) (turnaroundTime * 3600);
    }

    public long getTurnaroundTimeInMillis() {
        return (long) (turnaroundTime * 3600 * 1000);
    }

    public void setTurnaroundTimeInHours(double turnaroundTimeInHours) {
        this.turnaroundTime = turnaroundTimeInHours;
        assert !(turnaroundTime <= 0) : "Turnaround time must be positive!";
    }

    public void setTurnaroundTimeInMillis(double turnaroundTimeInMillis) {
        this.turnaroundTime = turnaroundTimeInMillis / 3600 / 1000;
        assert !(turnaroundTime <= 0) : "Turnaround time must be positive!";
    }

    @Override
    public String toString() {
        return "turnaround time in hours: " + turnaroundTime;
    }
}
