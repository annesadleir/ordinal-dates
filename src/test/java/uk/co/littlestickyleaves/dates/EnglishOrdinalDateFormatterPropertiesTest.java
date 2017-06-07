package uk.co.littlestickyleaves.dates;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.Assert.*;

public class EnglishOrdinalDateFormatterPropertiesTest {

    private static final int testsToRun = 1000000;

    private DateTimeGenerator dateTimeGenerator = new DateTimeGenerator();
    private FormatterPatternGenerator formatterPatternGenerator = new FormatterPatternGenerator();

    private static final List<String> ILLEGAL_STRINGS = Lists.newArrayList(" 1th", " 2th", " 3th",
            "11st", "12nd", "13rd", "21th", "22th", "23th", "31th");

    @Test
    public void xRandomCalls() throws Exception {
        for (int i = 0; i < testsToRun; i++) {

            // arrange
            TemporalAccessor temporalAccessor = dateTimeGenerator.get();
            String pattern = formatterPatternGenerator.get();

            // act
            EnglishOrdinalDateFormatter testObject = EnglishOrdinalDateFormatter.ofPattern(pattern);
            String formatted = testObject.format(temporalAccessor);

            // assert
            assertNoIllegalStrings(formatted, temporalAccessor, pattern);
            assertIfDddPresentOutputContainsDateInt(formatted, temporalAccessor, pattern);
            assertIfDddPresentOutputContainsAnOrdinalString(formatted, temporalAccessor, pattern);
            assertIfDddAbsentOutputSameAsFromDateTimeFormatter(formatted, temporalAccessor, pattern);
            assertIfDddPresentOutputLongerThanDdEquivalent(formatted, temporalAccessor, pattern);
        }
    }

    private void assertIfDddPresentOutputLongerThanDdEquivalent(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        if (pattern.contains("ddd")) {
            String newPattern = pattern.replace("ddd", "dd");
            String newPatternOutput = EnglishOrdinalDateFormatter.ofPattern(newPattern).format(temporalAccessor);
            int diffInLength = temporalAccessor.get(ChronoField.DAY_OF_MONTH) < 10 ? 1 : 2;
            assertEquals("When pattern contains 'ddd' then output should be " + diffInLength + " longer than if it had 'dd': " +
                            formatTestVariables(formatted, temporalAccessor, pattern),
                    newPatternOutput.length() + diffInLength, formatted.length());
        }
    }

    private void assertIfDddAbsentOutputSameAsFromDateTimeFormatter(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        if (!pattern.contains("ddd")) {
            String standardOutput = DateTimeFormatter.ofPattern(pattern).format(temporalAccessor);
            assertEquals("When pattern does not contain 'ddd' then output should be same as standard DateTimeFormatter: " +
                            formatTestVariables(formatted, temporalAccessor, pattern),
                    standardOutput, formatted);
        }
    }

    private void assertIfDddPresentOutputContainsAnOrdinalString(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        if (pattern.contains("ddd")) {
            assertTrue("When pattern contains 'ddd' then output must contain 'st', 'nd', 'rd' or 'th" +
                            formatTestVariables(formatted, temporalAccessor, pattern),
                    Stream.of("st", "nd", "rd", "th")
                            .anyMatch(formatted::contains));
        }
    }

    private void assertIfDddPresentOutputContainsDateInt(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        if (pattern.contains("ddd")) {
            int dateInt = temporalAccessor.get(ChronoField.DAY_OF_MONTH);
            assertTrue("When pattern contains 'ddd' then output must contain the day of the month: " +
                            formatTestVariables(formatted, temporalAccessor, pattern),
                    formatted.contains(String.valueOf(dateInt)));
        }
    }

    private void assertNoIllegalStrings(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        assertFalse("There's an illegal String in " + formatted + ", generated from " + pattern +
                        " with date time " + ISO_LOCAL_DATE_TIME.format(temporalAccessor),
                ILLEGAL_STRINGS.stream()
                        .anyMatch(formatted::contains));
    }

    private String formatTestVariables(String formatted, TemporalAccessor temporalAccessor, String pattern) {
        return "output=" + formatted + "; dateTime=" + ISO_LOCAL_DATE_TIME.format(temporalAccessor) + "; pattern=" + pattern;
    }
}