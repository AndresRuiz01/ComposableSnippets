package com.andresruiz.composablesnippets.battery

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BatteryIndicator() {

    val context = LocalContext.current

    // battery percentage and color
    var batteryPercentage by remember { mutableFloatStateOf(getBatteryPercentage(context) / 100f) }
    var batteryColor by remember { mutableStateOf(getBatteryColor((getBatteryPercentage(context)).toInt())) }


    // animate battery percentage and color
    val animatedBatteryPercentage = animateFloatAsState(
        targetValue = batteryPercentage,
    )

    val animatedBatteryColor = animateColorAsState(
        targetValue = batteryColor,
        animationSpec = tween(500)
    )

    // register battery broadcast receiver
    DisposableEffect(context) {
        val receiver = BatteryBroadcastReceiver { percentage ->
            batteryPercentage = percentage / 100f
            batteryColor = getBatteryColor(percentage)
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
    ) {

        // heart
        BatteryHeart(percentage = (batteryPercentage * 100).toInt())

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // full battery background
            Box(
                modifier = Modifier
                    .width(218.dp)
                    .height(68.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(4.dp)

            ) {
                // battery level and color
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(animatedBatteryPercentage.value)
                        .clip(RoundedCornerShape(12.dp))
                        .background(animatedBatteryColor.value)
                )

                // battery level splitters
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                                .background(Color(0x88FFFFFF))
                        )
                    }
                }
            }

            // battery edge
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(25.dp)
                    .offset(x = (-4).dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)

            )
        }

        // clover
        BatteryClover(percentage = (batteryPercentage * 100).toInt())
    }
}

fun getBatteryColor(percentage: Int) : Color {
    return when (percentage) {
        in 0..20 -> Color(0xFFFF4E51)
        in 21 .. 79 -> Color(0xFFFCB966)
        in 80..100 -> Color(0xFF19D181)
        else -> Color(0xFF000000)
    }
}

fun getBatteryPercentage(context: Context): Int {
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}