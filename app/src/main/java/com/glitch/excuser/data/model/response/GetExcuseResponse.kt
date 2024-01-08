package com.glitch.excuser.data.model.response

data class GetExcuseResponse (
	val id: Int,
	val excuse: String,
	val category: String
) : BaseResponse()