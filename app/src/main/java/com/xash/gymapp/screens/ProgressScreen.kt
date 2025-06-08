package com.xash.gymapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xash.gymapp.components.ProgressMetricCard
import kotlin.random.Random
import androidx.compose.ui.graphics.Color as ComposeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen() {
    var selectedPeriod by remember { mutableStateOf("This Week") }
    val periods = listOf("This Week", "This Month", "3 Months", "6 Months", "Year")
    var showPeriodMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Period selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.titleLarge
                )

                Box {
                    Button(
                        onClick = { showPeriodMenu = true },
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(selectedPeriod)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Period"
                        )
                    }

                    DropdownMenu(
                        expanded = showPeriodMenu,
                        onDismissRequest = { showPeriodMenu = false }
                    ) {
                        periods.forEach { period ->
                            DropdownMenuItem(
                                text = { Text(period) },
                                onClick = {
                                    selectedPeriod = period
                                    showPeriodMenu = false
                                }
                            )
                        }
                    }
                }
            }

            // Progress metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProgressMetricCard(
                    title = "Workouts",
                    value = "12",
                    change = "+3",
                    positive = true,
                    modifier = Modifier.weight(1f)
                )

                ProgressMetricCard(
                    title = "Calories",
                    value = "8,540",
                    change = "+12%",
                    positive = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProgressMetricCard(
                    title = "Time",
                    value = "6h 20m",
                    change = "+45m",
                    positive = true,
                    modifier = Modifier.weight(1f)
                )

                ProgressMetricCard(
                    title = "Weight",
                    value = "165 lbs",
                    change = "-2.5",
                    positive = true,
                    modifier = Modifier.weight(1f)
                )
            }

            val chartColor = ComposeColor(MaterialTheme.colorScheme.primary.toArgb())


            // Activity chart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Activity",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        // Simple line chart
                        val points = remember {
                            List(7) { Random.nextInt(20, 80) }
                        }

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val width = size.width
                            val height = size.height
                            val stepX = width / (points.size - 1)

                            // Draw horizontal grid lines
                            val strokeWidth = 1f
                            val gridColor = Color.LightGray.copy(alpha = 0.5f)

                            for (i in 0..4) {
                                val y = height * i / 4
                                drawLine(
                                    color = gridColor,
                                    start = Offset(0f, y),
                                    end = Offset(width, y),
                                    strokeWidth = strokeWidth
                                )
                            }

                            // Draw line chart
                            val path = Path()
                            val maxValue = points.maxOrNull()?.toFloat() ?: 100f

                            points.forEachIndexed { index, value ->
                                val x = index * stepX
                                val y = height - (value / maxValue * height)

                                if (index == 0) {
                                    path.moveTo(x, y)
                                } else {
                                    path.lineTo(x, y)
                                }

                                // Draw points
                                drawCircle(
                                    color = chartColor,
                                    radius = 8f,
                                    center = Offset(x, y)
                                )
                            }

                            // Draw line
                            drawPath(
                                path = path,
                                color = chartColor,
                                style = Stroke(width = 4f)
                            )
                        }

                        // X-axis labels
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                            days.forEach { day ->
                                Text(
                                    text = day,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }

            // Personal records
            Text(
                text = "Personal Records",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RecordItem(
                        exercise = "Bench Press",
                        value = "185 lbs",
                        date = "May 15"
                    )

                    Divider()

                    RecordItem(
                        exercise = "Squat",
                        value = "225 lbs",
                        date = "May 12"
                    )

                    Divider()

                    RecordItem(
                        exercise = "Deadlift",
                        value = "275 lbs",
                        date = "May 10"
                    )

                    Divider()

                    RecordItem(
                        exercise = "Pull-ups",
                        value = "12 reps",
                        date = "May 18"
                    )
                }
            }
        }
    }
}

@Composable
fun RecordItem(
    exercise: String,
    value: String,
    date: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = exercise,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
