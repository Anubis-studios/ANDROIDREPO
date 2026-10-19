package com.creatorsurge.megasystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MegasystemMode(val label: String, val description: String) {
    ORCHESTRATOR("MODE_ORCHESTRATOR", "Multi-agent routing, tool selection, & task decomposition"),
    DEV("MODE_DEV", "Architecture, Kotlin/Compose code, infra & MCP wiring"),
    CREATOR("MODE_CREATOR", "2D Isometric World Sandbox, UX & Content Studio"),
    RESEARCH("MODE_RESEARCH", "Data gathering, synthesis & long-context memory")
}

data class AgentState(
    val name: String,
    val role: String,
    val status: String,
    val activeTask: String,
    val color: Color
)

data class MemoryEntry(
    val type: String,
    val title: String,
    val content: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegasystemDashboard(
    currentMode: MegasystemMode,
    onModeSelected: (MegasystemMode) -> Unit,
    onOpenSandboxClicked: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val agents = remember {
        listOf(
            AgentState("AGENT_ARCHITECT", "System Design & Infra", "ACTIVE", "Designing Isometric Engine API v2.0", Color(0xFF00E676)),
            AgentState("AGENT_ENGINEER", "Kotlin / Compose Implementation", "WORKING", "Compiling Compose Canvas Renderer", Color(0xFF29B6F6)),
            AgentState("AGENT_DESIGNER", "World & UX Design", "IDLE", "Awaiting World Sandbox Trigger", Color(0xFFFFB74D)),
            AgentState("AGENT_RESEARCHER", "Data & Context Synthesis", "ACTIVE", "Indexing Virtual Project Memory", Color(0xFFAB47BC))
        )
    }

    val memoryEntries = remember {
        listOf(
            MemoryEntry("SESSION_MEMORY", "Current Session", "Goal: Enable interactive 2D isometric sandbox & multi-mode dashboard."),
            MemoryEntry("PROJECT_MEMORY", "Surge Open World", "Architecture: Native Kotlin + Compose Canvas; Isometric tiles; WebSocket ready."),
            MemoryEntry("KNOWLEDGE_MEMORY", "Megasystem v2.0", "Core Directives: Deep reasoning, minimal redundancy, complete runnable artifacts.")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CREATOR SURGE STUDIOS",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF00E676),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Megasystem v2.0",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Button(
                onClick = onOpenSandboxClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Launch Sandbox", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mode Switcher Banner
        Text(
            text = "SYSTEM OPERATIONAL MODE",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MegasystemMode.values().forEach { mode ->
                val isSelected = mode == currentMode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (isSelected) Color(0xFF1E293B) else Color(0xFF1E1E1E),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) Color(0xFF00E676) else Color(0xFF333333),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onModeSelected(mode) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mode.name.replace("MODE_", ""),
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFF00E676) else Color.LightGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = currentMode.label,
                    color = Color(0xFF00E676),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = currentMode.description,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFF1A1A1A),
            contentColor = Color(0xFF00E676)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Agents Monitor", fontSize = 13.sp) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Virtual Memory", fontSize = 13.sp) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedTab) {
            0 -> AgentMonitorList(agents = agents)
            1 -> MemoryLogList(memoryEntries = memoryEntries)
        }
    }
}

@Composable
fun AgentMonitorList(agents: List<AgentState>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(agents) { agent ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF18181C)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(agent.color, shape = RoundedCornerShape(5.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = agent.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = agent.status,
                                color = agent.color,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Text(
                            text = agent.role,
                            color = Color.Gray,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Task: ${agent.activeTask}",
                            color = Color.LightGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryLogList(memoryEntries: List<MemoryEntry>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(memoryEntries) { entry ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF18181C)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = entry.type,
                        color = Color(0xFF00E676),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = entry.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = entry.content,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
