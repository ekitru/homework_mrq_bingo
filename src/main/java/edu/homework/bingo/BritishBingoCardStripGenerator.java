package edu.homework.bingo;

import edu.homework.bingo.model.Strip;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BritishBingoCardStripGenerator {

    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 9;
    private final BritishBingoNumberSeriesGenerator generator;

    public BritishBingoCardStripGenerator() {
        this(new BritishBingoNumberSeriesGenerator());
    }

    public BritishBingoCardStripGenerator(BritishBingoNumberSeriesGenerator generator) {
        this.generator = generator;
    }

    public Strip generate() {
        List<Queue<Integer>> numbersPool = generator.getNumbersPool();
        List<int[][]> cards = createNewStrip(numbersPool);
        printCards(cards);
        return new Strip(cards);
    }

    static List<int[][]> createNewStrip(List<Queue<Integer>> numbersPool) {
        List<int[][]> cards = createEmptyCards();
        fillCardsEachColumnByOneNumber(cards, numbersPool);
        fillNumbersRandomly(cards, numbersPool);

        return cards;
    }

    private static List<int[][]> createEmptyCards() {
        return IntStream.range(0, 6)
                .mapToObj(x -> new int[ROW_NUMBER][COLUMN_NUMBER])
                .collect(Collectors.toList());
    }

    private static void fillCardsEachColumnByOneNumber(List<int[][]> cards, List<Queue<Integer>> numbersPool) {
        for (int[][] matrix : cards) {
            fillCardEachColumnByOneRandomNumber(matrix, numbersPool);
        }
    }

    private static void fillCardEachColumnByOneRandomNumber(int[][] card, List<Queue<Integer>> numbersPool) {
        for (int col = 0; col < 9; col++) {
            card[getRandomRow()][col] = numbersPool.get(col).poll();
        }
    }

    private static void fillNumbersRandomly(List<int[][]> cards, List<Queue<Integer>> numbersPool) {
        for (int column = numbersPool.size() - 1; column > 0; column--) {
            do {
                int[][] matrix = cards.get(getRandomCard());
                if (countNumbersInCard(matrix) < 15 && countNumbersInColumn(matrix, column) < 3) {
                    putNumberInColumn(matrix, column, numbersPool.get(column).poll());
                }
            } while (!numbersPool.get(column).isEmpty());
        }
    }

    private static void putNumberInColumn(int[][] matrix, int column, Integer number) {
        for (int i = 0; i < ROW_NUMBER; i++) {
            if (matrix[i][column] == 0) {
                matrix[i][column] = number;
                break;
            }
        }
    }


    private static int getRandomCard() {
        return rand(6);
    }

    private static int getRandomRow() {
        return rand(3);
    }

    private static int countNumbersInCard(int[][] matrix) {
        int counter = 0;
        for (int[] row : matrix) {
            for (int number : row) {
                if (number != 0) {
                    counter++;
                }
            }
        }

        return counter;
    }

    private static int countNumbersInColumn(int[][] matrix, int column) {
        int counter = 0;
        for (int row = 0; row < ROW_NUMBER; row++) {
            if (matrix[row][column] > 0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * @param bound the upper bound (exclusive).  Must be positive.
     * @return the next pseudorandom, uniformly distributed {@code int}
     * value between zero (inclusive) and {@code bound} (exclusive)
     * from this random number generator's sequence
     */
    private static int rand(int bound) {
        return new Random().nextInt(bound);
    }

    private static void printCards(List<int[][]> cards) {
        var result = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            int[][] cardMatrix = cards.get(i);
            result.append("\nCARD " + (i + 1) + "\n");
            result.append("-----------------------------------------\n");
            for (int[] row : cardMatrix) {
                result.append("|");
                for (int cell : row) {
                    result.append("\t" + cell);
                }
                result.append("\t|\n");
                result.append("-----------------------------------------\n");
            }
        }

        System.out.println(result);
    }
}
