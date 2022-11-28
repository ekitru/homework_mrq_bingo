package edu.homework.bingo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixUtilsTest {

    @Test
    void testCountingNumbersInMatrix() {
        int[][] matrix = {{0, 1, 1}, {3, 3, 5}};
        assertEquals(5, MatrixUtils.countNumbersInCard(matrix));
    }

    @Test
    void testCountingNumbersInRowMatrix() {
        int[][] matrix = {{0, 1, 1}, {3, 3, 5}};
        assertEquals(3, MatrixUtils.countNumbersInRow(matrix, 1));
    }

    @Test
    void testCountingNumbersInColumnMatrix() {
        int[][] matrix = {{0, 1, 1}, {3, 0, 5}};
        assertEquals(1, MatrixUtils.countNumbersInColumn(matrix, 0));
        assertEquals(2, MatrixUtils.countNumbersInColumn(matrix, 2));
    }
}