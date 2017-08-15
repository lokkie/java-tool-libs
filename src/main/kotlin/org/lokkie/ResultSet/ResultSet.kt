package org.lokkie.extentions.java.sql.ResultSet

import java.sql.ResultSet

/**
 * Part of org.lokkie.sql. Created in web
 *
 * @author lokkie
 * @version 0.1
 */
fun ResultSet.getRowCount(): Int {
    var size = 0
    if (type == ResultSet.TYPE_SCROLL_SENSITIVE && concurrency == ResultSet.CONCUR_UPDATABLE) {
        try {
            last()
            size = row
            beforeFirst()
        } catch (ignored: Exception) {
        }
    }
    return size
}

val ResultSet.columnNames: Array<String>
    get() {
        val preResult = ArrayList<String>()
        (1..metaData.columnCount).mapTo(preResult) { metaData.getColumnName(it) }
        return preResult.toTypedArray()
    }