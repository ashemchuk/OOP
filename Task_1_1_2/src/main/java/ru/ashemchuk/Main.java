package ru.ashemchuk;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Game game = new Game();
        int roundsPlayed = 0;
        do {
            System.out.println("Round " + (++roundsPlayed));
            game.round();
        } while (true);

    }
}
