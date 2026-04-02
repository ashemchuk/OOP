package ru.ashemchuk.model.game;

import java.util.ArrayList;
import java.util.List;
import ru.ashemchuk.model.level.Level;

/**
 * Builder for constructing {@link Game} instances with a sequence of levels.
 */
public class GameBuilder {
    private List<Level> levels = new ArrayList<>();

    /**
     * Adds a single level to the builder.
     *
     * @param level the level to add
     * @return this builder for chaining
     */
    public GameBuilder level(Level level) {
        this.levels.add(level);
        return this;
    }

    /**
     * Replaces the current level list with the provided list.
     *
     * @param levels the new list of levels
     * @return this builder for chaining
     */
    public GameBuilder levels(List<Level> levels) {
        this.levels = new ArrayList<>(levels);
        return this;
    }

    /**
     * Removes all levels from the builder.
     *
     * @return this builder for chaining
     */
    public GameBuilder clearLevels() {
        this.levels.clear();
        return this;
    }

    /**
     * Constructs a {@link Game} instance with the accumulated levels.
     *
     * @return a new Game instance
     * @throws IllegalStateException if no levels have been added
     */
    public Game build() {
        if (levels.isEmpty()) {
            throw new IllegalStateException("At least one level must be provided");
        }
        return new Game(levels);
    }
}