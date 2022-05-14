package org.jpos.atmc.model;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	    ZonedDateTime localTime = value.atZone(ZoneId.of("America/Guayaquil"));

        String str = localTime.toString();

        gen.writeString(str);
    }

}
