package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);

        try {
            return mapper.readValue(is, new TypeReference<List<Measurement>>() {});
        } catch (IOException e) {
            throw new FileProcessException(e.getMessage());
        }
    }

}
