package org.example

fun main() {

    val data = readFile("day2").split("\r\n")
    val result = data.map {
        val policy = it.split(":")[0]
        val password = it.split(":")[1]
        val policyRange = policy.split(" ")[0]
        val policyLetter = policy.split(" ")[1].toCharArray()[0]
        val policyRangeBegin = policyRange.split("-")[0].toInt()
        val policyRangeEnd = policyRange.split("-")[1].toInt()
//        (password.filter { it == policyLetter }.count() in (policyRangeBegin .. policyRangeEnd))
        password[policyRangeBegin] != password[policyRangeEnd]
                && (password[policyRangeBegin] == policyLetter || password[policyRangeEnd] == policyLetter)
    }.filter { it }.count()
    print(result)
}