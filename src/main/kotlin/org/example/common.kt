package org.example

fun readFile(name: String): String {
    return object{}.javaClass.classLoader.getResource(name)?.readText() ?: ""
}

fun String.getLines() = this.split("\r\n").filter { it != "" }