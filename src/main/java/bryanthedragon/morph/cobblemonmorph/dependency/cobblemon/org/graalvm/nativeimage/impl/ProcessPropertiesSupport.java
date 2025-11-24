
package org.graalvm.nativeimage.impl;

import java.nio.file.Path;
import java.util.Map;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;

public interface ProcessPropertiesSupport {
    public String getExecutableName();

    public long getProcessID();

    public long getProcessID(Process var1);

    public String getObjectFile(String var1);

    public String getObjectFile(CEntryPointLiteral<?> var1);

    public String setLocale(String var1, String var2);

    public boolean destroy(long var1);

    public boolean destroyForcibly(long var1);

    public boolean isAlive(long var1);

    public int waitForProcessExit(long var1);

    public void exec(Path var1, String[] var2);

    public void exec(Path var1, String[] var2, Map<String, String> var3);

    public int getArgumentVectorBlockSize();

    public String getArgumentVectorProgramName();

    public boolean setArgumentVectorProgramName(String var1);
}

