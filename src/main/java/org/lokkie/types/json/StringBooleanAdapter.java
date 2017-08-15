package org.lokkie.types.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.lokkie.types.StringBoolean;

import java.io.IOException;

/**
 * JsonTypeAdapter rule to convert boolean in a string value to real boolean and back
 *
 * @author lokkie
 * @version 0.1
 * @see org.lokkie.types.UnixTimestamp
 */
public class StringBooleanAdapter extends TypeAdapter<StringBoolean> {

    @Override
    public void write(JsonWriter out, StringBoolean value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getStoredValue());
        }
    }

    @Override
    public StringBoolean read(JsonReader in) throws IOException {
        StringBoolean result = new StringBoolean();
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            result.setStoredValue(null);
        } else {
            String jsonData = in.nextString();
            result.setStoredValue(jsonData);
        }
        return result;
    }
}
