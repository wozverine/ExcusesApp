package com.glitch.excuser.data.repository

import android.util.Log
import com.glitch.excuser.common.Resource
import com.glitch.excuser.data.mapper.mapToExcuseUI
import com.glitch.excuser.data.model.response.ExcuseUI
import com.glitch.excuser.data.source.remote.ExcuseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExcuseRepository (
	private val excuseService: ExcuseService
){
	suspend fun getExcuse(category: String): Resource<ExcuseUI> = withContext(Dispatchers.IO) {
		try {
			val response = excuseService.getExcuseByCategory("children").body()

			if (response != null) {
				response.excuse?.excuse?.let { Log.v("ExcuseRepository", it) }
				when {
					response.status == 200 && response.excuse != null -> {
						Resource.Success(response.excuse.mapToExcuseUI())
					}
					else -> {
						Resource.Fail(response.message.orEmpty())
					}
				}
			} else {
				Resource.Error("Null response received")
			}
		} catch (e: Exception) {
			// Log the exception for debugging purposes
			e.printStackTrace()
			Resource.Error(e.message.orEmpty())
		}
	}

}