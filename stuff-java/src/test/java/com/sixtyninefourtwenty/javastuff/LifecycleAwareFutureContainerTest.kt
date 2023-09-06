package com.sixtyninefourtwenty.javastuff

import android.app.Application
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sixtyninefourtwenty.javastuff.concurrent.FutureContainer
import com.sixtyninefourtwenty.javastuff.concurrent.LifecycleAwareFutureContainer
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class LifecycleAwareFutureContainerTest {

    private lateinit var executor: ExecutorService

    @Before
    fun init() {
        executor = Executors.newFixedThreadPool(2)
        registerTestActivity()
    }

    @Test
    fun closeTest() {
        val future = executor.createNeverendingFuture()
        var container: FutureContainer? = null
        launchActivity<AppCompatActivity>().use {
            it.onActivity { activity ->
                container = LifecycleAwareFutureContainer(activity)
                container!!.add(future)
                assertFalse(future.isDone)
            }
        }
        assertNotNull(container)
        assertTrue(future.isDone)
    }

    private fun registerTestActivity() {
        val appContext: Application = ApplicationProvider.getApplicationContext()
        val activityInfo = ActivityInfo().apply {
            name = AppCompatActivity::class.java.name
            packageName = appContext.packageName
            theme = androidx.appcompat.R.style.Theme_AppCompat
        }
        shadowOf(appContext.packageManager).addOrUpdateActivity(activityInfo)
    }

}