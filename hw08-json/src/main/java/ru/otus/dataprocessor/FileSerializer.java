package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileSerializer implements Serializer {

    private String fileName;


    public FileSerializer(String fileName) {
        this.fileName = System.getProperty("user.dir")+"\\hw08-json\\src\\test\\resources\\"+fileName;

    }

    @Override
    public void serialize(Map<String, Double> data) {
        List<String> keySet = data.keySet().stream().toList();

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            StringBuilder text = new StringBuilder();
            text.append("{");
            for (int i = 0; i < keySet.size(); i++) {
                text.append("\"").append(keySet.get(i)).append("\"");
                text.append(":").append(data.get(keySet.get(i))).append(",");
            }
            text.setLength(text.length() - 1);
            text.append("}");
            fileWriter.write(text.toString());
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //формирует результирующий json и сохраняет его в файл
}

