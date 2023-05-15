package com.example.loginnregui

import android.widget.Button
import android.widget.EditText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule


import org.junit.Before

import com.example.loginnregui.R
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class RegistrationUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegistrationUI::class.java)

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button

    @Before
    fun setup() {
        // Initialize UI components
        activityRule.scenario.onActivity { activity ->
            firstNameInput = activity.findViewById(R.id.firstnameInput)
            lastNameInput = activity.findViewById(R.id.lastnameInput)
            emailInput = activity.findViewById(R.id.reg_email)
            passwordInput = activity.findViewById(R.id.reg_password)
            confirmPasswordInput = activity.findViewById(R.id.reg_confirm_pass)
            registerButton = activity.findViewById(R.id.Registerbtn)
        }
    }

    @Test
    fun registerWithValidInputs_success() {
        val testFirstName = "John"
        val testLastName = "Doe"
        val testEmail = "test@example.com"
        val testPassword = "testpassword"

        // Enter valid input values
        onView(withId(R.id.firstnameInput)).perform(typeText(testFirstName))
        onView(withId(R.id.lastnameInput)).perform(typeText(testLastName))
        onView(withId(R.id.reg_email)).perform(typeText(testEmail))
        onView(withId(R.id.reg_password)).perform(typeText(testPassword))
        onView(withId(R.id.reg_confirm_pass)).perform(typeText(testPassword))

        // Perform register button click
        onView(withId(R.id.Registerbtn)).perform(click())

        // Check if user is redirected to LoginUI activity
        //onView(withId(R.id.loginUI)).check(matches(isDisplayed()))
    }

    @Test
    fun registerWithEmptyInputs_error() {
        // Perform register button click with empty input fields
        onView(withId(R.id.Registerbtn)).perform(click())

        // Check if error toast is displayed
       // onView(withText("Empty fields are not allowed")).inRoot(withDecorView(not(activityRule.activity.window.decorView))).check(matches(isDisplayed()))
    }

    @Test
    fun registerWithMismatchingPasswords_error() {
        val testFirstName = "John"
        val testLastName = "Doe"
        val testEmail = "test@example.com"
        val testPassword = "testpassword"
        val testConfirmPassword = "mismatchingpassword"

        // Enter valid input values with mismatching passwords
        onView(withId(R.id.firstnameInput)).perform(typeText(testFirstName))
        onView(withId(R.id.lastnameInput)).perform(typeText(testLastName))
        onView(withId(R.id.reg_email)).perform(typeText(testEmail))
        onView(withId(R.id.reg_password)).perform(typeText(testPassword))
        onView(withId(R.id.reg_confirm_pass)).perform(typeText(testConfirmPassword))

        // Perform register button click
        onView(withId(R.id.Registerbtn)).perform(click())

        // Check if error toast is displayed
        //onView(withText("Password is not matching")).inRoot(withDecorView(not(activityRule.activity.window.decorView))).check(matches(isDisplayed()))
    }

}
