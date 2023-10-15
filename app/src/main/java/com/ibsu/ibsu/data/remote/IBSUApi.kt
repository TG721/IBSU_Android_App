package com.ibsu.ibsu.data.remote

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
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IBSUApi {
    @GET("week_value")
    suspend fun getCurrentWeek(): Response<CurrentWeek>

    @GET("slider_events")
    suspend fun getAllEvents(): Response<SliderEvents>

    @GET("useful_documents")
    suspend fun getUsefulDocs(): Response<UsefulDocs>

    @GET("student_life/clubs")
    suspend fun getClubs(): Response<Clubs>

    @GET("student_life/games")
    suspend fun getGames(): Response<Games>

    //bachelor programs
    @GET("programs/bachelor_programs")
    suspend fun getPrograms(): Response<Programs>

    //master programs
    @GET("programs/master_programs")
    suspend fun getMasterPrograms(): Response<Programs>

    //doctorate
    @GET("programs/doctorate_programs")
    suspend fun getDoctoratePrograms(): Response<Programs>

    @GET("student_life/facebook_fan_pages")
    suspend fun getFBFanPages(): Response<FBFanPages>

    @GET("School/{schoolVar}/adminStaff")
    suspend fun getAdminStaff(@Path("schoolVar") nameValue: String): Response<Administration>

    @GET("student_life/self_governance")
    suspend fun getSelfGovernance(): Response<Governance>

    @GET("student_life/sport_news")
    suspend fun getSportNews(): Response<News>

    @GET("student_life/game_room_location")
    suspend fun getGameRoomLocation(): Response<GameRoomLocation>

    @GET("School/{schoolVar}/lecturers")
    suspend fun getLecturers(@Path("schoolVar") nameValue: String): Response<Lecturers>

    @GET("contact/governing_board")
    suspend fun getGoverningBoard(): Response<Governance>

    @GET("contact/working_hours")
    suspend fun getWorkingHours(): Response<WorkingHours>

    @GET("contact/contact_info")
    suspend fun getContactInfo(): Response<ContactInfo>

    @GET("contact/address")
    suspend fun getAddress(): Response<Address>

    @GET("{type}/{programVar}/program")
    suspend fun getCourses(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<Courses>

    @GET("{type}/{programVar}/Credit Value")
    suspend fun getCreditValue(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<CreditValue>

    @GET("{type}/{programVar}/administration")
    suspend fun getProgramAdministration(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<ProgramAdmin>

    @GET("IRO/ErasmusPlus")
    suspend fun getExchangeUniversitiesForErasmusPlus(): Response<ExchangeUniversity>

    @GET("IRO/Bilateral")
    suspend fun getExchangeUniversitiesForBilateral(): Response<ExchangeUniversity>

    @GET("IRO/virtual")
    suspend fun getExchangeUniversitiesForVirtual(): Response<ExchangeUniversity>

}