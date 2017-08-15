package org.lokkie.utils

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Part of org.lokkie.utils. Created in cd

 * @author lokkie
 * *
 * @version 0.1
 */
object ContentUtils {
    @JvmStatic fun readStream(inputStream: InputStream): String {
        return BufferedReader(InputStreamReader(inputStream)).lines().toArray().joinToString("\n")
    }

    @JvmStatic fun compilePath(parts: Array<String>, startsFromUserDir: Boolean = false): String {
        var fullParts = parts
        if (startsFromUserDir) {
            fullParts = arrayOf(System.getProperty("user.dir")) + parts
        }
        return fullParts.joinToString(File.separator)
    }
}
