package com.example.weatherforecast.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecast.components.WeatherAppBar
import com.example.weatherforecast.navigation.WeatherScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Search",
            navigationIcon = Icons.Default.ArrowBack,
            isMainScree = false,
            elevation = 0.dp,
            navController = navController
        ){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {cityName ->
                    Log.d("TAG", "SearchScreen: $cityName")
                    navController.navigate(WeatherScreens.HomeScreen.name.plus("/$cityName")){
                        popUpTo(WeatherScreens.SearchScreen.name){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }
        val keyController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        CommonTextFiled(valueState = searchQueryState, placeHolder = "Search city",
            onAction = KeyboardActions {
                if (!valid) {
                    searchQueryState.value = ""
                    return@KeyboardActions
                }
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyController?.hide()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextFiled(
    valueState: MutableState<String>,
    placeHolder: String,
    onAction: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = placeHolder)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}
