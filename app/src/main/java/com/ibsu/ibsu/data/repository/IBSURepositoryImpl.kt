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
import com.ibsu.ibsu.data.remote.model.Lecturers
import com.ibsu.ibsu.data.remote.model.News
import com.ibsu.ibsu.data.remote.model.Programs
import com.ibsu.ibsu.data.remote.model.Governance
import com.ibsu.ibsu.data.remote.model.ProgramAdmin
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
    //retrofit
    override suspend fun getCurrentWeek(): Flow<ResponseState<CurrentWeek>> =
        flow {
            try {
                val response: Response<CurrentWeek> =
                    api.getCurrentWeek()
                val body: CurrentWeek? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getSliderEvents(): Flow<ResponseState<SliderEvents>> = flow {
        try {
            val response: Response<SliderEvents> =
                api.getAllEvents()
            val body: SliderEvents? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getPrograms(): Flow<ResponseState<Programs>> = flow {
        try {
            val response: Response<Programs> =
                api.getPrograms()
            val body: Programs? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getClubs(): Flow<ResponseState<Clubs>> = flow {
        try {
            val response: Response<Clubs> =
                api.getClubs()
            val body: Clubs? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getGames(): Flow<ResponseState<Games>> = flow {
        try {
            val response: Response<Games> =
                api.getGames()
            val body: Games? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getMasterPrograms(): Flow<ResponseState<Programs>> = flow {
        try {
            val response: Response<Programs> =
                api.getMasterPrograms()
            val body: Programs? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getFBFanPages(): Flow<ResponseState<FBFanPages>> = flow {
        try {
            val response: Response<FBFanPages> =
                api.getFBFanPages()
            val body: FBFanPages? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getAdminStaff(schoolName: String): Flow<ResponseState<Administration>> =
        flow {
            try {
                val response: Response<Administration> =
                    api.getAdminStaff(schoolName)
                d("aha", schoolName)
                val body: Administration? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getSelfGovernance(): Flow<ResponseState<Governance>> =
        flow {
            try {
                val response: Response<Governance> =
                    api.getSelfGovernance()
                val body: Governance? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getSportNews(): Flow<ResponseState<News>> = flow {
        try {
            val response: Response<News> =
                api.getSportNews()
            val body: News? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getGameRoomLocation(): Flow<ResponseState<GameRoomLocation>> = flow {
        try {
            val response: Response<GameRoomLocation> =
                api.getGameRoomLocation()
            val body: GameRoomLocation? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getLecturers(schoolName: String): Flow<ResponseState<Lecturers>> = flow {
        try {
            val response: Response<Lecturers> =
                api.getLecturers(schoolName)

            val body: Lecturers? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getGoverningBoard(): Flow<ResponseState<Governance>> =
        flow {
            try {
                val response: Response<Governance> =
                    api.getGoverningBoard()
                val body: Governance? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getWorkingHours(): Flow<ResponseState<WorkingHours>> = flow {
        try {
            val response: Response<WorkingHours> =
                api.getWorkingHours()
            val body: WorkingHours? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getContactInfo(): Flow<ResponseState<ContactInfo>> = flow {
        try {
            val response: Response<ContactInfo> =
                api.getContactInfo()
            val body: ContactInfo? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getAddress(): Flow<ResponseState<Address>> = flow {
        try {
            val response: Response<Address> =
                api.getAddress()
            val body: Address? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getCourses(programVar: String): Flow<ResponseState<Courses>> = flow {
        try {
            val response: Response<Courses> =
                api.getCourses(programVar)
            val body: Courses? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getCreditValue(programValue: String): Flow<ResponseState<CreditValue>> =
        flow {
            try {
                val response: Response<CreditValue> =
                    api.getCreditValue(programValue)
                val body: CreditValue? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getProgramAdministration(programVar: String): Flow<ResponseState<ProgramAdmin>> =
        flow {
            try {
                val response: Response<ProgramAdmin> =
                    api.getProgramAdministration(programVar)
                val body: ProgramAdmin? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getDoctoratePrograms(): Flow<ResponseState<Programs>> = flow {
        try {
            val response: Response<Programs> =
                api.getDoctoratePrograms()
            val body: Programs? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getUsefulDocs(): Flow<ResponseState<UsefulDocs>> = flow {
        try {
            val response: Response<UsefulDocs> =
                api.getUsefulDocs()
            val body: UsefulDocs? = response.body()
            if (response.isSuccessful && body != null) {
                emit(ResponseState.Success(body))
            } else {
                emit(ResponseState.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override suspend fun getExchangeUniversitiesForErasmusPlus(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            try {
                val response: Response<ExchangeUniversity> =
                    api.getExchangeUniversitiesForErasmusPlus()
                val body: ExchangeUniversity? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun getExchangeUniversitiesForBilateral(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            try {
                val response: Response<ExchangeUniversity> =
                    api.getExchangeUniversitiesForBilateral()
                val body: ExchangeUniversity? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }
    override suspend fun getExchangeUniversitiesForVirtual(): Flow<ResponseState<ExchangeUniversity>> =
        flow {
            try {
                val response: Response<ExchangeUniversity> =
                    api.getExchangeUniversitiesForVirtual()
                val body: ExchangeUniversity? = response.body()
                if (response.isSuccessful && body != null) {
                    emit(ResponseState.Success(body))
                } else {
                    emit(ResponseState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }
}