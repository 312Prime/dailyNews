package com.da312.dailynews.base

import org.json.JSONObject
import retrofit2.Response
import com.da312.dailynews.tools.Exception

open class BaseRepository {

    inline fun <reified T : Any> unWrap(response: Response<T>): T {
        // response 를 body 로, error 는 Exception 던지기
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
                ?: "{\"errorCode\" : \"\" , \"errorTitle\" : \"\", \"errorMessage\" : \"\"}"

            val errorJson = JSONObject(errorBody)
            throw Exception(
                statusCode = response.code(),
                errorTitle = if (errorJson.has("errorTitle")) errorJson.getString("errorTitle") else "",
                errorMessage = if (errorJson.has("errorMessage")) errorJson.getString("errorMessage") else ""
            )
        }

        return try {
            response.body()!!
        } catch (e: Exception) {
            Unit as T
        }
    }
}