
package org.graalvm.nativeimage.impl.clinit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassInitializationTracking {
    public static final Map<Class<?>, StackTraceElement[]> initializedClasses = new ConcurrentHashMap();
    public static final Map<Object, StackTraceElement[]> instantiatedObjects = Collections.synchronizedMap(new IdentityHashMap());

    public static void reportClassInitialized(Class<?> clazz, StackTraceElement[] stackTrace) {
        initializedClasses.put(clazz, ClassInitializationTracking.relevantStackTrace(stackTrace));
    }

    public static void reportObjectInstantiated(Object o, StackTraceElement[] stackTrace) {
        instantiatedObjects.putIfAbsent(o, ClassInitializationTracking.relevantStackTrace(stackTrace));
    }

    private static StackTraceElement[] relevantStackTrace(StackTraceElement[] stack) {
        ArrayList<StackTraceElement> filteredStack = new ArrayList<StackTraceElement>();
        int lastClinit = 0;
        boolean containsLambdaMetaFactory = false;
        for (int i = 0; i < stack.length; ++i) {
            StackTraceElement stackTraceElement = stack[i];
            if ("<clinit>".equals(stackTraceElement.getMethodName())) {
                lastClinit = i;
            }
            if (stackTraceElement.getClassName().equals("java.lang.invoke.LambdaMetafactory")) {
                containsLambdaMetaFactory = true;
            }
            filteredStack.add(stackTraceElement);
        }
        ArrayList<StackTraceElement> finalStack = lastClinit != 0 && !containsLambdaMetaFactory ? filteredStack.subList(0, lastClinit + 1) : filteredStack;
        return finalStack.toArray(new StackTraceElement[0]);
    }
}

