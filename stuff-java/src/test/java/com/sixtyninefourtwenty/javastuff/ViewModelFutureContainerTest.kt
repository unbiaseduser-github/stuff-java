package com.sixtyninefourtwenty.javastuff

import androidx.lifecycle.ViewModel
import com.sixtyninefourtwenty.javastuff.concurrent.ViewModelFutureContainer
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ViewModelFutureContainerTest {

    private lateinit var executor: ExecutorService

    @BeforeEach
    fun init() {
        executor = Executors.newFixedThreadPool(2)
    }

    @Test
    fun closeTest() {
        val future = executor.createNeverendingFuture()
        val vm = TestViewModel()
        val container = ViewModelFutureContainer(vm)
        container.add(future)
        assertFalse(future.isDone)
        vm.clear()
        assertTrue(future.isDone)
    }

    private fun ViewModel.clear() {
        val clearMethod = ViewModel::class.java.getDeclaredMethod("clear")
        clearMethod.isAccessible = true
        clearMethod.invoke(this)
    }

    private class TestViewModel : ViewModel()

}