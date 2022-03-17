package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;
import ru.otus.model.MeasurementTemp;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private FileInputStream inputStream;
    private ObjectMapper objectMapper;


    public ResourcesFileLoader(String fileName) {
        try {
            inputStream = new FileInputStream(getFile(fileName));
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        ObjectMapper mapper = new ObjectMapper();
        List<Measurement> measurementList = new ArrayList<>();
        List<MeasurementTemp> measurementTempList = new ArrayList<>();
        String json = "";

        try {
            json = new String(inputStream.readAllBytes(), "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            measurementTempList = Arrays.stream(mapper.readValue(json, MeasurementTemp[].class)).toList();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (MeasurementTemp temp : measurementTempList) {
            measurementList.add(new Measurement(temp.getName(), temp.getValue()));
        }
        //читает файл, парсит и возвращает результат
        return measurementList;
    }

    public List<Measurement> load(String s) {

        return null;
    }

    private File getFile(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new FileProcessException(e.getMessage());
            }

        }
    }
}
