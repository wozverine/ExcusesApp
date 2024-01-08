package com.glitch.excuser.data.mapper

import com.glitch.excuser.data.model.response.Excuse
import com.glitch.excuser.data.model.response.ExcuseUI

fun Excuse.mapToExcuseUI() = ExcuseUI(
	id = id ?: 1,
	category = category.orEmpty(),
	excuse = excuse.orEmpty(),
)


