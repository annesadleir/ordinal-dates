package uk.co.littlestickyleaves.dates;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Generates a random formatter pattern, which may or may not include a 'ddd'
 * -- creates patterns from random concatenations of pattern blocks
 */
public class FormatterPatternGenerator implements Supplier<String> {

    private static final String DDD = "ddd";

    private static final List<String> PATTERN_BLOCKS = Lists.newArrayList("YYYY", "yyyy",
            "dd", "d", "MM", "mm", "M", "EEEE", "hh", "HH", "a", "Q", "w", "ss");

    private final Random random = new Random();

    @Override
    public String get() {
        String result;
        if (random.nextBoolean()) {
            result = randomPattern() + DDD + " " + randomPattern();
        } else {
            result = randomBlock() + " " + randomPattern();
        }
        return result.trim();
    }

    private String randomPattern() {
        int randomInt = random.nextInt(100);
        if (randomInt < 30) {
            return "";
        } else {
            return randomBlock() + " " + randomPattern();
        }
    }

    private String randomBlock() {
        return PATTERN_BLOCKS.get(random.nextInt(PATTERN_BLOCKS.size()));
    }
}
