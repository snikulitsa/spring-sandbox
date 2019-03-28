package com.nikulitsa.springsandbox.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

/**
 * @author Sergey Nikulitsa
 */
public class InstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser parser,
                               DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Instant.ofEpochMilli(parser.getValueAsLong());
    }
}
