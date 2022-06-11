package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorConcatFields implements Processor {

    @Override
    public Message process(Message message) {
        System.out.println("ProcessorConcatFields");
        var newFieldValue = String.join(" ", "concat:", message.getField1(), message.getField2(), message.getField3());
        return message.toBuilder().field4(newFieldValue).build();
    }
}
