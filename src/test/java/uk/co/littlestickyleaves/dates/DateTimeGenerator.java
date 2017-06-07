package uk.co.littlestickyleaves.dates;

import com.google.common.collect.Lists;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Generates a random TemporalAccessor
 * -- could be a ZonedDateTime or a LocalDateTime
 * -- value will be a random minute in 2011 or 2012
 */
public class DateTimeGenerator implements Supplier<TemporalAccessor> {

    private static final long START_OF_2011 = 1293840001000L;
    private static final int MINUTES_IN_731_DAYS = 60 * 24 * 731;
    private static final ZoneId UTC = ZoneId.of("UTC");

    private static final List<Function<Long, TemporalAccessor>> DATE_TIME_CREATORS = Lists.newArrayList(
            millis -> LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), UTC),
            millis -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), UTC));

    private Random random;

    public DateTimeGenerator() {
        random = new Random();
    }

    public TemporalAccessor get() {
        int whichTemporalAccessor = random.nextInt(DATE_TIME_CREATORS.size());
        int minuteIn2011or2012 = random.nextInt(MINUTES_IN_731_DAYS);
        long millisIn2011or2012 = 1000L * 60 * minuteIn2011or2012 + START_OF_2011;
        return DATE_TIME_CREATORS.get(whichTemporalAccessor).apply(millisIn2011or2012);
    }

}
