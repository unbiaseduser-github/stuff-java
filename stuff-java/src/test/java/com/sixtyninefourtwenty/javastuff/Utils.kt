package com.sixtyninefourtwenty.javastuff

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

fun ExecutorService.createNeverendingFuture(): Future<*> = submit { Thread.sleep(Long.MAX_VALUE) }
