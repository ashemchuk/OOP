package ru.ashemchuk.model.food;

import java.util.List;
import java.util.Random;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.level.Level;

/**
 * Spawns food items based on level specifications and weighted random selection.
 */
public class FoodSpawner {
    private final Random random = new Random();

    /**
     * Spawns a food item at the given position according to the level's food specifications.
     * Uses weighted random selection based on the weights defined in {@link FoodSpec}.
     * If the level has no food specifications, defaults to an apple.
     *
     * @param position the position where the food should appear
     * @param level    the level containing food specifications
     * @return a newly created food item
     */
    public Food spawn(Point position, Level level) {
        List<FoodSpec> specs = level.foodSpecs();
        if (specs.isEmpty()) {
            // fallback to apple
            return new AppleFood(position);
        }
        // weighted random selection
        int totalWeight = specs.stream().mapToInt(FoodSpec::weight).sum();
        int roll = random.nextInt(totalWeight);
        int cumulative = 0;
        for (FoodSpec spec : specs) {
            cumulative += spec.weight();
            if (roll < cumulative) {
                return createFood(spec.type(), position);
            }
        }
        // should not reach here
        return new AppleFood(position);
    }

    private Food createFood(FoodSpec.FoodType type, Point position) {
        return switch (type) {
            case APPLE -> new AppleFood(position);
            case GRAPES -> new GrapesFood(position);
            case POISON -> new PoisonFood(position);
        };
    }
}