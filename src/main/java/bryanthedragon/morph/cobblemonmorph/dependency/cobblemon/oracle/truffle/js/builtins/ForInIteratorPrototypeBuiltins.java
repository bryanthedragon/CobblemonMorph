package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.PrimitiveValueProfile;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.HasOnlyShapePropertiesNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.ForInIterator;
import java.util.List;

public final class ForInIteratorPrototypeBuiltins extends JSBuiltinsContainer.Switch {
   public static final JSBuiltinsContainer BUILTINS = new ForInIteratorPrototypeBuiltins();

   protected ForInIteratorPrototypeBuiltins() {
      super(JSFunction.FOR_IN_ITERATOR_PROTOYPE_NAME);
      this.defineFunction(Strings.NEXT, 0);
   }

   @Override
   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget) {
      return Strings.equals(Strings.NEXT, builtin.getName())
         ? ForInIteratorPrototypeBuiltinsFactory.ForInIteratorPrototypeNextNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context))
         : null;
   }

   public static enum EnumerateIteratorPrototype implements BuiltinEnum<ForInIteratorPrototypeBuiltins.EnumerateIteratorPrototype> {
      next(0);

      private final int length;

      private EnumerateIteratorPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class ForInIteratorPrototypeNextNode extends JSBuiltinNode {
      @Node.Child
      private CreateIterResultObjectNode createIterResultObjectNode;
      @Node.Child
      private PropertyGetNode getIteratorNode;
      @Node.Child
      private GetPrototypeNode getPrototypeNode;
      @Node.Child
      private HasOnlyShapePropertiesNode hasOnlyShapePropertiesNode;
      @Node.Child
      private ListGetNode listGet;
      @Node.Child
      private ListSizeNode listSize;
      @Node.Child
      private JSGetOwnPropertyNode getOwnPropertyNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      private final BranchProfile growProfile = BranchProfile.create();
      private final ConditionProfile fastOwnKeysProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile sameShapeProfile = ConditionProfile.createBinaryProfile();
      private static final Object DONE = null;
      private static final int MAX_PROTO_DEPTH = 1000;

      public ForInIteratorPrototypeNextNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
         this.getIteratorNode = PropertyGetNode.createGetHidden(JSRuntime.FOR_IN_ITERATOR_ID, context);
         this.getPrototypeNode = GetPrototypeNode.create();
         this.hasOnlyShapePropertiesNode = HasOnlyShapePropertiesNode.create();
         this.getOwnPropertyNode = JSGetOwnPropertyNode.create();
         this.listGet = ListGetNode.create();
         this.listSize = ListSizeNode.create();
      }

      @Specialization
      public JSDynamicObject execute(VirtualFrame frame, Object target, @Cached("createEqualityProfile()") PrimitiveValueProfile valuesProfile) {
         Object iteratorValue = this.getIteratorNode.getValue(target);
         if (iteratorValue == Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorIncompatibleReceiver(target);
         } else {
            ForInIterator state = (ForInIterator)iteratorValue;
            Object nextValue = this.findNext(state);
            boolean done = nextValue == DONE;
            if (done) {
               nextValue = Undefined.instance;
            } else if (valuesProfile.profile(state.iterateValues)) {
               nextValue = JSObject.get(state.object, nextValue);
            } else {
               assert JSGuards.isString(nextValue);
            }

            return this.createIterResultObjectNode.execute(frame, nextValue, done);
         }
      }

      private Object findNext(ForInIterator state) {
         while (true) {
            JSDynamicObject object = state.object;
            if (!state.objectWasVisited) {
               JSClass jsclass = JSObject.getJSClass(object);
               Shape objectShape = object.getShape();
               boolean fastOwnKeys;
               List<?> list;
               int size;
               if (this.fastOwnKeysProfile.profile(this.hasOnlyShapePropertiesNode.execute(object, jsclass))) {
                  fastOwnKeys = true;
                  list = JSShape.getPropertiesIfHasEnumerablePropertyNames(objectShape);
                  size = list.size();
               } else {
                  fastOwnKeys = false;
                  list = jsclass.ownPropertyKeys(object);
                  size = this.listSize.execute(list);
               }

               state.objectShape = objectShape;
               state.remainingKeys = list;
               state.remainingKeysSize = size;
               state.remainingKeysIndex = 0;
               state.fastOwnKeys = fastOwnKeys;
               state.objectWasVisited = true;
            }

            assert state.remainingKeysSize == state.remainingKeys.size();

            while (state.remainingKeysIndex < state.remainingKeysSize) {
               Object next = this.listGet.execute(state.remainingKeys, state.remainingKeysIndex++);
               Object key = getKey(next);
               if (JSGuards.isString(key) && !state.isVisitedKey(key)) {
                  if (this.fastOwnKeysProfile.profile(state.fastOwnKeys && next instanceof Property)) {
                     if (this.sameShapeProfile.profile(state.objectShape == object.getShape())) {
                        if (JSProperty.isEnumerable((Property)next)) {
                           return key;
                        }
                        continue;
                     }

                     addPreviouslyVisitedKeys(state);
                     state.fastOwnKeys = false;
                  }

                  PropertyDescriptor desc = this.getOwnPropertyNode.execute(object, key);
                  if (desc != null) {
                     state.addVisitedKey(key);
                     if (desc.getEnumerable()) {
                        return key;
                     }
                  }
               }
            }

            JSDynamicObject proto = this.getPrototypeNode.execute(object);
            if (this.tryFastForwardImmutablePrototype(proto)) {
               proto = Null.instance;
            }

            state.object = proto;
            state.objectWasVisited = false;
            if (proto == Null.instance) {
               return DONE;
            }

            if (this.fastOwnKeysProfile.profile(state.fastOwnKeys)) {
               state.addVisitedShape(state.objectShape, this.growProfile);
            } else if (++state.protoDepth > 1000) {
               this.errorBranch.enter();
               throw Errors.createRangeErrorStackOverflow();
            }
         }
      }

      private static Object getKey(final Object next) {
         return next instanceof Property ? ((Property)next).getKey() : next;
      }

      @CompilerDirectives.TruffleBoundary
      private static void addPreviouslyVisitedKeys(ForInIterator state) {
         for (int i = 0; i < state.remainingKeysIndex - 1; i++) {
            state.addVisitedKey(getKey(state.remainingKeys.get(i)));
         }
      }

      private boolean tryFastForwardImmutablePrototype(JSDynamicObject proto) {
         if (proto == Null.instance) {
            return false;
         } else {
            JSClass jsclass = JSObject.getJSClass(proto);
            if (jsclass == JSObjectPrototype.INSTANCE
               && this.hasOnlyShapePropertiesNode.execute(proto, jsclass)
               && JSShape.getEnumerablePropertyNames(proto.getShape()).isEmpty()) {
               assert JSObject.getPrototype(proto) == Null.instance;

               return true;
            } else {
               return false;
            }
         }
      }
   }
}
