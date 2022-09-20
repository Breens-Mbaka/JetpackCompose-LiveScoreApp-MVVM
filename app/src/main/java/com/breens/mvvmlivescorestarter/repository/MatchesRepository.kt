package com.breens.mvvmlivescorestarter.repository

import com.breens.mvvmlivescorestarter.data.remote.SportsDataApiService
import com.breens.mvvmlivescorestarter.data.remote.models.Data
import javax.inject.Inject

class MatchesRepository @Inject constructor(private val sportsDataApiService: SportsDataApiService) {

    suspend fun getMatches(): List<Data> = sportsDataApiService.getMatches().data

    suspend fun getLiveMatches(): List<Data> = sportsDataApiService.getLiveMatches().data
}