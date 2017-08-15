package org.lokkie.types.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.lokkie.types.UnixTimestamp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Part of org.lokkie.types.json. Created in cd
 *
 * @author lokkie
 * @version 0.1
 */
public class UnixTimestampAdapter extends TypeAdapter<UnixTimestamp> {
    @Override
    public void write(JsonWriter out, UnixTimestamp value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getLong());
        }
    }

    @Override
    public UnixTimestamp read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        UnixTimestamp result = null;
        String jsonData = in.nextString();
        if (Pattern.compile("^\\d+$").matcher(jsonData).matches()) {
            result = new UnixTimestamp(jsonData);
        } else {
            try {
                result = new UnixTimestamp((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH)).parse(jsonData));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}
