package com.andresruiz.composablesnippets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresruiz.composablesnippets.segmentedcontrol.GenericSegmentedControl
import com.andresruiz.composablesnippets.segmentedcontrol.SegmentedControl
import com.andresruiz.composablesnippets.ui.theme.ComposableSnippetsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableSnippetsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color(0xFFFEF7FF))
                            .padding(16.dp)
                    ) {
                        GenericThousandsSeparatorSegmentedControl()
                    }
                }
            }
        }
    }
}

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
fun ThousandsSeparatorSegmentedControl(modifier: Modifier = Modifier) {

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
fun GenericThousandsSeparatorSegmentedControl(modifier: Modifier = Modifier) {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposableSnippetsTheme {
        Greeting("Android")
    }
}