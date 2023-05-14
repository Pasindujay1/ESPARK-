package com.example.espark
import com.example.espark.fragments.AddItemFragment
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val addItemFragment = AddItemFragment()

    @Test
    fun amount_isCorrect() {
        assertEquals(700.0, addItemFragment.calcAmount(10.0), 0.1)
        assertEquals(1000.0, addItemFragment.calcAmount(20.0), 0.1)
        assertEquals(1300.0, addItemFragment.calcAmount(30.0), 0.1)
        assertEquals(1487.0, addItemFragment.calcAmount(31.0), 0.1)
        assertEquals(1820.0, addItemFragment.calcAmount(40.0), 0.1)
        assertEquals(2560.0, addItemFragment.calcAmount(60.0), 0.1)

        // Test for values within second tier
        assertEquals(4430.0, addItemFragment.calcAmount(90.0), 0.1)
        assertEquals(6780.0, addItemFragment.calcAmount(120.0), 0.1)
        assertEquals(8280.0, addItemFragment.calcAmount(150.0), 0.1)
        assertEquals(9780.0, addItemFragment.calcAmount(180.0), 0.1)
        assertEquals(10355.0, addItemFragment.calcAmount(181.0), 0.1)
        assertEquals(11030.0, addItemFragment.calcAmount(190.0), 0.1)

        // Test for values above second tier
        assertEquals(34280.0, addItemFragment.calcAmount(500.0), 0.1)
        assertEquals(71780.0, addItemFragment.calcAmount(1000.0), 0.1)

    }

}