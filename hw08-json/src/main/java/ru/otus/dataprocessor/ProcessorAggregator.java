package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> values = new TreeMap<>();
        for (Measurement datum : data) {
            values.merge(datum.getName(), datum.getValue(), Double::sum);
        }
        //группирует выходящий список по name, при этом суммирует поля value
      return values;
    }
}
