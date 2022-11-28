package edu.homework.bingo;

import java.util.*;
import java.util.stream.IntStream;

/**
 * British Bingo has special series combination ranges.
 * set 1 has numbers from 1 to 9
 * set 2...8 have numbers from 10...19, 70...79 corresponding
 * set 9 has numbers from 80 to 90, 11 elements
 */
public class BritishBingoNumberSeriesGenerator {

    private final List<int[]> numberSeries;

    public BritishBingoNumberSeriesGenerator() {
        this.numberSeries = generateSeries();
    }

    public List<Queue<Integer>> getNumbersPool() {
        List<Queue<Integer>> list = new ArrayList<>(10);
        for (int[] numbers : numberSeries) {
            Queue<Integer> queue = new PriorityQueue<>();
            for (int number : Arrays.copyOf(numbers, numbers.length)) {
                queue.add(number);
            }
            list.add(queue);
        }

        return list;
    }

    static List<int[]> generateSeries() {
        return List.of(
                generateNumberSeries(1, 9),
                generateNumberSeries(10, 19),
                generateNumberSeries(20, 29),
                generateNumberSeries(30, 39),
                generateNumberSeries(40, 49),
                generateNumberSeries(50, 59),
                generateNumberSeries(60, 69),
                generateNumberSeries(70, 79),
                generateNumberSeries(80, 90)
        );
    }

    static int[] generateNumberSeries(int begin, int end) {
        return IntStream.rangeClosed(begin, end)
                .boxed()
                .mapToInt(i -> i)
                .toArray();
    }
}