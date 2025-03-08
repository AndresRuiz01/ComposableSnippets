package com.andresruiz.composablesnippets.segmentedcontrol

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun <T> GenericSegmentedControl(
    title: String,
    items: List<T>,
    selectedItem: T,
    onSelectedIndexChanged: (T) -> Unit,
    modifier: Modifier = Modifier
) {

    var indicatorOffset by remember { mutableIntStateOf(0) }

    val animatedOffset by animateIntAsState(
        targetValue = indicatorOffset,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        SegmentedControlTitle(
            title = title
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0x148138FF))
                .padding(4.dp)
        ) {
            // White box highlighting selected option
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1f / items.size)
                    .offset {
                        IntOffset(animatedOffset, 0)
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)

            )

            // List of segmented control options
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items.forEach { item ->
                    var xOffset by remember { mutableIntStateOf(0) }
                    val interactionSource = remember { MutableInteractionSource() }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .onGloballyPositioned {
                                xOffset = it.positionInParent().x.toInt()
                            }.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onSelectedIndexChanged(item)
                                indicatorOffset = xOffset
                            }
                    ) {
                        Text(
                            text = item.toString(),
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            letterSpacing = 0.sp,
                            color = if (item == selectedItem) Color.Black else Color(0xFF24005A),
                        )
                    }
                }
            }
        }
    }
}