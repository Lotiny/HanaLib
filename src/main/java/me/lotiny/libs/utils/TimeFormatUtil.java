package me.lotiny.libs.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeFormatUtil {

    public String formatTime(int timer) {
        int hours = timer / 3600;
        int secondsLeft = timer - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String formattedTime = "";
        if (hours > 0) {
            if (hours < 10) {
                formattedTime = formattedTime + "0";
            }
            formattedTime = formattedTime + hours + ":";
        }

        if (minutes < 10) {
            formattedTime = formattedTime + "0";
        }

        formattedTime = formattedTime + minutes + ":";
        if (seconds < 10) {
            formattedTime = formattedTime + "0";
        }

        formattedTime = formattedTime + seconds;
        return formattedTime;
    }

    public String formatTime(long duration) {
        return DurationFormatUtils.formatDuration(duration, (duration >= TimeUnit.HOURS.toMillis(1) ? "HH:" : "") + "mm:ss");
    }

    public long handleParseTime(String input) {
        if (Character.isLetter(input.charAt(0))) {
            return -1L;
        } else {
            long result = 0L;
            StringBuilder number = new StringBuilder();

            for(int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);
                if (Character.isDigit(c)) {
                    number.append(c);
                } else {
                    String str;
                    if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                        result += handleConvert(Integer.parseInt(str), c);
                        number = new StringBuilder();
                    }
                }
            }

            return result;
        }
    }

    private long handleConvert(int value, char charType) {
        switch (charType) {
            case 'M':
                return (long)value * TimeUnit.DAYS.toMillis(30L);
            case 'd':
                return (long)value * TimeUnit.DAYS.toMillis(1L);
            case 'h':
                return (long)value * TimeUnit.HOURS.toMillis(1L);
            case 'm':
                return (long)value * TimeUnit.MINUTES.toMillis(1L);
            case 's':
                return (long)value * TimeUnit.SECONDS.toMillis(1L);
            case 'w':
                return (long)value * TimeUnit.DAYS.toMillis(7L);
            case 'y':
                return (long)value * TimeUnit.DAYS.toMillis(365L);
            default:
                return -1L;
        }
    }

    public String formatTimeSeconds(int seconds) {
        if (seconds <= 0) {
            return "0 seconds";
        } else {
            int minutes = seconds / 60;
            int minutesFrag = seconds % 60;
            seconds %= 60L;
            int hours = minutes / 60;
            int hoursFrag = minutes % 60;
            minutes %= 60L;
            StringBuilder time = new StringBuilder();

            if (hours != 0) {
                time.append(hoursFrag > 0 ? hours + 1 : hours).append("h");
            }

            if (minutes != 0) {
                time.append(minutesFrag > 0 ? minutes + 1 : minutes).append("m");
            }

            if (minutes == 0 && seconds != 0) {
                time.append(seconds).append("s");
            }

            return time.toString().trim();
        }
    }

    public String formatTimeMillis(long millis) {
        long seconds = millis / 1000L;
        if (seconds <= 0L) {
            return "0 seconds";
        } else {
            long minutes = seconds / 60L;
            seconds %= 60L;
            long hours = minutes / 60L;
            minutes %= 60L;
            long day = hours / 24L;
            hours %= 24L;
            long years = day / 365L;
            day %= 365L;
            StringBuilder time = new StringBuilder();
            if (years != 0L) {
                time.append(years).append("y,");
            }

            if (day != 0L) {
                time.append(day).append("d,");
            }

            if (hours != 0L) {
                time.append(hours).append("h,");
            }

            if (minutes != 0L) {
                time.append(minutes).append("m,");
            }

            if (seconds != 0L) {
                time.append(seconds).append("s");
            }

            return time.toString().trim();
        }
    }
}
