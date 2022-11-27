package edu.homework.bingo;

import edu.homework.bingo.model.Card;
import edu.homework.bingo.model.Strip;

public class Main {


    public static void main(String[] args) {
        System.out.println("Super Bingo Generator !!!");

        BritishBingoCardStripGenerator stripGenerator = new BritishBingoCardStripGenerator();
        Strip strip = stripGenerator.generate();

        printStrip(strip);
    }

    private static void printStrip(Strip strip) {
        System.out.println("Bingo strip nr. " + strip.getStripNumber());
        for (int i = 0; i < strip.getCards().size(); i++) {
            System.out.printf("CARD %d:%n", (i + 1));
            printCard(strip.getCards().get(i));
        }
    }

    private static void printCard(Card card) {
        var result = new StringBuilder();

        int[][] numbers = card.getNumbers();
        result.append("-------------------------------------------------------------------------\n");
        for (int[] row : numbers) {
            result.append("|");
            for (int cell : row) {
                result.append("\t").append(cell != 0 ? cell : "").append("\t|");
            }
            result.append("\n-------------------------------------------------------------------------\n");
        }

        System.out.println(result);
    }
}