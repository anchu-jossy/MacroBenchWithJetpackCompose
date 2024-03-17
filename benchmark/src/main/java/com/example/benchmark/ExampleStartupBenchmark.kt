package com.example.benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startUpCompilationModeNone() = startup(CompilationMode.None())

    @Test
    fun startUpCompilationModePartial() = startup(CompilationMode.Partial())


    @Test
    fun scrollCompilationModeNone() = scrollAndNavigate(CompilationMode.None())

    @Test
    fun scrollCompilationModePartial() = scrollAndNavigate(CompilationMode.Partial())

    fun startup(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
    }

    fun scrollAndNavigate(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.masterproject.jetpackandmacrobenchmark",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()

        addElementsAndScrollDown()
    }
}
//
//fun MacrobenchmarkScope.addElementsAndScrollDown() {
//    val button = device.findObject(By.text("Click me"))
//    val list = device.findObject(By.res("item_list"))
//
//    repeat(30) {
//        button.click()
//    }
//
//    device.waitForIdle()
//
////    list.setGestureMargin(device.displayWidth / 5)
//  //  list.fling(Direction.DOWN)
//
//    device.wait(Until.findObject(By.text("Element 23")), 5000)
//    device.wait(Until.hasObject(By.text("Detail: Element 23")), 5000)
//}
fun MacrobenchmarkScope.addElementsAndScrollDown() {
    val button = device.findObject(By.text("Click me"))
    val list = device.findObject(By.res("item_list"))

    if (button != null) {
        repeat(30) {
            button.click()
        }
    } else {
        // Handle the case where the button object is null
        // This could happen if the button is not found on the screen
        // You may want to log a message or take appropriate action here
    }

    device.waitForIdle()

    // Uncomment the lines below if you want to use list scrolling
    // if (list != null) {
    //     list.setGestureMargin(device.displayWidth / 5)
    //     list.fling(Direction.DOWN)
    // }

    device.wait(Until.findObject(By.text("Element 23")), 5000)
    device.wait(Until.hasObject(By.text("Detail: Element 23")), 5000)
}
