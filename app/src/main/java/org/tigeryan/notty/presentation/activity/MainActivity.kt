package org.tigeryan.notty.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.presentation.navigation.RootNavGraph
import org.tigeryan.notty.presentation.theme.NottyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val isDarkTheme = when (viewModel.appTheme.collectAsStateWithLifecycle().value) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            NottyTheme(
                darkTheme = isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RootNavGraph()
                }
            }
        }
    }
}