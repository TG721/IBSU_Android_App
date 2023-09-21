package com.ibsu.ibsu.data.remote.model

data class ProgramAdminItem(
    val email: String,
    val id: Int,
    val nameEn: String,
    val nameGe: String,
    val phone: String?,
    val positionEn: String,
    val positionGe: String,
    val room: String?,
)