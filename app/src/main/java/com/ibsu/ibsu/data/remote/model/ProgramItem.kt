package com.ibsu.ibsu.data.remote.model

data class ProgramItem(
    val CostForNonGeorgians: Int,
    val School: String,
    val englishSectorAvailable: Boolean,
    val englishSectorCost: Int,
    val georgianSectorAvailable: Boolean,
    val georgianSectorCost: Int,
    val id: Int,
    val isReady: Boolean,
    val shouldDownloadFile: Boolean,
    val fileLink: String?,
    val programNameEn: String,
    val programNameGe: String
)