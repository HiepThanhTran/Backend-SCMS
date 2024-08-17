package com.fh.scm.util;

import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;


public final class ClassScanner {

    public static Set<Class<?>> getClassesInPackage(String packageName) {
        Reflections reflections = new Reflections(packageName);

        return reflections.getTypesAnnotatedWith(Entity.class);
    }
}
