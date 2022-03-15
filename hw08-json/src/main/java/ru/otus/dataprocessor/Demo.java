package ru.otus.dataprocessor;

import java.io.File;

public class Demo {
    public static void main(String[] args) {
        ResourcesFileLoader resourcesFileLoader = new ResourcesFileLoader("inputData.json");
        ProcessorAggregator aggregator = new ProcessorAggregator();
        FileSerializer serializer = new FileSerializer("outputData.json");
        serializer.serialize(aggregator.process(resourcesFileLoader.load()));

    }
}
