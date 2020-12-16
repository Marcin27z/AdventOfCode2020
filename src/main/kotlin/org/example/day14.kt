package org.example

fun String.isMask() = this.contains("mask")
fun String.getMask() = this.split(" = ")[1]
fun String.getMemExpr() = this.split(" = ")[0]
fun String.getMemValue() = this.split(" = ")[1].toLong()
fun String.getMemAddr() = this.getMemExpr().filter { it.isDigit() }.toLong()

fun Long.applyMask(mask: String): Long {
    val oneMask = mask.map { if (it == 'X') '0' else it }.joinToString("").toLong(2)
    val zeroMask = mask.map { if (it == 'X' || it == '1') '0' else '1' }.joinToString("").toLong(2)
    return ((this or oneMask).inv() or zeroMask).inv()
}

fun String.process(): List<String> {
    return reversed().fold(mutableListOf(mutableListOf<Char>())) { acc, it ->
        if (it == 'X') {
            acc.addAll(acc.map { it.toMutableList() })
            for (i in 0 until acc.size / 2) {
                acc[i].add('0')
            }
            for (j in (acc.size / 2) until acc.size) {
                acc[j].add('1')
            }
        } else {
            acc.forEach { string ->
                string.add(it)
            }
        }
        acc
    }.map {
        it.joinToString("").reversed()
    }
}

fun MutableMap<Long, Long>.task1(addr: Long, value: Long, mask: String) {
    this[addr] = value.applyMask(mask)
}

fun MutableMap<Long, Long>.task2(addr: Long, value: Long, mask: String) {
    val binaryAddress = addr.toString(2)
    val maskedAddr = (0..35 - binaryAddress.length).map { '0' }.joinToString("").plus(binaryAddress).zip(mask)
        .map { (bit, mask) -> if (mask == '0') bit else if (mask == '1') '1' else 'X' }
        .joinToString("")
    val addresses = maskedAddr.process().map { it.toLong(2) }
    addresses.forEach {
        this[it] = value
    }
}

fun main() {
    println("00000000000000000000000000000001X0XX".process())
    val input = readFile("day14")
        .getLines()
        .fold(mutableListOf<MutableList<String>>()) { acc, s ->
            if (s.isMask()) {
                acc.add(mutableListOf())
            }
            acc.last().add(s)
            acc
        }
        .map {
            it.groupBy(String::isMask).map(Map.Entry<Boolean, List<String>>::toPair).unzip().second
        }
        .fold(mutableMapOf<Long, Long>()) { map, list ->
            val mask = list[0][0].getMask()
            val instructions = list[1].map { it.getMemAddr() to it.getMemValue() }
            instructions.forEach {
//                map[it.first] = it.second.applyMask(mask)
                map.task2(it.first, it.second, mask)
            }
            map
        }.values.sum()
    println(input)

}