package com.xash.gymapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xash.gymapp.components.CategoryChip
import com.xash.gymapp.components.WorkoutCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen() {
    val categories = listOf("All", "Strength", "Cardio", "Flexibility", "HIIT", "Recovery")
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Workouts",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Workout"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Categories
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        text = category,
                        selected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Workouts list
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Featured Workouts",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(5) { index ->
                    val workouts = listOf(
                        Triple("Full Body Blast", "45 min • Intermediate", 12),
                        Triple("Upper Body Focus", "30 min • Advanced", 10),
                        Triple("Core Crusher", "25 min • Intermediate", 8),
                        Triple("Leg Day Challenge", "40 min • Advanced", 9),
                        Triple("HIIT Cardio", "20 min • Beginner", 6)
                    )

                    val (title, subtitle, exercises) = workouts[index]

                    WorkoutCard(
                        title = title,
                        time = "",
                        duration = subtitle.split("•")[0].trim(),
                        difficulty = subtitle.split("•")[1].trim(),
                        exercises = exercises,
                        showStartButton = true
                    )
                }

                item {
                    Text(
                        text = "Recommended For You",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                items(3) { index ->
                    val workouts = listOf(
                        Triple("Recovery Day", "20 min • Beginner", 5),
                        Triple("Mobility Flow", "15 min • Beginner", 7),
                        Triple("Quick Burn", "10 min • Intermediate", 4)
                    )

                    val (title, subtitle, exercises) = workouts[index]

                    WorkoutCard(
                        title = title,
                        time = "",
                        duration = subtitle.split("•")[0].trim(),
                        difficulty = subtitle.split("•")[1].trim(),
                        exercises = exercises,
                        showStartButton = true
                    )
                }
            }
        }
    }
}
