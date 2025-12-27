package org.graalvm.nativeimage.impl;

import org.graalvm.nativeimage.Isolate;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.Isolates;

public interface IsolateSupport {
   IsolateThread createIsolate(Isolates.CreateIsolateParameters parameters) throws Isolates.IsolateException;

   IsolateThread attachCurrentThread(Isolate isolate) throws Isolates.IsolateException;

   IsolateThread getCurrentThread(Isolate isolate) throws Isolates.IsolateException;

   Isolate getIsolate(IsolateThread thread) throws Isolates.IsolateException;

   void detachThread(IsolateThread thread) throws Isolates.IsolateException;

   void tearDownIsolate(IsolateThread thread) throws Isolates.IsolateException;
}
