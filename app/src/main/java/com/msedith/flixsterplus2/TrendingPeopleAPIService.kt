package com.msedith.flixsterplus2

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingPeopleApiService {
    @GET("person/popular")
    suspend fun getTrendingPeople(@Query("api_key") apiKey: String): PeopleResponse
}
