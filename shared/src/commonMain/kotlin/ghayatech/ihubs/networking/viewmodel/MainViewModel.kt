package ghayatech.ihubs.networking.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.CreateBookingRequest
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.models.CreateBookingWithHoursRequest
import ghayatech.ihubs.networking.models.ListBaseResponse
import ghayatech.ihubs.networking.models.LoginRequest
import ghayatech.ihubs.networking.models.LoginResponse
import ghayatech.ihubs.networking.models.PackagesResponse
import ghayatech.ihubs.networking.models.RegisterRequest
import ghayatech.ihubs.networking.models.RegisterResponse
import ghayatech.ihubs.networking.models.ServiceListResponse
import ghayatech.ihubs.networking.models.UpdateProfileRequest
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.models.VerificationResponse
import ghayatech.ihubs.networking.models.VerifyPhoneRequest
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.repository.ApiRepository
import ghayatech.ihubs.networking.util.NetworkError
import ghayatech.ihubs.networking.util.Result
import ghayatech.ihubs.utils.PushTokenProvider

// Syntax highlighting has been temporarily turned off in file MainViewMode.kt because of an internal error

class MainViewModel(private val repository: ApiRepository) {


    private val vmScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Auth
    private val _loginState = MutableStateFlow<UiState<BaseResponse<LoginResponse>>?>(null)
    val loginState: StateFlow<UiState<BaseResponse<LoginResponse>>?> = _loginState

//    private val _registerState = MutableStateFlow<UiState<BaseResponse<RegisterResponse>>?>(null)
//    val registerState: StateFlow<UiState<BaseResponse<LoginResponse>>?> = _registerState

    private val _verifyPhoneState =
        MutableStateFlow<UiState<BaseResponse<VerificationResponse>>?>(null)
    val verifyPhoneState: StateFlow<UiState<BaseResponse<VerificationResponse>>?> =
        _verifyPhoneState

    private val _logoutState = MutableStateFlow<UiState<BaseResponse<Unit>>?>(null)
    val logoutState: StateFlow<UiState<BaseResponse<Unit>>?> = _logoutState

    // Profile
    private val _profileState = MutableStateFlow<UiState<BaseResponse<User>>?>(null)
    val profileState: StateFlow<UiState<BaseResponse<User>>?> = _profileState

    private val _updateProfileState = MutableStateFlow<UiState<BaseResponse<User>>?>(null)
    val updateProfileState: StateFlow<UiState<BaseResponse<User>>?> = _updateProfileState

    // Workspaces
    private val _workspacesState =
        MutableStateFlow<UiState<ListBaseResponse<Workspace>>?>(null)
    val workspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState

//    private val _searchWorkspacesState =
//        MutableStateFlow<UiState<ListBaseResponse<Workspace>>?>(null)
    val searchWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState
    val filterLocationWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState
    val filterBankWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState

    private val _workspaceState = MutableStateFlow<UiState<BaseResponse<WorkspaceDetails>>?>(null)
    val workspaceState: StateFlow<UiState<BaseResponse<WorkspaceDetails>>?> = _workspaceState

    private val _workspaceServicesState =
        MutableStateFlow<UiState<BaseResponse<ServiceListResponse>>?>(null)
    val workspaceServicesState: StateFlow<UiState<BaseResponse<ServiceListResponse>>?> =
        _workspaceServicesState

    private val _workspacePackagesState =
        MutableStateFlow<UiState<BaseResponse<PackagesResponse>>?>(null)
    val workspacePackagesState: StateFlow<UiState<BaseResponse<PackagesResponse>>?> =
        _workspacePackagesState


    private val _createBookingState = MutableStateFlow<UiState<BaseResponse<CreateBookingResponse>>?>(null)
    val createBookingState: StateFlow<UiState<BaseResponse<CreateBookingResponse>>?> = _createBookingState


    private val _bookingState = MutableStateFlow<UiState<ListBaseResponse<CreateBookingResponse>>?>(null)
    val bookingState: StateFlow<UiState<ListBaseResponse<CreateBookingResponse>>?> = _bookingState


//    private val pushTokenProvider: PushTokenProvider



    // Generalized executor
    private fun <T> executeApiCall(
        call: suspend () -> Result<T, NetworkError>,
        stateFlow: MutableStateFlow<UiState<T>?>
    ) {
        vmScope.launch {
            stateFlow.value = UiState.Loading
            when (val result = call()) {
                is Result.Success -> stateFlow.value = UiState.Success(result.data)
                is Result.Error -> stateFlow.value = UiState.Error(result.error)
            }
        }
    }


    // Auth functions
    fun login(phone: String, password: String) {
        val data = LoginRequest(phone, password)
        executeApiCall({
            repository.login(data)
        }, _loginState)
    }

    fun register(data: RegisterRequest) {
        executeApiCall({ repository.register(data) }, _loginState)
    }

    fun verifyPhone(data: VerifyPhoneRequest) {
        executeApiCall({ repository.verifyPhone(data) }, _verifyPhoneState)
    }

    fun logout() {
        executeApiCall({ repository.logout() }, _logoutState)
    }

    // Profile functions
    fun getProfile() {
        executeApiCall({ repository.getProfile() }, _profileState)
    }

    fun updateProfile(data: UpdateProfileRequest) {
        executeApiCall({ repository.updateProfile(data) }, _updateProfileState)
    }

    // Workspaces functions
//    fun getWorkspaces() {
//        executeApiCall({ repository.getWorkspaces() }, _workspacesState)
//    }
    fun getWorkspaces() {
        executeApiCall({
            repository.getWorkspaces()
        }, _workspacesState)
    }

//    fun searchWorkspaces(query:String) {
//        executeApiCall({ repository.searchWorkspaces(query) }, _workspacesState)
//    }
//
//    fun filterLocationWorkspaces(query:String) {
//        executeApiCall({ repository.filterLocationWorkspaces(query) }, _workspacesState)
//    }
//
//    fun filterBankWorkspaces(query:Boolean) {
//        executeApiCall({ repository.filterBankWorkspaces(query) }, _workspacesState)
//    }

    fun getWorkspace(id: Int) {
        executeApiCall({ repository.getWorkspace(id) }, _workspaceState)
    }

    fun getWorkspaceServices(id: Int) {
        executeApiCall({ repository.getWorkspaceServices(id) }, _workspaceServicesState)
    }

    fun getWorkspacePackages(id: Int) {
        executeApiCall({ repository.getWorkspacePackages(id) }, _workspacePackagesState)
    }


    fun createBooking(data: CreateBookingRequest) {
        executeApiCall({ repository.createBooking(data) }, _createBookingState)
    }

    fun createBookingWithHours(data: CreateBookingWithHoursRequest) {
        executeApiCall({ repository.createBookingWithHours(data) }, _createBookingState)
    }

//    fun getBookings() {
//        executeApiCall({ repository.getBookings() }, _bookingState)
//    }


//    fun getBookings() {
//        executeApiCall({
//            repository.getBooking()
//        }, _bookingState)
//    }

    fun onCleared() {
        vmScope.cancel()
    }

    fun clearLoginState() {
        _loginState.value = null
    }

//    fun getFilteredWorkspaces(
//        search: String? = null,
//        location: String? = null,
//        bankPayment: Boolean? = null
//    ) {
//        val baseUrl = ApiConstants.BASE_URL + ApiRoutes.LIST_WORKSPACES
//        val queryParams = mutableListOf<String>()
//
//        if (!search.isNullOrBlank()) queryParams += "search=$search"
//        if (!location.isNullOrBlank()) queryParams += "location=$location"
//        if (bankPayment != null) queryParams += "bank_payment_supported=$bankPayment"
//
//        val fullUrl = if (queryParams.isNotEmpty()) {
//            "$baseUrl?${queryParams.joinToString("&")}"
//        } else baseUrl
//
//        executeApiCall({
//            repository.getFilteredWorkspaces(fullUrl)
//        }, _workspacesState)
//    }


    fun getFilteredWorkspaces(
        search: String? = null,
        location: String? = null,
        bankPaymentSupported: Boolean? = null
    ) {
        executeApiCall({
            repository.getWorkspaces(search, location, bankPaymentSupported)
        }, _workspacesState)
//        executeApiCall(
//            state = _workspacesState,
//            apiCall = { repository.getWorkspaces(search, location, bankPaymentSupported) }
//        )
    }



    fun getBookings(
        query: String? = null,
    ) {
        executeApiCall({
            repository.getBookings(query)
        }, _bookingState)

    }





}