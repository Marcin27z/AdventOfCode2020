package org.example

fun questionsInGroup(group: String): Int {
    var answers = group.split("\r\n")
    val peopleCount = answers.size
    answers = answers.map { it.split("") }.flatten().filter { it != "" }
    return answers.groupingBy { it }.eachCount().filter { it.value == peopleCount }.count()
}

fun main() {
    val groups = readFile("day6").split("\r\n\r\n").filter { it != "" }
    println(groups.map { questionsInGroup(it) }.sum())
}