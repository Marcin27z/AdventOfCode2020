package org.example

fun findTwoThatSumUpTo(data: List<Int>, sum: Int): Pair<Int, Int> {
    val table = IntArray(sum) { 0 }

    var result = 0
    for (it in data) {
        if (it >= sum) {
            continue
        }
        table[it] += 1
        table[sum - it] += 1
        if (table[it] == 2) {
            result = it
            break
        }
    }
    return Pair(result, sum - result)
}

fun partOne(data: List<Int>) = findTwoThatSumUpTo(data, 2020)

fun partTwo(data: List<Int>) {
    for (number in data) {
        val (a, b) = findTwoThatSumUpTo(data, 2020 - number)
        if (a != 0 && (a != number || b != number)){
            println(number)
            println(a)
            println(b)
            println(number * a * b)
            break
        }
    }
}

fun main() {
    val data = readFile("day1").split("\r\n").map { it.toInt() }
    val (a, b) = partOne(data)
    println(a * b)
    partTwo(data)
}

