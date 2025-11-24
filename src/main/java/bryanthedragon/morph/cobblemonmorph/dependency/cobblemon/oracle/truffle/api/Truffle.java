/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Module
 *  java.lang.ModuleLayer
 */
package com.oracle.truffle.api;

import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.TruffleRuntimeAccess;
import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

public final class Truffle {
    private static final TruffleRuntime RUNTIME = Truffle.initRuntime();

    private Truffle() {
    }

    public static TruffleRuntime getRuntime() {
        return RUNTIME;
    }

    private static TruffleRuntimeAccess selectTruffleRuntimeAccess(List<Iterable<TruffleRuntimeAccess>> lookups) {
        TruffleRuntimeAccess selectedAccess = null;
        for (Iterable<TruffleRuntimeAccess> lookup : lookups) {
            if (lookup == null) continue;
            Iterator<TruffleRuntimeAccess> it = lookup.iterator();
            while (it.hasNext()) {
                TruffleRuntimeAccess access;
                try {
                    access = it.next();
                }
                catch (ServiceConfigurationError err) {
                    continue;
                }
                if (selectedAccess == null) {
                    selectedAccess = access;
                    continue;
                }
                if (selectedAccess == access || selectedAccess.getClass() == access.getClass()) continue;
                if (selectedAccess.getPriority() == access.getPriority()) {
                    throw new InternalError(String.format("Providers for %s with same priority %d: %s (loader: %s) vs. %s (loader: %s)", TruffleRuntimeAccess.class.getName(), access.getPriority(), selectedAccess, selectedAccess.getClass().getClassLoader(), access, access.getClass().getClassLoader()));
                }
                if (selectedAccess.getPriority() >= access.getPriority()) continue;
                selectedAccess = access;
            }
        }
        return selectedAccess;
    }

    private static TruffleRuntime initRuntime() {
        return AccessController.doPrivileged(new PrivilegedAction<TruffleRuntime>(){

            @Override
            public TruffleRuntime run() {
                String runtimeClassName = System.getProperty("truffle.TruffleRuntime");
                if (runtimeClassName != null && !runtimeClassName.isEmpty()) {
                    if (runtimeClassName.equals(DefaultTruffleRuntime.class.getName())) {
                        return new DefaultTruffleRuntime();
                    }
                    try {
                        ClassLoader cl = Thread.currentThread().getContextClassLoader();
                        Class<?> runtimeClass = Class.forName(runtimeClassName, false, cl);
                        return (TruffleRuntime)runtimeClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (Throwable e) {
                        throw new InternalError(e);
                    }
                }
                Class<Truffle> lookupClass = Truffle.class;
                ModuleLayer moduleLayer = lookupClass.getModule().getLayer();
                TruffleRuntimeAccess access = moduleLayer != null ? Truffle.selectTruffleRuntimeAccess(List.of((Object)ServiceLoader.load((ModuleLayer)moduleLayer, TruffleRuntimeAccess.class))) : Truffle.selectTruffleRuntimeAccess(List.of(ServiceLoader.load(TruffleRuntimeAccess.class, lookupClass.getClassLoader())));
                if (access == null) {
                    access = Truffle.selectTruffleRuntimeAccess(List.of(ServiceLoader.load(TruffleRuntimeAccess.class)));
                }
                if (access != null) {
                    Truffle.exportTo(access.getClass());
                    return access.getRuntime();
                }
                return new DefaultTruffleRuntime();
            }
        });
    }

    private static void exportTo(Class<?> client) {
        Module truffleModule = Truffle.class.getModule();
        Truffle.exportFromTo(truffleModule, client.getModule());
    }

    private static void exportFromTo(Module truffleModule, Module clientModule) {
        if (truffleModule != clientModule) {
            Set packages = truffleModule.getPackages();
            for (String pkg : packages) {
                boolean exported = truffleModule.isExported(pkg, clientModule);
                if (exported) continue;
                truffleModule.addExports(pkg, clientModule);
            }
        }
    }
}

