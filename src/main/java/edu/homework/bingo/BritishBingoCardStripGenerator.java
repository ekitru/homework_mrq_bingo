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

    static List<int[][]> createNewStrip(List<Queue<Integer>> numbersPool) {
        List<int[][]> cards = createEmptyCards();
        fillCardsEachColumnByOneNumber(cards, numbersPool);
        fillNumbersRandomly(cards, numbersPool);
        fillAndSwap(cards, numbersPool);

//        sortColumns(cards);
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
                if (MatrixUtils.countNumbersInRow(card, randomRow) >= 5) {
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
        return MatrixUtils.countNumbersInColumn(matrix, column) < 3
                && MatrixUtils.countNumbersInRow(matrix, row) < 5
                && MatrixUtils.countNumbersInCard(matrix) < 15;
    }

    private static int[][] getCardWithLowestNumberOfNumbers(List<int[][]> cards) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (MatrixUtils.countNumbersInCard(cards.get(i)) < min) {
                min = MatrixUtils.countNumbersInCard(cards.get(i));
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
                if (isColumnFull(card, colNum) || MatrixUtils.countNumbersInCard(card) == 15) {
                    continue; // skip filled cards
                }

                for (int row = 0; row < ROW_NUMBER; row++) {
                    if (card[row][colNum] == 0 && !numbers.isEmpty()) {
                        card[row][colNum] = numbers.poll();

                        normalizeRowSize(card);
                        break;
                    }
                }
            }
        }
    }

    private static void normalizeRowSize(int[][] card) {
        int swapRow = -1;
        int invalidRow = -1;

        for (int rowNumber = 0; rowNumber < card.length; rowNumber++) {
            if (MatrixUtils.countNumbersInRow(card, rowNumber) < 5) {
                swapRow = rowNumber;
            }

            if (MatrixUtils.countNumbersInRow(card, rowNumber) > 5) {
                invalidRow = rowNumber;
            }
        }

        if (swapRow == -1 && invalidRow == -1) {
            return;
        }

        for (int colNumber = 0; colNumber < card[0].length; colNumber++) {
            if (card[invalidRow][colNumber] != 0 && card[swapRow][colNumber] == 0) {
                int temp = card[invalidRow][colNumber];
                card[swapRow][colNumber] = temp;
                card[invalidRow][colNumber] = 0;
                break;
            }
        }
    }

    private static boolean isColumnFull(int[][] card, int columnNumber) {
        return card[0][columnNumber] != 0 && card[1][columnNumber] != 0 && card[2][columnNumber] != 0;
    }

    private static void sortColumns(List<int[][]> cards) {
        for (int[][] card : cards) {
            sortColumns(card);
        }

    }

    private static void sortColumns(int[][] card) {
        //TODO: need like "bubble sort" simple implementation
    }

    private static int getRandomRow() {
        return rand(3);
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

    public Strip generate() {
        List<Queue<Integer>> numbersPool = generator.getNumbersPool();
        List<int[][]> cards = createNewStrip(numbersPool);
        return new Strip(cards);
    }
}
