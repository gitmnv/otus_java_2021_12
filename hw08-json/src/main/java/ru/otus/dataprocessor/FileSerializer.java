package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final ObjectMapper mapper;
    private final FileWriter fileWriter;

    public FileSerializer(String fileName) {
        this.mapper = new ObjectMapper();
        try {
            this.fileWriter = new FileWriter(fileName);
        } catch (IOException exception) {
            throw new FileProcessException(exception.getMessage());
        }
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            String text = mapper.writeValueAsString(data);
            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException exception) {
            throw new FileProcessException(exception.getMessage());
        }
    }
//формирует результирующий json и сохраняет его в файл
}

