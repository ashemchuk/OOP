package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class TaskTest {

    @Test
    void testCompletedTask() {
        Task task = new Task.Builder()
            .withDescription("Finish report")
            .completed()
            .build();

        assertEquals("[x] Finish report", task.toMarkdown());
    }

    @Test
    void testIncompleteTask() {
        Task task = new Task.Builder()
            .withDescription("Buy groceries")
            .incomplete()
            .build();

        assertEquals("[ ] Buy groceries", task.toMarkdown());
    }

    @Test
    void testTaskDefaultState() {
        Task task = new Task.Builder()
            .withDescription("Default task")
            .build();

        assertEquals("[ ] Default task", task.toMarkdown());
    }

    @Test
    void testEmptyTaskDescription() {
        assertThrows(IllegalStateException.class, () -> new Task.Builder().build());

        assertThrows(IllegalStateException.class, () -> new Task.Builder()
            .withDescription("")
            .build());
    }

    @Test
    void testTaskList() {
        Task.TaskList taskList = new Task.TaskList.Builder()
            .addTask("Task 1", true)
            .addTask("Task 2", false)
            .addTask("Task 3", true)
            .build();

        String expected = "[x] Task 1\n[ ] Task 2\n[x] Task 3";
        assertEquals(expected, taskList.toMarkdown());
    }

    @Test
    void testTaskListWithTaskObjects() {
        Task task1 = new Task.Builder()
            .withDescription("First task")
            .completed()
            .build();

        Task task2 = new Task.Builder()
            .withDescription("Second task")
            .incomplete()
            .build();

        Task.TaskList taskList = new Task.TaskList.Builder()
            .addTask(task1)
            .addTask(task2)
            .build();

        String expected = "[x] First task\n[ ] Second task";
        assertEquals(expected, taskList.toMarkdown());
    }

    @Test
    void testEmptyTaskList() {
        assertThrows(IllegalStateException.class, () -> new Task.TaskList.Builder().build());
    }
}