package ru.vsu.tripshare_mobile

//import okhttp3.Response
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.tripshare_mobile.api.AuthInterceptor
import ru.vsu.tripshare_mobile.api.RetrofitApi
import ru.vsu.tripshare_mobile.api.dto.TokenDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.data_store.AuthTokenDataStore
import ru.vsu.tripshare_mobile.data_store.FirstLaunchDataStore
import ru.vsu.tripshare_mobile.services.AuthService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.white24
import ru.vsu.tripshare_mobile.ui.theme.white32

class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppConfig.init(this)
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = remember { FirstLaunchDataStore(context) }
    var isFirstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        dataStore.isFirstLaunch.collectLatest { firstLaunch ->
            isFirstLaunch = firstLaunch
        }
    }

    if (isFirstLaunch) {
        WelcomeScreen {
            scope.launch {
                dataStore.setFirstLaunchCompleted()
                isFirstLaunch = false
            }
        }
    } else {
        val navController = rememberNavController()

        var startDestination by remember { mutableStateOf<String?>(null) }
        CoroutineScope(Dispatchers.Main).launch {
            val person = AuthService.getUser()
            person.onSuccess { fetchedUser ->
                AppConfig.initUser(person.getOrNull())
                if(AppConfig.currentUser!!.imageId == null){
                    AppConfig.currentUser!!.imageId = R.drawable.baseline_person
                }
                startDestination = "profile_screen"
            }.onFailure { throwable ->
                startDestination = "first_auth"
            }
        }

        Scaffold(
            content = {
                startDestination?.let {
                    NavGraph(navHostController = navController, startDestination = startDestination!!)
                } ?: run {
                    CircularProgressIndicator()
                }
            }
        )
    }
}

@Composable
fun WelcomeScreen(onGetStartedClick: () -> Unit) {
    val brush = Brush.linearGradient(listOf(Color(0xFF4AFEB9), Color(0xFF2D9FCE)))
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ){
        Logo(R.drawable.logo)
        Greeting()
        ContinueButton(onGetStartedClick)
    }
}

@Composable
private fun Logo(logoId: Int){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoId),
            contentDescription = "person",
            modifier = Modifier.size(200.dp)
        )
    }
}


@Composable
private fun Greeting(){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.Text(
            text = "Добро пожаловать", style = white32,
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.Text(
            text = "в Trip Share!", style = white32,
        )
    }
}

@Composable
private fun ContinueButton(onGetStartedClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onGetStartedClick,
            colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
            modifier = Modifier
                .height(70.dp)
                .width(380.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Продолжить", style = white24)
            }
        }
    }
}