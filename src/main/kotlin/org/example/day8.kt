package org.example

fun main() {
    val instructions = readFile("day8").split("\r\n").filter { it != "" }
        .map {
            val instruction = it.split(" ")[0]
            val arg = it.split(" ")[1].toInt()
            Pair(instruction, arg)
        }


    fun checkInfiniteLoop(instructions: List<Pair<String, Int>>): Pair<Boolean, Int> {
        var accumulator = 0
        var ip = 0
        val visited = mutableSetOf<Int>()
        val instructionsCount = instructions.size
        while (ip !in visited) {
            visited.add(ip)
            val currentInstruction = instructions[ip]
            when (currentInstruction.first) {
                "jmp" -> ip += currentInstruction.second
                "acc" -> {
                    accumulator += currentInstruction.second
                    ip += 1
                }
                "nop" -> ip += 1
            }
            if (ip >= instructionsCount) {
                return Pair(false, accumulator)
            }
        }
        return Pair(true, accumulator)
    }

    val options = mutableListOf<List<Pair<String, Int>>>()
    instructions.forEachIndexed { index, pair ->
        if (pair.first == "nop") {
            options.add(instructions.toMutableList().apply {
                this[index] = Pair("jmp", pair.second)
            })
        } else if (pair.first == "jmp") {
            options.add(instructions.toMutableList().apply {
                this[index] = Pair("nop", pair.second)
            })
        }
    }
    options.forEach {
        val (result, accumulator) = checkInfiniteLoop(it)
        if (!result) {
            println(accumulator)
        }
    }
}