package org.example

fun isValidPassport(passport: String): Boolean {
    val entries = passport.split("\r\n").joinToString(" ").split(" ")
    if (entries.size == 8) {
        return true
    }
    var containsCid = false
    for (entry in entries) {
        if (entry.split(":")[0] == "cid") {
            containsCid = true
            break
        }
    }
    return entries.size == 7 && !containsCid
}

fun hasValidFields(passport: String): Boolean {
    val validationMap = hashMapOf<String, (String) -> Boolean>(
        "byr" to { it.toInt() in 1920..2002 },
        "iyr" to { it.toInt() in 2010..2020 },
        "eyr" to { it.toInt() in 2020..2030 },
        "hgt" to { try { if (it.takeLast(2) == "cm") it.take(3).toInt() in 150..193 else it.take(2).toInt() in 59..76 } catch (e: Exception) { false } },
        "hcl" to { it.matches(Regex("^#[0-9a-f]{6}$")) },
        "ecl" to { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
        "pid" to { it.matches(Regex("^[0-9]{9}$")) }
    )
    val validEntriesCount = passport
        .split("\r\n")
        .joinToString(" ")
        .split(" ")
        .map { Pair(it.split(":")[0], it.split(":")[1]) }
        .filter { (id, value) -> validationMap[id]?.invoke(value) ?: false }
        .count()

    return validEntriesCount == 7

}

fun main() {

    val passports = readFile("day4").split("\r\n\r\n")
    println(passports.filter { isValidPassport(it) }.filter { hasValidFields(it) }. count())
}



