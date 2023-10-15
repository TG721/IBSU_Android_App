package com.ibsu.ibsu.data.remote.model

data class ContactInfoItem(
    val Email: String,
    val Phone: String,
    val extra: String,
    val id: Int,
    val nameEn: String?,
    val nameGe: String?
)