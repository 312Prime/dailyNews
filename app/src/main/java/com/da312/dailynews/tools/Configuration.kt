package com.da312.dailynews.tools

object Configuration {

    enum class Mode { DEVELOPMENT, TEST, PRODUCTION, }
    enum class Developer { SI }

    val mode = Mode.PRODUCTION

    val endPoint = when (mode) {
        Mode.DEVELOPMENT -> "https://"
        Mode.TEST -> "http://"
        Mode.PRODUCTION -> "https://"
    }
}