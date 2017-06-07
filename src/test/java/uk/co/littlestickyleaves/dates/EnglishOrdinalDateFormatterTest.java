package uk.co.littlestickyleaves.dates;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EnglishOrdinalDateFormatterTest {
    @Test
    public void format() throws Exception {
        // arrange
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2016, 2, 3, 10, 0, 0, 0, ZoneId.of("UTC"));
        EnglishOrdinalDateFormatter testObject = EnglishOrdinalDateFormatter.ofPattern("EEEE ddd MMMM");

        // act
        String output = testObject.format(zonedDateTime);

        // assert
        Assert.assertEquals("Wednesday 3rd February", output);
    }
}