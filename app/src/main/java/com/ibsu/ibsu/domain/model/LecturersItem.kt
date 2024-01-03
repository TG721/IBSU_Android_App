package com.ibsu.ibsu.domain.model

data class LecturersItem(
    val id: Int,
    val isAcademic: Boolean,
    val isInvited: Boolean,
    val nameEn: String,
    val nameGe: String,
    val email: String?,
    val pictureURL: String?,
    val statusEn: String,
    val statusGe: String
)