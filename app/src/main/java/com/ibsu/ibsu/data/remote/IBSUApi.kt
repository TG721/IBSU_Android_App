package com.ibsu.ibsu.data.remote

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
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IBSUApi {
    @GET("week_value")
    suspend fun getCurrentWeek(): Response<com.ibsu.ibsu.domain.model.CurrentWeek>

    @GET("slider_events")
    suspend fun getAllEvents(): Response<com.ibsu.ibsu.domain.model.SliderEvents>

    @GET("useful_documents")
    suspend fun getUsefulDocs(): Response<com.ibsu.ibsu.domain.model.UsefulDocs>

    @GET("student_life/clubs")
    suspend fun getClubs(): Response<com.ibsu.ibsu.domain.model.Clubs>

    @GET("student_life/games")
    suspend fun getGames(): Response<com.ibsu.ibsu.domain.model.Games>

    //bachelor programs
    @GET("programs/bachelor_programs")
    suspend fun getPrograms(): Response<com.ibsu.ibsu.domain.model.Programs>

    //master programs
    @GET("programs/master_programs")
    suspend fun getMasterPrograms(): Response<com.ibsu.ibsu.domain.model.Programs>

    //doctorate
    @GET("programs/doctorate_programs")
    suspend fun getDoctoratePrograms(): Response<com.ibsu.ibsu.domain.model.Programs>

    @GET("student_life/facebook_fan_pages")
    suspend fun getFBFanPages(): Response<com.ibsu.ibsu.domain.model.FBFanPages>

    @GET("School/{schoolVar}/adminStaff")
    suspend fun getAdminStaff(@Path("schoolVar") nameValue: String): Response<com.ibsu.ibsu.domain.model.Administration>

    @GET("student_life/self_governance")
    suspend fun getSelfGovernance(): Response<com.ibsu.ibsu.domain.model.Governance>

    @GET("student_life/sport_news")
    suspend fun getSportNews(): Response<com.ibsu.ibsu.domain.model.News>

    @GET("student_life/game_room_location")
    suspend fun getGameRoomLocation(): Response<com.ibsu.ibsu.domain.model.GameRoomLocation>

    @GET("School/{schoolVar}/lecturers")
    suspend fun getLecturers(@Path("schoolVar") nameValue: String): Response<com.ibsu.ibsu.domain.model.Lecturers>

    @GET("contact/governing_board")
    suspend fun getGoverningBoard(): Response<com.ibsu.ibsu.domain.model.Governance>

    @GET("contact/working_hours")
    suspend fun getWorkingHours(): Response<com.ibsu.ibsu.domain.model.WorkingHours>

    @GET("contact/contact_info")
    suspend fun getContactInfo(): Response<com.ibsu.ibsu.domain.model.ContactInfo>

    @GET("contact/address")
    suspend fun getAddress(): Response<com.ibsu.ibsu.domain.model.Address>

    @GET("{type}/{programVar}/program")
    suspend fun getCourses(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<com.ibsu.ibsu.domain.model.Courses>

    @GET("{type}/{programVar}/Credit Value")
    suspend fun getCreditValue(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<com.ibsu.ibsu.domain.model.CreditValue>

    @GET("{type}/{programVar}/administration")
    suspend fun getProgramAdministration(@Path("type") typeValue: String, @Path("programVar") programValue: String): Response<com.ibsu.ibsu.domain.model.ProgramAdmin>

    @GET("IRO/ErasmusPlus")
    suspend fun getExchangeUniversitiesForErasmusPlus(): Response<com.ibsu.ibsu.domain.model.ExchangeUniversity>

    @GET("IRO/Bilateral")
    suspend fun getExchangeUniversitiesForBilateral(): Response<com.ibsu.ibsu.domain.model.ExchangeUniversity>

    @GET("IRO/virtual")
    suspend fun getExchangeUniversitiesForVirtual(): Response<com.ibsu.ibsu.domain.model.ExchangeUniversity>

}