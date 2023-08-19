package com.ibsu.ibsu.data.remote.model

data class AdministrationItem(
    val id: Int,
    val nameEn: String,
    val nameGe: String,
    val pictureURL: String?,
    val positionEn: String,
    val positionGe: String
)