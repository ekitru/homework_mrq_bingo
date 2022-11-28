package edu.homework.bingo;

public class MatrixUtils {
    public static int countNumbersInCard(int[][] matrix) {
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

    public static int countNumbersInRow(int[][] matrix, final int row) {
        int counter = 0;
        for (int column = 0; column < matrix[0].length; column++) {
            if (matrix[row][column] > 0) {
                counter++;
            }
        }
        return counter;
    }

    public static int countNumbersInColumn(int[][] matrix, final int column) {
        int counter = 0;
        for (int[] row : matrix) {
            if (row[column] > 0) {
                counter++;
            }
        }
        return counter;
    }
}