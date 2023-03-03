package org.example.util;

public class LiftRide {

    private Integer time = null;
    private Integer liftID = null;

    public LiftRide() {
    }

    public LiftRide(Integer time, Integer liftID) {
        this.time = time;
        this.liftID = liftID;
    }

    public Integer getTime() {
        return time;
    }

    public LiftRide setTime(Integer time) {
        this.time = time;
        return this;
    }

    public Integer getLiftID() {
        return liftID;
    }

    public LiftRide setLiftID(Integer liftID) {
        this.liftID = liftID; return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiftRide)) return false;

        LiftRide liftRide = (LiftRide) o;

        if (!getTime().equals(liftRide.getTime())) return false;
        return getLiftID().equals(liftRide.getLiftID());
    }

    @Override
    public int hashCode() {
        int result = getTime().hashCode();
        result = 31 * result + getLiftID().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{\"LiftRide\":{"
                + "\"time\":\"" + time + "\""
                + ", \"liftID\":\"" + liftID + "\""
                + "}}";
    }
}
