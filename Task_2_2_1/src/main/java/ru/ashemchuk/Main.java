package ru.ashemchuk;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Pizzeria p = new Pizzeria();
        p.configure("src/main/resources/config.json");
        p.startSimulate();
    }
}
