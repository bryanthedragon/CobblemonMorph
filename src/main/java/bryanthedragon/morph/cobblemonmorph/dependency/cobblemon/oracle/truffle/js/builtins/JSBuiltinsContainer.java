package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.BuiltinArgumentBuilder;
import com.oracle.truffle.js.nodes.function.BuiltinNodeFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.Pair;

public class JSBuiltinsContainer {
   private final TruffleString name;
   private final EconomicMap<TruffleString, JSBuiltin> builtins = EconomicMap.create();
   private final EconomicMap<Object, Pair<JSBuiltin, JSBuiltin>> accessors = EconomicMap.create();

   protected JSBuiltinsContainer(TruffleString name) {
      assert name == null || JSRuntime.isPropertyKey(name);

      this.name = name;
   }

   public final JSBuiltin lookupFunctionByName(TruffleString methodName) {
      return this.builtins.get(methodName);
   }

   public final Pair<JSBuiltin, JSBuiltin> lookupAccessorByKey(Object key) {
      return this.accessors.get(key);
   }

   public final void forEachBuiltin(Consumer<? super JSBuiltin> consumer) {
      this.builtins.getValues().forEach(consumer);
   }

   public final void forEachAccessor(BiConsumer<? super JSBuiltin, ? super JSBuiltin> consumer) {
      this.accessors.getValues().forEach(pair -> consumer.accept(pair.getLeft(), pair.getRight()));
   }

   protected final void register(JSBuiltin builtin) {
      assert !this.builtins.containsKey(builtin.getName()) : builtin.getName();

      this.builtins.put(builtin.getName(), builtin);
      if (builtin.isGetter()) {
         Pair<JSBuiltin, JSBuiltin> existing = this.accessors.get(builtin.getKey(), Pair.empty());

         assert existing.getLeft() == null : builtin.getKey();

         this.accessors.put(builtin.getKey(), Pair.create(builtin, existing.getRight()));
      } else if (builtin.isSetter()) {
         Pair<JSBuiltin, JSBuiltin> existing = this.accessors.get(builtin.getKey(), Pair.empty());

         assert existing.getRight() == null : builtin.getKey();

         this.accessors.put(builtin.getKey(), Pair.create(existing.getLeft(), builtin));
      }
   }

   protected static BuiltinArgumentBuilder args() {
      return BuiltinArgumentBuilder.builder();
   }

   public final TruffleString getName() {
      return this.name;
   }

   public static <E extends Enum<E> & BuiltinEnum<E>> JSBuiltinsContainer fromEnum(TruffleString name, Class<E> builtinEnum) {
      return new JSBuiltinsContainer.SwitchEnum<>(name, builtinEnum);
   }

   public static <E extends Enum<E> & BuiltinEnum<E>> JSBuiltinsContainer fromEnum(Class<E> builtinEnum) {
      return fromEnum(null, builtinEnum);
   }

   public abstract static class Lambda extends JSBuiltinsContainer {
      protected Lambda(TruffleString name) {
         super(name);
      }

      protected final void defineFunction(TruffleString name, int length, BuiltinNodeFactory nodeFactory) {
         assert !Strings.isEmpty(name);

         this.register(new JSBuiltin(this.getName(), name, length, JSAttributes.getDefaultNotEnumerable(), nodeFactory));
      }

      protected final void defineFunction(TruffleString name, int length, int attributeFlags, BuiltinNodeFactory nodeFactory) {
         assert !Strings.isEmpty(name);

         this.register(new JSBuiltin(this.getName(), name, length, attributeFlags, nodeFactory));
      }

      protected final void defineConstructor(TruffleString name, int length, BuiltinNodeFactory nodeFactory, BuiltinNodeFactory constructorFactory) {
         assert !Strings.isEmpty(name);

         this.register(
            new JSBuiltin(this.getName(), name, name, length, JSAttributes.getDefaultNotEnumerable(), 5, false, nodeFactory, constructorFactory, null)
         );
      }
   }

   public abstract static class Switch extends JSBuiltinsContainer {
      protected Switch(TruffleString name) {
         super(name);
      }

      protected final void defineFunction(TruffleString name, int length) {
         this.defineFunction(name, length, JSAttributes.getDefaultNotEnumerable());
      }

      protected final void defineFunction(TruffleString name, int length, int attributeFlags) {
         this.defineBuiltin(name, length, attributeFlags, false, false);
      }

      protected final void defineConstructor(TruffleString name, int length, boolean isNewTargetConstructor) {
         assert !Strings.isEmpty(name);

         this.defineBuiltin(name, length, JSAttributes.getDefaultNotEnumerable(), true, isNewTargetConstructor);
      }

      private void defineBuiltin(TruffleString name, int length, int attributeFlags, boolean isConstructor, boolean isNewTargetConstructor) {
         assert JSRuntime.isPropertyKey(name);

         assert !Strings.isEmpty(name);

         class FactoryImpl implements BuiltinNodeFactory {
            private final boolean construct;
            private final boolean newTarget;

            FactoryImpl(boolean construct, boolean newTarget) {
               this.construct = construct;
               this.newTarget = newTarget;
            }

            @Override
            public Object createObject(JSContext context, JSBuiltin builtin) {
               return Switch.this.createNode(context, builtin, this.construct, this.newTarget);
            }
         }

         BuiltinNodeFactory call = new FactoryImpl(false, false);
         BuiltinNodeFactory construct = isConstructor ? new FactoryImpl(true, false) : null;
         BuiltinNodeFactory constructNewTarget = isNewTargetConstructor ? new FactoryImpl(true, true) : null;
         this.register(new JSBuiltin(this.getName(), name, name, length, attributeFlags, 5, false, call, construct, constructNewTarget));
      }

      protected abstract Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget);
   }

   public static class SwitchEnum<E extends Enum<E> & BuiltinEnum<E>> extends JSBuiltinsContainer {
      private final Class<E> enumType;

      protected SwitchEnum(TruffleString name, Class<E> enumType) {
         super(name);
         this.enumType = enumType;

         for (E builtin : (Enum[])enumType.getEnumConstants()) {
            if (builtin.isEnabled() && (!JSConfig.SubstrateVM || builtin.isAOTSupported())) {
               this.loadBuiltin(builtin);
            }
         }
      }

      protected SwitchEnum(Class<E> enumType) {
         this(null, enumType);
      }

      private void loadBuiltin(E builtinEnum) {
         class FactoryImpl implements BuiltinNodeFactory {
            private final boolean construct;
            private final boolean newTarget;

            FactoryImpl(boolean construct, boolean newTarget) {
               this.construct = construct;
               this.newTarget = newTarget;
            }

            @Override
            public Object createObject(JSContext context, JSBuiltin builtin) {
               return SwitchEnum.this.createNode(context, builtin, this.construct, this.newTarget, builtinEnum);
            }
         }

         BuiltinNodeFactory call = new FactoryImpl(false, false);
         BuiltinNodeFactory construct = builtinEnum.isConstructor() ? new FactoryImpl(true, false) : null;
         BuiltinNodeFactory constructNewTarget = builtinEnum.isNewTargetConstructor() ? new FactoryImpl(true, true) : null;

         assert JSRuntime.isPropertyKey(builtinEnum.getName());

         this.register(this.createBuiltin(builtinEnum, call, construct, constructNewTarget));
      }

      private JSBuiltin createBuiltin(
         E builtinEnum, BuiltinNodeFactory functionNodeFactory, BuiltinNodeFactory constructorNodeFactory, BuiltinNodeFactory newTargetConstructorFactory
      ) {
         Object key = builtinEnum.getKey();

         assert JSRuntime.isPropertyKey(key);

         TruffleString name = builtinEnum.getName();
         int length = builtinEnum.getLength();
         int attributeFlags = JSAttributes.fromConfigurableEnumerableWritable(
            builtinEnum.isConfigurable(), builtinEnum.isEnumerable(), builtinEnum.isWritable()
         );
         return new JSBuiltin(
            this.getName(),
            name,
            key,
            length,
            attributeFlags,
            builtinEnum.getECMAScriptVersion(),
            builtinEnum.isAnnexB(),
            functionNodeFactory,
            constructorNodeFactory,
            newTargetConstructorFactory
         );
      }

      public Class<E> getEnumType() {
         return this.enumType;
      }

      protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, E builtinEnum) {
         return builtinEnum.createNode(context, builtin, construct, newTarget);
      }
   }
}
