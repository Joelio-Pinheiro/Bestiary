package com.example.bestiariodd

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bestiariodd.model.MonsterDetails
import com.example.bestiariodd.network.RetrofitInstance

@Composable
fun MonsterDetailScreen(monsterIndex: String) {
    var monster by remember { mutableStateOf<MonsterDetails?>(null) }

    LaunchedEffect(monsterIndex) {
        try {
            monster = RetrofitInstance.api.getMonsterDetails(monsterIndex)
        } catch (e: Exception) {
            println("Erro ao buscar o monstro: ${e.message}")
        }
    }

    monster?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E))
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://www.dnd5eapi.co" + it.image),
                contentDescription = "Monster image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = it.name,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(0xFFFFD700),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "HP: ${it.hit_points} (${it.hit_points_roll})",
                    fontSize = 18.sp,
                    color = Color(0xFFFF0000)
                )
                Text(
                    text = "Armor Class: ${it.armor_class[0].value}",
                    fontSize = 18.sp,
                    color = Color(0xFF00BFFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Type: ${it.type}", fontSize = 18.sp, color = Color(0xFFBB86FC))
                Text(text = "Alignment: ${it.alignment}", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AttributeItem("STR", it.strength)
                AttributeItem("DEX", it.dexterity)
                AttributeItem("CON", it.constitution)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AttributeItem("INT", it.intelligence)
                AttributeItem("WIS", it.wisdom)
                AttributeItem("CHA", it.charisma)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Special Skills:",
                fontSize = 22.sp,
                color = Color(0xFFFF9800),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
            it.special_abilities.forEach { ability ->
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp)
                ) {
                    Text(
                        text = ability.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFFFFD700),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = ability.desc,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Actions:",
                fontSize = 22.sp,
                color = Color(0xFFFF9800),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
            it.actions.forEach { action ->
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp)
                ) {
                    Text(
                        text = action.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFFFFD700),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = action.desc,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun AttributeItem(attribute: String, value: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = attribute,
            fontSize = 18.sp,
            color = Color.White
        )
        Text(
            text = value.toString(),
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
