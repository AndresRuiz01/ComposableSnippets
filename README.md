
### Generic Segmented Control

1. Define the `Enum` or `Class` with a `toString()` override


```kotlin
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
```
2. Utilize class in `GenericSegmentedControl`

```kotlin
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
```

https://github.com/user-attachments/assets/6feb0497-a319-4e3e-977c-c573d0939c5e

### Battery Indicator

https://github.com/user-attachments/assets/45a6ea97-dab9-462e-819f-3d5012c39619









