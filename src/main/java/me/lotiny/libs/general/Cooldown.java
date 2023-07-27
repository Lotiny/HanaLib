package me.lotiny.libs.general;

import lombok.Getter;
import lombok.Setter;
import me.lotiny.libs.utils.TimeFormatUtil;

import java.text.DecimalFormat;

public class Cooldown {

    @Getter
    @Setter
    private long start = System.currentTimeMillis();
    @Getter
    @Setter
    private long expire;

    private static final DecimalFormat SECONDS_FORMAT = new DecimalFormat("#0.0");

    public Cooldown(int seconds) {
        long duration = 1000L * seconds;
        this.expire = this.start + duration;
    }

    public long getPassed() {
        return System.currentTimeMillis() - this.start;
    }

    public long getRemaining() {
        return this.expire - System.currentTimeMillis();
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - this.expire > 1L;
    }

    public int getSecondsLeft() {
        return (int)this.getRemaining() / 1000;
    }

    public String getMilliSecondsLeft() {
        return formatSeconds(this.getRemaining());
    }

    public String getTimeLeft() {
        return TimeFormatUtil.formatTime(this.getSecondsLeft());
    }

    public void cancelCountdown() {
        this.expire = 0L;
    }

    private static String formatSeconds(long time) {
        return SECONDS_FORMAT.format((float)time / 1000.0F);
    }
}
