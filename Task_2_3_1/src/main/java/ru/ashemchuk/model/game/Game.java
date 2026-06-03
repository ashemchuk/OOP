package ru.ashemchuk.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ru.ashemchuk.model.Direction;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.Food;
import ru.ashemchuk.model.food.FoodSpawner;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.RobotSpec;
import ru.ashemchuk.model.snake.FollowPlayerRobot;
import ru.ashemchuk.model.snake.PlayerSnake;
import ru.ashemchuk.model.snake.RandomRobot;
import ru.ashemchuk.model.snake.Snake;

/**
 * Core game logic class. Manages the game state, including snakes, food, collisions,
 * level progression, and win/lose conditions.
 */
public class Game {
    private final List<Level> levels;
    private int currentLevelIndex;
    private Level currentLevel;
    private final List<Snake> snakes;
    private PlayerSnake playerSnake;
    private final List<Food> foods;
    private boolean gameOver;
    private boolean win;
    private int score;
    private final Random random = new Random();
    private final FoodSpawner foodSpawner = new FoodSpawner();

    public Game(Level level) {
        this(List.of(level));
    }

    /**
     * Constructs a game with the given list of levels.
     *
     * @param levels the levels to play; must not be empty
     * @throws IllegalArgumentException if levels is empty
     */
    public Game(List<Level> levels) {
        if (levels.isEmpty()) {
            throw new IllegalArgumentException("Levels list cannot be empty");
        }
        this.levels = new ArrayList<>(levels);
        this.currentLevelIndex = 0;
        this.currentLevel = levels.get(0);
        this.foods = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.gameOver = false;
        this.win = false;
        this.score = 0;
        initialize();
    }

    private void initialize() {
        // Create player snake at center of the field
        int startX = currentLevel.width() / 2;
        int startY = currentLevel.height() / 2;
        Point start = new Point(startX, startY);
        playerSnake = new PlayerSnake(start, currentLevel.snakeSpeed());
        snakes.add(playerSnake);

        // Create robots according to level configuration
        for (RobotSpec spec : currentLevel.robotSpecs()) {
            Point robotStart = new Point(startX + spec.dx(), startY + spec.dy());
            Snake robot = switch (spec.type()) {
                case FOLLOW_PLAYER ->
                    new FollowPlayerRobot(robotStart, currentLevel.snakeSpeed(), playerSnake,
                        currentLevel);
                case RANDOM -> new RandomRobot(robotStart, currentLevel.snakeSpeed(), currentLevel);
            };
            snakes.add(robot);
        }

        // Spawn initial food
        for (int i = 0; i < currentLevel.foodAmount(); i++) {
            spawnFood();
        }
    }

    /**
     * Advances the game by one tick.
     * Moves all snakes, checks collisions, updates score, and determines win/lose state.
     * If the game is already over or won, does nothing.
     */
    public void update() {
        if (gameOver || win) {
            return;
        }

        // Update temporary speed boosts (check expiration)
        for (Snake snake : snakes) {
            snake.updateBoost();
        }

        // Store previous heads for crossing detection
        java.util.Map<Snake, Point> previousHeads = new java.util.HashMap<>();
        for (Snake snake : snakes) {
            previousHeads.put(snake, snake.getBody().get(0));
        }

        // Move all snakes
        for (Snake snake : snakes) {
            snake.move();
        }

        // Check collisions for player snake
        if (isOutOfBounds(playerSnake.getBody().get(0))) {
            gameOver = true;
            return;
        }
        if (isSelfCollision(playerSnake)) {
            gameOver = true;
            return;
        }
        // Check collision with other snakes (player head vs robot body)
        if (isCollisionWithOtherSnakes(playerSnake)) {
            gameOver = true;
            return;
        }

        // Check crossing collisions (head swapping)
        checkCrossCollisions(previousHeads);

        // Check robot collisions (robot head vs player body)
        checkRobotCollisions();

        // Check food collision for all snakes
        checkFoodCollision();

        // Check win condition (player length)
        if (playerSnake.getBody().size() >= currentLevel.snakeLengthFinal()) {
            win = true;
        }
    }

    /**
     * Checks whether there is a next level after the current one.
     *
     * @return true if there is at least one more level, false otherwise
     */
    public boolean hasNextLevel() {
        return currentLevelIndex + 1 < levels.size();
    }

    /**
     * Advances to the next level, if available.
     *
     * @return true if the level was advanced, false if there is no next level
     */
    public boolean nextLevel() {
        if (!hasNextLevel()) {
            return false;
        }
        currentLevelIndex++;
        currentLevel = levels.get(currentLevelIndex);
        resetForLevel();
        return true;
    }

    /**
     * Jumps to the specified level index, resetting the game state for that level.
     *
     * @param index the zero‑based index of the level to switch to
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public void setLevel(int index) {
        if (index < 0 || index >= levels.size()) {
            throw new IllegalArgumentException("Invalid level index: " + index);
        }
        if (index == currentLevelIndex) {
            // Already at this level, just reset
            resetForLevel();
            return;
        }
        currentLevelIndex = index;
        currentLevel = levels.get(index);
        resetForLevel();
    }

    private void resetForLevel() {
        snakes.clear();
        foods.clear();
        gameOver = false;
        win = false;
        initialize();
    }

    private boolean isOutOfBounds(Point point) {
        return point.x() < 0 || point.x() >= currentLevel.width()
            || point.y() < 0 || point.y() >= currentLevel.height();
    }

    private boolean isSelfCollision(Snake snake) {
        Point head = snake.getBody().get(0);
        // Check if head collides with any other segment of the same snake
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollisionWithOtherSnakes(Snake snake) {
        Point head = snake.getBody().get(0);
        for (Snake other : snakes) {
            if (other == snake) {
                continue;
            }
            for (Point segment : other.getBody()) {
                if (head.equals(segment)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkCrossCollisions(java.util.Map<Snake, Point> previousHeads) {
        // Check for head swapping between any two snakes
        List<Snake> snakeList = new java.util.ArrayList<>(snakes);
        for (int i = 0; i < snakeList.size(); i++) {
            Snake a = snakeList.get(i);
            if (!snakes.contains(a)) {
                continue; // may have been removed
            }
            Point anPrev = previousHeads.get(a);
            Point anCurr = a.getBody().get(0);
            for (int j = i + 1; j < snakeList.size(); j++) {
                Snake b = snakeList.get(j);
                if (!snakes.contains(b)) {
                    continue;
                }
                Point bePrev = previousHeads.get(b);
                Point beCurr = b.getBody().get(0);
                // Check if a's current head equals b's previous head and vice versa
                if (anCurr.equals(bePrev) && beCurr.equals(anPrev)) {
                    // Crossing detected
                    handleCrossing(a, b);
                    // No need to continue checking for these snakes
                    break;
                }
            }
        }
    }

    private void handleCrossing(Snake a, Snake b) {
        // Determine if player is involved
        boolean anIsPlayer = a == playerSnake;
        boolean beIsPlayer = b == playerSnake;
        if (anIsPlayer || beIsPlayer) {
            // Player dies
            gameOver = true;
            // Remove the robot(s) involved
            if (!anIsPlayer) {
                snakes.remove(a);
            }
            if (!beIsPlayer) {
                snakes.remove(b);
            }
        } else {
            // Both are robots, remove both
            snakes.remove(a);
            snakes.remove(b);
        }
    }

    private void checkRobotCollisions() {
        // Iterate over robots (all snakes except player)
        java.util.Iterator<Snake> it = snakes.iterator();
        while (it.hasNext()) {
            Snake snake = it.next();
            if (snake == playerSnake) {
                continue; // skip player
            }
            Point head = snake.getBody().get(0);
            // Check collision with player's body
            // (excluding player's head? we can include head as well)
            for (Point segment : playerSnake.getBody()) {
                if (head.equals(segment)) {
                    // Robot collided with player -> robot dies
                    it.remove();
                    score += 5; // bonus for killing a robot
                    break;
                }
            }
        }
    }

    private void checkFoodCollision() {
        // For each snake, check collision with foods
        for (Snake snake : snakes) {
            Point head = snake.getBody().get(0);
            Food toRemove = null;
            for (Food food : foods) {
                if (head.equals(food.getPosition())) {
                    snake.eat(food);
                    food.eat();
                    toRemove = food;
                    if (snake == playerSnake) {
                        score++;
                    }
                    break;
                }
            }
            if (toRemove != null) {
                foods.remove(toRemove);
                spawnFood();
                // Only one food eaten per update (simplification)
                break;
            }
        }
    }

    private void spawnFood() {
        // Generate random position not occupied by any snake or existing food
        Point pos;
        int attempts = 0;
        do {
            int x = random.nextInt(currentLevel.width());
            int y = random.nextInt(currentLevel.height());
            pos = new Point(x, y);
            attempts++;
            if (attempts > 100) {
                // Avoid infinite loop
                return;
            }
        } while (isOccupied(pos));

        // Use FoodSpawner to create a random food based on level's foodSpecs
        Food food = foodSpawner.spawn(pos, currentLevel);
        food.post();
        foods.add(food);
    }

    private boolean isOccupied(Point point) {
        // Check all snake bodies
        for (Snake snake : snakes) {
            for (Point p : snake.getBody()) {
                if (p.equals(point)) {
                    return true;
                }
            }
        }
        // Check existing food
        for (Food f : foods) {
            if (f.getPosition().equals(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the direction of the player snake.
     *
     * @param direction the new direction to set
     */
    public void changeDirection(Direction direction) {
        playerSnake.turn(direction);
    }

    /**
     * Returns the player snake.
     *
     * @return the player snake instance
     */
    public PlayerSnake getPlayerSnake() {
        return playerSnake;
    }

    /**
     * Returns an unmodifiable list of all snakes (player and robots) in the game.
     *
     * @return list of snakes
     */
    public List<Snake> getSnakes() {
        return Collections.unmodifiableList(snakes);
    }

    /**
     * Returns an unmodifiable list of all food items currently on the field.
     *
     * @return list of food items
     */
    public List<Food> getFoods() {
        return Collections.unmodifiableList(foods);
    }

    /**
     * Returns whether the game is over (player died).
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns whether the player has won the current level.
     *
     * @return true if the player has reached the target length, false otherwise
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Returns the current player score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current level configuration.
     *
     * @return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the zero‑based index of the current level.
     *
     * @return current level index
     */
    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    /**
     * Creates a snapshot of the current game state.
     *
     * @return an immutable {@link GameState} object representing the current game
     */
    public GameState getState() {
        // Include player snake positions and robot snake positions
        List<List<Point>> robotPositions = snakes.stream()
            .skip(1) // skip player
            .map(snake -> snake.getBody().stream().toList())
            .toList();
        return new GameState(
            playerSnake.getBody().stream().toList(),
            robotPositions,
            foods, // pass List<Food> directly
            score,
            gameOver,
            win,
            currentLevel
        );
    }
}