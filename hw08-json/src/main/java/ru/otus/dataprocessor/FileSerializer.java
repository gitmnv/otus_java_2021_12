package ru.otus.dataprocessor;


import java.io.*;
import java.util.List;
import java.util.Map;

public class FileSerializer implements Serializer {

    private String fileName;


    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        List<String> keySet = data.keySet().stream().toList();

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            StringBuilder text = new StringBuilder();
            text.append("{");
            for (String s : keySet) {
                text.append("\"").append(s).append("\"");
                text.append(":").append(data.get(s)).append(",");
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

