package ru.otus.hwClass;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class HWDemoClass {

    @Before
    public void getBefore(){
        System.out.println("-----> Before");
    }

    @Before
    public void getBefore2(){
        System.out.println("-----> Before-2");
    }

    @After
    public void getAfter () {
        System.out.println("-----> After");
    }

    @Test
    public void getTest() {
        System.out.println("-----> Test");
    }

    public HWDemoClass() {
        System.out.println("-----> constructor");
    }

    @Test
    public void getTestEx() {
        System.out.println("-----> Test Exception");
        throw new RuntimeException("test Exception");
    }
}
