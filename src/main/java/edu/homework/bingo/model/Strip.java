package edu.homework.bingo.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Strip {

    private static final AtomicInteger series = new AtomicInteger();

    private final int stripNumber;
    private final List<Card> cards;

    public Strip(List<int[][]> cards) {
        this.stripNumber = series.getAndIncrement();

        this.cards = cards.stream()
                .map(x -> new Card(stripNumber, x))
                .collect(Collectors.toList());
    }

    private static void printCard(Card card) {
        int[][] numbers = card.getNumbers();
        System.out.println("----------------------------------------------------------------");
        for (int[] row : numbers) {
            System.out.format("|");
            for (int cell : row) {
                System.out.printf("%4s  |", cell != 0 ? cell : "");
            }
            System.out.println("\n----------------------------------------------------------------");
        }
    }

    public int getStripNumber() {
        return stripNumber;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Strip{" +
                "stripNumber=" + stripNumber +
                ", cards=" + cards +
                '}';
    }

    public void print() {
        System.out.println("Bingo strip nr. " + this.getStripNumber());
        for (int i = 0; i < this.getCards().size(); i++) {
            System.out.printf("\nCARD %d:%n", (i + 1));
            printCard(this.getCards().get(i));
        }
    }
}
