package ru.ashemchuk.model.game;

import java.util.List;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;
import ru.ashemchuk.model.level.Level;

/**
 * Immutable record representing the state of the game at a given moment.
 * Contains positions of the player snake, robot snakes, food items, score,
 * game-over status, win status, and the current level.
 *
 * @param playerSnakePositions positions of the player snake's body segments
 * @param robotSnakePositions  list of positions for each robot snake
 * @param foods                list of food items currently on the field
 * @param score                current player score
 * @param gameOver             true if the game is over
 * @param win                  true if the player has won the current level
 * @param currentLevel         the level currently being played
 */
public record GameState(
    List<Point> playerSnakePositions,
    List<List<Point>> robotSnakePositions,
    List<Food> foods,
    int score,
    boolean gameOver,
    boolean win,
    Level currentLevel
) {
    /**
     * Creates a GameState snapshot from a live Game instance.
     *
     * @param game the game to snapshot
     * @return a new GameState representing the current state of the game
     */
    public static GameState fromGame(Game game) {
        return new GameState(
            game.getPlayerSnake().getBody().stream().toList(),
            game.getSnakes().stream()
                .skip(1) // skip player
                .map(snake -> snake.getBody().stream().toList())
                .toList(),
            game.getFoods(),
            game.getScore(),
            game.isGameOver(),
            game.isWin(),
            game.getCurrentLevel()
        );
    }

    /**
     * Returns the player snake positions (for backward compatibility).
     * This method exists because older code may call {@code snakePositions()}
     * expecting the player's snake.
     *
     * @return player snake positions
     */
    public List<Point> snakePositions() {
        return playerSnakePositions;
    }

    /**
     * Returns a list of positions of all food items (for convenience).
     *
     * @return list of food positions
     */
    public List<Point> foodPositions() {
        return foods.stream().map(Food::getPosition).toList();
    }
}