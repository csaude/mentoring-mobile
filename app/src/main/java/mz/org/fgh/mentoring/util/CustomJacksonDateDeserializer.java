package mz.org.fgh.mentoring.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class CustomJacksonDateDeserializer extends JsonDeserializer<Date> {
    private String[] supportedFormats = {
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss+HH:mm",
        "yyyy-MM-dd'T'HH:mm:ss-HH:mm",
        "EEE, dd MMM yyyy HH:mm:ss zzz",
        "yyyy-MM-dd",
        "dd-MM-yyyy",
        "dd-MM-yyyy HH:mm:ss",
    };

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        try {
            return DateUtils.parseDate(jsonParser.getText(), supportedFormats);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
