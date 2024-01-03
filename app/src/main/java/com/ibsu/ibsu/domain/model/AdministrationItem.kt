package com.ibsu.ibsu.domain.model

data class AdministrationItem(
    val id: Int,
    val nameEn: String,
    val nameGe: String,
    val email: String?,
    val pictureURL: String?,
    val positionEn: String,
    val positionGe: String
)