package edu.homework.bingo;

import edu.homework.bingo.model.Card;
import edu.homework.bingo.model.Strip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

class BritishBingoCardStripGeneratorTest {

    /**
     * Non-correct benchmark performance measure
     */
    @Test
    void testPerformanceInSingleThread() {
//        int numberOfOperations = 10_000;
        int numberOfOperations = 1000;

        var generator = new BritishBingoCardStripGenerator();

        Instant start = Instant.now();
        for (int i = 0; i < numberOfOperations; i++) {
            try {
                generator.generate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Instant end = Instant.now();
        long mills = Duration.between(start, end).toMillis();

        System.out.println("===================");
        System.out.println("Performance: " + mills + " ms. (" + numberOfOperations / mills + " operation per ms)");
        System.out.println("===================");
    }

    @Test
    void stripeSizeShouldHave90numbersFrom0to90() {
        var generator = new BritishBingoCardStripGenerator();
        Strip strip = generator.generate();

        List<IntSummaryStatistics> collect = strip.getCards().stream()
                .map(Card::getNumbers)
                .map(this::findStats)
                .toList();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int counter = 0;

        for (IntSummaryStatistics stats : collect) {
            min = Math.min(min, stats.getMin());
            max = Math.max(max, stats.getMax());

            counter += stats.getCount();
        }

        strip.print();
        System.out.format("Min:\t%s, Max:\t%s\n", min, max);


        Assertions.assertEquals(90, counter, "90 values should be present in one stripe");
        Assertions.assertEquals(1, min);
        Assertions.assertEquals(90, max);
    }

    IntSummaryStatistics findStats(int[][] array) {
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .boxed()
                .filter(x -> x > 0)
                .collect(Collectors.summarizingInt(value -> value));
    }
}