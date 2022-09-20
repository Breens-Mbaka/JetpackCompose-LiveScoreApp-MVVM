package com.breens.mvvmlivescorestarter.data.remote.models

data class AwayTeam(
    val common_name: String,
    val country: Country,
    val logo: String,
    val name: String,
    val short_code: String,
    val team_id: Int
)