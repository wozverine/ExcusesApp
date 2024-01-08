package com.glitch.excuser.data.source.remote

import com.glitch.excuser.data.model.response.GetExcuseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExcuseService {
	@GET("{category}")
	suspend fun getExcuseByCategory(@Path("category") category: String): Response<GetExcuseResponse>
}
