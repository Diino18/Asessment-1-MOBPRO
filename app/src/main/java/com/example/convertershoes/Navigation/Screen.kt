package com.example.convertershoes.Navigation

sealed class Screen (val route: String){
    data object Home:Screen("mainScreen")
    data object About: Screen("AboutUs")
}