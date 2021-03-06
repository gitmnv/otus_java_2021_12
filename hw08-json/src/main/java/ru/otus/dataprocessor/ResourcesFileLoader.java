package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper;
    private static final String encoding = "UTF8";

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        String json;
        List<Measurement> measurementList;

        try {
            final FileInputStream inputStream = new FileInputStream(getFile(fileName));
            json = new String(inputStream.readAllBytes(), encoding);

            measurementList = objectMapper.readValue(json, new TypeReference<>() {
            });
            inputStream.close();
        } catch (IOException exception) {
            throw new FileProcessException(exception.getMessage());
        }
        //читает файл, парсит и возвращает результат
        return measurementList;
    }

    private File getFile(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        if (resource == null) {
            throw new FileProcessException("file not found!");
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new FileProcessException(e.getMessage());
            }
        }
    }
}
