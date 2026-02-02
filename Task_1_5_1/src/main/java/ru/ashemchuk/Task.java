package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task (checklist item) in Markdown.
 * Tasks can be marked as completed or incomplete.
 */
public class Task extends Element {
    private final String description;
    private final boolean completed;

    private Task(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    /**
     * Converts this task to its Markdown representation.
     *
     * @return a Markdown-formatted task string
     */
    @Override
    public String toMarkdown() {
        return (completed ? "[x] " : "[ ] ") + description;
    }

    /**
     * A builder class for constructing {@link Task} instances.
     */
    public static class Builder extends AbstractBuilder<Task> {
        private String description;
        private boolean completed = false;

        /**
         * Sets the task description.
         *
         * @param description the task description
         * @return this builder instance for method chaining
         */
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Marks the task as completed.
         *
         * @return this builder instance for method chaining
         */
        public Builder completed() {
            this.completed = true;
            return this;
        }

        /**
         * Marks the task as incomplete (default).
         *
         * @return this builder instance for method chaining
         */
        public Builder incomplete() {
            this.completed = false;
            return this;
        }

        /**
         * Builds a {@link Task} instance.
         *
         * @return a new {@link Task} with the configured properties
         * @throws IllegalStateException if description is null or empty
         */
        @Override
        public Task build() {
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalStateException("Task description cannot be empty");
            }
            return new Task(description, completed);
        }
    }

    /**
     * Represents a list of tasks in Markdown.
     */
    public static class TaskList extends Element {
        private final List<Task> tasks;

        private TaskList(List<Task> tasks) {
            this.tasks = tasks;
        }

        /**
         * Converts this task list to its Markdown representation.
         *
         * @return a Markdown-formatted task list string
         */
        @Override
        public String toMarkdown() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(tasks.get(i).toMarkdown());
                if (i < tasks.size() - 1) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        /**
         * A builder class for constructing {@link TaskList} instances.
         */
        public static class Builder extends AbstractBuilder<TaskList> {
            private final List<Task> tasks = new ArrayList<>();

            /**
             * Adds a task to the list.
             *
             * @param description the task description
             * @param completed true if the task is completed, false otherwise
             * @return this builder instance for method chaining
             */
            public Builder addTask(String description, boolean completed) {
                tasks.add(new Task(description, completed));
                return this;
            }

            /**
             * Adds a pre-built {@link Task} to the list.
             *
             * @param task the task to add
             * @return this builder instance for method chaining
             */
            public Builder addTask(Task task) {
                tasks.add(task);
                return this;
            }

            /**
             * Builds a {@link TaskList} instance.
             *
             * @return a new {@link TaskList} containing the configured tasks
             * @throws IllegalStateException if no tasks have been added
             */
            @Override
            public TaskList build() {
                if (tasks.isEmpty()) {
                    throw new IllegalStateException("Task list must have at least one task");
                }
                return new TaskList(new ArrayList<>(tasks));
            }
        }
    }
}