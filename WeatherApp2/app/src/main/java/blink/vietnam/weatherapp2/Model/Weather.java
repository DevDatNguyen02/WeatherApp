package blink.vietnam.weatherapp2.Model;

public class Weather {
    private String time;
    private String temp;
    private String Urlicon;

    public Weather(String time, String temp,  String urlIcon) {
        this.time = time;
        this.temp = temp;
        this.Urlicon = Urlicon;

    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return Urlicon;
    }

    public void setIcon(String icon) {
        this.Urlicon = icon;
    }


}

