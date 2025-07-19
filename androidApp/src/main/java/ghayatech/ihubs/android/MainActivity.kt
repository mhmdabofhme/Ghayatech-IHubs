package ghayatech.ihubs.android

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.messaging.FirebaseMessaging
import ghayatech.ihubs.App
import ghayatech.ihubs.utils.appModule
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, true)
//        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

//        WindowCompat.setDecorFitsSystemWindows(window, true)

        // إخفاء شريط الحالة وشريط التنقل
//        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
//        insetsController.hide(
//            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
//        )
//
//        // تحديد السلوك: إظهار مؤقت عند السحب ثم يختفي تلقائيًا
//        insetsController.systemBarsBehavior =
//            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


//        val controller = WindowInsetsControllerCompat(window, window.decorView)
//        controller.hide(WindowInsetsCompat.Type.statusBars())
//        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

//        // يجعل المحتوى يمتد خلف النظام
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        // إخفاء شريط الحالة
//        val controller = WindowInsetsControllerCompat(window, window.decorView)
//        controller.hide(WindowInsetsCompat.Type.statusBars())
//
//        // السلوك عند السحب (لإظهاره مؤقتًا ثم اختفاؤه تلقائيًا)
//        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


//        startKoin {
//            modules(appModule)
//        }
        checkPermission()
        enableEdgeToEdge()

        setContent {

            App()
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.d("FCM_TOKEN", "Token: $token")
                    // أرسله إلى Laravel عند تسجيل الدخول مثلاً
                }
        }


//        setContent {
//            MaterialTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//            }

//        }


    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}

//@Composable
//fun DefaultPreview() {
//    App()
//}

