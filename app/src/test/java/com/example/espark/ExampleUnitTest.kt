package com.example.espark

import com.example.espark.ui.billcalculator.BillcalculatorViewModel
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {

    private val billCalViewModel = BillcalculatorViewModel()

    @Test
    fun unitCalculation_isCorrect(){
        assertEquals(100, billCalViewModel.powerUnits(100,200))
    }

    @Test
    fun unitCalculation_isIncorrect(){
        assertNotEquals(200, billCalViewModel.powerUnits(2000,1500))
    }

    @Test
    fun domesticBillCalc_isCorrect(){
        assertEquals(4178.00, billCalViewModel.calculateSum("Domestic",25500, 25584), 0.0)
    }

    @Test
    fun industrialBillCalc_isCorrect(){
        assertEquals(55784.00, billCalViewModel.calculateSum("Industrial",52500, 54584), 0.0)
    }

    @Test
    fun religiousBillCalc_isCorrect(){
        assertEquals(56230.00, billCalViewModel.calculateSum("Religious and charitable institutions",32569, 33692), 0.0)
    }

    @Test
    fun domesticBillCalc_isIncorrect(){
        assertNotEquals(4178.00, billCalViewModel.calculateSum("Domestic",25584, 25500), 0.0)
    }

    @Test
    fun industrialBillCalc_isIncorrect(){
        assertNotEquals(5784.00, billCalViewModel.calculateSum("Industrial",65500, 54584), 0.0)
    }

    @Test
    fun religiousBillCalc_isIncorrect(){
        assertNotEquals(45230.00, billCalViewModel.calculateSum("Religious and charitable institutions",30569, 23692), 0.0)
    }

    @Test
    fun religiousBillCalc_isIncorrect2(){
        assertNotEquals(55250.00, billCalViewModel.calculateSum("Religious and charitable institutions",30569, 23692), 0.0)
    }

    @Test
    fun billCalc_isIncorrectType(){
        assertEquals(0.0, billCalViewModel.calculateSum("Something", 25000,26000),0.0)
    }
}