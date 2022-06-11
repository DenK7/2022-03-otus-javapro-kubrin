package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
     */

    public static void main(String[] args) {
        var processors = List.of(
                new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()),
                new ProcessorSwap(),
                new ProcessorException());

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new HistoryListener(); //ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var list = new ArrayList<String>();
        list.add("row1");
        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(list);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
