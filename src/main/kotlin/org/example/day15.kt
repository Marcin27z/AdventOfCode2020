package org.example

val input = mutableListOf(2, 0, 1, 7, 4, 14, 18)

fun main() {
    val map = mutableMapOf<Int, Int>()
    var i = input.size
    for (j in input.indices) {
        map[input[j]] = j + 1
    }
    input.add(0)
    while (input.size != 30000000) {
        if (map[input[i]] != null) {
            input.add(i + 1 - map[input[i]]!!)
            map[input[i]] = i + 1
        } else {
            map[input[i]] = i + 1
            input.add(0)
        }
        i += 1
    }
    println(input[30000000 - 1])
}