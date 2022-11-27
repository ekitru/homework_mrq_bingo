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

        assert cards.size() != 6;
        this.cards = cards.stream()
                .map(x -> new Card(stripNumber, x))
                .collect(Collectors.toList());
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
}
