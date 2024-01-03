package com.ibsu.ibsu.domain.repository

import com.ibsu.ibsu.domain.model.Address
import com.ibsu.ibsu.domain.model.Administration
import com.ibsu.ibsu.domain.model.Clubs
import com.ibsu.ibsu.domain.model.ContactInfo
import com.ibsu.ibsu.domain.model.Courses
import com.ibsu.ibsu.domain.model.CreditValue
import com.ibsu.ibsu.domain.model.CurrentWeek
import com.ibsu.ibsu.domain.model.ExchangeUniversity
import com.ibsu.ibsu.domain.model.FBFanPages
import com.ibsu.ibsu.domain.model.GameRoomLocation
import com.ibsu.ibsu.domain.model.Games
import com.ibsu.ibsu.domain.model.Governance
import com.ibsu.ibsu.domain.model.Lecturers
import com.ibsu.ibsu.domain.model.News
import com.ibsu.ibsu.domain.model.ProgramAdmin
import com.ibsu.ibsu.domain.model.Programs
import com.ibsu.ibsu.domain.model.SliderEvents
import com.ibsu.ibsu.domain.model.UsefulDocs
import com.ibsu.ibsu.domain.model.WorkingHours
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface IBSURepository {
    //retrofit
    suspend fun getCurrentWeek(): Flow<ResponseState<com.ibsu.ibsu.domain.model.CurrentWeek>>
    suspend fun getSliderEvents(): Flow<ResponseState<com.ibsu.ibsu.domain.model.SliderEvents>>

    //bachelor programs
    suspend fun getBachelorPrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>>
    suspend fun getClubs(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Clubs>>
    suspend fun getGames(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Games>>
    suspend fun getMasterPrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>>
    suspend fun getFBFanPages(): Flow<ResponseState<com.ibsu.ibsu.domain.model.FBFanPages>>
    suspend fun getAdminStaff(schoolName: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Administration>>
    suspend fun getSelfGovernance(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Governance>>
    suspend fun getSportNews(): Flow<ResponseState<com.ibsu.ibsu.domain.model.News>>
    suspend fun getGameRoomLocation(): Flow<ResponseState<com.ibsu.ibsu.domain.model.GameRoomLocation>>
    suspend fun getLecturers(schoolName: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Lecturers>>
    suspend fun getGoverningBoard(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Governance>>
    suspend fun getWorkingHours(): Flow<ResponseState<com.ibsu.ibsu.domain.model.WorkingHours>>
    suspend fun getContactInfo(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ContactInfo>>

    suspend fun getAddress(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Address>>

    suspend fun getCourses(typeValue: String, programVar: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Courses>>

    suspend fun getCreditValue(typeValue: String, programVar: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.CreditValue>>

    suspend fun getProgramAdministration(typeValue: String, programVar: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.ProgramAdmin>>

    suspend fun getDoctoratePrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>>

    suspend fun getUsefulDocs(): Flow<ResponseState<com.ibsu.ibsu.domain.model.UsefulDocs>>

    suspend fun getExchangeUniversitiesForErasmusPlus(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>>

    suspend fun getExchangeUniversitiesForBilateral(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>>

    suspend fun getExchangeUniversitiesForVirtual(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>>


}