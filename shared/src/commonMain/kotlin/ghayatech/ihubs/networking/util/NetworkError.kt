package ghayatech.ihubs.networking.util

import androidx.compose.runtime.Composable
import ihubs.shared.generated.resources.Res
import ihubs.shared.generated.resources.conflict
import ihubs.shared.generated.resources.no_internet
import ihubs.shared.generated.resources.request_timeout
import ihubs.shared.generated.resources.server_error
import ihubs.shared.generated.resources.too_many_requests
import ihubs.shared.generated.resources.unauthorized
import ihubs.shared.generated.resources.unknown
import org.jetbrains.compose.resources.stringResource


sealed class NetworkError : Error {
    data class HttpError(val code: Int, val message: String) : NetworkError()
    data object RequestTimeout : NetworkError()
    data object Unauthorized : NetworkError()
    data object Conflict : NetworkError()
    data object TooManyRequests : NetworkError()
    data object NoInternet : NetworkError()
    data object PayloadTooLarge : NetworkError()
    data object ServerError : NetworkError()
    data object Serialization : NetworkError()
    data object Unknown : NetworkError()
}

@Composable
fun NetworkError.getMessage(): String {
    return when (this) {
        is NetworkError.Unauthorized -> stringResource(Res.string.unauthorized)
        is NetworkError.Conflict -> stringResource(Res.string.conflict)
        is NetworkError.PayloadTooLarge -> stringResource(Res.string.too_many_requests)
        is NetworkError.TooManyRequests -> stringResource(Res.string.too_many_requests)
        is NetworkError.ServerError -> stringResource(Res.string.server_error)
        is NetworkError.Serialization -> stringResource(Res.string.unknown)
        is NetworkError.NoInternet -> stringResource(Res.string.no_internet)
        is NetworkError.RequestTimeout -> stringResource(Res.string.request_timeout)
        is NetworkError.Unknown -> stringResource(Res.string.unknown)
        is NetworkError.HttpError -> this.message
    }
}