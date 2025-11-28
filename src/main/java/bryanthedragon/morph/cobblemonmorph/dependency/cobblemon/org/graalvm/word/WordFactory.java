package org.graalvm.word;

import org.graalvm.word.impl.WordBoxFactory;
import org.graalvm.word.impl.WordFactoryOpcode;
import org.graalvm.word.impl.WordFactoryOperation;

public final class WordFactory {
   private WordFactory() {
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.ZERO)
   public static <T extends WordBase> T zero() {
      return WordBoxFactory.box(0L);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.ZERO)
   public static <T extends PointerBase> T nullPointer() {
      return WordBoxFactory.box(0L);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.FROM_UNSIGNED)
   public static <T extends UnsignedWord> T unsigned(long val) {
      return WordBoxFactory.box(val);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.FROM_UNSIGNED)
   public static <T extends PointerBase> T pointer(long val) {
      return WordBoxFactory.box(val);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.FROM_UNSIGNED)
   public static <T extends UnsignedWord> T unsigned(int val) {
      return WordBoxFactory.box(val & 4294967295L);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.FROM_SIGNED)
   public static <T extends SignedWord> T signed(long val) {
      return WordBoxFactory.box(val);
   }

   @WordFactoryOperation(opcode = WordFactoryOpcode.FROM_SIGNED)
   public static <T extends SignedWord> T signed(int val) {
      return WordBoxFactory.box(val);
   }
}
