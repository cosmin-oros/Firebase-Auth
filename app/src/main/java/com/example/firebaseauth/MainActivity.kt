package com.example.firebaseauth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebaseauth.ui.theme.FirebaseAuthTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthTheme {
                Scaffold (
                    content = {
                        FormContainer(this)
                    }
                        )
            }
        }
    }
}

@Composable
fun FormContainer(context: ComponentActivity) {
    val auth = Firebase.auth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val emailValue = remember { mutableStateOf(TextFieldValue()) }
        val passwordValue = remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            label = {
                Text("Email")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = emailValue.value,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                emailValue.value = it
            }
        )

        OutlinedTextField(
            label = {
                Text("Password")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = passwordValue.value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                passwordValue.value = it
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                auth.createUserWithEmailAndPassword(
                    emailValue.value.text.trim(),
                    passwordValue.value.text.trim()
                )
                    .addOnCompleteListener(context){ task ->
                        if (task.isSuccessful){
                            Log.d("AUTH","Success!")
                        }else{
                            Log.d("Auth","Failed: ${task.exception}")
                        }
                    }
            }
        ) {
            Text(text = "Register")
        }
    }
}