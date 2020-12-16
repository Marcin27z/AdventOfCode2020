package org.example

fun validNumberSetForRule(rule: String): Set<Int> {
    return rule.split(": ")[1].split(" or ").fold(mutableSetOf()) { acc, range ->
        val rangeStart = range.split("-")[0].toInt()
        val rangeEnd = range.split("-")[1].toInt()
        acc.addAll(rangeStart..rangeEnd)
        acc
    }
}

fun String.getNumbersOnTicket(): List<Int> {
    return this.split(",").map { it.toInt() }
}

fun <T> List<List<T>>.columns(): List<List<T>> {
    println(this[0])
    val columns = mutableListOf<MutableList<T>>().apply { addAll(this@columns[0].map { mutableListOf() }) }
    return fold(columns) { acc, list ->
        list.forEachIndexed { index, element ->
            acc[index].add(element)
        }
        acc
    }
}

fun main() {
    val data = readFile("day16").split("\r\n\r\n")
    val rules = data[0].getLines()
    val myTicket = data[1].getLines()[1].getNumbersOnTicket()
    val nearbyTickets = data[2].getLines().drop(1).map { it.getNumbersOnTicket() }
    val allValidNumbers = rules.fold(mutableSetOf<Int>()) { acc, rule ->
        acc.addAll(validNumberSetForRule(rule))
        acc
    }
    val invalidNumbers = nearbyTickets.flatten().filter {
        it !in allValidNumbers
    }
    val validTickets = nearbyTickets.filter { ticket ->
        ticket.none { it !in allValidNumbers }
    }
    println(invalidNumbers.sum())
    println(validTickets)
    val validNumberSets = rules.map { validNumberSetForRule(it) }
    val possibleAssignments = validTickets.columns().map { column ->
        validNumberSets.mapIndexed { index, value -> Pair(index, value) }.filter { (index, value) ->
            column.all { it in value }
        }.map { it.first }
    }
    println(possibleAssignments)
    val assignments = IntArray(20)
    fun findAssignments(possibleAssignments: List<List<Int>>, assignments: IntArray, index: Int): Boolean {
        for (possibleAssignment in possibleAssignments[index]) {
            assignments[index] = possibleAssignment
            if (index == 19) {
                return true
            }
            if (findAssignments(
                    possibleAssignments.map { it.filter { it != possibleAssignment } },
                    assignments,
                    index + 1
                )
            ) {
                return true
            }
        }
        return false
    }
    findAssignments(possibleAssignments.sortedBy { it.size }, assignments, 0)
    val assignmentsIndexes = possibleAssignments.withIndex().sortedBy { it.value.size }.map { (index, _) -> index }
    val result = assignmentsIndexes.zip(assignments.toList()).map { it.second to it.first }.toMap()

    println(result)

    val departureRulesIndexes =
        rules.withIndex().filter { (_, value) -> value.contains("departure") }.map { (index, _) -> index }
    val product = departureRulesIndexes.fold(1L) { acc, index ->
        acc * myTicket[result[index]!!]
    }
    println(product)

}