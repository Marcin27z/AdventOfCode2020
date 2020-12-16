package org.example

fun task1(jolts: List<Int>): Int {
    val joltsSet = jolts.toHashSet()
    val start = 0
    var oneJolt = 0
    var threeJolt = 0
    fun check(adapter: Int) {
        if (joltsSet.contains(adapter + 1)) {
            oneJolt += 1
            check(adapter + 1)
        } else if (joltsSet.contains(adapter + 2)) {
            check(adapter + 2)
        } else if (joltsSet.contains(adapter + 3)) {
            threeJolt += 1
            check(adapter + 3)
        }
        return
    }
    check(start)
    return oneJolt * (threeJolt + 1)
}
fun binoms(i: Int): Int {
    return when(i) {
        -1 -> 0
        0 -> 0
        1 -> 2
        2 -> 4
        else -> 7
    }
}

fun task2(jolts: List<Int>): Long {
    fun count(jolts: List<Int>, index: Int): Int {
        if (index == jolts.size - 1) {
            return 1
        }
        if (jolts[index + 1] - jolts[index - 1] <= 3) {
            return count(jolts.minus(jolts[index]), index) + count(jolts, index + 1)
        } else {
            return count(jolts, index + 1)
        }
    }
    var newJolts = mutableListOf<MutableList<Int>>()
    val tmpJolts = mutableListOf<Int>()
    for (i in 0 until jolts.size - 1) {
        tmpJolts.add(jolts[i])
        if (jolts[i + 1] - jolts[i] == 3) {
            newJolts.add(tmpJolts.toMutableList())
            tmpJolts.clear()
        }
    }
    newJolts = newJolts.filter { it.size > 2 }.toMutableList()
    val newJoltsOptions = newJolts.map { count(it, 1) }
    return newJoltsOptions.fold(1L) { acc, value -> acc * value }
}

fun main() {
    val jolts = readFile("day10").getLines().map { it.toInt() }
    println(listOf(0, 28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1 ,32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3).sorted())
//    println(task1(jolts))
    println(task2(listOf(listOf(0), jolts.sorted(), listOf(jolts.sorted().max()!! + 3)).flatten()))
}