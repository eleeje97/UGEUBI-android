package duksung.android.hororok.ugeubi.alarm;

public class Alarm_data implements Comparable<Alarm_data> {

    public String notification_date;
    public String notification_time;
    public String notification_content;
    public String passedDay;

    public Alarm_data(String notification_date, String notification_time, String notification_content, String passedDay) {
        this.notification_date = notification_date;
        this.notification_time = notification_time;
        this.notification_content = notification_content;
        this.passedDay = passedDay;
    }


    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getMedicine_content() {
        return notification_content;
    }

    public void setMedicine_content(String notification_content) {
        this.notification_content = notification_content;
    }

    public String getPassedDay() {
        return passedDay;
    }

    public void setPassedDay(String passedDay) {
        this.passedDay = passedDay;
    }

    @Override
    public int compareTo(Alarm_data o) {
        if (o.notification_date.compareTo(notification_date) == 0) {
            return o.notification_time.compareTo(notification_time);
        } else {
            return o.notification_date.compareTo(notification_date);
        }
    }
}
