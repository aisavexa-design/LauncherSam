package com.launchersam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(apps: List<AppItem>, columns: Int = 5) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101418))
            .padding(top = 48.dp, start = 8.dp, end = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(apps, key = { it.packageName + it.activityName }) { app ->
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .clickable { AppRepository.launch(context, app) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val icon = IconCache.get(context, app.packageName)
                if (icon != null) {
                    Image(
                        bitmap = icon.asImageBitmap(),
                        contentDescription = app.label,
                        modifier = Modifier.size(56.dp)
                    )
                } else {
                    Box(Modifier.size(56.dp).background(Color.Gray, RoundedCornerShape(14.dp)))
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = app.label,
                    color = Color.White,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}
