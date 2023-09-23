package com.ibsu.ibsu.data.remote.model

data class CoursesItem(
    val ETC: Int,
    val description_En: String?,
    val description_Ge: String?,
    val hours: Int?,
    val id: Int,
    val isCore: Boolean?,
    val nameEn: String,
    val nameGe: String,
    val prerequisites: List<String>?,
    val prerequisitesGe: List<String?>,
    val semesterNumber: Int?,
    val semesterSeason: String?
)