//package com.example.loginnregui
//
//import com.google.errorprone.annotations.DoNotMock
//import org.junit.Test
//
//import org.junit.Assert.*
//
///import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import org.junit.Assert.*
//import org.junit.Before
//import org.mockito.Mock
//
//
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//import java.lang.Exception
//
//class RegistrationUITest {
//
//    @MOCK
//    lateinit var mockAuth: FirebaseAuth
//
//    @Mock
//    lateinit var mockDb: FirebaseDatabase
//
//    @Mock
//    lateinit var mockDbReference: DatabaseReference
//
//    private lateinit var registrationUI: RegistrationUI
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        `when`(mockAuth.currentUser).thenReturn(null)
//        `when`(mockDb.reference).thenReturn(mockDbReference)
//        `when`(mockDbReference.child(anyString())).thenReturn(mockDbReference)
//
//        registrationUI = RegistrationUI()
//        registrationUI.firebaseAuth = mockAuth
//        registrationUI.database = mockDb
//        registrationUI.databaseReference = mockDbReference
//    }
//
//    @Test
//    fun `create user with valid inputs`() {
//        val email = "test@example.com"
//        val pass = "password"
//        val fname = "John"
//        val lname = "Doe"
//        val confirmPass = pass
//
//        `when`(mockAuth.createUserWithEmailAndPassword(email, pass)).thenReturn(mock())
//
//        registrationUI.createAccount(fname, lname, email, pass, confirmPass)
//
//        verify(mockAuth).createUserWithEmailAndPassword(email, pass)
//        verify(mockDbReference).child(anyString())
//        verify(mockDbReference).child(anyString()).child("firstnameInput").setValue(fname)
//        verify(mockDbReference).child(anyString()).child("lastnameInput").setValue(lname)
//        verify(mockDbReference).child(anyString()).child("reg_email").setValue(email)
//    }
//
//    @Test
//    fun `create user with empty fields`() {
//        val email = ""
//        val pass = ""
//        val fname = ""
//        val lname = ""
//        val confirmPass = ""
//
//        registrationUI.createAccount(fname, lname, email, pass, confirmPass)
//
//        verifyZeroInteractions(mockAuth)
//        verifyZeroInteractions(mockDbReference)
//    }
//
//    @Test
//    fun `create user with password not matching`() {
//        val email = "test@example.com"
//        val pass = "password"
//        val fname = "John"
//        val lname = "Doe"
//        val confirmPass = "differentpassword"
//
//        registrationUI.createAccount(fname, lname, email, pass, confirmPass)
//
//        verifyZeroInteractions(mockAuth)
//        verifyZeroInteractions(mockDbReference)
//    }
//
//    @Test
//    fun `create user with firebase error`() {
//        val email = "test@example.com"
//        val pass = "password"
//        val fname = "John"
//        val lname = "Doe"
//        val confirmPass = pass
//
//        val exception = mock(Exception::class.java)
//        `when`(exception.toString()).thenReturn("Firebase error message")
//        `when`(mockAuth.createUserWithEmailAndPassword(email, pass)).thenThrow(exception)
//
//        registrationUI.createAccount(fname, lname, email, pass, confirmPass)
//
//        verify(mockAuth).createUserWithEmailAndPassword(email, pass)
//        verifyZeroInteractions(mockDbReference)
//        assertEquals("Firebase error message", registrationUI.errorMessage)
//    }
//}
