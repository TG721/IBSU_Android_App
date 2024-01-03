package com.ibsu.ibsu.domain.model

data class GovernanceItem(
    val NameEn: String,
    val NameGe: String,
    val governingPositionEn: String?,
    val governingPositionGe: String?,
    val id: Int,
    val descriptionEn: String?,
    val descriptionGe: String?,
    val pictureURL: String,
    val academicPositionEn: String,
    val academicPositionGe: String
)