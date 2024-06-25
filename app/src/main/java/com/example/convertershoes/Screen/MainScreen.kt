package com.example.convertershoes.Screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.convertershoes.Navigation.Screen
import com.example.convertershoes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Converter Shoe") },

            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                    Icon(imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(id = R.string.tentang_aplikasi),
                        tint = MaterialTheme.colorScheme.primary)

                }
            }
        )




    })

    {
        paddingValues -> ScreenContent(modifier = Modifier.padding(paddingValues))

    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    val context = LocalContext.current
    var SizeIndo by remember {
        mutableStateOf("")
    }
    var SizeIndoError by remember {
        mutableStateOf(false)
    }
    val radioOptions = listOf(
        stringResource(id = R.string.size_uk),
        stringResource(id = R.string.size_us)
    )
    var convertType by remember {
        mutableStateOf(radioOptions[0])
    }

    var kondisi by remember {
        mutableStateOf(false)
    }
    var hasilConvert by remember {
        mutableStateOf(0)
    }
    var SizeError by remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(id = R.string.convert_size),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = SizeIndo,
            onValueChange = {SizeIndo = it},
            label = { Text(text = stringResource(id = R.string.size_indo)) },isError = SizeIndoError,
            trailingIcon = { IconPicker(SizeIndoError, "", SizeError) },
            supportingText = { ErrorHint(isError = SizeIndoError , SizeError )},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = convertType == text,
                    modifier = Modifier
                        .selectable(
                            selected = convertType == text,
                            onClick = { convertType = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = {
                SizeIndoError = (SizeIndo == "")
                if (SizeIndoError) return@Button
                SizeError = SizeIndo.toInt() < 40
                if (SizeError) return@Button
                kondisi = true
                hasilConvert = ConverterSize(SizeIndo.toInt(), convertType == radioOptions[0] )
            },
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.convert_size))
        }
        if (kondisi == true ) {
            Text(text = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(id = R.string.result, hasilConvert ),
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template,
                            hasilConvert)

                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.bagikan))
            }
        }


    }
}
private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}
@Composable
fun IconPicker(isError: Boolean, unit: String , SizeError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else if (
        SizeError
    ){
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
    else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean, SizeError : Boolean) {
    if (isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
    else if (SizeError){
        Text(text = stringResource(id = R.string.size_invalid))

    }
}
@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )

    }
}

fun ConverterSize(sizeSepatu:Int, typeConvert:Boolean): Int {

    if (typeConvert){
      return sizeSepatu - 34
    }else {
        return sizeSepatu - 33
    }


}



