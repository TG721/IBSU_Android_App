package com.ibsu.ibsu.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClubsItem(
    val clubNameEn: String,
    val clubNameGe: String,
    val id: Int,
    val descriptionEn: String?,
    val descriptionGe: String?,
    val fbLink: String?,
    val pictureURL: String,
    val presidentNumber: String,
    val presidentEmail: String,
    val presidentNameEn: String,
    val presidentNameGe: String,
    val vicePresidentNumber: String?,
    val vicePresidentEmail: String?,
    val vicePresidentNameEn: String?,
    val vicePresidentNameGe: String?
) : Parcelable