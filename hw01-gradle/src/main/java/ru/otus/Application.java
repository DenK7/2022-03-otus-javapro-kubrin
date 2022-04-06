package ru.otus;

import com.google.common.collect.Lists;

import java.util.List;

public class Application {
    public static void main(String... args) {
        List<String> vals = Lists.newArrayList("val01", "val02", "val03", "val04");
        System.out.println(Lists.reverse(vals));

    }
}
