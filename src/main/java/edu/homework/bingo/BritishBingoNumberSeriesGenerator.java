package edu.homework.bingo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * British Bingo has special series combination ranges.
 * set 1 has numbers from 1 to 9
 * set 2...8 have numbers from 10...19, 70...79 corresponding
 * set 9 has numbers from 80 to 90, 11 elements
 */
public class BritishBingoNumberSeriesGenerator {

    private final List<List<Integer>> numberSeries;

    public BritishBingoNumberSeriesGenerator() {
        this.numberSeries = generateSeries();
    }

    public List<Queue<Integer>> getNumbersPool() {
        return numberSeries.stream()
                .map(x->shuffleNumbers(x))
                .collect(Collectors.toList());

//        List<Queue<Integer>> newCopy = new ArrayList<>();
//        for (List<Integer> series : numberSeries) {
//            newCopy.add(shuffleNumbers(series));
//        }
//
//        return newCopy;
    }

    Queue<Integer> shuffleNumbers(Collection<Integer> series) {
        LinkedList<Integer> numbers = new LinkedList<>(series);
        Collections.shuffle(numbers);
        return numbers;
    }

    static List<List<Integer>> generateSeries() {
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

    static List<Integer> generateNumberSeries(int begin, int end) {
        return IntStream.rangeClosed(begin, end)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
    }
}