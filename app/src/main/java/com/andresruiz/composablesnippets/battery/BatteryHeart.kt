package com.andresruiz.composablesnippets.battery

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BatteryHeart(
    percentage: Int,
) {

    val scope = rememberCoroutineScope()
    var isLowPower by remember { mutableStateOf(percentage <= 20) }

    LaunchedEffect(percentage) {
        isLowPower = percentage <= 20
    }

    val animationScale = remember { Animatable(1f) }
    val animationColor = remember {
        androidx.compose.animation.Animatable(
            if (isLowPower) Color(0xFFFF4E51) else Color(0xFFC1C5D2)
        )
    }

    LaunchedEffect(isLowPower) {
        if (isLowPower) {
            scope.launch {
                launch {
                    animationColor.animateTo(
                        targetValue = Color(0xFFFF4E51),
                        animationSpec = tween(500)
                    )
                }
                launch {
                    animationScale.animateTo(
                        targetValue = 1.2f,
                        animationSpec = tween(500)
                    )
                }
                launch {
                    delay(500)
                    launch {
                        animationColor.animateTo(
                            targetValue = Color(0xFFFF7E81),
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 500, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                    }
                    launch {
                        animationScale.animateTo(
                            targetValue = 1.4f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 500, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                    }
                }
            }
        } else {
            // return to default size
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
        painter = painterResource(id = R.drawable.heart),
        contentDescription = null, // decorative element
        tint = animationColor.value,
        modifier = Modifier
            .scale(animationScale.value)
    )

}