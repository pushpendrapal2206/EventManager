package com.salesforce.eventmanager.api.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ReflectionUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean isConstructorPrivateAndInstantiationThrowUnsupportedException(final Class type)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        try {
            final Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (InvocationTargetException ex) {
            return true;
        }
        return false;
    }
}
