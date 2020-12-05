package org.example

fun seatId(boardingPass: String): Int {
    val row = boardingPass.substring(0..6).map { if (it == 'B') '1' else '0' }.joinToString("").toInt(2)
    val column = boardingPass.substring(7..9).map { if (it == 'R') '1' else '0' }.joinToString("").toInt(2)
    return row * 8 + column
}

fun main() {

    val boardingPasses = readFile("day5").split("\r\n").filter { it != "" }
//    println(boardingPasses.map { seatId(it) }.max())
    val gap = boardingPasses.asSequence().map { seatId(it) }.sorted().zipWithNext()
        .filter { (first, second) -> second - first == 2 }.first()
    println(gap.first + 1)
}