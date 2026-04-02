package ru.ashemchuk.model;

/**
 * Represents a point in 2D space with integer coordinates.
 * Used for positions of snakes, food, and other game elements.
 *
 * @param x the x-coordinate (horizontal)
 * @param y the y-coordinate (vertical)
 */
public record Point(int x, int y) {
}
