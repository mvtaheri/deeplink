package com.vahid.deeplink

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.vahid.deeplink.ui.theme.DeepLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeepLinkTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "home"
                ) {
                    composable("home") {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { navController.navigate("detail") }) {
                                Text(text = "to detail")
                            }
                        }
                    }
                    composable(
                        route = "detail",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "https://amozeshpm.com/{id}"
                                action = Intent.ACTION_VIEW
                            }
                        ),
                        arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                            defaultValue = -1
                        })
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Text(text = "the id is : $id")
                        }
                    }
                }
                //consuming app link
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Button(onClick = {
//                        val intent = Intent(
//                            Intent.ACTION_VIEW,
//                            android.net.Uri.parse("https://amozeshpm.com/23")
//                        )
//                        val pendeingIntent = TaskStackBuilder.create(applicationContext).run {
//                            addNextIntentWithParentStack(intent)
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                getPendingIntent(
//                                    0,
//                                    PendingIntent.FLAG_UPDATE_CURRENT or Intent.FLAG_ACTIVITY_NEW_TASK
//                                )
//                            } else
//                                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//                        }
//                        pendeingIntent.send()
//                    }) {
//                        Text(text = "trigger deep link")
//                    }
//                }
            }
        }
    }
}

