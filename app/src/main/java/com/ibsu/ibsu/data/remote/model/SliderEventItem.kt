package com.ibsu.ibsu.data.remote.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SliderEventItem(
    val date: String,
    val id: Int,
    val nameEn: String,
    val nameGe: String,
    val descriptionEn: String?,
    val descriptionGe: String?,
    val pictureURL: String?,
    val locationEn: String,
    val locationGe: String
) : Parcelable