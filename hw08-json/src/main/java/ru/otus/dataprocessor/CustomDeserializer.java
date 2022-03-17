package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomDeserializer extends StdDeserializer<List<Measurement>> {

    protected CustomDeserializer() {
        super(Measurement.class);
    }

    @Override
    public List<Measurement> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        final JsonNode node = p.getCodec().readTree(p);
        List<Measurement> measurementTempList = new ArrayList<>();

        final String name = node.get("name").asText();
        final double value = (node.get("value")).doubleValue();
        measurementTempList.add(new Measurement(name, value));

        return measurementTempList;

    }

}

