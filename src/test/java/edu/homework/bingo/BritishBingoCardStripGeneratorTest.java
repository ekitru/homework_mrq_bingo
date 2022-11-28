package edu.homework.bingo;

import edu.homework.bingo.model.Card;
import edu.homework.bingo.model.Strip;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BritishBingoCardStripGeneratorTest {

    @Test
    void cardShouldHave15Numbers() {
        new BritishBingoCardStripGenerator().generate().getCards().forEach(this::assertCardSize);
    }

    private void assertCardSize(Card card) {
        assertEquals(15, MatrixUtils.countNumbersInCard(card.getNumbers()), " Each card should have always exactly 15 numbers");
    }

    @Test
    void cardShouldHaveAtLeast1NumberInEachColumn() {
        new BritishBingoCardStripGenerator().generate().getCards().forEach(this::assertColumnSizeInCard);
    }

    private void assertColumnSizeInCard(Card card) {
        for (int col = 0; col < card.getNumbers()[0].length; col++) {
            assertTrue(MatrixUtils.countNumbersInColumn(card.getNumbers(), col) > 0);
        }
    }

    @Test
    void cardShouldHave5numbersInEachRow() {
        new BritishBingoCardStripGenerator().generate().getCards().forEach(this::assertRowSizeInCard);
    }

    private void assertRowSizeInCard(Card card) {
        for (int row = 0; row < card.getNumbers().length; row++) {
            assertEquals(5, MatrixUtils.countNumbersInRow(card.getNumbers(), row), "only 5 numbers in row allowed");
        }
    }

    @Test
    void stripeSizeShouldHave90numbersFrom0to90() {
        Strip strip = new BritishBingoCardStripGenerator().generate();
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
        System.out.format("Min:\t%s, Max:\t%s, Count:\t%s\n", min, max, counter);

        assertEquals(1, min, "Minimal number value is 1");
        assertEquals(90, max, "Maximum number value is 90");
        assertEquals(90, counter, "90 values should be present in one stripe");
    }

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

    IntSummaryStatistics findStats(int[][] array) {
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .boxed()
                .filter(x -> x > 0)
                .collect(Collectors.summarizingInt(value -> value));
    }
}