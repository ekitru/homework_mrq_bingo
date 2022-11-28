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
        return new Strip(cards);
    }

    static List<int[][]> createNewStrip(List<Queue<Integer>> numbersPool) {
        List<int[][]> cards = createEmptyCards();
        fillCardsEachColumnByOneNumber(cards, numbersPool);
        fillNumbersRandomly(cards, numbersPool);

        if (!numbersPool.isEmpty()) {
            fillAndSwap(cards, numbersPool);
        }

        sortColumns(cards);
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
        for (int column = 0; column < 9; column++) {
            while (true) {
                final int randomRow = getRandomRow();
                if (countNumbersInRow(card, randomRow) >= 5) {
                    continue;
                }

                card[randomRow][column] = numbersPool.get(column).poll();
                break;
            }
        }
    }

    private static void fillNumbersRandomly(List<int[][]> cards, List<Queue<Integer>> numbersPool) {
        for (int i = 0; i < 50; i++) {
            for (int column = 0; column < COLUMN_NUMBER; column++) {
                int[][] matrix = getCardWithLowestNumberOfNumbers(cards);
                int counter = 0;
                do {
                    int row = getRandomRow();
                    if (isRowAndColumnFullFillRules(column, matrix, row)) {
                        if (matrix[row][column] == 0) {
                            Integer newValue = numbersPool.get(column).poll();
                            if (newValue != null) {
                                matrix[row][column] = newValue;
                            }
                            break;
                        }

                    }
                } while (counter++ < 10);
            }
        }
    }

    private static boolean isRowAndColumnFullFillRules(int column, int[][] matrix, int row) {
        return countNumbersInColumn(matrix, column) < 3
                && countNumbersInRow(matrix, row) < 5
                && countNumbersInCard(matrix) < 15;
    }

    private static int[][] getCardWithLowestNumberOfNumbers(List<int[][]> cards) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (countNumbersInCard(cards.get(i)) < min) {
                min = countNumbersInCard(cards.get(i));
                index = i;
            }
        }

        return cards.get(index);
    }

    private static void fillAndSwap(List<int[][]> cards, List<Queue<Integer>> numbersPool) {
        for (int colNum = 0; colNum < numbersPool.size(); colNum++) {
            Queue<Integer> numbers = numbersPool.get(colNum);
            if (numbers.isEmpty()) {
                continue;
            }

            for (int[][] card : cards) {
                if (countNumbersInCard(card) == 15) {
                    continue; // skip filled cards
                }

                if (card[0][colNum] != 0 && card[1][colNum] != 0 && card[2][colNum] != 0) {
                    continue; // skip cards which does not  suits well to fulfill
                }

                for (int row = 0; row < ROW_NUMBER; row++) {
                    if (card[row][colNum] != 0 && !numbers.isEmpty()) {
                        card[row][colNum] = numbers.poll();
                        break;
                    }
                }
            }
        }
    }

    private static void sortColumns(List<int[][]> cards) {
        for (int[][] card : cards) {
            sortColumns(card);
        }

    }

    private static void sortColumns(int[][] card) {
        //TODO:
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

    private static int countNumbersInRow(int[][] matrix, final int row) {
        int counter = 0;
        for (int column = 0; column < COLUMN_NUMBER; column++) {
            if (matrix[row][column] > 0) {
                counter++;
            }
        }
        return counter;
    }

    private static int countNumbersInColumn(int[][] matrix, final int column) {
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
}
