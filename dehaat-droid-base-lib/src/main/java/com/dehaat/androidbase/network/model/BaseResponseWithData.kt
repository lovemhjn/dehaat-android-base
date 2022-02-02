package com.dehaat.androidbase.network.model

import com.google.gson.annotations.SerializedName

data class BaseResponseWithData<D>(
    @SerializedName("data")
    val data: D?
)