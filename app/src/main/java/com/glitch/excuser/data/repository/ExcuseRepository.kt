package com.glitch.excuser.data.repository

import com.glitch.excuser.common.Resource
import com.glitch.excuser.data.model.response.GetExcuseResponse
import com.glitch.excuser.data.source.remote.ExcuseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExcuseRepository(private val excuseService: ExcuseService) {

	suspend fun getExcuse(category: String): Resource<List<GetExcuseResponse>> =
		withContext(Dispatchers.IO) {
			try {
				val response = excuseService.getExcuseByCategory(category)

				if (response.isSuccessful) {
					val excuses = response.body()
					if (excuses != null) {
						Resource.Success(excuses)
					} else {
						Resource.Fail("Excuse list is null or empty")
					}
				} else {
					Resource.Fail("Unsuccessful response: ${response.code()}")
				}
			} catch (e: Exception) {
				Resource.Error(e.message.orEmpty())
			}
		}
}
