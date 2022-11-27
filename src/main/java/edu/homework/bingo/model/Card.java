package edu.homework.bingo.model;

import java.util.Arrays;

public class Card {

    private final int stripNumber;
    private final int[][] numbers;

    public Card(int stripNumber, int[][] numbers) {
        this.stripNumber = stripNumber;
        this.numbers = numbers;
    }

    public int getStripNumber() {
        return stripNumber;
    }

    public int[][] getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return "Card{" +
                "stripNumber=" + stripNumber +
                ", card=" + Arrays.deepToString(numbers) +
                '}';
    }
}
