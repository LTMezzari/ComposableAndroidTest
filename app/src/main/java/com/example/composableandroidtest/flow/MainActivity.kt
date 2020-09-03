package com.example.composableandroidtest.flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.composableandroidtest.ui.ComposableAndroidTestTheme

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableAndroidTestTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        horizontalGravity = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalGravity = Alignment.CenterVertically
                        ) {
                            AddressField(viewModel.cep)
                            SearchButton {
                                viewModel.getAddress()
                            }
                        }
                        Row(
                            verticalGravity = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            LoaderView(viewModel.isLoading)
                        }
                        Row(
                            verticalGravity = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ErrorView(viewModel.addressError)
                        }
                        Row {
                            AddressView(viewModel.address)
                        }
                    }
                }
            }
        }
    }
}