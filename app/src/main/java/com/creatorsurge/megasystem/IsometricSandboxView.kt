package com.creatorsurge.megasystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

enum class TileType(val label: String, val color: Color, val topColor: Color, val sideColor: Color) {
    GRASS("Grass", Color(0xFF4CAF50), Color(0xFF66BB6A), Color(0xFF388E3C)),
    WATER("Water", Color(0xFF2196F3), Color(0xFF42A5F5), Color(0xFF1976D2)),
    STONE("Stone", Color(0xFF9E9E9E), Color(0xFFBDBDBD), Color(0xFF616161)),
    SAND("Sand", Color(0xFFFFCA28), Color(0xFFFFD54F), Color(0xFFFFA000)),
    BUILDING("Structure", Color(0xFFE91E63), Color(0xFFEC407A), Color(0xFFC2185B))
}

data class TileEntity(
    val x: Int,
    val y: Int,
    var type: TileType,
    var height: Float = 0f,
    var hasAvatar: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IsometricSandboxView(
    onBackToDashboard: () -> Unit
) {
    val gridSize = 10
    val tileWidth = 80f
    val tileHeight = 40f

    var selectedTileType by remember { mutableStateOf(TileType.GRASS) }
    var panOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var scale by remember { mutableFloatStateOf(1f) }
    var lastTappedTile by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val gridMap = remember {
        mutableStateMapOf<Pair<Int, Int>, TileEntity>().apply {
            for (x in 0 until gridSize) {
                for (y in 0 until gridSize) {
                    val initialType = when {
                        (x == 2 && y == 2) || (x == 2 && y == 3) -> TileType.WATER
                        (x in 4..6 && y in 4..6) -> TileType.STONE
                        (x == 7 && y == 7) -> TileType.BUILDING
                        else -> TileType.GRASS
                    }
                    val avatar = (x == 5 && y == 5)
                    put(Pair(x, y), TileEntity(x, y, initialType, height = if (initialType == TileType.BUILDING) 20f else 0f, hasAvatar = avatar))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E293B))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SURGE OPEN WORLD",
                    color = Color(0xFF00E676),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(
                    text = "2D Isometric Sandbox",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Button(
                onClick = onBackToDashboard,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155))
            ) {
                Text("Dashboard", color = Color.White)
            }
        }

        // Palette Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E24))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tool:", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            TileType.values().forEach { type ->
                val isSelected = type == selectedTileType
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedTileType = type },
                    label = { Text(type.label, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = type.topColor,
                        selectedLabelColor = Color.Black,
                        containerColor = Color(0xFF2A2A32),
                        labelColor = Color.White
                    )
                )
            }
        }

        // Isometric Canvas Viewport
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = (scale * zoom).coerceIn(0.5f, 3.0f)
                        panOffset += pan
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        val center = Offset(size.width / 2f, size.height / 3f) + panOffset
                        val relativeX = (tapOffset.x - center.x) / scale
                        val relativeY = (tapOffset.y - center.y) / scale

                        // Inverse Isometric transformation
                        // isoX = (isoGridX - isoGridY) * (tileWidth / 2)
                        // isoY = (isoGridX + isoGridY) * (tileHeight / 2)
                        val gridX = ((relativeX / (tileWidth / 2f)) + (relativeY / (tileHeight / 2f))) / 2f
                        val gridY = ((relativeY / (tileHeight / 2f)) - (relativeX / (tileWidth / 2f))) / 2f

                        val tileX = gridX.roundToInt()
                        val tileY = gridY.roundToInt()

                        if (tileX in 0 until gridSize && tileY in 0 until gridSize) {
                            lastTappedTile = Pair(tileX, tileY)
                            val current = gridMap[Pair(tileX, tileY)]
                            if (current != null) {
                                gridMap[Pair(tileX, tileY)] = current.copy(
                                    type = selectedTileType,
                                    height = if (selectedTileType == TileType.BUILDING) 30f else 0f
                                )
                            }
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 3f) + panOffset

                // Render grid back-to-front
                for (x in 0 until gridSize) {
                    for (y in 0 until gridSize) {
                        val tile = gridMap[Pair(x, y)] ?: continue

                        // Isometric projection formula
                        val isoX = center.x + (x - y) * (tileWidth / 2f) * scale
                        val isoY = center.y + (x + y) * (tileHeight / 2f) * scale - (tile.height * scale)

                        drawIsometricTile(
                            center = Offset(isoX, isoY),
                            tileWidth = tileWidth * scale,
                            tileHeight = tileHeight * scale,
                            tile = tile,
                            isSelected = lastTappedTile == Pair(x, y)
                        )
                    }
                }
            }

            // HUD Overlay Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color(0xCC000000), shape = RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = "Pan / Pinch to Zoom active",
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
                lastTappedTile?.let { (tx, ty) ->
                    val tile = gridMap[Pair(tx, ty)]
                    Text(
                        text = "Tile ($tx, $ty): ${tile?.type?.label}",
                        color = Color(0xFF00E676),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                } ?: Text("Tap grid to place active tile type", color = Color.Gray, fontSize = 11.sp)
            }
        }
    }
}

fun DrawScope.drawIsometricTile(
    center: Offset,
    tileWidth: Float,
    tileHeight: Float,
    tile: TileEntity,
    isSelected: Boolean
) {
    val halfW = tileWidth / 2f
    val halfH = tileHeight / 2f

    val topPath = Path().apply {
        moveTo(center.x, center.y - halfH)
        lineTo(center.x + halfW, center.y)
        lineTo(center.x, center.y + halfH)
        lineTo(center.x - halfW, center.y)
        close()
    }

    // Draw Top Face
    drawPath(path = topPath, color = tile.type.topColor)

    // Outline
    drawPath(
        path = topPath,
        color = if (isSelected) Color.White else Color(0x33000000),
        style = Stroke(width = if (isSelected) 3f else 1f)
    )

    // Side face for 3D elevation
    val sideHeight = 12f
    val leftSidePath = Path().apply {
        moveTo(center.x - halfW, center.y)
        lineTo(center.x, center.y + halfH)
        lineTo(center.x, center.y + halfH + sideHeight)
        lineTo(center.x - halfW, center.y + sideHeight)
        close()
    }
    val rightSidePath = Path().apply {
        moveTo(center.x, center.y + halfH)
        lineTo(center.x + halfW, center.y)
        lineTo(center.x + halfW, center.y + sideHeight)
        lineTo(center.x, center.y + halfH + sideHeight)
        close()
    }

    drawPath(path = leftSidePath, color = tile.type.sideColor)
    drawPath(path = rightSidePath, color = tile.type.sideColor.copy(alpha = 0.8f))

    // Draw Avatar marker if present
    if (tile.hasAvatar) {
        drawCircle(
            color = Color(0xFFFFEA00),
            radius = halfH * 0.5f,
            center = Offset(center.x, center.y - halfH * 0.3f)
        )
    }
}
