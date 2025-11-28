package org.graalvm.word;

public interface ComparableWord extends WordBase {
   boolean equal(ComparableWord val);

   boolean notEqual(ComparableWord val);
}
