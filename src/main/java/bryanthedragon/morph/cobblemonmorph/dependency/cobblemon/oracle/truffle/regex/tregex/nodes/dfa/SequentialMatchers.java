package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import java.util.Objects;

public abstract class SequentialMatchers extends Matchers {
   private final short noMatchSuccessor;

   SequentialMatchers(short noMatchSuccessor) {
      this.noMatchSuccessor = noMatchSuccessor;
   }

   public short getNoMatchSuccessor() {
      return this.noMatchSuccessor;
   }

   public abstract int size();

   static int size(CharMatcher[]... matchersArr) {
      for (CharMatcher[] matchers : matchersArr) {
         if (matchers != null) {
            return matchers.length;
         }
      }

      return 0;
   }

   public abstract boolean match(int i, int c);

   public int match(int c) {
      for (int i = 0; i < this.size(); i++) {
         if (this.match(i, c)) {
            return i;
         }
      }

      return this.noMatchSuccessor;
   }

   public abstract String toString(int i);

   static boolean match(CharMatcher[] matchers, int i, int c) {
      return matchers != null && matchers[i] != null && matchers[i].match(c);
   }

   @CompilerDirectives.TruffleBoundary
   static String toString(CharMatcher[] matchers, int i) {
      return matchers != null && matchers[i] != null ? Objects.toString(matchers[i]) : "";
   }

   public static final class Builder {
      private final ObjectArrayBuffer<CharMatcher>[] buffers;
      private short noMatchSuccessor = -1;

      public Builder(int nBuffers) {
         this.buffers = new ObjectArrayBuffer[nBuffers];

         for (int i = 0; i < this.buffers.length; i++) {
            this.buffers[i] = new ObjectArrayBuffer<>();
         }
      }

      public void reset(int nTransitions) {
         for (ObjectArrayBuffer<CharMatcher> buf : this.buffers) {
            buf.asFixedSizeArray(nTransitions);
         }

         this.noMatchSuccessor = -1;
      }

      public ObjectArrayBuffer<CharMatcher> getBuffer(int i) {
         return this.buffers[i];
      }

      public short getNoMatchSuccessor() {
         return this.noMatchSuccessor;
      }

      public void setNoMatchSuccessor(short noMatchSuccessor) {
         this.noMatchSuccessor = noMatchSuccessor;
      }

      public int estimatedCost(int i) {
         int ret = 0;

         for (ObjectArrayBuffer<CharMatcher> buf : this.buffers) {
            if (buf != null && buf.get(i) != null) {
               ret = Math.max(ret, buf.get(i).estimatedCost());
            }
         }

         return ret;
      }

      public void createSplitMatcher(int i, CodePointSet cps, CompilationBuffer compilationBuffer, CodePointSet... splitRanges) {
         for (int j = 0; j < splitRanges.length; j++) {
            CodePointSet intersection = splitRanges[j].createIntersection(cps, compilationBuffer);
            if (intersection.matchesSomething()) {
               assert i < this.buffers[j].length();

               this.buffers[j].set(i, CharMatchers.createMatcher(intersection, compilationBuffer));
            }
         }
      }

      public CharMatcher[] materialize(int buf) {
         return isEmpty(this.buffers[buf]) ? null : this.buffers[buf].toArray(new CharMatcher[this.buffers[buf].length()]);
      }

      private static boolean isEmpty(ObjectArrayBuffer<CharMatcher> buf) {
         for (CharMatcher m : buf) {
            if (m != null) {
               return false;
            }
         }

         return true;
      }
   }

   public static final class SimpleSequentialMatchers extends SequentialMatchers {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] matchers;

      public SimpleSequentialMatchers(CharMatcher[] matchers, short noMatchSuccessor) {
         super(noMatchSuccessor);
         this.matchers = matchers;
      }

      public CharMatcher[] getMatchers() {
         return this.matchers;
      }

      @Override
      public int size() {
         return this.matchers.length;
      }

      @Override
      public boolean match(int i, int c) {
         return match(this.matchers, i, c);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString(int i) {
         return this.matchers[i].toString();
      }
   }

   public static final class UTF16Or32SequentialMatchers extends SequentialMatchers {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] ascii;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] latin1;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] bmp;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] astral;

      public UTF16Or32SequentialMatchers(CharMatcher[] ascii, CharMatcher[] latin1, CharMatcher[] bmp, CharMatcher[] astral, short noMatchSuccessor) {
         super(noMatchSuccessor);
         this.ascii = ascii;
         this.latin1 = latin1;
         this.bmp = bmp;
         this.astral = astral;
      }

      public CharMatcher[] getAscii() {
         return this.ascii;
      }

      public CharMatcher[] getLatin1() {
         return this.latin1;
      }

      public CharMatcher[] getBmp() {
         return this.bmp;
      }

      public CharMatcher[] getAstral() {
         return this.astral;
      }

      @Override
      public int size() {
         return size(this.latin1, this.bmp, this.astral);
      }

      @Override
      public boolean match(int i, int c) {
         return match(this.latin1, i, c) || match(this.bmp, i, c) || match(this.astral, i, c);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString(int i) {
         return toString(this.latin1, i) + toString(this.bmp, i) + toString(this.astral, i);
      }
   }

   public static final class UTF16RawSequentialMatchers extends SequentialMatchers {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] ascii;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] latin1;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] bmp;

      public UTF16RawSequentialMatchers(CharMatcher[] ascii, CharMatcher[] latin1, CharMatcher[] bmp, short noMatchSuccessor) {
         super(noMatchSuccessor);
         this.ascii = ascii;
         this.latin1 = latin1;
         this.bmp = bmp;
      }

      public CharMatcher[] getAscii() {
         return this.ascii;
      }

      public CharMatcher[] getLatin1() {
         return this.latin1;
      }

      public CharMatcher[] getBmp() {
         return this.bmp;
      }

      @Override
      public int size() {
         return size(this.latin1, this.bmp);
      }

      @Override
      public boolean match(int i, int c) {
         return match(this.latin1, i, c) || match(this.bmp, i, c);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString(int i) {
         return toString(this.latin1, i) + toString(this.bmp, i);
      }
   }

   public static final class UTF8SequentialMatchers extends SequentialMatchers {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] ascii;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] enc2;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] enc3;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final CharMatcher[] enc4;
      private final int maxBytes;

      public UTF8SequentialMatchers(CharMatcher[] ascii, CharMatcher[] enc2, CharMatcher[] enc3, CharMatcher[] enc4, short noMatchSuccessor) {
         super(noMatchSuccessor);
         this.ascii = ascii;
         this.enc2 = enc2;
         this.enc3 = enc3;
         this.enc4 = enc4;
         this.maxBytes = enc4 != null ? 4 : (enc3 != null ? 3 : (enc2 != null ? 2 : 1));
      }

      public CharMatcher[] getAscii() {
         return this.ascii;
      }

      public CharMatcher[] getEnc2() {
         return this.enc2;
      }

      public CharMatcher[] getEnc3() {
         return this.enc3;
      }

      public CharMatcher[] getEnc4() {
         return this.enc4;
      }

      public int getMaxBytes() {
         return this.maxBytes;
      }

      @Override
      public int size() {
         return size(this.ascii, this.enc2, this.enc3, this.enc4);
      }

      @Override
      public boolean match(int i, int c) {
         return match(this.ascii, i, c) || match(this.enc2, i, c) || match(this.enc3, i, c) || match(this.enc4, i, c);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString(int i) {
         return toString(this.ascii, i) + toString(this.enc2, i) + toString(this.enc3, i) + toString(this.enc4, i);
      }
   }
}
