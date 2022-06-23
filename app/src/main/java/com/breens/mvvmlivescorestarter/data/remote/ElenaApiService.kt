package com.breens.mvvmlivescorestarter.data.remote

import com.breens.mvvmlivescorestarter.data.remote.models.InplayMatchesResponse
import com.breens.mvvmlivescorestarter.util.GET_INPLAY_FIXTURES
import retrofit2.http.GET
import retrofit2.http.Header

interface ElenaApiService {

    @GET(GET_INPLAY_FIXTURES)
    suspend fun getInplayMatches(): InplayMatchesResponse
}