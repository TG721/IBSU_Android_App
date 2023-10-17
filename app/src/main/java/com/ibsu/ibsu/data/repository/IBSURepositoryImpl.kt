package com.ibsu.ibsu.data.repository

import android.util.Log.d
import com.ibsu.ibsu.data.remote.IBSUApi
import com.ibsu.ibsu.data.remote.model.Address
import com.ibsu.ibsu.data.remote.model.Administration
import com.ibsu.ibsu.data.remote.model.Clubs
import com.ibsu.ibsu.data.remote.model.ContactInfo
import com.ibsu.ibsu.data.remote.model.Courses
import com.ibsu.ibsu.data.remote.model.CreditValue
import com.ibsu.ibsu.data.remote.model.CurrentWeek
import com.ibsu.ibsu.data.remote.model.ExchangeUniversity
import com.ibsu.ibsu.data.remote.model.FBFanPages
import com.ibsu.ibsu.data.remote.model.GameRoomLocation
import com.ibsu.ibsu.data.remote.model.Games
import com.ibsu.ibsu.data.remote.model.Governance
import com.ibsu.ibsu.data.remote.model.Lecturers
import com.ibsu.ibsu.data.remote.model.News
import com.ibsu.ibsu.data.remote.model.ProgramAdmin
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.data.remote.model.SliderEvents
import com.ibsu.ibsu.data.remote.model.UsefulDocs
import com.ibsu.ibsu.data.remote.model.WorkingHours
import com.ibsu.ibsu.domain.repository.IBSURepository
import com.ibsu.ibsu.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class IBSURepositoryImpl @Inject constructor(private val api: IBSUApi) :
    IBSURepository {

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
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
    override suspend fun getCurrentWeek(): Flow<ResponseState<CurrentWeek>> =
        flow {
            emit(safeApiCall<CurrentWeek> { api.getCurrentWeek() })
        }

    override suspend fun getSliderEvents(): Flow<ResponseState<SliderEvents>> = flow {
        emit(safeApiCall { api.getAllEvents() })
    }

    override suspend fun getPrograms(): Flow<ResponseState<Programs>> = flow {
        emit(safeApiCall { api.getPrograms() })
    }

    override suspend fun getClubs(): Flow<ResponseState<Clubs>> = flow {
        emit(safeApiCall { api.getClubs() })
    }

    override suspend fun getGames(): Flow<ResponseState<Games>> = flow {
        emit(safeApiCall { api.getGames() })
    }

    override suspend fun getMasterPrograms(): Flow<ResponseState<Programs>> = flow {
        emit(safeApiCall { api.getMasterPrograms() })
    }

    override suspend fun getFBFanPages(): Flow<ResponseState<FBFanPages>> = flow {
        emit(safeApiCall { api.getFBFanPages() })
    }

    override suspend fun getAdminStaff(schoolName: String): Flow<ResponseState<Administration>> =
        flow {
            emit(safeApiCall { api.getAdminStaff(schoolName) })
        }


    override suspend fun getSelfGovernance(): Flow<ResponseState<Governance>> =
        flow {
            emit(safeApiCall { api.getSelfGovernance() })
        }

    override suspend fun getSportNews(): Flow<ResponseState<News>> = flow {
        getSportNews()
    }

    override suspend fun getGameRoomLocation(): Flow<ResponseState<GameRoomLocation>> = flow {
        emit(safeApiCall { api.getGameRoomLocation() })
    }

    override suspend fun getLecturers(schoolName: String): Flow<ResponseState<Lecturers>> = flow {
        emit(safeApiCall { api.getLecturers(schoolName) })
    }

    override suspend fun getGoverningBoard(): Flow<ResponseState<Governance>> =
        flow {
            emit(safeApiCall { api.getGoverningBoard() })
        }

    override suspend fun getWorkingHours(): Flow<ResponseState<WorkingHours>> = flow {
        emit(safeApiCall { api.getWorkingHours() })
    }

    override suspend fun getContactInfo(): Flow<ResponseState<ContactInfo>> = flow {
        emit(safeApiCall { api.getContactInfo() })
    }

    override suspend fun getAddress(): Flow<ResponseState<Address>> = flow {
        emit(safeApiCall { api.getAddress() })
    }

    override suspend fun getCourses(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<Courses>> = flow {
        emit(safeApiCall { api.getCourses(typeValue, programVar) })
    }

    override suspend fun getCreditValue(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<CreditValue>> =
        flow {
            emit(safeApiCall { api.getCreditValue(typeValue, programVar) })
        }

    override suspend fun getProgramAdministration(
        typeValue: String,
        programVar: String,
    ): Flow<ResponseState<ProgramAdmin>> =
        flow {
            emit(safeApiCall { api.getProgramAdministration(typeValue, programVar) })
        }

    override suspend fun getDoctoratePrograms(): Flow<ResponseState<Programs>> = flow {
        emit(safeApiCall { api.getDoctoratePrograms() })
    }

    override suspend fun getUsefulDocs(): Flow<ResponseState<UsefulDocs>> = flow {
        emit(safeApiCall { api.getUsefulDocs() })
    }

    override suspend fun getExchangeUniversitiesForErasmusPlus(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForErasmusPlus() })
        }

    override suspend fun getExchangeUniversitiesForBilateral(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForBilateral() })
        }

    override suspend fun getExchangeUniversitiesForVirtual(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            emit(safeApiCall { api.getExchangeUniversitiesForVirtual() })
        }
}