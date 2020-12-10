package org.example

fun task1(sequence: List<Long>): Long {
    fun check(index: Int): Boolean {
        for (i in index - 25 until index) {
            if (sequence.subList(index - 25, index).contains(sequence[index] - sequence[i])) {
                return true
            }
        }
        return false
    }

    for (i in 25 until sequence.size) {
        if (!check(i)) {
            return sequence[i]
        }
    }
    return 0
}

fun main() {
    val sequence = readFile("day9").split("\r\n").filter { it != "" }.map { it.toLong() }
    val resultTask1 = task1(sequence)
    println(resultTask1)

    var i = 0
    var j = 0
    var sum = sequence[i]
    while (sum != resultTask1) {
        if (sum < resultTask1) {
            j += 1
            sum += sequence[j]
        }
        if (sum > resultTask1) {
            sum -= sequence[i]
            i += 1
        }
    }

    println(sequence.subList(i, j + 1).min()!! + sequence.subList(i, j + 1).max()!!)

}