package com.oracle.truffle.js.nodes.control;

public class BreakTarget {
   private final Object label;
   private final int id;
   private final BreakException breakException;
   private static final BreakTarget DIRECT_BREAK_TARGET = new BreakTarget(null, 0, DirectBreakException.instance);

   protected BreakTarget(String label, int id, BreakException breakException) {
      this.label = label;
      this.id = id;
      this.breakException = breakException;
   }

   public Object getLabel() {
      return this.label;
   }

   public final BreakException getBreakException() {
      return this.breakException;
   }

   public static BreakTarget forLabel(String label, int id) {
      return new BreakTarget(label, id, new LabelBreakException(id));
   }

   public static BreakTarget forSwitch() {
      return DIRECT_BREAK_TARGET;
   }

   public int getId() {
      return this.id;
   }
}
