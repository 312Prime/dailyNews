package com.da312.dailynews.tools

class Exception (
    val statusCode: Int,
    val errorTitle: String? = null,
    val errorMessage: String? = null
) : Throwable() {}