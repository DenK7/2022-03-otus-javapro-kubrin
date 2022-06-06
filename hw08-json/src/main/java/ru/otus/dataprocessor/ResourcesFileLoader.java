package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(classloader.getResource(fileName)).getFile().replace("%20", " "));

        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);

        try {
            return mapper.readValue(file, new TypeReference<List<Measurement>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
