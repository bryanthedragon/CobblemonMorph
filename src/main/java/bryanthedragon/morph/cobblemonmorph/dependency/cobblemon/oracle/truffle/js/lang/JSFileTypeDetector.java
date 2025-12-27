package com.oracle.truffle.js.lang;

import com.oracle.truffle.api.TruffleFile;
import java.io.IOException;
import java.nio.charset.Charset;

public final class JSFileTypeDetector implements TruffleFile.FileTypeDetector {
   @Override
   public String findMimeType(TruffleFile file) throws IOException {
      String fileName = file.getName();
      if (fileName != null) {
         if (fileName.endsWith(".js")) {
            return "application/javascript";
         }

         if (fileName.endsWith(".mjs")) {
            return "application/javascript+module";
         }

         if (fileName.endsWith(".json")) {
            return "application/json";
         }
      }

      return null;
   }

   @Override
   public Charset findEncoding(TruffleFile file) throws IOException {
      return null;
   }
}
