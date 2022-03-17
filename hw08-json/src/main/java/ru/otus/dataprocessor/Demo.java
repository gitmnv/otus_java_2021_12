package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Demo {
    public static void main(String[] args) throws JsonProcessingException {
        ResourcesFileLoader resourcesFileLoader = new ResourcesFileLoader("inputData.json");
        resourcesFileLoader.load();
       // ProcessorAggregator aggregator = new ProcessorAggregator();
       // FileSerializer serializer = new FileSerializer("outputData.json");
      //  serializer.serialize(aggregator.process(resourcesFileLoader.load()));
    }
}
