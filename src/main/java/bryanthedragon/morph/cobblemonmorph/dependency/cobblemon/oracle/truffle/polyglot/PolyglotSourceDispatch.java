package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.source.Source;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.ByteSequence;

final class PolyglotSourceDispatch extends AbstractPolyglotImpl.AbstractSourceDispatch {
   protected PolyglotSourceDispatch(AbstractPolyglotImpl engineImpl) {
      super(engineImpl);
   }

   @Override
   public String getName(Object impl) {
      Source source = (Source)impl;
      return source.getName();
   }

   @Override
   public String getPath(Object impl) {
      Source source = (Source)impl;
      return source.getPath();
   }

   @Override
   public boolean isCached(Object impl) {
      Source source = (Source)impl;
      return source.isCached();
   }

   @Override
   public boolean isInteractive(Object impl) {
      Source source = (Source)impl;
      return source.isInteractive();
   }

   @Override
   public boolean isInternal(Object impl) {
      Source source = (Source)impl;
      return source.isInternal();
   }

   @Override
   public URL getURL(Object impl) {
      Source source = (Source)impl;
      return source.getURL();
   }

   @Override
   public URI getURI(Object impl) {
      Source source = (Source)impl;
      return source.getURI();
   }

   @Override
   public Reader getReader(Object impl) {
      Source source = (Source)impl;
      return source.getReader();
   }

   @Override
   public InputStream getInputStream(Object impl) {
      return new ByteArrayInputStream(this.getCharacters(impl).toString().getBytes());
   }

   @Override
   public int getLength(Object impl) {
      Source source = (Source)impl;
      return source.getLength();
   }

   @Override
   public CharSequence getCharacters(Object impl) {
      Source source = (Source)impl;
      return source.getCharacters();
   }

   @Override
   public CharSequence getCharacters(Object impl, int lineNumber) {
      Source source = (Source)impl;
      return source.getCharacters(lineNumber);
   }

   @Override
   public int getLineCount(Object impl) {
      Source source = (Source)impl;
      return source.getLineCount();
   }

   @Override
   public int getLineNumber(Object impl, int offset) {
      Source source = (Source)impl;
      return source.getLineNumber(offset);
   }

   @Override
   public int getColumnNumber(Object impl, int offset) {
      Source source = (Source)impl;
      return source.getColumnNumber(offset);
   }

   @Override
   public int getLineStartOffset(Object impl, int lineNumber) {
      Source source = (Source)impl;
      return source.getLineStartOffset(lineNumber);
   }

   @Override
   public int getLineLength(Object impl, int lineNumber) {
      Source source = (Source)impl;
      return source.getLineLength(lineNumber);
   }

   @Override
   public String toString(Object impl) {
      Source source = (Source)impl;
      return source.toString();
   }

   @Override
   public String getMimeType(Object impl) {
      Source source = (Source)impl;
      return source.getMimeType();
   }

   @Override
   public String getLanguage(Object impl) {
      Source source = (Source)impl;
      return source.getLanguage();
   }

   @Override
   public int hashCode(Object impl) {
      return impl.hashCode();
   }

   @Override
   public boolean equals(Object impl, Object otherImpl) {
      return impl.equals(otherImpl);
   }

   @Override
   public ByteSequence getBytes(Object impl) {
      Source source = (Source)impl;
      return source.getBytes();
   }

   @Override
   public boolean hasBytes(Object impl) {
      Source source = (Source)impl;
      return source.hasBytes();
   }

   @Override
   public boolean hasCharacters(Object impl) {
      Source source = (Source)impl;
      return source.hasCharacters();
   }
}
