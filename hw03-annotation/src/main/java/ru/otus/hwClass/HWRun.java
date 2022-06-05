package ru.otus.hwClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

public class HWRun {

    /*
    * алгоритм запуска
    * 1 - определение класса
    * 2 - создание мапы для вывода статистики и определения уже отработанных методов помеченных аннотацией Тест
    * 3 - определяем количество методов помеченных аннотацией Тест - если имена будут одинаковые, нужно будет переработать алгоритм определения отработанного метода
    * 4 - для каждого метода создаем экземпляр и отрабатываем все методы помеченные Before и After
    * 5 - After отрабатываем всегда, Before если падает - прерывает выполнение Test
    * 6 - статистику заполняем по мере выполнения
    * 7 - вывод статистики после выполнения на основе мапы*/
    public static void run (String className) throws Exception {
        System.out.println("--------> Run");

        Class<?> clazz = Class.forName(className);
        System.out.println("Class ----> " + clazz.getName());

        var statistic = new HashMap<String, Boolean>();
        createObjectAndRunProcess(clazz, statistic);

        System.out.println("");
        System.out.println("-----------> Statistic <-----------");
        statistic.forEach((s, bool) -> System.out.println(s + " : " + bool));
    }

    private static void createObjectAndRunProcess(Class<?> clazz, HashMap<String, Boolean> statistic) throws Exception {
        for (int i = 0; i < getCountTestMethods(clazz); i++) {

            // на каждый метод Test свой экземпляр класса
            Constructor<?> constructor = clazz.getConstructor();
            var objectFromClazz = constructor.newInstance();

            runProcess(clazz, objectFromClazz, statistic);
        }
    }

    // количество циклов которые нужно запустить - на каждый Test все Before и After
    private static int getCountTestMethods(Class<?> clazz) {
        int i =0;
        for (var method: clazz.getMethods()) {
            if (checkAnnotation(method, "Test")) {
                i += 1;
            }
        }
        return i;
    }

    // процесс запуска 1 метода с аннотацией Test
    private static void runProcess(Class<?> clazz, Object objectFromClazz, HashMap<String, Boolean> statistic) {
        try {
            runMethod("Before", clazz, objectFromClazz, statistic);
            runMethod("Test", clazz, objectFromClazz, statistic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            // after запускаем всегда вообще
            runMethod("After", clazz, objectFromClazz, statistic);
        }
    }

    // запуска метода с аннотацией Test
    private static void runMethod(String annotationName, Class<?> clazz, Object objectFromClazz, HashMap<String, Boolean> statistic) {
        for (Method method: clazz.getMethods()) {
            if (checkAnnotation(method, annotationName) &&
                    (!"Test".equals(annotationName) || statistic.get(method.getName()) == null)) {
                try {
                    method.invoke(objectFromClazz);
                    if ("Test".equals(annotationName)) {
                        statistic.put(method.getName(), Boolean.TRUE);
                        break;
                    }
                } catch (Exception e) {
                    // для вывода статистики выводим все ошибочные
                    statistic.put(method.getName(), Boolean.FALSE);
                }
            }
        }
    }

    //проверка на наличие аннотации
    private static Boolean checkAnnotation (Method method, String annotationName) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation: annotations) {
            if (annotationName.equals(annotation.annotationType().getSimpleName())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
