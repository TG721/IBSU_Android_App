package com.ibsu.ibsu.domain.repository

import com.ibsu.ibsu.data.remote.model.Administration
import com.ibsu.ibsu.data.remote.model.AdministrationItem
import com.ibsu.ibsu.data.remote.model.Clubs
import com.ibsu.ibsu.data.remote.model.CurrentWeek
import com.ibsu.ibsu.data.remote.model.FBFanPages
import com.ibsu.ibsu.data.remote.model.GameRoomLocation
import com.ibsu.ibsu.data.remote.model.Games
import com.ibsu.ibsu.data.remote.model.Lecturers
import com.ibsu.ibsu.data.remote.model.News
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.data.remote.model.SelfGovernance
import com.ibsu.ibsu.data.remote.model.SliderEvents
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path

interface IBSURepository {
    //retrofit
    suspend fun getCurrentWeek(): Flow<ResponseState<CurrentWeek>>
    suspend fun getSliderEvents(): Flow<ResponseState<SliderEvents>>

    //bachelor programs
    suspend fun getPrograms(): Flow<ResponseState<Programs>>
    suspend fun getClubs(): Flow<ResponseState<Clubs>>
    suspend fun getGames(): Flow<ResponseState<Games>>
    suspend fun getMasterPrograms(): Flow<ResponseState<Programs>>
    suspend fun getFBFanPages(): Flow<ResponseState<FBFanPages>>
    suspend fun getAdminStaff(schoolName: String): Flow<ResponseState<Administration>>
    suspend fun getSelfGovernance(): Flow<ResponseState<SelfGovernance>>
    suspend fun getSportNews(): Flow<ResponseState<News>>
    suspend fun getGameRoomLocation(): Flow<ResponseState<GameRoomLocation>>
    suspend fun getLecturers(schoolName: String): Flow<ResponseState<Lecturers>>


}