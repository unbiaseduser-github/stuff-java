package com.sixtyninefourtwenty.javastuff

import com.sixtyninefourtwenty.javastuff.concurrent.FutureContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FutureContainerTest {

    private lateinit var executor: ExecutorService
    private lateinit var container: FutureContainer

    @BeforeEach
    fun init() {
        executor = Executors.newFixedThreadPool(2)
        container = FutureContainer()
    }

    @Test
    fun closeTest() {
        val future = executor.createNeverendingFuture()
        container.add(future)
        assertFalse(future.isDone)
        container.close()
        assertTrue(future.isDone)
    }

    @Test
    fun numberOfFuturesTest() {
        assertEquals(0, container.numberOfFutures())
        val future = executor.createNeverendingFuture()
        container.add(future)
        assertEquals(1, container.numberOfFutures())
    }

    @Test
    fun numberOfActiveFuturesTest() {
        assertEquals(0, container.numberOfActiveFutures())
        val future = executor.createNeverendingFuture()
        container.add(future)
        assertEquals(1, container.numberOfActiveFutures())
        future.cancel(true)
        assertEquals(0, container.numberOfActiveFutures())
    }

}
