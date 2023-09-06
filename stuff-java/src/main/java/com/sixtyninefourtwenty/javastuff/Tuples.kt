@file:JvmName("Tuples")
@file:Suppress("unused")

package com.sixtyninefourtwenty.javastuff

fun <T, U> Pair<T, U>.withFirst(first: T) = copy(first = first)

fun <T, U> Pair<T, U>.withSecond(second: U) = copy(second = second)

fun <T, U, V> Triple<T, U, V>.withFirst(first: T) = copy(first = first)

fun <T, U, V> Triple<T, U, V>.withSecond(second: U) = copy(second = second)

fun <T, U, V> Triple<T, U, V>.withThird(third: V) = copy(third = third)

fun <T, U, V> Triple<T, U, V>.withFirstAndSecond(first: T, second: U) = copy(first = first, second = second)

fun <T, U, V> Triple<T, U, V>.withFirstAndThird(first: T, third: V) = copy(first = first, third = third)

fun <T, U, V> Triple<T, U, V>.withSecondAndThird(second: U, third: V) = copy(second = second, third = third)
