package com.ibsu.ibsu.data.remote.model

data class SelfGovernanceItem(
    val NameEn: String,
    val NameGe: String,
    val governingPositionEn: String,
    val governingPositionGe: String,
    val id: Int,
    val pictureURL: String,
    val academicPositionEn: String,
    val academicPositionGe: String
)