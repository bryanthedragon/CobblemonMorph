package com.oracle.js.parser;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.Base64.Encoder;

public final class Source {
   private static final int BUF_SIZE = 8192;
   private final String name;
   private final String base;
   private final Source.Data data;
   private int hash;
   private volatile byte[] digest;
   private String explicitURL;

   private Source(final String name, final String base, final Source.Data data) {
      this.name = name;
      this.base = base;
      this.data = data;
   }

   private String data() {
      return this.data.data();
   }

   private int length() {
      return this.data.length();
   }

   public static Source sourceFor(final String name, final CharSequence content, final boolean isEval) {
      return new Source(name, baseName(name), new Source.RawData(content, isEval));
   }

   public static Source sourceFor(final String name, final String content) {
      return sourceFor(name, content, false);
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Source)) {
         return false;
      } else {
         Source other = (Source)obj;
         return Objects.equals(this.name, other.name) && this.data.equals(other.data);
      }
   }

   @Override
   public int hashCode() {
      int h = this.hash;
      if (h == 0) {
         h = this.hash = this.data.hashCode() ^ Objects.hashCode(this.name);
      }

      return h;
   }

   public String getName() {
      return this.name;
   }

   public long getLastModified() {
      return this.data.lastModified();
   }

   public String getBase() {
      return this.base;
   }

   public String getString(final int start, final int len) {
      return this.data().substring(start, start + len);
   }

   public String getString(final long token) {
      int start = Token.descPosition(token);
      int len = Token.descLength(token);
      return this.getString(start, len);
   }

   public URL getURL() {
      return this.data.url();
   }

   public String getExplicitURL() {
      return this.explicitURL;
   }

   public void setExplicitURL(String explicitURL) {
      this.explicitURL = explicitURL;
   }

   public boolean isEvalCode() {
      return this.data.isEvalCode();
   }

   private int findBOLN(final int position) {
      String d = this.data();

      for (int i = position - 1; i >= 0; i--) {
         char ch = d.charAt(i);
         if (ch == '\n' || ch == '\r') {
            return i + 1;
         }
      }

      return 0;
   }

   private int findEOLN(final int position) {
      String d = this.data();
      int length = this.length();

      for (int i = position; i < length; i++) {
         char ch = d.charAt(i);
         if (ch == '\n' || ch == '\r') {
            return i - 1;
         }
      }

      return length - 1;
   }

   public int getLine(final int position) {
      String d = this.data();
      int line = 1;

      for (int i = 0; i < position; i++) {
         char ch = d.charAt(i);
         if (ch == '\n') {
            line++;
         }
      }

      return line;
   }

   public int getColumn(final int position) {
      return position - this.findBOLN(position);
   }

   public String getSourceLine(final int position) {
      int first = this.findBOLN(position);
      int last = this.findEOLN(position);
      return this.getString(first, last + 1 - first);
   }

   public String getContent() {
      return this.data();
   }

   public int getLength() {
      return this.data.length();
   }

   public static String readFully(final Reader reader) throws IOException {
      char[] arr = new char[8192];
      StringBuilder sb = new StringBuilder();

      int numChars;
      try {
         while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
            sb.append(arr, 0, numChars);
         }
      } finally {
         reader.close();
      }

      return sb.toString();
   }

   public String getDigest() {
      return new String(this.getDigestBytes(), StandardCharsets.US_ASCII);
   }

   private byte[] getDigestBytes() {
      byte[] ldigest = this.digest;
      if (ldigest == null) {
         byte[] bytes = this.data().getBytes(StandardCharsets.UTF_16LE);

         try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (this.name != null) {
               md.update(this.name.getBytes(StandardCharsets.UTF_8));
            }

            if (this.base != null) {
               md.update(this.base.getBytes(StandardCharsets.UTF_8));
            }

            if (this.getURL() != null) {
               md.update(this.getURL().toString().getBytes(StandardCharsets.UTF_8));
            }

            Encoder base64 = Base64.getUrlEncoder().withoutPadding();
            this.digest = ldigest = base64.encode(md.digest(bytes));
         } catch (NoSuchAlgorithmException var5) {
            throw new RuntimeException(var5);
         }
      }

      return ldigest;
   }

   private static String baseName(final String name) {
      int idx = name.lastIndexOf(47);
      if (idx == -1) {
         idx = name.lastIndexOf(92);
      }

      return idx != -1 ? name.substring(0, idx + 1) : null;
   }

   @Override
   public String toString() {
      return this.getName();
   }

   private interface Data {
      URL url();

      int length();

      long lastModified();

      String data();

      boolean isEvalCode();
   }

   private static final class RawData implements Source.Data {
      private final String source;
      private final boolean evalCode;
      private int hash;

      private RawData(final CharSequence source, final boolean evalCode) {
         this.source = source.toString();
         this.evalCode = evalCode;
      }

      private RawData(final Reader reader) throws IOException {
         this(Source.readFully(reader), false);
      }

      @Override
      public int hashCode() {
         int h = this.hash;
         if (h == 0) {
            h = this.hash = this.source.hashCode() ^ (this.evalCode ? 1 : 0);
         }

         return h;
      }

      @Override
      public boolean equals(final Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof Source.RawData)) {
            return false;
         } else {
            Source.RawData other = (Source.RawData)obj;
            return this.source.equals(other.source) && this.evalCode == other.evalCode;
         }
      }

      @Override
      public String toString() {
         return this.data();
      }

      @Override
      public URL url() {
         return null;
      }

      @Override
      public int length() {
         return this.source.length();
      }

      @Override
      public long lastModified() {
         return 0L;
      }

      @Override
      public String data() {
         return this.source;
      }

      @Override
      public boolean isEvalCode() {
         return this.evalCode;
      }
   }
}
