package org.example

import kotlin.math.abs
import kotlin.math.absoluteValue

fun part1(notes: List<String>) {
    val timestamp = notes[0].toLong()
    val ids = notes[1].split(",").filter { it != "x" }.map { it.toLong() }
    println(ids)

    println(ids.map { (((timestamp / it) + 1) * it) - timestamp })
    println(421 * 7)
}

fun part2(notes: List<String>) {
    val ids = notes[1].split(",").mapIndexed { index, s -> Pair(index, s) }.filter { it.second != "x" }
        .map { Pair(it.first, it.second.toLong()) }

    var t = 1L
    loop@ while (true) {
        var all = 0
        for((index, value) in ids) {
            if ((t + index.toLong()) % value == 0L) {
                all += 1
            }
        }
        if (all == ids.size) {
            break
        }
        t += 1
    }
    println(t)
}

fun main() {
    val notes = readFile("day13").getLines()
//    part1(notes)

    part2(notes)
//    fun egcd(a: Int, b: Int): List<Int> {
//        return if (a == 0) {
//            listOf(b, 0, 1)
//        } else {
//            val (gcd, x, y) = egcd(b % a, a)
//            listOf(gcd, y - (b / a) * x, x)
//        }
//    }

//    val a = 19
//    val b = -17
//    val c = 3
//    val (gcd, u, v) = egcd(a, b)
//    println(u)
//    println(v)
//    val x1 = u * (c / gcd)
//    val y1 = v * (c / gcd)
//    println(x1)
//    println(y1)
//    for (r in -12..3) {
//        val x = x1 - ((b * r) / gcd)
//        val y = y1 + ((a * r) / gcd)
//        println(x)
//        println(y)
//        println(a * x + b * y)
//    }
//    val r = ((x1 - searchedX) * gcd) / b
////    val r = -2
//    val x = x1 - ((b * r) / gcd)
//    val y = y1 + ((a * r) / gcd)
//    println(x)
//    println(y)
//    println(x * a + y * b)
//    val matrix = arrayOf(intArrayOf(2, 1, -1, 8), intArrayOf(-3, -1, 2, -11), intArrayOf(-2, 1, 2, -3))
//    val matrix = arrayOf(doubleArrayOf(5.0, 6.0, 1.0, 0.0, 0.0), doubleArrayOf(6.0, -11.0, 0.0, 1.0, 0.0), doubleArrayOf(8.0, 7.0, 0.0, 0.0, 1.0))
////    gaussianElimination(matrix).forEach {
////        it.forEach {
////            print(it)
////            print(",")
////        }
////        println()
////    }
//    matrix.toReducedRowEchelonForm()
//    matrix.printf("Hello")

}

fun gaussianElimination(matrix: Array<IntArray>): Array<IntArray> {
    var h = 0
    var k = 0
    val m = 3
    val n = 5
    while (h < m && k < n) {
        val i_max = (h until m).map { Pair(matrix[it][k], it) }.maxBy { it.first.absoluteValue }!!.second
        if (matrix[i_max][k] == 0) {
            k += 1
        } else {
            val tmp = matrix[h]
            matrix[h] = matrix[i_max]
            matrix[i_max] = tmp
            for (i in (h + 1) until m) {
                val f = matrix[i][k] / matrix[h][k]
                matrix[i][k] = 0
                for (j in (k + 1) until n) {
                    matrix[i][j] = matrix[i][j] - matrix[h][j] * f
                }
            }
            h += 1
            k += 1
        }

    }
    return matrix

}

typealias Matrix = Array<DoubleArray>

fun Matrix.toReducedRowEchelonForm() {
    var lead = 0
    val rowCount = this.size
    val colCount = this[0].size
    for (r in 0 until rowCount) {
        if (colCount <= lead) return
        var i = r

        while (this[i][lead] == 0.0) {
            i++
            if (rowCount == i) {
                i = r
                lead++
                if (colCount == lead) return
            }
        }

        val temp = this[i]
        this[i] = this[r]
        this[r] = temp

        if (this[r][lead] != 0.0) {
            val div = this[r][lead]
            for (j in 0 until colCount) this[r][j] /= div
        }

        for (k in 0 until rowCount) {
            if (k != r) {
                val mult = this[k][lead]
                for (j in 0 until colCount) this[k][j] -= this[r][j] * mult
            }
        }

        lead++
    }
}

fun Matrix.printf(title: String) {
    println(title)
    val rowCount = this.size
    val colCount = this[0].size

    for (r in 0 until rowCount) {
        for (c in 0 until colCount) {
            if (this[r][c] == -0.0) this[r][c] = 0.0 // get rid of negative zeros
            print("${"% 6.2f".format(this[r][c])}  ")
        }
        println()
    }

    println()
}

//fun rowEchelon(mat: Array<IntArray>): Array<IntArray> {
//        val n = 6
//        val m = 3
//        var i = 0
//        while (i < n) {
//            var j = i + 1
//            while (j < m) {
//                if (abs(mat[i][i]) < abs(mat[j][i])) {
//                    var k = 0
//                    while (k < n) {
//
//                        /* swapping mat[i][k] and mat[j][k] */mat[i][k] = mat[i][k] + mat[j][k]
//                        mat[j][k] = mat[i][k] - mat[j][k]
//                        mat[i][k] = mat[i][k] - mat[j][k]
//                        k++
//                    }
//                }
//                j++
//            }
//            i++
//        }
//        i = 0
//        while (i < m - 1) {
//            var j = i + 1
//            while (j < m) {
//                val f = (mat[j][i] / mat[i][i]).toFloat()
//                var k = 0
//                while (k < n) {
//                    mat[j][k] = (mat[j][k] - f * mat[i][k]).toInt()
//                    k++
//                }
//                j++
//            }
//            i++
//        }
//    return mat
//
//}