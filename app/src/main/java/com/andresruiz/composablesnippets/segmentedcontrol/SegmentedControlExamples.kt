package com.andresruiz.composablesnippets.segmentedcontrol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ThousandsSeparator {
    DOT {
        override fun toString() = "1.000"
    },
    COMMA {
        override fun toString() = "1,000"
    },
    SPACE {
        override fun toString() = "1 000"
    }
}

@Composable
fun SegmentedControlExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ThousandsSeparatorSegmentedControl()
        GenericThousandsSeparatorSegmentedControl()
    }
}

@Composable
private fun ThousandsSeparatorSegmentedControl(modifier: Modifier = Modifier) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    SegmentedControl(
        title = "Thousands separator",
        items = listOf("1.000", "1,000", "1 000"),
        selectedIndex = selectedIndex,
        onSelectedIndexChanged = { index ->
            selectedIndex = index
        },
    )
}

@Composable
private fun GenericThousandsSeparatorSegmentedControl(modifier: Modifier = Modifier) {
    val items = listOf(ThousandsSeparator.DOT, ThousandsSeparator.COMMA, ThousandsSeparator.SPACE)
    val selectedItem = remember { mutableStateOf(ThousandsSeparator.DOT) }

    GenericSegmentedControl(
        title = "Generic Thousands Separator",
        items = items,
        selectedItem = selectedItem.value,
        onSelectedIndexChanged = { selectedItem.value = it },
        modifier = modifier
    )
}
