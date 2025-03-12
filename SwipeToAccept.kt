/**
 * Call this Method from your onCreate to show this button
 * once onClaim Callback has come update the icon, disable the draggable and change button text.
 */
@Composable
fun ShowButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        var icon by remember { mutableStateOf(Icons.AutoMirrored.Default.ArrowForward) }
        var buttonText by remember { mutableStateOf(Swipe to Accept the call) }
        val maximumOffset = with(LocalDensity.current) {
            (300.dp - 50.dp - 8.dp).toPx() // layout width - icon width - padding
        }
        var dragEnabled by remember { mutableStateOf(true) }

        SwipeToClaimButton(
            buttonText = buttonText,
            icon = icon,
            maximumOffset = maximumOffset,
            dragEnabled = dragEnabled,
            modifier = Modifier
                .width(300.dp)
                .height(55.dp)
        ) {
            icon = Icons.Default.Done
            buttonText = Accepted
            dragEnabled = false
        }
    }
}


@Composable
fun SwipeToClaimButton(
    modifier: Modifier,
    buttonText: String,
    icon: ImageVector,
    maximumOffset: Float,
    dragEnabled: Boolean,
    onClaim: () -> Unit,
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = FloatAnimation)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF166FE5), Color(0xFF7E39FB))))
            .padding(horizontal = 5.dp, vertical = 4.dp)
            .draggable(
                enabled = dragEnabled,
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX = (offsetX + delta).coerceIn(0f, maximumOffset)
                },
                onDragStopped = {
                    if (offsetX > maximumOffset * 0.8f) {
                        offsetX = maximumOffset
                        onClaim()
                    } else {
                        offsetX = 0f
                    }
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedContent(targetState = buttonText, label =) {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.toInt(), 0) }
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = Swipe, tint = Color.Blue)
        }

    }
}
