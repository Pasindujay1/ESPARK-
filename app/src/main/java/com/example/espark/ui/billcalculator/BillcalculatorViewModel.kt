package com.example.espark.ui.billcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.annotations.TestOnly

class BillcalculatorViewModel : ViewModel() {

    //function for return consumed units
    fun powerUnits(lastReading: Int, currentReading: Int): Int {
        return currentReading - lastReading
    }

    //function to calculate the electricity bill
    fun calculateSum(selectedOption: String, lastReading: Int, currentReading: Int): Double {

        var consumedUnits = currentReading - lastReading
        var fixedCharge = 0.00;
        var energyCharge = 0.00

        //domestic type calculation
        if (selectedOption == "Domestic") {
            if (consumedUnits in 0..60) {
                if (consumedUnits <= 30) {
                    fixedCharge = 400.00
                    energyCharge = 30.00 * consumedUnits
                } else {
                    fixedCharge = 550.00
                    energyCharge = (30.00 * 30) + 37.00 * (consumedUnits - 30)
                }
            } else {
                if (consumedUnits in 0..90) {
                    fixedCharge = 650.00
                    energyCharge = 42.00 * consumedUnits
                } else if (consumedUnits in 91..180) {
                    fixedCharge = 1500.00
                    energyCharge = (42.00 * 90) + 50.00 * (consumedUnits - 90)
                } else if (consumedUnits >= 181) {
                    fixedCharge = 2000.00
                    energyCharge = (42.00 * 90) + (50.00 * 90) + 75 * (consumedUnits - 180)
                } else {
                    fixedCharge = 0.00
                    energyCharge = 0.00
                }
            }

            //Religious and charitable institutions type calculation
        } else if (selectedOption == "Religious and charitable institutions") {
            if (consumedUnits in 0..30) {
                fixedCharge = 400.00
                energyCharge = 30.00 * consumedUnits
            } else if (consumedUnits in 31..90) {
                fixedCharge = 550.00
                energyCharge = (30.00 * 30) + 37.0 * (consumedUnits - 30)
            } else if (consumedUnits in 91..120) {
                fixedCharge = 650.00
                energyCharge = (30.00 * 30) + (37.0 * 60) + 42.00 * (consumedUnits - 90)
            } else if (consumedUnits in 121..180) {
                fixedCharge = 1500.00
                energyCharge =
                    (30.00 * 30) + (37.0 * 60) + (42.00 * 30) + 45.00 * (consumedUnits - 120)
            } else if (consumedUnits > 181) {
                fixedCharge = 2000.00
                energyCharge =
                    (30.00 * 30) + (37.0 * 60) + (42.00 * 30) + (45.00 * 60) + 50.00 * (consumedUnits - 180)
            } else {
                fixedCharge = 0.00
                energyCharge = 0.00
            }
            //industrial type calculation
        } else if (selectedOption == "Industrial") {
            if (consumedUnits in 0..300) {
                fixedCharge = 1200.00
                energyCharge = 26.00 * consumedUnits
            } else if (consumedUnits >= 301) {
                fixedCharge = 1600.00
                energyCharge = 26.00 * consumedUnits
            } else {
                fixedCharge = 0.00
                energyCharge = 0.00
            }
        } else {
            fixedCharge = 0.00
            energyCharge = 0.00
        }

        return fixedCharge + energyCharge
    }

}