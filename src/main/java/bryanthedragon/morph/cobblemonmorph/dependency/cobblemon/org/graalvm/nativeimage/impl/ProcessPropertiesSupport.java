package org.graalvm.nativeimage.impl;

import java.nio.file.Path;
import java.util.Map;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;

public interface ProcessPropertiesSupport {
   String getExecutableName();

   long getProcessID();

   long getProcessID(Process process);

   String getObjectFile(String symbol);

   String getObjectFile(CEntryPointLiteral<?> symbol);

   String setLocale(String category, String locale);

   boolean destroy(long processID);

   boolean destroyForcibly(long processID);

   boolean isAlive(long processID);

   int waitForProcessExit(long processID);

   void exec(Path executable, String[] args);

   void exec(Path executable, String[] args, Map<String, String> env);

   int getArgumentVectorBlockSize();

   String getArgumentVectorProgramName();

   boolean setArgumentVectorProgramName(String name);
}
