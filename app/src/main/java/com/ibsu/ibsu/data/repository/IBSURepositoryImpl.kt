package com.ibsu.ibsu.data.repository

import com.ibsu.ibsu.data.remote.IBSUApi
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
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class IBSURepositoryImpl @Inject constructor(private val api: IBSUApi) :
    IBSURepository {

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>,
    ): ResponseState<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                ResponseState.Success(response.body()!!)
            } else {
                ResponseState.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "Unknown error")
        }
    }

    //retrofit
    override suspend fun getCurrentWeek(): Flow<ResponseState<com.ibsu.ibsu.domain.model.CurrentWeek>> =
        flow {
            emit(safeApiCall<com.ibsu.ibsu.domain.model.CurrentWeek> { api.getCurrentWeek() })
        }

    override suspend fun getSliderEvents(): Flow<ResponseState<com.ibsu.ibsu.domain.model.SliderEvents>> = flow {
        emit(safeApiCall { api.getAllEvents() })
    }

    override suspend fun getBachelorPrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>> = flow {
        emit(safeApiCall { api.getPrograms() })
    }

    override suspend fun getClubs(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Clubs>> = flow {
        emit(safeApiCall { api.getClubs() })
    }

    override suspend fun getGames(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Games>> = flow {
        emit(safeApiCall { api.getGames() })
    }

    override suspend fun getMasterPrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>> = flow {
        emit(safeApiCall { api.getMasterPrograms() })
    }

    override suspend fun getFBFanPages(): Flow<ResponseState<com.ibsu.ibsu.domain.model.FBFanPages>> = flow {
        emit(safeApiCall { api.getFBFanPages() })
    }

    override suspend fun getAdminStaff(schoolName: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Administration>> =
        flow {
            emit(safeApiCall { api.getAdminStaff(schoolName) })
        }


    override suspend fun getSelfGovernance(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Governance>> =
        flow {
            emit(safeApiCall { api.getSelfGovernance() })
        }

    override suspend fun getSportNews(): Flow<ResponseState<com.ibsu.ibsu.domain.model.News>> = flow {
        emit(safeApiCall { api.getSportNews() })
    }

    override suspend fun getGameRoomLocation(): Flow<ResponseState<com.ibsu.ibsu.domain.model.GameRoomLocation>> = flow {
        emit(safeApiCall { api.getGameRoomLocation() })
    }

    override suspend fun getLecturers(schoolName: String): Flow<ResponseState<com.ibsu.ibsu.domain.model.Lecturers>> = flow {
        emit(safeApiCall { api.getLecturers(schoolName) })
    }

    override suspend fun getGoverningBoard(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Governance>> =
        flow {
            emit(safeApiCall { api.getGoverningBoard() })
        }

    override suspend fun getWorkingHours(): Flow<ResponseState<com.ibsu.ibsu.domain.model.WorkingHours>> = flow {
        emit(safeApiCall { api.getWorkingHours() })
    }

    override suspend fun getContactInfo(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ContactInfo>> = flow {
        emit(safeApiCall { api.getContactInfo() })
    }

    override suspend fun getAddress(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Address>> = flow {
        emit(safeApiCall { api.getAddress() })
    }

    override suspend fun getCourses(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<com.ibsu.ibsu.domain.model.Courses>> = flow {
        emit(safeApiCall { api.getCourses(typeValue, programVar) })
    }

    override suspend fun getCreditValue(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<com.ibsu.ibsu.domain.model.CreditValue>> =
        flow {
            emit(safeApiCall { api.getCreditValue(typeValue, programVar) })
        }

    override suspend fun getProgramAdministration(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<com.ibsu.ibsu.domain.model.ProgramAdmin>> =
        flow {
            emit(safeApiCall { api.getProgramAdministration(typeValue, programVar) })
        }

    override suspend fun getDoctoratePrograms(): Flow<ResponseState<com.ibsu.ibsu.domain.model.Programs>> = flow {
        emit(safeApiCall { api.getDoctoratePrograms() })
    }

    override suspend fun getUsefulDocs(): Flow<ResponseState<com.ibsu.ibsu.domain.model.UsefulDocs>> = flow {
        emit(safeApiCall { api.getUsefulDocs() })
    }

    override suspend fun getExchangeUniversitiesForErasmusPlus(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForErasmusPlus() })
        }

    override suspend fun getExchangeUniversitiesForBilateral(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForBilateral() })
        }

    override suspend fun getExchangeUniversitiesForVirtual(): Flow<ResponseState<com.ibsu.ibsu.domain.model.ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForVirtual() })
        }
}