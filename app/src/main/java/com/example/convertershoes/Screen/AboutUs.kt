package com.example.convertershoes.Screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.convertershoes.Model.Profil
import com.example.convertershoes.ui.theme.ConverterShoesTheme
import com.example.convertershoes.R
import com.example.convertershoes.Screen.MainScreen
import com.example.convertershoes.ui.theme.ConverterShoesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary)

                    }
                },
                title = { Text(text = stringResource(id = R.string.tentang_aplikasi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary

                )
            )
        }
    ) { padding ->
        val profilList = getData()

        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)

        ){
            Image(
                painter = painterResource(id = profilList[0].imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.deskripsi_profile),
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp))
            Text(
                text = stringResource(id = R.string.copyright),
                modifier = Modifier
                    .padding(16.dp).padding(top = 180.dp),
                style = TextStyle(fontSize = 10.sp)
            )

        }

    }

}

private fun getData(): List<Profil> {
    return listOf(
        Profil("Tegar", R.drawable.tegar)
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview() {
    ConverterShoesTheme {
        AboutScreen(rememberNavController())
    }

}
