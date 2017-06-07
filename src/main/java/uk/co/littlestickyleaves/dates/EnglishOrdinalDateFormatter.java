package uk.co.littlestickyleaves.dates;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * Formatter which can replace 'ddd' with a date with English ordinal
 * -- for example, ddd MMM -> 7th Feb
 * -- only does English
 * -- only does formatting, not parsing
 * -- if there's no 'ddd' just passes on to normal DateTimeFormatter
 * -- treats 'dddd' as 'ddd' + 'd', etc, and assumes only one 'ddd' per pattern
 */
public class EnglishOrdinalDateFormatter {

    private static final String THREE_LOWER_CASE_DS = "ddd";

    private final Optional<DateTimeFormatter> noDateOrdinalFormatter;

    private final DateTimeFormatter firstPartFormatter;

    private final DateTimeFormatter secondPartFormatter;

    private EnglishOrdinalDateFormatter(String noDateOrdinalPattern,
                                        String firstPartPattern,
                                        String secondPartPattern) {
        this.noDateOrdinalFormatter = noDateOrdinalPattern.isEmpty() ?
                Optional.empty() :
                Optional.of(DateTimeFormatter.ofPattern(noDateOrdinalPattern));
        this.firstPartFormatter = DateTimeFormatter.ofPattern(firstPartPattern);
        this.secondPartFormatter = DateTimeFormatter.ofPattern(secondPartPattern);
    }

    public static EnglishOrdinalDateFormatter ofPattern(String pattern) {

        int dddIndex = pattern.indexOf(THREE_LOWER_CASE_DS);
        if (dddIndex < 0) {
            return new EnglishOrdinalDateFormatter(pattern, "", "");
        }

        String beginningPattern = pattern.substring(0, dddIndex);
        String endPattern = pattern.substring(dddIndex + THREE_LOWER_CASE_DS.length(), pattern.length());
        return new EnglishOrdinalDateFormatter("", beginningPattern, endPattern);
    }

    public String format(TemporalAccessor temporalAccessor) {
        return noDateOrdinalFormatter.map(dateTimeFormatter -> dateTimeFormatter.format(temporalAccessor))
                .orElseGet(() -> getFormatWithOrdinalDate(temporalAccessor));
    }

    private String getFormatWithOrdinalDate(TemporalAccessor temporalAccessor) {
        return firstPartFormatter.format(temporalAccessor)
                + DateOrdinalUtil.ordinalFromDate(temporalAccessor)
                + secondPartFormatter.format(temporalAccessor);
    }

}

