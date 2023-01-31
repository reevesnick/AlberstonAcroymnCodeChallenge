package com.nr.acronyminitalismcodechallenge.repo

import com.nr.acronyminitalismcodechallenge.api.ApiInterface

class SfRepo constructor(private val apiInterface: ApiInterface) {


    suspend fun getAllData() = apiInterface.getAllData()

    suspend fun getAbbrData(abbr: String) = apiInterface.getDetailAbbr(abbr)
}