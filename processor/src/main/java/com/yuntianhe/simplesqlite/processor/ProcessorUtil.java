package com.yuntianhe.simplesqlite.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * desc:
 * author: daiwj on 2020-04-10 22:13
 */
public class ProcessorUtil {

    public static <T> boolean isEmpty(Collection<? extends T> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isAnnotation(TypeElement annotation, Class<? extends Annotation> c) {
        return annotation.getQualifiedName().toString().equals(c.getCanonicalName());
    }

    public static boolean hasAnnotation(Element e, Class<? extends Annotation> c) {
        return e.getAnnotation(c) != null;
    }

    public static List<Element> getElements(RoundEnvironment environment, Class<? extends Annotation> c) {
        List<Element> list = new ArrayList<>();
        Set<? extends Element> targetSet = environment.getElementsAnnotatedWith(c);
        for (Element target : targetSet) {
            list.add(target);
        }
        return list;
    }

    public static List<Element> getInnerElements(Element outerElement, Class<? extends Annotation>... classList) {
        List<Element> list = new ArrayList<>();
        List<? extends Element> targetList = outerElement.getEnclosedElements();
        for (Element target : targetList) {
            for (Class<? extends Annotation> c : classList) {
                if (hasAnnotation(target, c)) {
                    list.add(target);
                }
            }
        }
        return list;
    }

    public static String getElementName(Element target) {
        return target.getSimpleName().toString();
    }

    public static String getElementType(Element target) {
        return target.asType().toString();
    }

    public static String getPackageName(Element classElement) {
        PackageElement pe = (PackageElement) classElement.getEnclosingElement();
        return pe.asType().toString();
    }

    public static String getClassName(Element classElement) {
        return getElementName(classElement);
    }

    public static String getClassPath(Element classElement) {
        return classElement.asType().toString();
    }

}
