package com.breens.mvvmlivescorestarter.data.remote

import com.breens.mvvmlivescorestarter.data.remote.models.InplayMatchesResponse
import com.breens.mvvmlivescorestarter.data.remote.models.UpcomingMatchesResponse
import com.breens.mvvmlivescorestarter.util.GET_INPLAY_FIXTURES
import com.breens.mvvmlivescorestarter.util.GET_UPCOMING_MATCHES
import retrofit2.http.GET

interface ElenaApiService {

    @GET(GET_INPLAY_FIXTURES)
    suspend fun getInplayMatches(): InplayMatchesResponse

    @GET(GET_UPCOMING_MATCHES)
    suspend fun getUpcomingMatches(): UpcomingMatchesResponse
}