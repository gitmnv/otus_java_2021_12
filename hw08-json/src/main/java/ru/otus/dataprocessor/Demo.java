package ru.otus.dataprocessor;

public class Demo {
    public static void main(String[] args) {
        new FileSerializer("outputData.json").serialize(
                new ProcessorAggregator().process(
                        new ResourcesFileLoader("inputData.json").load()));
    }
}
