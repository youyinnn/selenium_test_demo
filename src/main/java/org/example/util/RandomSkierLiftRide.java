package org.example.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSkierLiftRide {

    private LiftRide body;
    private Integer resortID;
    private String seasonID;
    private String dayID;
    private Integer skierID;

    public RandomSkierLiftRide() {
        body = new LiftRide()
                .setTime(ThreadLocalRandom.current().nextInt(1, 361))
                .setLiftID(ThreadLocalRandom.current().nextInt(1, 40));

        resortID = ThreadLocalRandom.current().nextInt(1, 10);
        seasonID = "2022";
        dayID = "1";
        skierID = ThreadLocalRandom.current().nextInt(1, 100001);
    }

    public LiftRide getBody() {
        return body;
    }

    public void setBody(LiftRide body) {
        this.body = body;
    }

    public Integer getResortID() {
        return resortID;
    }

    public void setResortID(Integer resortID) {
        this.resortID = resortID;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public String getDayID() {
        return dayID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public Integer getSkierID() {
        return skierID;
    }

    public void setSkierID(Integer skierID) {
        this.skierID = skierID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomSkierLiftRide)) return false;

        RandomSkierLiftRide that = (RandomSkierLiftRide) o;

        if (getBody() != null ? !getBody().equals(that.getBody()) : that.getBody() != null) return false;
        if (getResortID() != null ? !getResortID().equals(that.getResortID()) : that.getResortID() != null)
            return false;
        if (getSeasonID() != null ? !getSeasonID().equals(that.getSeasonID()) : that.getSeasonID() != null)
            return false;
        if (getDayID() != null ? !getDayID().equals(that.getDayID()) : that.getDayID() != null) return false;
        return getSkierID() != null ? getSkierID().equals(that.getSkierID()) : that.getSkierID() == null;
    }

    @Override
    public int hashCode() {
        int result = getBody() != null ? getBody().hashCode() : 0;
        result = 31 * result + (getResortID() != null ? getResortID().hashCode() : 0);
        result = 31 * result + (getSeasonID() != null ? getSeasonID().hashCode() : 0);
        result = 31 * result + (getDayID() != null ? getDayID().hashCode() : 0);
        result = 31 * result + (getSkierID() != null ? getSkierID().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{\"RandomSkierLiftRide\":{"
                + "\"body\":" + body
                + ", \"resortID\":\"" + resortID + "\""
                + ", \"seasonID\":\"" + seasonID + "\""
                + ", \"dayID\":\"" + dayID + "\""
                + ", \"skierID\":\"" + skierID + "\""
                + "}}";
    }
}
