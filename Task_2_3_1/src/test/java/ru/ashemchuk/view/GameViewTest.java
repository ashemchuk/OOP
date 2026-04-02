package ru.ashemchuk.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashemchuk.model.Point;
import ru.ashemchuk.model.food.AppleFood;
import ru.ashemchuk.model.food.Food;
import ru.ashemchuk.model.food.FoodSpec;
import ru.ashemchuk.model.game.GameState;
import ru.ashemchuk.model.level.Level;
import ru.ashemchuk.model.level.LevelBuilder;
import ru.ashemchuk.model.level.RobotSpec;

/**
 * Unit tests for {@link GameView}.
 */
@ExtendWith(MockitoExtension.class)
@org.junit.jupiter.api.Disabled("JavaFX environment required")
class GameViewTest {
    @Mock
    private GridPane gameGrid;
    @Mock
    private Label scoreLabel;
    @Mock
    private Label lengthLabel;
    @Mock
    private Label levelLabel;
    @Mock
    private Label statusLabel;

    private GameView gameView;

    @BeforeEach
    void setUp() {
        gameView = new GameView(gameGrid, scoreLabel, lengthLabel, levelLabel, statusLabel);
    }

    @Test
    void constructor_initializesFields() {
        assertNotNull(gameView);
        // Verify that cell size is default (25.0)
        assertEquals(25.0, gameView.getCellSize());
    }

    @Test
    void setupGrid_createsColumnsAndRowsAndRectangles() {
        int width = 5;
        int height = 3;

        gameView.setupGrid(width, height);

        // Verify grid cleared
        verify(gameGrid).getChildren().clear();
        verify(gameGrid).getColumnConstraints().clear();
        verify(gameGrid).getRowConstraints().clear();

        // Verify column constraints added
        verify(gameGrid, times(width)).getColumnConstraints().add(any(ColumnConstraints.class));
        // Verify row constraints added
        verify(gameGrid, times(height)).getRowConstraints().add(any(RowConstraints.class));

        // Verify rectangles added (width * height) times
        verify(gameGrid, times(width * height))
            .add(any(Rectangle.class), any(Integer.class), any(Integer.class));
    }

    @Test
    void resizeCells_updatesConstraintsAndRectangles() {
        // First setup a grid
        gameView.setupGrid(2, 2);
        // Mock the children list to contain rectangles
        Rectangle rect1 = mock(Rectangle.class);
        Rectangle rect2 = mock(Rectangle.class);
        Rectangle rect3 = mock(Rectangle.class);
        Rectangle rect4 = mock(Rectangle.class);
        when(gameGrid.getChildren())
            .thenReturn(javafx.collections.FXCollections.observableArrayList(
                rect1, rect2, rect3, rect4
            ));
        // Mock column and row constraints
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        when(gameGrid.getColumnConstraints())
            .thenReturn(javafx.collections.FXCollections.observableArrayList(col1, col2));
        when(gameGrid.getRowConstraints())
            .thenReturn(javafx.collections.FXCollections.observableArrayList(row1, row2));

        double newCellSize = 30.0;
        gameView.resizeCells(newCellSize);

        // Verify column constraints updated
        assertEquals(newCellSize, col1.getPrefWidth());
        assertEquals(newCellSize, col2.getPrefWidth());
        // Verify row constraints updated
        assertEquals(newCellSize, row1.getPrefHeight());
        assertEquals(newCellSize, row2.getPrefHeight());
        // Verify rectangles resized
        verify(rect1).setWidth(newCellSize);
        verify(rect1).setHeight(newCellSize);
        verify(rect2).setWidth(newCellSize);
        verify(rect2).setHeight(newCellSize);
        verify(rect3).setWidth(newCellSize);
        verify(rect3).setHeight(newCellSize);
        verify(rect4).setWidth(newCellSize);
        verify(rect4).setHeight(newCellSize);
        // Verify cell size stored
        assertEquals(newCellSize, gameView.getCellSize());
    }

    @Test
    void resizeCells_withNegativeSize_doesNothing() {
        double originalSize = gameView.getCellSize();
        gameView.resizeCells(-10.0);
        // Should not change
        assertEquals(originalSize, gameView.getCellSize());
        // No interactions with grid
        verify(gameGrid, never()).getColumnConstraints();
        verify(gameGrid, never()).getRowConstraints();
    }

    @Test
    void update_updatesLabelsAndColors() {
        // Create a simple level
        Level level = new LevelBuilder()
            .width(4)
            .height(4)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(1)
            .addRobotSpec(new RobotSpec(RobotSpec.RobotType.RANDOM, 5, 0))
            .build();
        // Create a game state with player at (1,1), robot at (2,2), food at (3,3)
        List<Point> playerPositions = List.of(new Point(1, 1), new Point(1, 2));
        List<List<Point>> robotPositions = List.of(
            List.of(new Point(2, 2), new Point(2, 3))
        );
        Food food = new AppleFood(new Point(3, 3));
        List<Food> foods = List.of(food);
        GameState state =
            new GameState(playerPositions, robotPositions, foods, 42, false, false, level);

        gameView.update(state, false);

        verify(scoreLabel).setText("42");
        verify(lengthLabel).setText("2 / 10");
        verify(statusLabel).setText("Playing");
        // level label is not updated by update (controller does that)
        verify(levelLabel, never()).setText(any());
    }

    @Test
    void update_paused_showsPausedStatus() {
        Level level = new LevelBuilder()
            .width(4)
            .height(4)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        GameState state = new GameState(
            List.of(new Point(0, 0)),
            List.of(),
            List.of(),
            0,
            false,
            false,
            level
        );

        gameView.update(state, true);

        verify(statusLabel).setText("Paused");
    }

    @Test
    void update_gameOver_showsGameOverStatus() {
        Level level = new LevelBuilder()
            .width(4)
            .height(4)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        GameState state = new GameState(
            List.of(new Point(0, 0)),
            List.of(),
            List.of(),
            0,
            true,
            false,
            level
        );

        gameView.update(state, false);

        verify(statusLabel).setText("Game Over");
    }

    @Test
    void update_win_showsWinStatus() {
        Level level = new LevelBuilder()
            .width(4)
            .height(4)
            .snakeSpeed(5)
            .snakeLengthFinal(10)
            .foodAmount(0)
            .build();
        GameState state = new GameState(
            List.of(new Point(0, 0)),
            List.of(),
            List.of(),
            0,
            false,
            true,
            level
        );

        gameView.update(state, false);

        verify(statusLabel).setText("You Win!");
    }
}