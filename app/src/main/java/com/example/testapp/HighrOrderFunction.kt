package com.example.testapp

fun add(a: Int, b: Int): Int {
    return a + b
}

fun higherOrder(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return add(x, y)
}

fun main() {
    val result = higherOrder(5, 10, ::add)
    println("Result = $result")
}