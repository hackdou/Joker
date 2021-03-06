package com.lucifaer.jokerframework.sdk.util.yso.util;

import com.nqzero.permit.Permit;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/11
 */
public class Reflections {
    public Reflections() {
    }

    public static void setAccessible(AccessibleObject member) {
        Permit.setAccessible(member);
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                field = getField(clazz.getSuperclass(), fieldName);
            }
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws IllegalAccessException {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws IllegalAccessException {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object ... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static <T> T createWithoutConstructor(Class<T> classToInstantiate) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    public static <T> T createWithConstructor ( Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        return (T)sc.newInstance(consArgs);
    }
}
