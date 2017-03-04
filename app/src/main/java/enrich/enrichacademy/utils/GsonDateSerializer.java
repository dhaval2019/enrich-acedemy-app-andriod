package enrich.enrichacademy.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 02-Mar-17.
 */

public class GsonDateSerializer implements JsonSerializer<Date>,
        JsonDeserializer<Date> {

    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat format3 = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss a");

    public GsonDateSerializer() {

    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type,
                                              JsonSerializationContext jsonSerializationContext) {
        synchronized (format1) {

            String dateFormatAsString = format1.format(date);
            return new JsonPrimitive(dateFormatAsString);
        }
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                         JsonDeserializationContext jsonDeserializationContext) {
        Date date = null;
        try {
            synchronized (format1) {
                String j = jsonElement.getAsJsonPrimitive().getAsString();
                date = parseDate(j);//dateFormat.parse(jsonElement.getAsString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date parseDate(String dateString) throws ParseException {
        if (dateString != null && dateString.trim().length() > 0) {
            try {
                return format1.parse(dateString);
            } catch (ParseException pe) {
                try {
                    return format2.parse(dateString);
                } catch (ParseException pp) {
                    return format3.parse(dateString);
                }
            }

        } else {
            return null;
        }
    }
}
