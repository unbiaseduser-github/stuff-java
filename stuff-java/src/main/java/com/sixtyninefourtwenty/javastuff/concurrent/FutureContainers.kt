@file:Suppress("unused")

package com.sixtyninefourtwenty.javastuff.concurrent

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.io.Closeable
import java.util.concurrent.Future

@Suppress("MemberVisibilityCanBePrivate")
open class FutureContainer {

    protected val futures: MutableSet<Future<*>> = mutableSetOf()

    fun add(future: Future<*>) {
        futures.add(future)
    }

    fun <T : Future<*>> addAndReturn(future: T) = future.also(::add)

    fun numberOfFutures() = futures.size

    fun numberOfActiveFutures() = futures.count { !it.isDone }

    fun close() = futures.forEach { it.cancel(true) }

}

class LifecycleAwareFutureContainer(private val lifecycle: Lifecycle) : FutureContainer(), LifecycleEventObserver {

    constructor(lifecycleOwner: LifecycleOwner) : this(lifecycleOwner.lifecycle)

    init {
        val state = lifecycle.currentState
        if (state != Lifecycle.State.DESTROYED && state.isAtLeast(Lifecycle.State.INITIALIZED)) {
            lifecycle.addObserver(this)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
            close()
        }
    }

}

class ViewModelFutureContainer(vm: ViewModel) : FutureContainer(), Closeable {

    init {
        vm.addCloseable(this)
    }

}
