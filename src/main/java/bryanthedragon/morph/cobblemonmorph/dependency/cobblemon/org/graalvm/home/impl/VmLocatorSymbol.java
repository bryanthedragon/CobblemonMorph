package org.graalvm.home.impl;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.function.CFunctionPointer;

final class VmLocatorSymbol {
   static final CEntryPointLiteral<CFunctionPointer> SYMBOL = CEntryPointLiteral.create(VmLocatorSymbol.class, "vmLocatorSymbol", IsolateThread.class);

   private VmLocatorSymbol() {
      throw new IllegalStateException("No instance allowed");
   }

   @CEntryPoint(name = "graal_vm_locator_symbol", publishAs = CEntryPoint.Publish.SymbolOnly)
   private static void vmLocatorSymbol(IsolateThread thread) {
   }
}
