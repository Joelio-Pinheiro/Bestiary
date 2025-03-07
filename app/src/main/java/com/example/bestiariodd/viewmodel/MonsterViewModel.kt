package com.example.bestiariodd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiariodd.model.Monster
import com.example.bestiariodd.model.ResultsMonster
import com.example.bestiariodd.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MonsterViewModel : ViewModel() {

    private val _monsters = MutableStateFlow<List<ResultsMonster>>(emptyList())
    val monsters: StateFlow<List<ResultsMonster>> = _monsters

    private val _resultsMonsters = MutableStateFlow<List<ResultsMonster>>(emptyList())
    val resultsMonsters: StateFlow<List<ResultsMonster>> = _resultsMonsters


    private val _monsterDetails = MutableStateFlow<Map<String, Monster>>(emptyMap())
    val monsterDetails: StateFlow<Map<String, Monster>> = _monsterDetails

    init {
        fetchMonsters()
    }

    private fun fetchMonsters() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMonsters()
                _resultsMonsters.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchMonsterDetails(index: String) {
        if (_monsterDetails.value.containsKey(index)) return

        viewModelScope.launch {
            try {
                val monster = RetrofitInstance.api.getMonster(index)
                _monsterDetails.value = _monsterDetails.value + (index to monster)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
