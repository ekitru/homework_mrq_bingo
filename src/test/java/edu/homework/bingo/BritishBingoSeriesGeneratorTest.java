package edu.homework.bingo;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

class BritishBingoSeriesGeneratorTest {

    @Test
    void testGettingNewSeriesCopy() {
        BritishBingoNumberSeriesGenerator generator = new BritishBingoNumberSeriesGenerator();
        List<Queue<Integer>> series1 = generator.getNumbersPool();
        List<Queue<Integer>> series2 = generator.getNumbersPool();
        Assertions.assertNotEquals(series1, series2);

        Assertions.assertEquals(9, series1.get(0).size());
        Assertions.assertDoesNotThrow(() -> series1.get(0).poll());
        Assertions.assertEquals(8, series1.get(0).size());
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
    void generatedNumbersSeriesAreImmutable() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            BritishBingoNumberSeriesGenerator.generateNumberSeries(1, 6).add(123);
            BritishBingoNumberSeriesGenerator.generateNumberSeries(1, 6).remove(123);
        });
    }

    @Test
    void generatedSeriesShouldHave9setsWithSizes_9_10_11() {
        assertThat(BritishBingoNumberSeriesGenerator.generateSeries())
                .hasSize(9)
                .map(x -> x.size())
                .contains(9, Index.atIndex(0))
                .contains(10, Index.atIndex(2))
                .contains(10, Index.atIndex(7))
                .contains(11, Index.atIndex(8))

        ;
    }

    @Test
    void generatedSeriesAreImmutable() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            BritishBingoNumberSeriesGenerator.generateSeries().remove(5);
        });
    }
}