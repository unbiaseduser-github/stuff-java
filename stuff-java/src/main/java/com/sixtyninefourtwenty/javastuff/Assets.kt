@file:JvmName("AssetsJava")
@file:Suppress("unused")

package com.sixtyninefourtwenty.javastuff

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import java.nio.charset.Charset
import java.util.stream.Stream

fun AssetManager.openQuietly(path: String) = open(path)

@JvmOverloads
fun AssetManager.readEntireTextFile(path: String, charset: Charset = Charsets.UTF_8) =
    open(path).bufferedReader(charset).use { it.readText() }

fun AssetManager.createDrawableFromAsset(path: String) = open(path).use { Drawable.createFromStream(it, path) }

@JvmOverloads
inline fun <R> AssetManager.readTextFileLines(path: String, charset: Charset = Charsets.UTF_8, block: (Stream<String>) -> R) =
    open(path).bufferedReader(charset).use { block(it.lines()) }

@JvmOverloads
fun AssetManager.readFirstFileLine(path: String, charset: Charset = Charsets.UTF_8): String? =
    open(path).bufferedReader(charset).use { it.readLine() }
