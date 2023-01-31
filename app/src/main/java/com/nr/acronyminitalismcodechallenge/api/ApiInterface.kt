package com.nr.acronyminitalismcodechallenge.api

import com.nr.acronyminitalismcodechallenge.model.SfModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/software/acromine/dictionary.py") // It in a PY Extension (Python) assuming it can ready python instead of JSON?
    suspend fun getAllData(): Response<List<SfModel>>

    @GET("/software/acromine/dictionary.py")
    suspend fun getDetailAbbr(@Query("sf") abbr: String): Response<List<SfModel>>

    companion object {
        var retrofitService: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.nactem.ac.uk")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiInterface::class.java)
            }
            return retrofitService!!
        }
    }
}