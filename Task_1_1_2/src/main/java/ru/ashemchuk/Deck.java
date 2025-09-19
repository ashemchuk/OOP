package ru.ashemchuk;

public class Deck {
    public Card pull() {
        return Card.randomOf();
    }
}