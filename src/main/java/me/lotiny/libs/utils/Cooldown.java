package me.lotiny.libs.utils;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
public class Cooldown {

    private final DecimalFormat SECONDS_FORMAT = new DecimalFormat("#0.0");
    @Setter
    private long start = System.currentTimeMillis();
    @Setter
    private long expire;
    private boolean paused = false;
    private long pausedTime;

    /**
     * Creates a new `Cooldown` instance with the specified duration in seconds.
     *
     * @param seconds The duration of the cooldown in seconds.
     */
    public Cooldown(int seconds) {
        long duration = 1000L * seconds;
        this.expire = this.start + duration;
    }

    /**
     * Pause the cooldown.
     */
    public void pause() {
        if (!paused) {
            paused = true;
            pausedTime = System.currentTimeMillis();
        }
    }

    /**
     * Unpause the cooldown.
     */
    public void unpause() {
        if (paused) {
            long pausedDuration = System.currentTimeMillis() - pausedTime;
            start += pausedDuration;
            expire += pausedDuration;
            paused = false;
        }
    }

    /**
     * Get the time that has passed since the cooldown started, considering pauses.
     *
     * @return The time in milliseconds that has passed since the cooldown started.
     */
    public long getPassed() {
        if (paused) {
            return pausedTime - this.start;
        } else {
            return System.currentTimeMillis() - this.start;
        }
    }

    /**
     * Get the remaining time until the cooldown expires, considering pauses.
     *
     * @return The remaining time in milliseconds until the cooldown expires.
     */
    public long getRemaining() {
        if (paused) {
            return this.expire - pausedTime;
        } else {
            return this.expire - System.currentTimeMillis();
        }
    }

    /**
     * Check if the cooldown has expired.
     *
     * @return `true` if the cooldown has expired, otherwise `false`.
     */
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expire;
    }

    /**
     * Get the remaining time in seconds until the cooldown expires.
     *
     * @return The remaining time in seconds until the cooldown expires.
     */
    public int getSecondsLeft() {
        return (int) (this.getRemaining() / 1000);
    }

    /**
     * Get the remaining time in seconds formatted as a string with one decimal place.
     *
     * @return The remaining time formatted as a string.
     */
    public String getMilliSecondsLeft() {
        return formatSeconds(this.getRemaining());
    }

    /**
     * Get the remaining time in a human-readable format.
     *
     * @return The remaining time in a human-readable format, e.g., "1m 30s".
     */
    public String getTimeLeft() {
        return TimeFormatUtil.formatTime(this.getSecondsLeft());
    }

    /**
     * Cancel the countdown by setting the expire time to 0.
     */
    public void cancelCountdown() {
        this.expire = 0L;
    }

    private String formatSeconds(long time) {
        return SECONDS_FORMAT.format((float) time / 1000.0F);
    }
}
