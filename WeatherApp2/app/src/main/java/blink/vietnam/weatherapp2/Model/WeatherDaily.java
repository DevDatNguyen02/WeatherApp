package blink.vietnam.weatherapp2.Model;

public class WeatherDaily {
    public     String timeDaily;
    public     String statusDaily;
    public     String tempDaily;
    public     String iconDaily;

    public WeatherDaily(String timeDaily, String statusDaily, String tempDaily, String iconDaily) {
        this.timeDaily = timeDaily;
        this.statusDaily = statusDaily;
        this.tempDaily = tempDaily;
        this.iconDaily = iconDaily;
    }

    public String getTimeDaily() {
        return timeDaily;
    }

    public void setTimeDaily(String timeDaily) {
        this.timeDaily = timeDaily;
    }

    public String getStatusDaily() {
        return statusDaily;
    }

    public void setStatusDaily(String statusDaily) {
        this.statusDaily = statusDaily;
    }

    public String getTempDaily() {
        return tempDaily;
    }

    public void setTempDaily(String tempDaily) {
        this.tempDaily = tempDaily;
    }

    public String getIconDaily() {
        return iconDaily;
    }

    public void setIconDaily(String iconDaily) {
        this.iconDaily = iconDaily;
    }
}
