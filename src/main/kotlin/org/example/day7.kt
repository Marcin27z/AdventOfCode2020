package org.example

fun task1() {
    val rules = readFile("day7").split("\r\n").filter { it != "" }
    val rulesMap = HashMap<String, MutableList<String>>()
    rules.forEach {
        val left = it.split("contain ")[0].dropLast(2)
        val right = it.split("contain ")[1]
            .split(", ")
            .map { s ->
                val token = when {
                    s.last() == '.' && s.take(2) != "1 " -> {
                        s.dropLast(2)
                    }
                    s.last() == '.' && s.take(2) == "1 " -> {
                        s.dropLast(1)
                    }
                    s.take(2) != "1 " -> {
                        s.dropLast(1)
                    }
                    else -> {
                        s
                    }
                }
                token.filter { c -> !c.isDigit() }.trim()
            }
        right.forEach { item ->
            rulesMap[item] = rulesMap.getOrDefault(item, mutableListOf()).apply { add(left) }
        }
    }

    fun search(entry: String): List<String> {
        val list = rulesMap[entry]
        return list?.fold(mutableListOf(entry)) { acc , s ->  acc.apply { addAll(search(s)) } } ?: mutableListOf(entry)
    }
    val result = search("shiny gold bag").distinct()
    print(result)
    print(result.count() - 1)
}

fun task2() {
    val rules = readFile("day7").split("\r\n").filter { it != "" }
    val rulesMap = HashMap<String, MutableList<Pair<Long, String>>>()
    rules.forEach {
        val left = it.split("contain ")[0].dropLast(2)
        val right = it.split("contain ")[1]
            .split(", ")
            .map { s ->
                val token = when {
                    s.last() == '.' && s.take(2) != "1 " -> {
                        s.dropLast(2)
                    }
                    s.last() == '.' && s.take(2) == "1 " -> {
                        s.dropLast(1)
                    }
                    s.take(2) != "1 " -> {
                        s.dropLast(1)
                    }
                    else -> {
                        s
                    }
                }
                Pair(try { Regex("[0-9]+").findAll(token).map(MatchResult::value).toList()[0].toLong() } catch (e: Exception) { 0L }, token.filter { !it.isDigit() }.trim())
            }
        right.forEach { pair ->
            rulesMap[left] = rulesMap.getOrDefault(left, mutableListOf()).apply { add(pair) }
        }
    }

    fun search(entry: String): Long {
        val list = rulesMap[entry]
        return list?.fold(0L) { acc , s ->  acc + s.first + (s.first * search(s.second)) } ?: 1L
    }
    val result = search("shiny gold bag")
    print(result)
}

fun main() {
    task2()
}