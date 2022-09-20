package com.breens.mvvmlivescorestarter.data.remote

import com.breens.mvvmlivescorestarter.data.remote.models.MatchesResponse
import com.breens.mvvmlivescorestarter.util.API_KEY
import com.breens.mvvmlivescorestarter.util.GET_MATCHES_ENDPOINT
import com.breens.mvvmlivescorestarter.util.SEASON_ID
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsDataApiService {

    @GET(GET_MATCHES_ENDPOINT)
    suspend fun getMatches(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("season_id") seasonId: Int = SEASON_ID
    ): MatchesResponse

    @GET(GET_MATCHES_ENDPOINT)
    suspend fun getLiveMatches(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("live") seasonId: Boolean = true
    ): MatchesResponse
}