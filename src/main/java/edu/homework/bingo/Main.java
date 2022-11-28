package edu.homework.bingo;

import edu.homework.bingo.model.Card;
import edu.homework.bingo.model.Strip;

public class Main {

    public static void main(String[] args) {
        System.out.println("Super Bingo Generator !!!");

        BritishBingoCardStripGenerator stripGenerator = new BritishBingoCardStripGenerator();
        Strip strip = stripGenerator.generate();

        strip.print();
    }
}