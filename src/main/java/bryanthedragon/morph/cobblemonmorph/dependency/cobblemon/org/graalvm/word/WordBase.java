package org.graalvm.word;

public interface WordBase {
   long rawValue();

   @Deprecated
   @Override
   boolean equals(Object o);
}
