package com.dehaat.androidbase.paging.response

import com.google.gson.annotations.SerializedName

data class BasePagingResponse<R>(
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: R?
)