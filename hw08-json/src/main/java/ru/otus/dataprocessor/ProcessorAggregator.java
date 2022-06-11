package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        var result = new TreeMap<String, Double>();

        for (Measurement measurement: data) {
            result.merge(measurement.getName(), measurement.getValue(), Double::sum);
        }
        return result;
    }
}
