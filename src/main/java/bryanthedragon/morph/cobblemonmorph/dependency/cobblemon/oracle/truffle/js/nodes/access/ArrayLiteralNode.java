package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantByteArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

@GenerateWrapper
public abstract class ArrayLiteralNode extends JavaScriptNode {
   protected final JSContext context;

   public ArrayLiteralNode(ArrayLiteralNode copy) {
      this.context = copy.context;
   }

   protected ArrayLiteralNode(JSContext context) {
      this.context = context;
   }

   public abstract JSArrayObject execute(VirtualFrame frame);

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.LiteralTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.ArrayLiteral.name());
   }

   @Override
   public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
      return new ArrayLiteralNodeWrapper(this, this, probe);
   }

   public static ArrayLiteralNode create(JSContext context, JavaScriptNode[] elements) {
      if (elements != null && elements.length != 0) {
         Object[] constantValues = resolveConstants(elements);
         if (constantValues != null) {
            return createConstantArray(context, elements, constantValues);
         } else {
            for (JavaScriptNode element : elements) {
               if (element instanceof EmptyNode) {
                  return new ArrayLiteralNode.DefaultObjectArrayWithEmptyLiteralNode(context, elements);
               }
            }

            return (ArrayLiteralNode)(elements.length == 1
               ? new ArrayLiteralNode.DefaultArrayLiteralOneElementNode(context, elements[0])
               : new ArrayLiteralNode.DefaultArrayLiteralNode(context, elements));
         }
      } else {
         return new ArrayLiteralNode.ConstantEmptyArrayLiteralNode(context);
      }
   }

   public static ArrayLiteralNode createWithSpread(JSContext context, JavaScriptNode[] elements) {
      return new ArrayLiteralNode.DefaultArrayLiteralWithSpreadNode(context, elements);
   }

   private static ArrayLiteralNode createConstantArray(JSContext context, JavaScriptNode[] elements, Object[] constantValues) {
      ArrayLiteralNode.ArrayContentType type = identifyPrimitiveContentType(constantValues, true);
      if (type == ArrayLiteralNode.ArrayContentType.Byte) {
         return new ArrayLiteralNode.ConstantArrayLiteralNode(
            context, ConstantByteArray.createConstantByteArray(), createByteArray(constantValues), elements.length
         );
      } else if (type == ArrayLiteralNode.ArrayContentType.Integer) {
         return new ArrayLiteralNode.ConstantArrayLiteralNode(
            context, ConstantIntArray.createConstantIntArray(), createIntArray(constantValues), elements.length
         );
      } else {
         return (ArrayLiteralNode)(type == ArrayLiteralNode.ArrayContentType.Double
            ? new ArrayLiteralNode.ConstantArrayLiteralNode(
               context, ConstantDoubleArray.createConstantDoubleArray(), createDoubleArray(constantValues), elements.length
            )
            : createConstantObjectArray(context, elements, constantValues));
      }
   }

   private static ArrayLiteralNode createConstantObjectArray(JSContext context, JavaScriptNode[] elements, Object array) {
      boolean hasEmpty = false;
      boolean emptyOnly = true;

      for (Object value : (Object[])array) {
         if (value == null) {
            hasEmpty = true;
         } else {
            emptyOnly = false;
         }
      }

      if (emptyOnly) {
         return new ArrayLiteralNode.ConstantEmptyArrayWithCapLiteralNode(context, elements.length);
      } else {
         return hasEmpty
            ? new ArrayLiteralNode.ConstantArrayLiteralNode(context, ConstantObjectArray.createConstantHolesObjectArray(), array, elements.length)
            : new ArrayLiteralNode.ConstantArrayLiteralNode(context, ConstantObjectArray.createConstantObjectArray(), array, elements.length);
      }
   }

   private static Object[] resolveConstants(JavaScriptNode[] nodes) {
      Object[] values = new Object[nodes.length];

      for (int i = 0; i < values.length; i++) {
         JavaScriptNode node = nodes[i];
         if (node instanceof JSConstantNode) {
            values[i] = ((JSConstantNode)node).getValue();
         } else {
            if (!(node instanceof EmptyNode)) {
               return null;
            }

            values[i] = null;
         }
      }

      return values;
   }

   public static ArrayLiteralNode.ArrayContentType identifyPrimitiveContentType(Object[] values, boolean createBytes) {
      boolean bytes = createBytes;
      boolean integers = true;
      boolean hasHoles = false;

      for (int i = 0; i < values.length; i++) {
         Object value = values[i];
         if (value == null) {
            hasHoles = true;
         } else if (integers && value instanceof Integer) {
            bytes = bytes && ScriptArray.valueIsByte((Integer)value);
         } else if (value instanceof Double) {
            bytes = false;
            integers = false;
         } else if (!(value instanceof Integer) && !(value instanceof Double)) {
            return ArrayLiteralNode.ArrayContentType.Object;
         }
      }

      if (bytes) {
         return hasHoles ? ArrayLiteralNode.ArrayContentType.ByteWithHoles : ArrayLiteralNode.ArrayContentType.Byte;
      } else if (integers) {
         return hasHoles ? ArrayLiteralNode.ArrayContentType.IntegerWithHoles : ArrayLiteralNode.ArrayContentType.Integer;
      } else {
         return hasHoles ? ArrayLiteralNode.ArrayContentType.DoubleWithHoles : ArrayLiteralNode.ArrayContentType.Double;
      }
   }

   private static Object createPrimitiveArray(Object[] values, boolean createBytes) {
      ArrayLiteralNode.ArrayContentType type = identifyPrimitiveContentType(values, createBytes);
      if (type == ArrayLiteralNode.ArrayContentType.Byte) {
         return createByteArray(values);
      } else if (type == ArrayLiteralNode.ArrayContentType.Integer) {
         return createIntArray(values);
      } else {
         return type == ArrayLiteralNode.ArrayContentType.Double ? createDoubleArray(values) : values;
      }
   }

   public static double[] createDoubleArray(Object[] values) {
      double[] doubleArray = new double[values.length];

      for (int i = 0; i < values.length; i++) {
         Object oValue = values[i];
         if (oValue instanceof Double) {
            doubleArray[i] = (Double)oValue;
         } else if (oValue instanceof Integer) {
            doubleArray[i] = ((Integer)oValue).intValue();
         }
      }

      return doubleArray;
   }

   public static int[] createIntArray(Object[] values) {
      int[] intArray = new int[values.length];

      for (int i = 0; i < values.length; i++) {
         if (values[i] == null) {
            intArray[i] = Integer.MIN_VALUE;
         } else {
            intArray[i] = (Integer)values[i];
         }
      }

      return intArray;
   }

   public static byte[] createByteArray(Object[] values) {
      byte[] byteArray = new byte[values.length];

      for (int i = 0; i < values.length; i++) {
         byteArray[i] = (byte)((Integer)values[i]).intValue();
      }

      return byteArray;
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == JSDynamicObject.class;
   }

   public static enum ArrayContentType {
      Byte,
      ByteWithHoles,
      Integer,
      IntegerWithHoles,
      Double,
      DoubleWithHoles,
      Object;
   }

   private static final class ConstantArrayLiteralNode extends ArrayLiteralNode {
      private final AbstractConstantArray arrayType;
      private final Object array;
      private final long length;

      ConstantArrayLiteralNode(JSContext context, AbstractConstantArray arrayType, Object array, long length) {
         super(context);
         this.arrayType = arrayType;
         this.array = array;
         this.length = length;
      }

      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         return JSArray.create(this.context, this.getRealm(), this.arrayType, this.array, this.length);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return this.copy();
      }
   }

   private static final class ConstantEmptyArrayLiteralNode extends ArrayLiteralNode {
      ConstantEmptyArrayLiteralNode(JSContext context) {
         super(context);
      }

      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         return JSArray.createConstantEmptyArray(this.context, this.getRealm());
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return this.copy();
      }
   }

   private static final class ConstantEmptyArrayWithCapLiteralNode extends ArrayLiteralNode {
      private final int capacity;

      ConstantEmptyArrayWithCapLiteralNode(JSContext context, int cap) {
         super(context);
         this.capacity = cap;
      }

      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         return JSArray.createConstantEmptyArray(this.context, this.getRealm(), this.capacity);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return this.copy();
      }
   }

   private abstract static class DefaultArrayLiteralBaseNode extends ArrayLiteralNode {
      @CompilerDirectives.CompilationFinal
      protected byte state;
      protected static final byte INT_ARRAY = 1;
      protected static final byte DOUBLE_ARRAY = 2;
      protected static final byte OBJECT_ARRAY = 3;
      @CompilerDirectives.CompilationFinal
      protected boolean seenUnexpectedInteger;

      DefaultArrayLiteralBaseNode(JSContext context) {
         super(context);
      }

      protected abstract int getLength();

      protected abstract JavaScriptNode getElement(int index);

      protected final JSArrayObject executeAndSpecialize(Object[] values) {
         CompilerAsserts.neverPartOfCompilation();
         Object primitive = ArrayLiteralNode.createPrimitiveArray(values, false);
         JSRealm realm = this.getRealm();
         if (primitive instanceof int[]) {
            this.state = 1;
            return JSArray.createZeroBasedIntArray(this.context, realm, (int[])primitive);
         } else if (primitive instanceof double[]) {
            this.state = 2;
            return JSArray.createZeroBasedDoubleArray(this.context, realm, (double[])primitive);
         } else if (primitive instanceof Object[]) {
            this.state = 3;
            return JSArray.createZeroBasedObjectArray(this.context, realm, values);
         } else {
            throw Errors.shouldNotReachHere();
         }
      }

      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         if (this.state != 0) {
            if (this.state == 1) {
               return this.executeZeroBasedIntArray(frame);
            } else if (this.state == 2) {
               return this.executeZeroBasedDoubleArray(frame);
            } else {
               assert this.state == 3;

               return this.executeZeroBasedObjectArray(frame);
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object[] values = new Object[this.getLength()];

            for (int i = 0; i < this.getLength(); i++) {
               values[i] = this.getElement(i).execute(frame);
            }

            return this.executeAndSpecialize(values);
         }
      }

      @ExplodeLoop
      private JSArrayObject executeZeroBasedIntArray(VirtualFrame frame) {
         int[] primitiveArray = new int[this.getLength()];

         for (int i = 0; i < this.getLength(); i++) {
            try {
               primitiveArray[i] = this.getElement(i).executeInt(frame);
            } catch (UnexpectedResultException var5) {
               CompilerDirectives.transferToInterpreterAndInvalidate();

               assert !(var5.getResult() instanceof Integer);

               return this.executeIntArrayFallback(frame, primitiveArray, i, var5.getResult());
            }
         }

         return JSArray.createZeroBasedIntArray(this.context, this.getRealm(), primitiveArray);
      }

      private JSArrayObject executeIntArrayFallback(VirtualFrame frame, int[] primitiveArray, int failIdx, Object failValue) {
         Object[] objectArray = new Object[this.getLength()];

         for (int j = 0; j < failIdx; j++) {
            objectArray[j] = primitiveArray[j];
         }

         return this.executeFallback(frame, objectArray, failIdx, failValue);
      }

      @ExplodeLoop
      private JSArrayObject executeZeroBasedDoubleArray(VirtualFrame frame) {
         double[] primitiveArray = new double[this.getLength()];

         for (int i = 0; i < this.getLength(); i++) {
            try {
               double doubleValue;
               if (this.seenUnexpectedInteger) {
                  Object objectValue = this.getElement(i).execute(frame);
                  if (objectValue instanceof Double) {
                     doubleValue = (Double)objectValue;
                  } else {
                     if (!(objectValue instanceof Integer)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        throw new UnexpectedResultException(objectValue);
                     }

                     doubleValue = ((Integer)objectValue).intValue();
                  }
               } else {
                  doubleValue = this.getElement(i).executeDouble(frame);
               }

               primitiveArray[i] = doubleValue;
            } catch (UnexpectedResultException var7) {
               CompilerDirectives.transferToInterpreterAndInvalidate();

               assert !(var7.getResult() instanceof Double);

               if (!(var7.getResult() instanceof Integer)) {
                  return this.executeDoubleArrayFallback(frame, primitiveArray, i, var7.getResult());
               }

               primitiveArray[i] = ((Integer)var7.getResult()).intValue();
               this.seenUnexpectedInteger = true;
            }
         }

         return JSArray.createZeroBasedDoubleArray(this.context, this.getRealm(), primitiveArray);
      }

      private JSArrayObject executeDoubleArrayFallback(VirtualFrame frame, double[] primitiveArray, int failIdx, Object failValue) {
         Object[] objectArray = new Object[this.getLength()];

         for (int j = 0; j < failIdx; j++) {
            objectArray[j] = primitiveArray[j];
         }

         return this.executeFallback(frame, objectArray, failIdx, failValue);
      }

      @ExplodeLoop
      private JSArrayObject executeZeroBasedObjectArray(VirtualFrame frame) {
         Object[] primitiveArray = new Object[this.getLength()];

         for (int i = 0; i < this.getLength(); i++) {
            primitiveArray[i] = this.getElement(i).execute(frame);
         }

         return JSArray.createZeroBasedObjectArray(this.context, this.getRealm(), primitiveArray);
      }

      private JSArrayObject executeFallback(VirtualFrame frame, Object[] objectArray, int failingIndex, Object failingValue) {
         objectArray[failingIndex] = failingValue;

         for (int j = failingIndex + 1; j < this.getLength(); j++) {
            objectArray[j] = this.getElement(j).execute(frame);
         }

         return this.executeAndSpecialize(objectArray);
      }
   }

   @NodeInfo(cost = NodeCost.MONOMORPHIC)
   private static class DefaultArrayLiteralNode extends ArrayLiteralNode.DefaultArrayLiteralBaseNode {
      @Node.Children
      protected final JavaScriptNode[] elements;

      DefaultArrayLiteralNode(JSContext context, JavaScriptNode[] elements) {
         super(context);
         this.elements = elements;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ArrayLiteralNode.DefaultArrayLiteralNode(this.context, cloneUninitialized(this.elements, materializedTags));
      }

      @Override
      protected int getLength() {
         return this.elements.length;
      }

      @Override
      protected JavaScriptNode getElement(int index) {
         return this.elements[index];
      }
   }

   @NodeInfo(cost = NodeCost.MONOMORPHIC)
   private static class DefaultArrayLiteralOneElementNode extends ArrayLiteralNode.DefaultArrayLiteralBaseNode {
      @Node.Child
      protected JavaScriptNode child;

      DefaultArrayLiteralOneElementNode(JSContext context, JavaScriptNode child) {
         super(context);
         this.child = child;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ArrayLiteralNode.DefaultArrayLiteralOneElementNode(this.context, cloneUninitialized(this.child, materializedTags));
      }

      @Override
      protected int getLength() {
         return 1;
      }

      @Override
      protected JavaScriptNode getElement(int index) {
         assert index == 0;

         return this.child;
      }
   }

   private static final class DefaultArrayLiteralWithSpreadNode extends ArrayLiteralNode.DefaultArrayLiteralNode {
      private final BranchProfile growProfile = BranchProfile.create();

      DefaultArrayLiteralWithSpreadNode(JSContext context, JavaScriptNode[] elements) {
         super(context, elements);

         assert elements.length > 0;
      }

      @ExplodeLoop
      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         SimpleArrayList<Object> evaluatedElements = new SimpleArrayList<>(this.elements.length + 3);
         int holeCount = 0;
         int holesBeforeLastNonEmpty = 0;
         int arrayOffset = 0;
         int lastNonEmptyPlusOne = 0;

         for (int i = 0; i < this.elements.length; i++) {
            Node node = this.elements[i];
            if (this.elements[i] instanceof InstrumentableNode.WrapperNode) {
               node = ((InstrumentableNode.WrapperNode)this.elements[i]).getDelegateNode();
            }

            if (node instanceof EmptyNode) {
               evaluatedElements.add(null, this.growProfile);
               holeCount++;
               if (i == arrayOffset) {
                  arrayOffset++;
               }
            } else if (node instanceof ArrayLiteralNode.SpreadArrayNode) {
               int count = ((ArrayLiteralNode.SpreadArrayNode)node).executeToList(frame, evaluatedElements, this.growProfile);
               if (count != 0) {
                  lastNonEmptyPlusOne = evaluatedElements.size();
                  holesBeforeLastNonEmpty = holeCount;
               }
            } else {
               evaluatedElements.add(this.elements[i].execute(frame), this.growProfile);
               lastNonEmptyPlusOne = evaluatedElements.size();
               holesBeforeLastNonEmpty = holeCount;
            }
         }

         int usedLength = lastNonEmptyPlusOne - arrayOffset;
         int holesInUsedLength = holesBeforeLastNonEmpty - arrayOffset;
         return JSArray.createZeroBasedHolesObjectArray(this.context, this.getRealm(), evaluatedElements.toArray(), usedLength, arrayOffset, holesInUsedLength);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ArrayLiteralNode.DefaultArrayLiteralWithSpreadNode(this.context, cloneUninitialized(this.elements, materializedTags));
      }
   }

   @NodeInfo(cost = NodeCost.MONOMORPHIC)
   private static final class DefaultObjectArrayWithEmptyLiteralNode extends ArrayLiteralNode.DefaultArrayLiteralNode {
      DefaultObjectArrayWithEmptyLiteralNode(JSContext context, JavaScriptNode[] elements) {
         super(context, elements);

         assert elements.length > 0;
      }

      @ExplodeLoop
      @Override
      public JSArrayObject execute(VirtualFrame frame) {
         Object[] primitiveArray = new Object[this.elements.length];
         int holeCount = 0;
         int holesBeforeLastNonEmpty = 0;
         int arrayOffset = 0;
         int lastNonEmpty = -1;

         for (int i = 0; i < this.elements.length; i++) {
            if (this.elements[i] instanceof EmptyNode) {
               holeCount++;
               if (i == arrayOffset) {
                  arrayOffset++;
               }
            } else {
               primitiveArray[i] = this.elements[i].execute(frame);
               lastNonEmpty = i;
               holesBeforeLastNonEmpty = holeCount;
            }
         }

         int usedLength = lastNonEmpty + 1 - arrayOffset;
         int holesInUsedLength = holesBeforeLastNonEmpty - arrayOffset;
         return JSArray.createZeroBasedHolesObjectArray(this.context, this.getRealm(), primitiveArray, usedLength, arrayOffset, holesInUsedLength);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ArrayLiteralNode.DefaultObjectArrayWithEmptyLiteralNode(this.context, cloneUninitialized(this.elements, materializedTags));
      }
   }

   public static final class SpreadArrayNode extends JavaScriptNode {
      @Node.Child
      private GetIteratorNode getIteratorNode;
      @Node.Child
      private IteratorGetNextValueNode iteratorStepNode;

      private SpreadArrayNode(JSContext context, GetIteratorNode getIteratorNode) {
         this.getIteratorNode = getIteratorNode;
         this.iteratorStepNode = IteratorGetNextValueNode.create(context, null, JSConstantNode.create(null), false);
      }

      public static ArrayLiteralNode.SpreadArrayNode create(JSContext context, GetIteratorNode getIteratorNode) {
         return new ArrayLiteralNode.SpreadArrayNode(context, getIteratorNode);
      }

      public int executeToList(VirtualFrame frame, SimpleArrayList<Object> toList, BranchProfile growProfile) {
         IteratorRecord iteratorRecord = this.getIteratorNode.execute(frame);
         int count = 0;

         while (true) {
            Object nextArg = this.iteratorStepNode.execute(frame, iteratorRecord);
            if (nextArg == null) {
               return count;
            }

            toList.add(nextArg, growProfile);
            count++;
         }
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere("Cannot execute SpreadArrayNode");
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         ArrayLiteralNode.SpreadArrayNode copy = (ArrayLiteralNode.SpreadArrayNode)this.copy();
         copy.getIteratorNode = cloneUninitialized(this.getIteratorNode, materializedTags);
         copy.iteratorStepNode = cloneUninitialized(this.iteratorStepNode, materializedTags);
         return copy;
      }
   }
}
