package com.da312.daily_news_core.logger

internal fun Any.getMessage(funcName: String, prefix: String = ""): String {
    return String.format(
        "$prefix${funcName}:: %s%s",
        javaClass.simpleName,
        "(${hashCode()})"
    )
}
