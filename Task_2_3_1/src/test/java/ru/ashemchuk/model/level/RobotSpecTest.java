package ru.ashemchuk.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RobotSpecTest {

    @Test
    void robotSpecRecord() {
        RobotSpec spec = new RobotSpec(RobotSpec.RobotType.FOLLOW_PLAYER, -1, 0);
        assertEquals(RobotSpec.RobotType.FOLLOW_PLAYER, spec.type());
        assertEquals(-1, spec.dx());
        assertEquals(0, spec.dy());
    }

    @Test
    void robotTypeValues() {
        RobotSpec.RobotType[] types = RobotSpec.RobotType.values();
        assertEquals(2, types.length);
        assertEquals(RobotSpec.RobotType.FOLLOW_PLAYER, types[0]);
        assertEquals(RobotSpec.RobotType.RANDOM, types[1]);
    }
}