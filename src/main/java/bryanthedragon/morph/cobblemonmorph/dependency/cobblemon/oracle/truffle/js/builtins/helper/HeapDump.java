package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.js.runtime.JSConfig;
import com.sun.management.HotSpotDiagnosticMXBean;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.management.MBeanServer;
import org.graalvm.nativeimage.VMRuntime;

public final class HeapDump {
   private HeapDump() {
   }

   public static void dump(String fileName, boolean live) throws IOException {
      if (JSConfig.SubstrateVM) {
         dumpSVM(fileName, live);
      } else {
         dumpHotSpot(fileName, live);
      }
   }

   private static void dumpSVM(String fileName, boolean live) throws IOException {
      VMRuntime.dumpHeap(fileName, live);
   }

   private static void dumpHotSpot(String fileName, boolean live) throws IOException {
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();
      HotSpotDiagnosticMXBean hotspotMBean = ManagementFactory.newPlatformMXBeanProxy(
         server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class
      );
      hotspotMBean.dumpHeap(fileName, live);
   }

   public static String defaultDumpName() {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
      LocalDateTime now = LocalDateTime.now();
      return "heapdump-" + dtf.format(now) + ".hprof";
   }
}
