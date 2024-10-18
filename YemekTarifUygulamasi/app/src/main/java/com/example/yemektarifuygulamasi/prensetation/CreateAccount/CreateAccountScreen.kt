package com.example.yemektarifuygulamasi.prensetation.CreateAccount

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()



    LaunchedEffect(key1 = true) {
            userSignOut(auth)
        //checkUser(auth = auth , navController = navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isRegistering) "Create Account" else "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (isRegistering) {
                    createAccount(email, password  , context , auth = auth) { success, message ->
                        errorMessage = message

                    }
                } else {
                    userLogin(email, password , context = context  , auth, navController) { success, message ->
                        errorMessage = message
                    }

                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(if (isRegistering) "Register" else "Login", color = Color.White)
        }

        TextButton(onClick = { isRegistering = !isRegistering }) {
            Text(if (isRegistering) "Already have an account? Login" else "Don't have an account? Register")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun checkUser(auth: FirebaseAuth , navController: NavController) {
val currentUser = auth.currentUser
    if (currentUser != null){
        //navController.navigate("RandomRecipes")
    }
}

fun createAccount(email: String, password: String , context: Context , auth: FirebaseAuth, onResult: (Boolean, String) -> Unit ) {

    if (email.isNotEmpty() && password.isNotEmpty()){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("Kullanıcı Oluştu", "$email - $password")
                    onResult(true, "Kullanıcı başarıyla oluşturuldu!")

                } else {
                    val errorMessage = when(task.exception?.message){
                        "The email address is badly formatted." -> "Geçersiz email formatı."
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> "Şifre en az 6 karakter olmalı."
                        "The email address is already in use by another account." -> "Bu email zaten kullanılıyor."
                        else -> task.exception?.message ?: "Kullanıcı oluşturulamadı."
                    }
                    Log.e("Kullanıcı Oluşmadı", "$email - $password")
                    onResult(false, errorMessage)
                }
            }
    }else{
        Toast.makeText(context , "şifre ve email boş olamaz!!" , Toast.LENGTH_SHORT).show()

    }

}

@SuppressLint("SuspiciousIndentation")
fun userLogin(email: String, password: String , context: Context , auth: FirebaseAuth , navController: NavController, onResult: (Boolean, String) -> Unit) {

    if (email.isNotEmpty() && password.isNotEmpty()){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("Giriş Yapıldı", "$password - $email")
                    navController.navigate(route = "RandomRecipes"){
                        popUpTo("AuthenticationScreen"){inclusive = true}
                    }
                    onResult(true, "Giriş başarılı!")

                } else {
                    val errorMessage = when (task.exception?.message){
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> "Kullanıcı bulunamadı."
                        "The password is invalid or the user does not have a password." -> "Geçersiz şifre."
                        "The email address is badly formatted." -> "Geçersiz email formatı."
                        else -> task.exception?.message ?: "Giriş yapılamadı."
                    }
                    onResult(false, errorMessage)
                }
            }
    }else{
        Toast.makeText(context , "şifre ve email boş olamaz!!" , Toast.LENGTH_SHORT).show()
    }

}


fun userSignOut(auth: FirebaseAuth){
    auth.signOut()
    Log.e("kullanıcı ", "oturum kapandı")
}



