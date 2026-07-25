package com.example.montasch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.montasch.ui.theme.MontaschTheme

private val Ink = Color(0xFF22283A)
private val MutedInk = Color(0xFF72798B)
private val BrandBlue = Color(0xFF5269E8)
private val AppBackground = Color(0xFFF7F8FC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MontaschTheme(dynamicColor = false) {
                MainMenu()
            }
        }
    }
}

@Composable
fun MainMenu(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        topBar = { MainNavigationBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Вітаємо!",
                color = Ink,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Оберіть, з чого бажаєте почати!",
                color = MutedInk,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(28.dp))
            MenuCard(
                symbol = "＋",
                title = "Створити запис",
                description = "Додайте нову подію або нагадування",
                accent = BrandBlue
            )
            Spacer(Modifier.height(16.dp))
            MenuCard(
                symbol = "≡",
                title = "Мої записи",
                description = "Переглядайте та впорядковуйте записи",
                accent = Color(0xFF2DAA83)
            )
            Spacer(Modifier.height(16.dp))
            MenuCard(
                symbol = "□",
                title = "Календар",
                description = "Плануйте справи та важливі дати",
                accent = Color(0xFFF39B4A)
            )
        }
    }
}

@Composable
private fun MainNavigationBar() {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(11.dp))
                        .background(BrandBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("M", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                }
                Spacer(Modifier.width(10.dp))
                Text("Montasch", color = Ink, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavigationTab("Головна", selected = true)
                NavigationTab("Записи")
                NavigationTab("Календар")
                NavigationTab("Профіль")
            }
        }
    }
}

@Composable
private fun NavigationTab(label: String, selected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = if (selected) BrandBlue else MutedInk,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
        )
        Box(
            Modifier
                .width(if (selected) 34.dp else 0.dp)
                .height(3.dp)
                .clip(CircleShape)
                .background(if (selected) BrandBlue else Color.Transparent)
        )
    }
}

@Composable
private fun MenuCard(symbol: String, title: String, description: String, accent: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(accent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(symbol, color = accent, fontSize = 28.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Ink, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(5.dp))
                Text(description, color = MutedInk, fontSize = 14.sp, lineHeight = 19.sp)
            }
            Text("›", color = Color(0xFFB1B6C4), fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainMenuPreview() {
    MontaschTheme(dynamicColor = false) {
        MainMenu()
    }
}