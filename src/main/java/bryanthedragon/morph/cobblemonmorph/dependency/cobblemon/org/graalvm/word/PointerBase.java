package org.graalvm.word;

public interface PointerBase extends ComparableWord {
   boolean isNull();

   boolean isNonNull();
}
