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
        fillNumbersRandomly(cards, numbersPool);
        fillAndSwap(cards, numbersPool);
        fillAndSwap(cards, numbersPool);

        sortColumns(cards);
        return cards;
    }

    private static List<int[][]> createEmptyCards() {
        return IntStream.range(0, 6)
                .mapToObj(x -> new int[ROW_NUMBER][COLUMN_NUMBER])
                .collect(Collectors.toList());
    }

    private static void fillNumbersRandomly(List<int[][]> cards, List<Queue<Integer>> numbersPool) {
        for (int i = 0; i < 4; i++) {
            for (int column = COLUMN_NUMBER - 1; column > 0; column--) {
                int[][] matrix = getCardWithLowestNumberOfNumbers(cards);

                int counter = 0;
                do {
                    int randRow = new Random().nextInt(3);
                    if (isRowAndColumnFullFillRules(matrix, randRow, column)) {
                        if (matrix[randRow][column] == 0) {
                            matrix[randRow][column] = numbersPool.get(column).poll();
                            break;
                        }
                    }
                } while (counter++ < 5);
            }
        }
    }

    private static boolean isRowAndColumnFullFillRules(int[][] matrix, int row, int column) {
        return MatrixUtils.countNumbersInColumn(matrix, column) < 2
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

        if (swapRow == -1 || invalidRow == -1) {
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
        for (int col = 0; col < card[0].length; col++) {
            for (int row = 0; row < card.length - 1; row++) {
                int first = card[row][col];
                int second = card[row + 1][col];

                if (first != 0 && second != 0) {
                    card[row][col] = Math.min(first, second);
                    card[row + 1][col] = Math.max(first, second);
                }
            }
        }
    }

}