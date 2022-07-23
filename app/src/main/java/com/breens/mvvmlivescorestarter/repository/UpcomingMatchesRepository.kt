package com.breens.mvvmlivescorestarter.repository

import com.breens.mvvmlivescorestarter.data.remote.ElenaApiService
import com.breens.mvvmlivescorestarter.data.remote.models.Match
import javax.inject.Inject

class UpcomingMatchesRepository @Inject constructor(private val elenaApiService: ElenaApiService) {

    suspend fun getAllUpcomingMatches(): List<Match> = elenaApiService.getUpcomingMatches().data
}