package org.example


fun Char.occupied() = this == '#'
fun Char?.empty() = this == 'L'

fun Boolean.toInt() = if (this) 1 else 0

typealias Grid = List<List<Char>>

fun <T> List<T>?.prev(i: Int): T? {
    return if (i > 0) {
        this?.get(i - 1)
    } else {
        null
    }
}

fun <T> List<T>?.next(i: Int): T? {
    return if (i < this?.size?.dec() ?: -1) {
        this?.get(i + 1)
    } else {
        null
    }
}

fun Grid.adjacent(i: Int, j: Int): List<Char> {
    return listOfNotNull(
        this.prev(i).prev(j), this.prev(i).next(j), this.next(i).next(j), this.next(i).prev(j),
        this.next(i)?.get(j), this.prev(i)?.get(j), this[i].prev(j), this[i].next(j)
    )
}

fun Grid.goDown(position: Pair<Int, Int>?): Pair<Int, Int>? {
    return position?.let {
        if (position.first < this.size.dec()) {
            Pair(position.first + 1, position.second)
        } else {
            null
        }
    }
}

fun Grid.goUp(position: Pair<Int, Int>?): Pair<Int, Int>? {
    return position?.let {
        if (position.first > 0) {
            Pair(position.first - 1, position.second)
        } else {
            null
        }
    }
}

fun Grid.goLeft(position: Pair<Int, Int>?): Pair<Int, Int>? {
    return position?.let {
        if (position.second > 0) {
            Pair(position.first, position.second - 1)
        } else {
            null
        }
    }
}

fun Grid.goRight(position: Pair<Int, Int>?): Pair<Int, Int>? {
    return position?.let {
        if (position.second < this[position.first].size.dec()) {
            Pair(position.first, position.second + 1)
        } else {
            null
        }
    }
}

fun Grid.doNothing(position: Pair<Int, Int>?) = position

fun Grid.get(position: Pair<Int, Int>): Char {
    return this[position.first][position.second]
}

typealias GridStep = Grid.(Pair<Int, Int>?) -> Pair<Int, Int>?

fun Grid.visible(i: Int, j: Int): Int {
    if (this.get(Pair(i, j)) == '.') {
        return -1
    }

    val ops = listOf<Pair<GridStep, GridStep>>(
        Pair(Grid::goUp, Grid::doNothing),
        Pair(Grid::goUp, Grid::goLeft),
        Pair(Grid::goUp, Grid::goRight),
        Pair(Grid::goLeft, Grid::doNothing),
        Pair(Grid::goRight, Grid::doNothing),
        Pair(Grid::goDown, Grid::doNothing),
        Pair(Grid::goDown, Grid::goLeft),
        Pair(Grid::goDown, Grid::goRight)
    )
    return ops.sumBy { (action1, action2) ->
        var oldPosition = Pair(i, j)
        do {
            val newPosition = this.action1(this.action2(oldPosition))
            if (newPosition == null || this.get(newPosition).empty())
                break
            if (this.get(newPosition).occupied()) {
                return@sumBy 1
            }
            oldPosition = newPosition
        } while (true)
        return@sumBy 0
    }
}

fun main() {

    val seatGrid = readFile("day11").getLines().map { it.toCharArray().toList() }

    fun step(seatGrid: Grid): Pair<Grid, Boolean> {
        var change = false
        return Pair(seatGrid.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, element ->
                if (seatGrid.adjacent(rowIndex, columnIndex).none { it.occupied() } && element.empty()) {
                    change = true
                    '#'
                } else if (seatGrid.adjacent(rowIndex, columnIndex).filter { it.occupied() }
                        .count() >= 4 && element.occupied()) {
                    change = true
                    'L'
                } else {
                    element
                }
            }
        }, change)
    }

    fun step2(seatGrid: Grid): Pair<Grid, Boolean> {
        var change = false
        return Pair(seatGrid.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, element ->
                if (seatGrid.visible(rowIndex, columnIndex) == 0 && element.empty()) {
                    change = true
                    '#'
                } else if (seatGrid.visible(rowIndex, columnIndex) >= 5 && element.occupied()) {
                    change = true
                    'L'
                } else {
                    element
                }
            }
        }, change)
    }

    var oldGrid = seatGrid
    do {
        val (newGrid, changed) = step2(oldGrid)
        println(newGrid)
        oldGrid = newGrid
    } while (changed)

    println(oldGrid.sumBy { it.sumBy { it.occupied().toInt() } })
}