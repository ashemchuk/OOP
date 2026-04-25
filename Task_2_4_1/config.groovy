groups {
    group {
        name = "24216"
        students {
            student {
                nicknameGH = "ashemchuk"
                name = "Nerlikh Anna"
                repoURL = "https://github.com/ashemchuk/OOP"
            }
        }
    }
}

tasks {
    task {
        id = "Task_1_1_1"
        name = "СОртироовочка"
        softDeadline = date("14-02-2025")
        hardDeadline = date("21-02-2025")
        maxScore = 1
    }
    task {
        id = "Task_2_2_1"
        name = "Простые числа 2"
        softDeadline = null
        hardDeadline = date("30-05-2026")
        maxScore = 1
    }
}

checkpoints {
    checkpoint {
        date = date("01-03-2025")
        description = "Контрольная неделя"
    }
}
assignments {
    assignment {
        taskId = "Task_1_1_1"
        studentNicknameGH = "ashemchuk"
    }
}

extraScores {
    extraScore {
        taskId = "Task_1_1_1"
        studentNicknameGH = "ashemchuk"
        score = 0.5
    }
}