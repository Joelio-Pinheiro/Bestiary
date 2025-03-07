package com.example.bestiariodd.model


data class Monster(
    val index: String,
    val name: String,
    val type: String,
    val hit_points: Int,
)
data class MonsterDetails(
    val index: String,
    val name: String,
    val type: String,
    val hit_points: Int,
    val hit_points_roll: String,
    val image: String,
    val alignment: String,
    val armor_class: List<ArmorClass>,
    val special_abilities: List<Ability>,
    val actions: List<Action>,

    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
)
data class ArmorClass(
    val type: String,
    val value: Int,
)
data class Ability(
    val name: String,
    val desc: String,
)
data class Action(
    val name: String,
    val desc: String,
)

data class ResultsMonster(
    val index: String,
    val name: String,
    val url: String,
)
data class MonsterResponse(
    val count: Int,
    val results: List<ResultsMonster>
)
