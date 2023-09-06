package com.sixtyninefourtwenty.javastuff.concurrent

import android.os.Handler
import android.os.Looper
import androidx.core.os.ExecutorCompat
import java.util.concurrent.Executor

@Suppress("unused")
object MainThreadExecutor : Executor by ExecutorCompat.create(Handler(Looper.getMainLooper()))
