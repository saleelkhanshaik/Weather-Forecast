package com.example.weatherforecast.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: ImageVector? = null,
    isMainScree: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title, style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 4.dp),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = {
                    onButtonClicked.invoke()
                }) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "backArrow_icon",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }

        },
        actions = {
            if (isMainScree) {
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search_icon")
                }
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "menu_icon")
                }
            } else Box {

            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}
