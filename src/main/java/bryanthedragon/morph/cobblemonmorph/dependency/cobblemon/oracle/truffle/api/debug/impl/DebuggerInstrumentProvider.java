package com.oracle.truffle.api.debug.impl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import java.util.Arrays;
import java.util.Collection;

@GeneratedBy(DebuggerInstrument.class)
@TruffleInstrument.Registration(id = "debugger", name = "Debugger")
public final class DebuggerInstrumentProvider implements TruffleInstrument.Provider {
   @Override
   public String getInstrumentClassName() {
      return "com.oracle.truffle.api.debug.impl.DebuggerInstrument";
   }

   @Override
   public TruffleInstrument create() {
      return new DebuggerInstrument();
   }

   @Override
   public Collection<String> getServicesClassNames() {
      return Arrays.asList("com.oracle.truffle.api.debug.Debugger");
   }
}
