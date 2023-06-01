package com.emikhalets.fitness.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType

@Composable
fun WorkoutTypeHeader(
    type: WorkoutType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = type.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(16.dp)
                .align(Alignment.CenterStart)
        )
    }
}

@Composable
fun WorkoutStagesList(
    workoutList: List<WorkoutEntity>,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .border(width = 2.dp, color = MaterialTheme.colors.onBackground)
    ) {
        items(workoutList) { workout ->
            WorkoutStageBox(
                workout = workout,
                onDoneTypeChange = onDoneTypeChange,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun WorkoutStageBox(
    workout: WorkoutEntity,
    onDoneTypeChange: (WorkoutEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val fontSize by remember { mutableStateOf(14.sp) }
    val verticalRowPadding by remember { mutableStateOf(8.dp) }

    val color = when (workout.doneType) {
        WorkoutDoneType.NOT_DONE -> Color.Transparent
        WorkoutDoneType.DONE -> Color.Green.copy(alpha = 0.5f)
        WorkoutDoneType.REPEAT -> Color.Blue.copy(alpha = 0.2f)
    }
    Row(modifier = modifier.background(color = color)) {
        Text(
            text = "${workout.stage}",
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(0.4f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(3f)
        ) {
            workout.reps.forEach { rep ->
                Text(
                    text = "$rep",
                    fontSize = fontSize
                )
            }
        }
        Text(
            text = "${workout.repsCount}",
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(vertical = verticalRowPadding)
                .weight(0.6f)
        )
        Text(
            text = workout.doneType.title,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.1f))
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .clickable {
                    onDoneTypeChange(workout.copy(doneType = changeDoneType(workout.doneType)))
                }
                .padding(vertical = verticalRowPadding)
                .weight(1.2f)
        )
    }
}

private fun changeDoneType(instant: WorkoutDoneType): WorkoutDoneType {
    return when (instant) {
        WorkoutDoneType.NOT_DONE -> WorkoutDoneType.DONE
        WorkoutDoneType.DONE -> WorkoutDoneType.REPEAT
        WorkoutDoneType.REPEAT -> WorkoutDoneType.NOT_DONE
    }
}