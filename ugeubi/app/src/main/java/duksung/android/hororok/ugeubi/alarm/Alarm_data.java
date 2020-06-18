package duksung.android.hororok.ugeubi.alarm;

public class Alarm_data {

    public String alarm_date;
    public String alarm_time;
    public String dose_name;
    public String dday;

    public Alarm_data(String alarm_date, String alarm_time, String dose_name, String dday){
        this.alarm_date = alarm_date;
        this.alarm_time = alarm_time;
        this.dose_name = dose_name;
        this.dday = dday;
    }


    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getDose_name() {
        return dose_name;
    }

    public void setDose_name(String dose_name) {
        this.dose_name = dose_name;
    }

    public String getDday() {
        return dday;
    }

    public void setDday(String dday) {
        this.dday = dday;
    }
}
