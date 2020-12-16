package org.example.b

import org.example.getLines
import org.example.readFile
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import kotlin.reflect.KFunction2

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}

operator fun Pair<Int, Int>.times(other: Int): Pair<Int, Int> {
    return Pair(this.first * other, this.second * other)
}

private fun Ship.moveEast(value: Int) {
    waypoint += Pair(value, 0)
}

private fun Ship.moveSouth(value: Int) {
    waypoint += Pair(0, -value)
}

private fun Ship.moveWest(value: Int) {
    waypoint += Pair(-value, 0)
}

private fun Ship.moveNorth(value: Int) {
    waypoint += Pair(0, value)
}

private fun Ship.rotateRight(value: Int) {
    waypoint *= rotationMatrix(-value)
}

private fun Ship.rotateLeft(value: Int) {
    waypoint *= rotationMatrix(value)
}

private fun Ship.forward(value: Int) {
    position += waypoint * value
}

fun rotationMatrix(degree: Int) = rotationMatrix(degree.toDouble() * PI / 180)
fun rotationMatrix(degree: Double) = listOf(listOf(cos(degree), -sin(degree)), listOf(sin(degree), cos(degree)))

operator fun Pair<Int, Int>.times(other: List<List<Double>>): Pair<Int, Int> {
    return Pair(first * other[0][0].toInt() + second * other[0][1].toInt(), first * other[1][0].toInt() + second * other[1][1].toInt())
}

private enum class Actions {
    E {
        override fun action() = Ship::moveEast
    },
    S {
        override fun action() = Ship::moveSouth
    },
    W {
        override fun action() = Ship::moveWest
    },
    N {
        override fun action() = Ship::moveNorth
    },
    F {
        override fun action() = Ship::forward
    },
    R {
        override fun action() = Ship::rotateRight
    },
    L {
        override fun action() = Ship::rotateLeft
    };

    abstract fun action(): KFunction2<Ship, Int, Unit>

}

private class Ship {
    var position = Pair(0, 0)
    var waypoint = Pair(10, 1)
}

fun main() {
    val instructions = readFile("day12").getLines().map { Pair(it.take(1), it.drop(1).toInt()) }
    val ship = Ship()

    instructions.forEach {
        Actions.valueOf(it.first).action().invoke(ship, it.second)
    }

    println(ship.position.first)
    println(ship.position.second)
    println(ship.position.first.absoluteValue + ship.position.second.absoluteValue)

}