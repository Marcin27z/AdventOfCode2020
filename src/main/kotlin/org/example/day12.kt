package org.example.a

import org.example.getLines
import org.example.readFile
import kotlin.math.absoluteValue
import kotlin.reflect.KFunction2

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

private fun Ship.moveEast(value: Int) {
    coords += Pair(0, value)
}

private fun Ship.moveSouth(value: Int) {
    coords += Pair(-value, 0)
}

private fun Ship.moveWest(value: Int) {
    coords += Pair(0, -value)
}

private fun Ship.moveNorth(value: Int) {
    coords += Pair(value, 0)
}

private fun Ship.rotateRight(value: Int) {
    facing = facing.rotateRight(value)
}

private fun Ship.rotateLeft(value: Int) {
    facing = facing.rotateLeft(value)
}

private fun Ship.forward(value: Int) {
    facing.forward().invoke(this, value)
}

private enum class Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH;

    fun rotateRight(degree: Int): Direction {
        return values()[(ordinal + degree / 90) % 4]
    }

    fun rotateLeft(degree: Int): Direction {
        return values()[(4 + ordinal - (degree % 360) / 90) % 4]
    }

    fun forward() = Actions.valueOf(name[0].toString()).action()
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
    var facing = Direction.EAST
    var coords = Pair(0, 0)
}

fun main() {
    val instructions = readFile("day12").getLines().map { Pair(it.take(1), it.drop(1).toInt()) }
    val ship = Ship()

    instructions.forEach {
        Actions.valueOf(it.first).action().invoke(ship, it.second)
    }

    println(ship.coords.first)
    println(ship.coords.second)
    println(ship.coords.first.absoluteValue + ship.coords.second.absoluteValue)

}