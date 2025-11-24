
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
        assert (name == null || JSRuntime.isPropertyKey(name));
        this.name = name;
    }

    public final JSBuiltin lookupFunctionByName(TruffleString methodName) {
        return (JSBuiltin)this.builtins.get(methodName);
    }

    public final Pair<JSBuiltin, JSBuiltin> lookupAccessorByKey(Object key) {
        return (Pair)this.accessors.get(key);
    }

    public final void forEachBuiltin(Consumer<? super JSBuiltin> consumer) {
        this.builtins.getValues().forEach(consumer);
    }

    public final void forEachAccessor(BiConsumer<? super JSBuiltin, ? super JSBuiltin> consumer) {
        this.accessors.getValues().forEach(pair -> consumer.accept((JSBuiltin)pair.getLeft(), (JSBuiltin)pair.getRight()));
    }

    protected final void register(JSBuiltin builtin) {
        assert (!this.builtins.containsKey(builtin.getName())) : builtin.getName();
        this.builtins.put(builtin.getName(), builtin);
        if (builtin.isGetter()) {
            Pair existing = this.accessors.get(builtin.getKey(), Pair.empty());
            assert (existing.getLeft() == null) : builtin.getKey();
            this.accessors.put(builtin.getKey(), Pair.create(builtin, (JSBuiltin)existing.getRight()));
        } else if (builtin.isSetter()) {
            Pair existing = this.accessors.get(builtin.getKey(), Pair.empty());
            assert (existing.getRight() == null) : builtin.getKey();
            this.accessors.put(builtin.getKey(), Pair.create((JSBuiltin)existing.getLeft(), builtin));
        }
    }

    protected static BuiltinArgumentBuilder args() {
        return BuiltinArgumentBuilder.builder();
    }

    public final TruffleString getName() {
        return this.name;
    }

    public static <E extends Enum<E>> JSBuiltinsContainer fromEnum(TruffleString name, Class<E> builtinEnum) {
        return new SwitchEnum<E>(name, builtinEnum);
    }

    public static <E extends Enum<E>> JSBuiltinsContainer fromEnum(Class<E> builtinEnum) {
        return JSBuiltinsContainer.fromEnum(null, builtinEnum);
    }

    public static abstract class Lambda
    extends JSBuiltinsContainer {
        protected Lambda(TruffleString name) {
            super(name);
        }

        protected final void defineFunction(TruffleString name, int length, BuiltinNodeFactory nodeFactory) {
            assert (!Strings.isEmpty(name));
            this.register(new JSBuiltin(this.getName(), name, length, JSAttributes.getDefaultNotEnumerable(), nodeFactory));
        }

        protected final void defineFunction(TruffleString name, int length, int attributeFlags, BuiltinNodeFactory nodeFactory) {
            assert (!Strings.isEmpty(name));
            this.register(new JSBuiltin(this.getName(), name, length, attributeFlags, nodeFactory));
        }

        protected final void defineConstructor(TruffleString name, int length, BuiltinNodeFactory nodeFactory, BuiltinNodeFactory constructorFactory) {
            assert (!Strings.isEmpty(name));
            this.register(new JSBuiltin(this.getName(), name, name, length, JSAttributes.getDefaultNotEnumerable(), 5, false, nodeFactory, constructorFactory, null));
        }
    }

    public static class SwitchEnum<E extends Enum<E>>
    extends JSBuiltinsContainer {
        private final Class<E> enumType;

        protected SwitchEnum(TruffleString name, Class<E> enumType) {
            super(name);
            this.enumType = enumType;
            for (Enum builtin : (Enum[])enumType.getEnumConstants()) {
                if (!((BuiltinEnum)((Object)builtin)).isEnabled() || JSConfig.SubstrateVM && !((BuiltinEnum)((Object)builtin)).isAOTSupported()) continue;
                this.loadBuiltin(builtin);
            }
        }

        protected SwitchEnum(Class<E> enumType) {
            this(null, enumType);
        }

        private void loadBuiltin(E builtinEnum) {
            FactoryImpl constructNewTarget;
            class FactoryImpl
            implements BuiltinNodeFactory {
                private final boolean construct;
                private final boolean newTarget;
                final /* synthetic */ Enum val$builtinEnum;

                FactoryImpl(boolean construct, boolean newTarget) {
                    this.val$builtinEnum = val$builtinEnum;
                    this.construct = construct;
                    this.newTarget = newTarget;
                }

                @Override
                public Object createObject(JSContext context, JSBuiltin builtin) {
                    return SwitchEnum.this.createNode(context, builtin, this.construct, this.newTarget, this.val$builtinEnum);
                }
            }
            FactoryImpl call = new FactoryImpl(false, false);
            FactoryImpl construct = ((BuiltinEnum)builtinEnum).isConstructor() ? new FactoryImpl(true, false) : null;
            FactoryImpl factoryImpl = constructNewTarget = ((BuiltinEnum)builtinEnum).isNewTargetConstructor() ? new FactoryImpl(true, true) : null;
            assert (JSRuntime.isPropertyKey(((BuiltinEnum)builtinEnum).getName()));
            this.register(this.createBuiltin(builtinEnum, call, construct, constructNewTarget));
        }

        private JSBuiltin createBuiltin(E builtinEnum, BuiltinNodeFactory functionNodeFactory, BuiltinNodeFactory constructorNodeFactory, BuiltinNodeFactory newTargetConstructorFactory) {
            Object key = ((BuiltinEnum)builtinEnum).getKey();
            assert (JSRuntime.isPropertyKey(key));
            TruffleString name = ((BuiltinEnum)builtinEnum).getName();
            int length = ((BuiltinEnum)builtinEnum).getLength();
            int attributeFlags = JSAttributes.fromConfigurableEnumerableWritable(((BuiltinEnum)builtinEnum).isConfigurable(), ((BuiltinEnum)builtinEnum).isEnumerable(), ((BuiltinEnum)builtinEnum).isWritable());
            return new JSBuiltin(this.getName(), name, key, length, attributeFlags, ((BuiltinEnum)builtinEnum).getECMAScriptVersion(), ((BuiltinEnum)builtinEnum).isAnnexB(), functionNodeFactory, constructorNodeFactory, newTargetConstructorFactory);
        }

        public Class<E> getEnumType() {
            return this.enumType;
        }

        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, E builtinEnum) {
            return ((BuiltinEnum)builtinEnum).createNode(context, builtin, construct, newTarget);
        }
    }

    public static abstract class Switch
    extends JSBuiltinsContainer {
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
            assert (!Strings.isEmpty(name));
            this.defineBuiltin(name, length, JSAttributes.getDefaultNotEnumerable(), true, isNewTargetConstructor);
        }

        private void defineBuiltin(TruffleString name, int length, int attributeFlags, boolean isConstructor, boolean isNewTargetConstructor) {
            assert (JSRuntime.isPropertyKey(name));
            assert (!Strings.isEmpty(name));
            class FactoryImpl
            implements BuiltinNodeFactory {
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
            FactoryImpl call = new FactoryImpl(false, false);
            FactoryImpl construct = isConstructor ? new FactoryImpl(true, false) : null;
            FactoryImpl constructNewTarget = isNewTargetConstructor ? new FactoryImpl(true, true) : null;
            this.register(new JSBuiltin(this.getName(), name, name, length, attributeFlags, 5, false, call, construct, constructNewTarget));
        }

        protected abstract Object createNode(JSContext var1, JSBuiltin var2, boolean var3, boolean var4);
    }
}

