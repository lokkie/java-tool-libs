package org.lokkie.utils

import java.util.*

/**
 * Part of org.lokkie.utils. Created in web
 *
 * @author lokkie
 * @version 0.1
 */
class PlaceHolder {
    private val placeHolders = HashMap<String, String?>()

    companion object {
        @JvmStatic fun create(): PlaceHolder {
            return PlaceHolder()
        }
    }


    fun addKeyValue(key: String, value: String?):PlaceHolder {
        placeHolders.put(key, value)
        return this
    }

    fun compile(string: String): String {
        var result = string
        placeHolders.forEach { key, value ->  result = result.replace("$($key)", value ?: "")}
        return result
    }
}