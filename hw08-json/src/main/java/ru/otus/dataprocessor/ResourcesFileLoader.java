package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
        File is = new File(Objects.requireNonNull(classloader.getResource(fileName)).getFile().replace("%20", " "));

        ObjectMapper mapper = new ObjectMapper();
        try {
//            CollectionType listType =
//                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Measurement.class);
//            return mapper.readValue(is, listType);
            return mapper.readValue(is, new TypeReference<List>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
