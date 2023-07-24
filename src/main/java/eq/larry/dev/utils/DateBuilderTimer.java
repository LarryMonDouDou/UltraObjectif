package eq.larry.dev.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBuilderTimer {
    private long time;

    private Date date;

    private String build;

    private String longBuild;

    private int multiplier = 1;

    Format h;

    Format m;

    Format s;

    public DateBuilderTimer() {
        this(0L, true);
    }

    public DateBuilderTimer(long time) {
        this(time, false);
    }

    public DateBuilderTimer(long time, boolean reversed) {
        this.time = time + (new Date()).getTime();
        this.h = new SimpleDateFormat("hh");
        this.m = new SimpleDateFormat("mm");
        this.s = new SimpleDateFormat("ss");
        if (reversed)
            setReversed(true);
        loadDate();
    }

    public void setReversed(boolean b) {
        if (b)
            this.multiplier *= -1;
    }

    private long now() {
        long now = (new Date()).getTime();
        return (this.time - now) * this.multiplier;
    }

    public DateBuilderTimer loadDate() {
        Date date = new Date(now());
        int hour = (int)(date.getTime() / 3600000L % 24L);
        String minute = this.m.format(date);
        String second = this.s.format(date);
        StringBuilder sb = new StringBuilder();
        if (hour > 0)
            sb.append("0" + hour + ":");
        sb.append(minute + ":");
        sb.append(second);
        this.date = date;
        this.build = sb.toString();
        return this;
    }

    public DateBuilderTimer loadComplexDate() {
        long now = (new Date()).getTime();
        Date date = new Date(now());
        int hour = (int)(date.getTime() / 3600000L % 24L);
        String minute = this.m.format(date);
        String second = this.s.format(date);
        StringBuilder lsb = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append("0" + hour + ":");
            lsb.append(hour + " heure(s)");
            lsb.append(" et ");
        }
        if (Integer.parseInt(minute) != 0) {
            lsb.append(minute + " minute(s)");
            lsb.append(" et ");
        }
        lsb.append(second + " seconde(s)");
        sb.append(second);
        this.date = date;
        this.build = sb.toString();
        this.longBuild = lsb.toString();
        return this;
    }

    public Date getDate() {
        return this.date;
    }

    public String getBuild() {
        return this.build;
    }

    public String getLongBuild() {
        return this.longBuild;
    }
}
