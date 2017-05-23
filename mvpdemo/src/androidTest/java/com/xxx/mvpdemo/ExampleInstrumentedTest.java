package com.xxx.mvpdemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * 测试时需要使用 Android api支持
 测试时需要使用 Android 中的组件
 测试时需要访问 Android 中的特定环境元素（例如 Context)

 其他情况优先考虑使用 Unit Test ，因为它的速度要快很多，效率也要高很多。其次，Instrumented unit tes 是基于 Unit Test 的。所以我们先对 Unit Test 进行研究。
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.xxx.mvpdemo", appContext.getPackageName());
    }
}
