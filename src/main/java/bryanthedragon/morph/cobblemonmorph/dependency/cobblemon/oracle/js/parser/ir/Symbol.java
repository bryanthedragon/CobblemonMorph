package com.oracle.js.parser.ir;

import com.oracle.truffle.api.strings.TruffleString;

public final class Symbol implements Comparable<Symbol> {
   public static final int IS_LET = 1;
   public static final int IS_CONST = 2;
   public static final int IS_VAR = 4;
   public static final int KINDMASK = 7;
   public static final int IS_GLOBAL = 8;
   public static final int IS_PARAM = 16;
   public static final int IS_THIS = 32;
   public static final int IS_INTERNAL = 64;
   public static final int IS_FUNCTION_SELF = 128;
   public static final int IS_HOISTABLE_DECLARATION = 256;
   public static final int IS_PROGRAM_LEVEL = 512;
   public static final int HAS_BEEN_DECLARED = 1024;
   public static final int IS_HOISTED_BLOCK_FUNCTION = 2048;
   public static final int IS_VAR_REDECLARED_HERE = 4096;
   public static final int IS_DECLARED_IN_SWITCH_BLOCK = 8192;
   public static final int IS_IMPORT_BINDING = 16384;
   public static final int IS_CATCH_PARAMETER = 32768;
   public static final int IS_BLOCK_FUNCTION_DECLARATION = 65536;
   public static final int IS_PRIVATE_NAME = 131072;
   public static final int IS_PRIVATE_NAME_STATIC = 262144;
   public static final int IS_PRIVATE_NAME_METHOD = 524288;
   public static final int IS_PRIVATE_NAME_ACCESSOR = 1048576;
   public static final int IS_ARGUMENTS = 2097152;
   public static final int IS_USED = 4194304;
   public static final int IS_CLOSED_OVER = 8388608;
   public static final int IS_USED_IN_INNER_SCOPE = 16777216;
   public static final int IS_SUPER = 33554432;
   public static final int IS_NEW_TARGET = 67108864;
   private final String name;
   private final TruffleString nameTS;
   private int flags;

   public Symbol(final TruffleString name, final int flags) {
      this.nameTS = name;
      this.name = name.toJavaStringUncached();
      this.flags = flags;

      assert (flags & 7) != 0;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.name);
      sb.append(' ');
      if (this.isLet()) {
         sb.append('L');
      } else if (this.isConst()) {
         sb.append('C');
      } else if (this.isVar()) {
         if (this.isGlobal()) {
            sb.append('G');
         } else if (this.isParam()) {
            sb.append('P');
         } else {
            sb.append('V');
         }
      }

      return sb.toString();
   }

   public int compareTo(final Symbol other) {
      return this.getName().compareTo(other.getName());
   }

   public boolean isHoistableDeclaration() {
      return (this.flags & 256) != 0;
   }

   public boolean isVar() {
      return (this.flags & 4) != 0;
   }

   public boolean isGlobal() {
      return (this.flags & 8) != 0;
   }

   public boolean isParam() {
      return (this.flags & 16) != 0;
   }

   public boolean isProgramLevel() {
      return (this.flags & 512) != 0;
   }

   public boolean isConst() {
      return (this.flags & 2) != 0;
   }

   public boolean isInternal() {
      return (this.flags & 64) != 0;
   }

   public boolean isThis() {
      return (this.flags & 32) != 0;
   }

   public boolean isSuper() {
      return (this.flags & 33554432) != 0;
   }

   public boolean isNewTarget() {
      return (this.flags & 67108864) != 0;
   }

   public boolean isLet() {
      return (this.flags & 1) != 0;
   }

   public boolean isFunctionSelf() {
      return (this.flags & 128) != 0;
   }

   public boolean isBlockScoped() {
      return this.isLet() || this.isConst();
   }

   public boolean hasBeenDeclared() {
      return (this.flags & 1024) != 0;
   }

   public int getFlags() {
      return this.flags;
   }

   public String getName() {
      return this.name;
   }

   public TruffleString getNameTS() {
      return this.nameTS;
   }

   public boolean isDeclaredInSwitchBlock() {
      return (this.flags & 8192) != 0;
   }

   public boolean isImportBinding() {
      return (this.flags & 16384) != 0;
   }

   public boolean isCatchParameter() {
      return (this.flags & 32768) != 0;
   }

   public boolean isVarRedeclaredHere() {
      return (this.flags & 4096) != 0;
   }

   public boolean isHoistedBlockFunctionDeclaration() {
      return (this.flags & 2048) != 0;
   }

   public void setHoistedBlockFunctionDeclaration() {
      assert this.isBlockScoped();

      this.flags |= 2048;
   }

   public boolean isBlockFunctionDeclaration() {
      return (this.flags & 65536) != 0;
   }

   public boolean isPrivateName() {
      return (this.flags & 131072) != 0;
   }

   public boolean isPrivateNameStatic() {
      return (this.flags & 262144) != 0;
   }

   public boolean isPrivateField() {
      return this.isPrivateName() && !this.isPrivateMethod() && !this.isPrivateAccessor();
   }

   public boolean isPrivateMethod() {
      return (this.flags & 524288) != 0;
   }

   public boolean isPrivateAccessor() {
      return (this.flags & 1048576) != 0;
   }

   public boolean isArguments() {
      return (this.flags & 2097152) != 0;
   }

   public boolean isUsed() {
      return (this.flags & 4194304) != 0;
   }

   public void setUsed() {
      this.flags |= 4194304;
   }

   public boolean isClosedOver() {
      return (this.flags & 8388608) != 0;
   }

   public void setClosedOver() {
      this.flags |= 8388608;
   }

   public boolean isUsedInInnerScope() {
      return (this.flags & 16777216) != 0;
   }

   public void setUsedInInnerScope() {
      this.flags |= 16777216;
   }
}
