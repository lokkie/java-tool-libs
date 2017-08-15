package org.lokkie.types;

import java.time.Instant;
import java.util.Date;

/**
 * Allows to work with variant date formats (e.g. atlassian date)
 *
 * @author lokkie
 * @version 0.1
 */
public class UnixTimestamp {
    private Long value;

    /**
     * Constructs object from {@code Date}
     *
     * @param date Timr in {@code Date} representation
     */
    public UnixTimestamp(Date date) {
        this(date.getTime());
    }

    /**
     * Constructs object from {@code long} representation
     *
     * @param value UnixTimestamp in {@code long} representation
     */
    public UnixTimestamp(long value) {
        this.value = value;
    }

    /**
     * Constructs object from {@code String} representation of unixTimestamp
     *
     * @param s UnixTime in {@code String} representation
     * @throws NumberFormatException
     */
    public UnixTimestamp(String s) throws NumberFormatException {
        value = new Long(s);
    }

    /**
     * Getter in Date format
     *
     * @return {@code java.util.Date} with parsed value
     */
    public Date getDate() {
        return Date.from(Instant.ofEpochSecond(this.value));
    }

    /**
     * Getter for UnixTimestamp
     *
     * @return UnixTimestamp in {@code long}
     */
    public long getLong() {
        return value;
    }
}
