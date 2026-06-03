package ru.ashemchuk.model.level;

/**
 * Specification for a robot snake in a level.
 *
 * @param type the behavior type of the robot
 * @param dx   initial X direction (optional, used for some types)
 * @param dy   initial Y direction (optional, used for some types)
 */
public record RobotSpec(RobotType type, int dx, int dy) {
    /**
     * Enumeration of robot behavior types.
     */
    public enum RobotType {
        /**
         * Robot that follows the player snake.
         */
        FOLLOW_PLAYER,
        /**
         * Robot that moves randomly.
         */
        RANDOM
    }
}