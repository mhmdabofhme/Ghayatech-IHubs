package ghayatech.ihubs.networking.repository

import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.*
import ghayatech.ihubs.networking.network.ApiService
import ghayatech.ihubs.networking.util.NetworkError
import ghayatech.ihubs.networking.util.Result

class ApiRepository(private val api: ApiService) {

    // Auth
    suspend fun login(data: LoginRequest): Result<BaseResponse<LoginResponse>, NetworkError> =
        api.login(data)

    suspend fun register(data: RegisterRequest): Result<BaseResponse<LoginResponse>, NetworkError> =
        api.register(data)

    suspend fun verifyPhone(data: VerifyPhoneRequest): Result<BaseResponse<VerificationResponse>, NetworkError> =
        api.verifyPhone(data)

    suspend fun logout(): Result<BaseResponse<Unit>, NetworkError> =
        api.logout()

    // Profile
    suspend fun getProfile(): Result<BaseResponse<User>, NetworkError> =
        api.getProfile()

    suspend fun updateProfile(data: UpdateProfileRequest): Result<BaseResponse<User>, NetworkError> =
        api.updateProfile(data)

    // Workspaces
    suspend fun getWorkspaces(): Result<ListBaseResponse<Workspace>, NetworkError> =
        api.getWorkspaces()

    suspend fun getWorkspaces(
        search: String? = null,
        location: String? = null,
        bankPaymentSupported: Boolean? = null
    ): Result<ListBaseResponse<Workspace>, NetworkError> {
        return api.getWorkspaces(
            search = search,
            location = location,
            bankPaymentSupported = bankPaymentSupported
        )
    }


//    suspend fun getFilteredWorkspaces(url: String): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return api.getFilteredWorkspaces(url)
//    }


//    suspend fun searchWorkspaces(query:String): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.searchWorkspaces(query)
//
//    suspend fun filterLocationWorkspaces(query:String): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.filterLocationWorkspaces(query)
//
//    suspend fun filterBankWorkspaces(query:Boolean): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.filterBankWorkspaces(query)

    suspend fun getWorkspace(id: Int): Result<BaseResponse<WorkspaceDetails>, NetworkError> =
        api.getWorkspace(id)

    suspend fun getWorkspaceServices(id: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> =
        api.getWorkspaceServices(id)

    suspend fun getWorkspacePackages(id: Int): Result<BaseResponse<PackagesResponse>, NetworkError> =
        api.getWorkspacePackages(id)

    // Bookings
    suspend fun createBooking(data: CreateBookingRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> =
        api.createBooking(data)

    suspend fun createBookingWithHours(data: CreateBookingWithHoursRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> =
        api.createBookingWithHours(data)

//    suspend fun getBookings(): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> =
//        api.getBookings()


    suspend fun getBookings(
        query: String? = null,
    ): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> {
        return api.getBookings(
            query = query
        )
    }


    // Service Requests
    suspend fun createServiceRequest(
        bookingId: Int,
        data: CreateServiceRequest
    ): Result<BaseResponse<Service>, NetworkError> =
        api.createServiceRequest(bookingId, data)

    suspend fun getServiceRequests(bookingId: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> =
        api.getServiceRequests(bookingId)

    // Notifications
    suspend fun getNotifications(): Result<BaseResponse<NotificationResponse>, NetworkError> =
        api.getNotifications()

    suspend fun markNotificationsAsRead(): Result<BaseResponse<Unit>, NetworkError> =
        api.markNotificationsAsRead()

    // Conversations & Messages
    suspend fun startConversation(data: StartConversationRequest): Result<BaseResponse<Conversation>, NetworkError> =
        api.startConversation(data)

    suspend fun getConversations(): Result<ListBaseResponse<Conversation>, NetworkError> =
        api.getConversations()

    suspend fun deleteConversation(id: Int): Result<BaseResponse<Unit>, NetworkError> =
        api.deleteConversation(id)

    suspend fun getMessages(conversationId: Int): Result<BaseResponse<MessageListResponse>, NetworkError> =
        api.getMessages(conversationId)

    // Static Content
    suspend fun getTerms(): Result<BaseResponse<StaticContentResponse>, NetworkError> =
        api.getTerms()

    suspend fun getAbout(): Result<BaseResponse<StaticContentResponse>, NetworkError> =
        api.getAbout()


}
