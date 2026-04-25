html {
    head {
        meta(charset:"utf-8")
        title("OOP Course Checks")
        style("""
            table {
                border-collapse: collapse;
                width: 100%;
                margin-bottom: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: center;
            }
            th {
                background-color: #f2f2f2;
            }
            h2 {
                margin-top: 30px;
            }
        """)
    }
    body {
        h1("OOP Course Checks")
        reportByGroup().each { group ->
            h2(group.groupName)
            group.tasks.each { task ->
                h3(task.taskName)
                table {
                    thead {
                        tr {
                            th("Студент")
                            th("Сборка")
                            th("Документация")
                            th("Style guide")
                            th("Тесты")
                            th("Доп. балл")
                            th("Общий балл")
                        }
                    }
                    tbody {
                        task.studentTaskResults.each { result ->
                            tr {
                                td(result.studentName)
                                td(formatBoolean(result.buildSuccess))
                                td(formatBoolean(result.docsSuccess))
                                td(formatBoolean(result.codeStyleSuccess))
                                td(formatTestResults(result))
                                td(result.extraScore)
                                td(result.totalScore)
                            }
                        }
                    }
                }
            }
            // Overall group statistics (placeholder)
            h3("Общая статистика группы ${group.groupName}")
            table {
                thead {
                    tr {
                        th("Студент")
                        th("Сумма")
                        th("Активность")
                        th("Оценка")
                    }
                }
                tbody {
                    // This would require additional data; for now leave empty
                    tr {
                        td("Нет данных")
                        td("")
                        td("")
                        td("")
                    }
                }
            }
        }
    }
}