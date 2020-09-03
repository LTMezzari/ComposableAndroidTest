package com.example.composableandroidtest.flow

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.ui.tooling.preview.Preview
import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.ui.ComposableAndroidTestTheme
import com.example.composableandroidtest.ui.observe

@Composable
fun AddressField(data: MutableLiveData<String>) {
    val text = remember { mutableStateOf(TextFieldValue(data.value ?: "")) }

    observe(data) {
        onResult {
            if (text.value.text != result)
                text.value = TextFieldValue(result)
        }
    }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            data.value = it.text
        },
        label = {
            Text("Cep")
        }
    )
}

@Composable
fun SearchButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "Search")
    }
}

@Composable
fun AddressView(data: LiveData<Address>) {
    val address = remember { mutableStateOf(data.value) }

    observe(data) {
        onResult {
            if (address.value != result)
                address.value = result
        }
    }

    val a = address.value ?: return

    Column {
        Row {
            Text(text = "Rua: ${a.street}")
        }
        Row {
            Text(text = "Bairro: ${a.neighborhood}")
        }
    }
}

@Composable
fun LoaderView(data: LiveData<Boolean>) {
    val loading = remember { mutableStateOf(data.value ?: false) }
    observe(data) {
        onResult {
            if (loading.value != result)
                loading.value = result
        }
    }

    if (!loading.value)
        return

    CircularProgressIndicator(
        color = Color.Black
    )
}

@Composable
fun ErrorView(data: LiveData<String>) {
    val error = remember { mutableStateOf(data.value) }
    observe(data) {
        onResult {
            if (error.value != result)
                error.value = result
        }
    }

    val message = error.value ?: return

    Text(text = message)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableAndroidTestTheme {
        Column(
            horizontalGravity = Alignment.CenterHorizontally
        ) {
            Row(
                verticalGravity = Alignment.CenterVertically
            ) {
                AddressField(MutableLiveData())
                SearchButton {

                }
            }
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                LoaderView(MutableLiveData(true))
            }
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ErrorView(MutableLiveData("Erro"))
            }
            Row {
                AddressView(MutableLiveData(
                    Address(
                        "",
                        "Rua dos bobos",
                        "asda",
                        "Número 0",
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                ))
            }
        }
    }
}