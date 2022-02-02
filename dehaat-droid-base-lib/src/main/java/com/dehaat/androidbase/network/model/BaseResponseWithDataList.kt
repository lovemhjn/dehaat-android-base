package com.dehaat.androidbase.network.model

import com.google.gson.annotations.SerializedName

data class BaseResponseWithDataList<D>(
    @SerializedName("data")
    val data: ArrayList<D>?
)