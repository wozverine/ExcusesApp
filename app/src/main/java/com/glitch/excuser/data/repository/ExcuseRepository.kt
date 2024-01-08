package com.glitch.excuser.data.repository

import com.glitch.excuser.common.Resource
import com.glitch.excuser.data.source.remote.ExcuseService
import com.glitch.excuser.data.model.response.GetExcuseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ExcuseRepository(private val excuseService: ExcuseService) {

	suspend fun getExcuse(category: String): Resource<GetExcuseResponse> =
		withContext(Dispatchers.IO) {
			try {
				val response = excuseService.getExcuseByCategory(category)

				if (response.isSuccessful) {
					val excuse = response.body()
					if (excuse != null) {
						Resource.Success(excuse)
					} else {
						Resource.Fail("Excuse is null")
					}
				} else {
					Resource.Fail("Unsuccessful response: ${response.code()}")
				}
			} catch (e: Exception) {
				Resource.Error(e.message.orEmpty())
			}
		}
}
