package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public class CustomDeserializer extends StdDeserializer<Measurement> {

    protected CustomDeserializer() {
        super(Measurement.class);
    }

    @Override
    public Measurement deserialize(JsonParser p, DeserializationContext ctxt) {
        final JsonNode node;
        try {
            node = p.getCodec().readTree(p);
        } catch (IOException ioException) {
            throw new FileProcessException(ioException.getMessage());
        }

        final String name = node.get("name").asText();
        final double value = (node.get("value")).doubleValue();

        return new Measurement(name, value);
    }

}

