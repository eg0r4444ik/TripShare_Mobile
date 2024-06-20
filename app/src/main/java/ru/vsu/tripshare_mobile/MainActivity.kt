package ru.vsu.tripshare_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.data_store.FirstLaunchDataStore
import ru.vsu.tripshare_mobile.services.UserService
import ru.vsu.tripshare_mobile.ui.theme.MyBlue
import ru.vsu.tripshare_mobile.ui.theme.white24

class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppConfig.init(this)
            FirebaseApp.initializeApp(this)
//            CoroutineScope(Dispatchers.Default).launch {
//                while (true) {
//                    val chatUpdate = ChatService.checkChatUpdate()
//                    if(chatUpdate.isSuccess && chatUpdate.getOrNull()!!){
//                        Toast.makeText(
//                            AppConfig.appContext,
//                            "Вам пришло новое сообщение",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    delay(10000)
//                }
//            }
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
            val person = UserService.getMe()
            person.onSuccess {
                AppConfig.currentUser = person.getOrNull()
                startDestination = "profile_screen"
            }.onFailure {
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
            text = "Добро пожаловать", style = white24,
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.Text(
            text = "в Trip Share!", style = white24,
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