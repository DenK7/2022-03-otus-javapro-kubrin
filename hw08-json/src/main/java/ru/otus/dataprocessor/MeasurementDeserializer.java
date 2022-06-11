package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementDeserializer extends StdDeserializer<Measurement> {

    public MeasurementDeserializer() {
        this(null);
    }

    public MeasurementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Measurement deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String name = node.get("name").asText();
        double val = node.get("value").doubleValue();
        return new Measurement(name, val);
    }
}
