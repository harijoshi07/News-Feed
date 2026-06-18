package com.example.newsfeed.ui.headlines;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class PublicationTimeFormatter {

    private static final long MINUTE_MILLIS = 60_000L;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    private PublicationTimeFormatter() {
    }

    public static String format(String publishedAt) {
        Date publicationDate = parseUtcDate(publishedAt);
        if (publicationDate == null) {
            return "";
        }

        long elapsedMillis = System.currentTimeMillis() - publicationDate.getTime();
        if (elapsedMillis < MINUTE_MILLIS) {
            return "Just now";
        }

        if (elapsedMillis < HOUR_MILLIS) {
            long minutes = elapsedMillis / MINUTE_MILLIS;
            return minutes == 1 ? "1 min ago" : minutes + " mins ago";
        }

        if (elapsedMillis < DAY_MILLIS) {
            long hours = elapsedMillis / HOUR_MILLIS;
            return hours == 1 ? "1 hour ago" : hours + " hours ago";
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yy", Locale.US);
        displayFormat.setTimeZone(TimeZone.getDefault());
        return displayFormat.format(publicationDate);
    }

    private static Date parseUtcDate(String publishedAt) {
        if (publishedAt == null || publishedAt.trim().isEmpty()) {
            return null;
        }

        String[] patterns = {
                "yyyy-MM-dd'T'HH:mm:ssX",
                "yyyy-MM-dd'T'HH:mm:ss.SSSX"
        };

        for (String pattern : patterns) {
            SimpleDateFormat parser = new SimpleDateFormat(pattern, Locale.US);
            parser.setLenient(false);
            parser.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                return parser.parse(publishedAt);
            } catch (ParseException ignored) {
            }
        }

        return null;
    }
}
