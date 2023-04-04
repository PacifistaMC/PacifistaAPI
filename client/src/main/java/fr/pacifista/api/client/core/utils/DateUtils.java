package fr.pacifista.api.client.core.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateUtils {

    public static String dateToFrenchFormat(final Instant instant) {
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.FRANCE)
                .withZone(ZoneId.from(ZoneOffset.UTC));

        return formatter.format(instant);
    }

}
