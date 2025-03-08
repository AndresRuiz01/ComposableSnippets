package com.andresruiz.composablesnippets.battery

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.andresruiz.composablesnippets.R
import kotlinx.coroutines.launch

@Composable
fun BatteryClover(
    percentage: Int,
) {
    val scope = rememberCoroutineScope()

    var isHighPower by remember { mutableStateOf(percentage >= 80) }

    LaunchedEffect(percentage) {
        isHighPower = percentage >= 80
    }

    val animationScale = remember { Animatable(1f) }
    val animationColor = remember { androidx.compose.animation.Animatable(Color(0xFFC1C5D2)) }

    LaunchedEffect(isHighPower) {
        if (isHighPower) {
            scope.launch {
                launch {
                    animationScale.animateTo(
                        targetValue = 1.4f,
                        animationSpec = tween(500)
                    )
                }
                launch {
                    animationColor.animateTo(
                        targetValue = Color(0xFF19D181),
                        animationSpec = tween(500)
                    )
                }
            }
        } else {
            scope.launch {
                launch {
                    animationScale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(500)
                    )
                }
                launch {
                    animationColor.animateTo(
                        targetValue = Color(0xFFC1C5D2),
                        animationSpec = tween(500)
                    )
                }
            }
        }
    }

    Icon(
        painter = painterResource(id = R.drawable.union),
        contentDescription = null, // decorative element
        tint = animationColor.value,
        modifier = Modifier
            .scale(animationScale.value)
    )

}