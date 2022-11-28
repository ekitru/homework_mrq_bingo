package edu.homework.bingo;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BritishBingoSeriesGeneratorTest {

    @Test
    void testGettingNewSeriesCopy() {
        BritishBingoNumberSeriesGenerator generator = new BritishBingoNumberSeriesGenerator();
        List<Queue<Integer>> series1 = generator.getNumbersPool();
        List<Queue<Integer>> series2 = generator.getNumbersPool();
        assertNotEquals(series1, series2);

        assertEquals(9, series1.get(0).size());
        assertDoesNotThrow(() -> series1.get(0).poll());
        assertEquals(8, series1.get(0).size());
    }

    @Test
    void generatedSeriesShouldHaveSequenceOfNumbersFromRange() {
        assertThat(BritishBingoNumberSeriesGenerator.generateNumberSeries(1, 5))
                .hasSize(5)
                .containsOnlyOnce(1, 2, 3, 4, 5)
                .startsWith(1)
                .endsWith(5);
    }


    @Test
    void generatedSeriesShouldHave9setsWithSizes_9_10_11() {
        assertThat(BritishBingoNumberSeriesGenerator.generateSeries())
                .hasSize(9)
                .map(x -> x.length)
                .contains(9, Index.atIndex(0))
                .contains(10, Index.atIndex(2))
                .contains(10, Index.atIndex(7))
                .contains(11, Index.atIndex(8))

        ;
    }

    @Test
    void generatedSeriesAreImmutable() {
        assertThrows(UnsupportedOperationException.class, () -> BritishBingoNumberSeriesGenerator.generateSeries().remove(5));
    }

    /**
     * Non-correct benchmark performance measure
     */
    @Test
    void testPerformanceInSingleThread() {
        int numberOfOperations = 1_000_000;

        var generator = new BritishBingoNumberSeriesGenerator();


        Instant start = Instant.now();
        for (int i = 0; i < numberOfOperations; i++) {
            try {
                generator.getNumbersPool();
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
}