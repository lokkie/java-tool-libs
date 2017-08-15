package org.lokkie.utils

import java.util.*

/**
 * Part of org.lokkie.utils. Created in web
 *
 * @author lokkie
 * @version 0.1
 */
class ConsoleProgressMeter(private val title: String, private val total: Int) {
    var progress = 0
    var started = 0L

    fun start(): ConsoleProgressMeter {
        println("$title. Started at: " + Date().toString())
        started = Date().time / 1000
        return progress(current = 0)
    }

    fun progress(title: String = "", current: Int = -1, step: Int = 1): ConsoleProgressMeter {
        if (current > -1) {
            progress = 0
        } else {
            progress += step
        }
        val pers: Int = progress * 10 / total
        var elapsedSeconds = (Date().time / 1000) - started
        val elapsedMins = elapsedSeconds / 60
        var estimatedSeconds = (elapsedSeconds.toFloat() / progress * (total - progress)).toInt()
        elapsedSeconds -= elapsedMins * 60
        val estimateMins = estimatedSeconds / 60
        estimatedSeconds -= estimateMins * 60
        print("\r[" + "*".repeat(pers) + ".".repeat(10 - pers) + "] $progress/$total; t.El: " +
                "${elapsedMins}m${elapsedSeconds}s; t.Es: ${estimateMins}m${elapsedSeconds}s: $title".padEnd(80, ' '))
        return this
    }

    fun done(): ConsoleProgressMeter {
        var elapsedSeconds = Date().time / 1000 - started
        val elapsedMins = elapsedSeconds / 60
        elapsedSeconds -= elapsedMins * 60
        println()
        println("Done in ${elapsedMins}m${elapsedSeconds}s at " + Date().toString())
        return this
    }
}