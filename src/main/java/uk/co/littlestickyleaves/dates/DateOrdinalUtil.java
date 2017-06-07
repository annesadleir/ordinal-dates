package uk.co.littlestickyleaves.dates;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

/**
 * A [class] to provide the following to [whatever]:
 * -- something
 * -- another thing
 */
// TODO fill in JavaDoc properly
public class DateOrdinalUtil {

    private static final String ST = "st";
    private static final String ND = "nd";
    private static final String RD = "rd";
    private static final String TH = "th";

    private static Map<Integer, String> ORDINAL_NOT_TH = new HashMap<>();

    static {
        ORDINAL_NOT_TH.put(1, ST);
        ORDINAL_NOT_TH.put(2, ND);
        ORDINAL_NOT_TH.put(3, RD);
        ORDINAL_NOT_TH.put(21, ST);
        ORDINAL_NOT_TH.put(22, ND);
        ORDINAL_NOT_TH.put(23, RD);
        ORDINAL_NOT_TH.put(31, ST);
    }

    public static String ordinalFromDate(TemporalAccessor temporalAccessor) {
        int dateOfMonth = temporalAccessor.get(ChronoField.DAY_OF_MONTH);
        return String.valueOf(dateOfMonth)
                + ORDINAL_NOT_TH.getOrDefault(dateOfMonth, TH);
    }

}
