package org.lokkie.types;

/**
 * Allows to transform text boolean values to real boolean values, and back. <br>
 * Supports:
 * <ul>
 * <li>yes/no</li>
 * <li>on/off</li>
 * <li>'true'/'false'</li>
 * <li>'1'/'0'</li>
 * </ul>
 *
 * @author lokkie
 * @version 0.1
 */
public class StringBoolean {
    private String storedValue;
    private boolean value = false;

    /**
     * Getter for converted value
     *
     * @return {@code boolean} representation of object
     */
    public boolean isValue() {
        return value;
    }

    /**
     * Sets internal boolean value and converts it into output value using True/False Strategy
     *
     * @param value Sets {@code boolean} value
     * @see StringBoolean#setValue(boolean, Strategy)
     * @see StringBoolean.Strategy
     */
    public void setValue(boolean value) {
        setValue(value, Strategy.TRUEFALSE);
    }

    /**
     * Set's {@code boolean} value and converts it to outgoing value using provided {@code Strategy}
     *
     * @param value    Local {@code boolean} value to convert
     * @param strategy Strategy is using to convert {@code boolean} value
     * @see Strategy#convertBool(boolean)
     * @see Strategy
     */
    public void setValue(boolean value, Strategy strategy) {
        this.value = value;
        storedValue = strategy.convertBool(value);
    }

    /**
     * Getter for outside value
     *
     * @return Original/Target value, that was received/'ll be stored outside
     */
    public String getStoredValue() {
        return storedValue;
    }

    /**
     * Setter for outside value. Uses auto-detection of original store strategy.
     * In case strategy was not found, converts value to {@code false}
     *
     * @param storedValue String value in any supported format to convert into boolean
     * @see Strategy#convertString(String)
     */
    public void setStoredValue(String storedValue) {
        this.storedValue = storedValue;
        value = Strategy.convertString(storedValue);
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     **/
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * {@code String} &lt;-&gt; {@code boolean} conversion strategy
     *
     * @author lokkie
     * @version 0.1
     */
    public enum Strategy {
        /**
         * Strategy, when {@code true} equals to "yes" and {@code false} to "no"
         */
        YESNO {
            @Override
            public String getFalseValue() {
                return "no";
            }

            @Override
            public String getTrueValue() {
                return "yes";
            }
        },
        /**
         * Strategy, when {@code true} equals to "on" and {@code false} to "off"
         */
        ONOFF {
            @Override
            public String getTrueValue() {
                return "on";
            }

            @Override
            public String getFalseValue() {
                return "off";
            }
        },
        /**
         * Strategy, when {@code true} equals to "true" and {@code false} to "false"
         */
        TRUEFALSE {
            @Override
            public String getFalseValue() {
                return "false";
            }

            @Override
            public String getTrueValue() {
                return "true";
            }
        },
        /**
         * Strategy, when {@code true} equals to "1" and {@code false} to "0"
         */
        NUM;

        private String trueValue, falseValue;

        private Strategy() {
            trueValue = getTrueValue();
            falseValue = getFalseValue();
        }

        /**
         * Converts incoming {@code String} into {@code boolean} using any matching strategy.
         * In case no strategy is found returns {@code false}
         *
         * @param value Incoming {@code String} to convert with any strategy
         * @return Converted value into {@code boolean}
         */
        public static boolean convertString(String value) {
            boolean result = false;
            for (Strategy strategy : Strategy.values()) {
                Boolean res = strategy.convert(value);
                if (res != null) {
                    result = res;
                    break;
                }
            }
            return result;
        }

        /**
         * Converts {@code boolean} value into {@code String} using selected strategy
         *
         * @param value {@code boolean} value to convert with chosen strategy
         * @return Converted value
         */
        public String convertBool(boolean value) {
            return value ? trueValue : falseValue;
        }

        /**
         * Returns {@code String} matches to {@code false} in selected Strategy.
         * Need for different values in different strategies
         *
         * @return {@code String} matches to {@code false} in selected Strategy
         */
        public String getFalseValue() {
            return "0";
        }

        /**
         * Returns {@code String} matches to {@code true} in selected Strategy.
         * Need for different values in different strategies
         *
         * @return {@code String} matches to {@code true} in selected Strategy
         */
        public String getTrueValue() {
            return "1";
        }

        private Boolean convert(String value) {
            if (value == null) {
                return null;
            }
            if (falseValue.equalsIgnoreCase(value)) {
                return false;
            } else if (trueValue.equalsIgnoreCase(value)) {
                return true;
            } else {
                return null;
            }
        }
    }
}