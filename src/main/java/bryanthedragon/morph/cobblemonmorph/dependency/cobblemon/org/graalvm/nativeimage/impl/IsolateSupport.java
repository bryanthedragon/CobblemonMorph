
package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Isolate;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.Isolates;

public interface IsolateSupport {
    public IsolateThread createIsolate(Isolates.CreateIsolateParameters var1) throws Isolates.IsolateException;

    public IsolateThread attachCurrentThread(Isolate var1) throws Isolates.IsolateException;

    public IsolateThread getCurrentThread(Isolate var1) throws Isolates.IsolateException;

    public Isolate getIsolate(IsolateThread var1) throws Isolates.IsolateException;

    public void detachThread(IsolateThread var1) throws Isolates.IsolateException;

    public void tearDownIsolate(IsolateThread var1) throws Isolates.IsolateException;
}

