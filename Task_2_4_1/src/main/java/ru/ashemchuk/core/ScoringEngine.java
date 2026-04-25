package ru.ashemchuk.core;

import ru.ashemchuk.dsl.model.Task;
import ru.ashemchuk.dsl.model.ExtraScore;
import java.time.LocalDate;
import java.util.List;

public class ScoringEngine {
    /**
     * Compute total score for a task.
     * @param task the task definition
     * @param buildSuccess whether build succeeded
     * @param docsSuccess whether documentation generation succeeded
     * @param styleSuccess whether style check passed
     * @param testResults test results (passed/failed/skipped)
     * @param extraScore extra score from config (could be 0)
     * @param submissionDate date of submission (optional, if null assume on time)
     * @return total score, capped at task's maxScore
     */
    public double computeScore(Task task,
                               boolean buildSuccess,
                               boolean docsSuccess,
                               boolean styleSuccess,
                               TestRunner.TestResults testResults,
                               double extraScore,
                               LocalDate submissionDate) {
        // Binary success: all steps must pass
        boolean allStepsPass = buildSuccess && docsSuccess && styleSuccess && testResults.failed == 0;
        double baseScore = allStepsPass ? 1.0 : 0.0;

        // Apply extra score
        double total = baseScore + extraScore;

        // Apply deadline penalties if submissionDate is provided
        if (submissionDate != null) {
            total = applyDeadlinePenalty(task, submissionDate, total);
        }

        // Cap at maxScore
        if (total > task.getMaxScore()) {
            total = task.getMaxScore();
        }
        if (total < 0) {
            total = 0;
        }
        return total;
    }

    private double applyDeadlinePenalty(Task task, LocalDate submissionDate, double score) {
        LocalDate soft = task.getSoftDeadline();
        LocalDate hard = task.getHardDeadline();
        if (soft == null || hard == null) {
            return score; // no deadlines defined
        }
        if (submissionDate.isBefore(soft) || submissionDate.isEqual(soft)) {
            // on time, no penalty
            return score;
        } else if (submissionDate.isAfter(hard)) {
            // after hard deadline, zero score
            return 0;
        } else {
            // between soft and hard deadline, apply linear penalty? For simplicity, reduce by 0.5
            return score * 0.5;
        }
    }
}