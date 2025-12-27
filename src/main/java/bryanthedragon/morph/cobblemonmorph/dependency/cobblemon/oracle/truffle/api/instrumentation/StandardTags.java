package com.oracle.truffle.api.instrumentation;

public final class StandardTags {
   static final Class[] ALL_TAGS = new Class[]{
      StandardTags.StatementTag.class,
      StandardTags.CallTag.class,
      StandardTags.RootTag.class,
      StandardTags.RootBodyTag.class,
      StandardTags.ExpressionTag.class,
      StandardTags.TryBlockTag.class,
      StandardTags.ReadVariableTag.class,
      StandardTags.WriteVariableTag.class
   };

   private StandardTags() {
   }

   @Tag.Identifier("CALL")
   public static final class CallTag extends Tag {
      private CallTag() {
      }
   }

   @Tag.Identifier("EXPRESSION")
   public static final class ExpressionTag extends Tag {
      private ExpressionTag() {
      }
   }

   @Tag.Identifier("READ_VARIABLE")
   public static final class ReadVariableTag extends Tag {
      public static final String NAME = "readVariableName";

      private ReadVariableTag() {
      }
   }

   @Tag.Identifier("ROOT_BODY")
   public static final class RootBodyTag extends Tag {
      private RootBodyTag() {
      }
   }

   @Tag.Identifier("ROOT")
   public static final class RootTag extends Tag {
      private RootTag() {
      }
   }

   @Tag.Identifier("STATEMENT")
   public static final class StatementTag extends Tag {
      private StatementTag() {
      }
   }

   @Tag.Identifier("TRY_BLOCK")
   public static final class TryBlockTag extends Tag {
      public static final String CATCHES = "catches";

      private TryBlockTag() {
      }
   }

   @Tag.Identifier("WRITE_VARIABLE")
   public static final class WriteVariableTag extends Tag {
      public static final String NAME = "writeVariableName";

      private WriteVariableTag() {
      }
   }
}
