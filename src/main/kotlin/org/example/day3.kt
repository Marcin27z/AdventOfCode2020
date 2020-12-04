package org.example

fun calculate(data: List<String>, right: Int, down: Int): Long {
    var column = 0
    var sum = 0L
    for (i in data.indices.map { it * down }.filter { it < data.size }) {
        if (data[i][column % data[0].length] == '#') {
            sum += 1
        }
        column += right
    }
    return sum
}

fun main() {
    val data = readFile("day3").split("\r\n").filter { it != "" }

    val result = calculate(data, 1, 1) * calculate(data, 3, 1) *
            calculate(data, 5, 1) * calculate(data, 7, 1) * calculate(data, 1, 2)
    println(result)
}