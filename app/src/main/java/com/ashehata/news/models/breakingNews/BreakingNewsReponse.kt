package com.ashehata.news.models.breakingNews

import com.squareup.moshi.Json

data class BreakingNewsReponse (

	@Json(name = "status") val status : String,
	@Json(name = "totalResults") val totalResults : Int,
	@Json(name = "articles") val articles : List<Articles>
)