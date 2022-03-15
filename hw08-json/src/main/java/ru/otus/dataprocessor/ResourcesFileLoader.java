package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;
import ru.otus.model.MeasurementTemp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private FileInputStream inputStream;


    public ResourcesFileLoader(String fileName) {
        try {
            inputStream = new FileInputStream(generatePath(fileName));
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    private String generatePath(String fileName){

        return System.getProperty("user.dir")+"\\src\\test\\resources\\"+fileName;
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
}
