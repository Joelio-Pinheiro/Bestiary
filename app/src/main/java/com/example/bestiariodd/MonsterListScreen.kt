package com.example.bestiariodd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bestiariodd.model.ResultsMonster
import com.example.bestiariodd.viewmodel.MonsterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterListScreen(navController: NavHostController, viewModel: MonsterViewModel = viewModel()) {
    val resultsMonsters by viewModel.resultsMonsters.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column {
            TopAppBar(
                modifier = Modifier.height(100.dp),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF121212)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dd_logo_9_3800820979_removebg_preview),
                            contentDescription = "Logo",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF121212)
                ),
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(resultsMonsters) { monster ->
                    MonsterCard(monster, viewModel, navController = navController)
                }
            }
        }
    }
}


@Composable
fun MonsterCard(monster: ResultsMonster, viewModel: MonsterViewModel, navController: NavHostController) {
    val monsterDetails by viewModel.monsterDetails.collectAsState()

    val details = monsterDetails[monster.index]

    LaunchedEffect(monster.index) {
        if (details == null) {
            viewModel.fetchMonsterDetails(monster.index)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable {
                navController.navigate("monsterDetail/${monster.index}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D2D2D))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = monster.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFFFFD700)
                )
                if (details != null) {
                    Text(
                        text = "Type: ${details.type}",
                        fontSize = 16.sp,
                        color = Color(0xFFBB86FC)
                    )
                    Text(
                        text = "HP: ${details.hit_points}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE63946)
                    )
                } else {
                    Text(
                        text = "Carregando...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
