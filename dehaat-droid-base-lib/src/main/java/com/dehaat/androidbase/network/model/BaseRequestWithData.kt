package com.dehaat.androidbase.network.model

import com.google.gson.annotations.SerializedName

data class BaseRequestWithData<D>(
    @SerializedName("data")
    val data: D?
)