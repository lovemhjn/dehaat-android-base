package com.dehaat.androidbase.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dehaat.androidbase.paging.interfaces.OnError
import com.dehaat.androidbase.paging.response.BasePagingResponse
import retrofit2.HttpException

open class BasePagingSource<D : Any>(
    private val apiCall: suspend (MutableMap<String, Any>) -> BasePagingResponse<List<D>>,
    private val queryMap: MutableMap<String, Any> = mutableMapOf(),
    private val onResponse: suspend (BasePagingResponse<List<D>>) -> Unit = {},
    private val onError: OnError
) : PagingSource<Int, D>() {
    override fun getRefreshKey(state: PagingState<Int, D>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>) = try {
        val currentPageToLoad = params.key ?: 1
        queryMap["page"] = currentPageToLoad
        val response = apiCall.invoke(queryMap)
        onResponse.invoke(response)
        LoadResult.Page(
            data = response.results ?: listOf(),
            prevKey = null, // Only paging forward.
            nextKey = if (response.next == null) null else currentPageToLoad.plus(1)
        )
    } catch (e: Exception) {
        if(e is HttpException && e.code() == 401){
            onError.onErrorReceived(401)
            LoadResult.Error(java.lang.Exception(""))
        }
        else {
            LoadResult.Error(e)
        }
    }

}