package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.time.LocalDateTime;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()),new ReplaceProcessor(),new TimeProcessor(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        var listenerHistory = new HistoryListener();
        complexProcessor.addListener(listenerHistory);
        complexProcessor.addListener(listenerPrinter);
        var obj = new ObjectForMessage();
        obj.setData(List.of("data"));

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(obj)
                .build();

        var result = complexProcessor.handle(message);

        complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
