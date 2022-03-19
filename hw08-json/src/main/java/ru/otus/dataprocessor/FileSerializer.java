package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final ObjectMapper mapper;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.mapper = new ObjectMapper();
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            final FileWriter fileWriter = new FileWriter(fileName);
            String text = mapper.writeValueAsString(data);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException exception) {
            throw new FileProcessException(exception.getMessage());
        }
    }
//формирует результирующий json и сохраняет его в файл
}

