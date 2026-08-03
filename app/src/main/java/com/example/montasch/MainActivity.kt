package com.example.montasch

import android.app.ActivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.montasch.ui.theme.MontaschTheme

private val Ink = Color(0xFF17222B)
private val MutedInk = Color(0xFF687681)
private val BrandOrange = Color(0xFFE56A32)
private val BrandNavy = Color(0xFF173B4D)
private val AppBackground = Color(0xFFF4F1EB)

private enum class AppPage(val label: String, val symbol: String) {
    TODAY("Сьогодні", "⌂"),
    OBJECTS("Обʼєкти", "▣"),
    TASKS("Завдання", "✓"),
    PROFILE("Профіль", "●")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MontaschTheme(dynamicColor = false) {
                MontaschApp(onExitKiosk = ::exitKioskMode)
            }
        }
    }

    private fun exitKioskMode() {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        if (activityManager.lockTaskModeState != ActivityManager.LOCK_TASK_MODE_NONE) {
            runCatching { stopLockTask() }
        }
        finishAndRemoveTask()
    }
}

@Composable
fun MontaschApp(
    modifier: Modifier = Modifier,
    onExitKiosk: () -> Unit = {}
) {
    var selectedPage by remember { mutableStateOf(AppPage.TODAY) }
    var showExitDialog by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        topBar = {
            MainNavigationBar(
                selectedPage = selectedPage,
                onPageSelected = { selectedPage = it },
                onExitKiosk = { showExitDialog = true }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            when (selectedPage) {
                AppPage.TODAY -> TodayPage()
                AppPage.OBJECTS -> PlaceholderPage("Робочі обʼєкти", "Тут зберігатимуться адреси, замовники та статус монтажу.", "＋ Додати обʼєкт") {
                    feedbackMessage = "Форму нового обʼєкта відкрито"
                }
                AppPage.TASKS -> PlaceholderPage("Завдання", "Перевіряйте етапи складання та відмічайте виконану роботу.", "＋ Нове завдання") {
                    feedbackMessage = "Нове завдання додано до плану"
                }
                AppPage.PROFILE -> PlaceholderPage("Профіль монтажника", "Особисті дані, зміни, бригада та налаштування застосунку.", "Редагувати профіль") {
                    feedbackMessage = "Редагування профілю увімкнено"
                }
            }

            feedbackMessage?.let { message ->
                Spacer(Modifier.height(18.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable { feedbackMessage = null },
                    color = Color(0xFFDCEFE5),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("✓  $message", Modifier.padding(14.dp), color = Color(0xFF216B45), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Вийти з режиму Kiosk?") },
            text = { Text("Режим блокування буде завершено, а застосунок закриється.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    onExitKiosk()
                }) {
                    Text("Вийти", color = Color(0xFFB83B25), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Скасувати") }
            }
        )
    }
}

@Composable
private fun TodayPage() {
    var secondTaskDone by remember { mutableStateOf(false) }
    var thirdTaskDone by remember { mutableStateOf(false) }

    Text("Доброго ранку, Андрію!", color = Ink, fontSize = 27.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(6.dp))
    Text("Понеділок, 3 серпня  •  Бригада №4", color = MutedInk, fontSize = 14.sp)
    Spacer(Modifier.height(22.dp))

    Surface(color = BrandNavy, shape = RoundedCornerShape(22.dp)) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusPill("В РОБОТІ")
                Spacer(Modifier.weight(1f))
                Text("Обʼєкт #024", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            }
            Spacer(Modifier.height(18.dp))
            Text("Кухня та гардеробна", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(7.dp))
            Text("вул. Зелена, 115 · квартира 42", color = Color.White.copy(alpha = 0.76f), fontSize = 14.sp)
            Spacer(Modifier.height(18.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                InfoChip("09:00", "Початок")
                InfoChip("5 з 8", "Етапів")
            }
        }
    }

    Spacer(Modifier.height(24.dp))
    SectionHeader("План на сьогодні", "3 завдання")
    Spacer(Modifier.height(12.dp))
    WorkTask("✓", "Перевірити комплектацію", "Виконано о 09:24", true) {}
    Spacer(Modifier.height(10.dp))
    WorkTask(if (secondTaskDone) "✓" else "2", "Зібрати нижні модулі", if (secondTaskDone) "Виконано" else "Кухня · 6 модулів", secondTaskDone) {
        secondTaskDone = !secondTaskDone
    }
    Spacer(Modifier.height(10.dp))
    WorkTask(if (thirdTaskDone) "✓" else "3", "Встановити стільницю", if (thirdTaskDone) "Виконано" else "Після складання модулів", thirdTaskDone) {
        thirdTaskDone = !thirdTaskDone
    }
}

@Composable
private fun MainNavigationBar(
    selectedPage: AppPage,
    onPageSelected: (AppPage) -> Unit,
    onExitKiosk: () -> Unit
) {
    Surface(color = Color.White, shadowElevation = 3.dp) {
        Column(Modifier.fillMaxWidth().statusBarsPadding().padding(top = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(BrandOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Text("M", color = Color.White, fontWeight = FontWeight.Black, fontSize = 21.sp)
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("MONTASCH", color = Ink, fontSize = 18.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Text("меблевий монтаж", color = MutedInk, fontSize = 10.sp)
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onExitKiosk,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE9E3), contentColor = Color(0xFFB83B25)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text("Вийти", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                AppPage.entries.forEach { page ->
                    NavigationTab(page, page == selectedPage) { onPageSelected(page) }
                }
            }
        }
    }
}

@Composable
private fun NavigationTab(page: AppPage, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)).clickable(onClick = onClick).padding(horizontal = 7.dp, vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(page.symbol, color = if (selected) BrandOrange else MutedInk, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(page.label, color = if (selected) BrandOrange else MutedInk, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium)
        Spacer(Modifier.height(5.dp))
        Box(Modifier.width(30.dp).height(3.dp).clip(CircleShape).background(if (selected) BrandOrange else Color.Transparent))
    }
}

@Composable
private fun StatusPill(label: String) {
    Box(Modifier.clip(CircleShape).background(BrandOrange).padding(horizontal = 11.dp, vertical = 5.dp)) {
        Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
    }
}

@Composable
private fun InfoChip(value: String, label: String) {
    Row(Modifier.clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)).padding(horizontal = 12.dp, vertical = 9.dp)) {
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Spacer(Modifier.width(6.dp))
        Text(label, color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
    }
}

@Composable
private fun SectionHeader(title: String, detail: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = Ink, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Text(detail, color = BrandOrange, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun WorkTask(number: String, title: String, description: String, completed: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(if (completed) Color(0xFFDCEFE5) else Color(0xFFFFE9DD)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, color = if (completed) Color(0xFF287A50) else BrandOrange, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(13.dp))
            Column {
                Text(title, color = if (completed) MutedInk else Ink, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(3.dp))
                Text(description, color = MutedInk, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun PlaceholderPage(title: String, description: String, action: String, onAction: () -> Unit) {
    Text(title, color = Ink, fontSize = 27.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text(description, color = MutedInk, fontSize = 15.sp, lineHeight = 22.sp)
    Spacer(Modifier.height(24.dp))
    Button(onClick = onAction, colors = ButtonDefaults.buttonColors(containerColor = BrandOrange), shape = RoundedCornerShape(14.dp)) {
        Text(action, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MontaschAppPreview() {
    MontaschTheme(dynamicColor = false) { MontaschApp() }
}
