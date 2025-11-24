
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotBindings;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import java.util.Map;
import java.util.Set;
import org.graalvm.polyglot.TypeLiteral;
import org.graalvm.polyglot.Value;

final class PolyglotBindingsValue
extends PolyglotValueDispatch {
    final Value delegateBindings;
    final Map<String, Value> values;

    PolyglotBindingsValue(PolyglotLanguageContext context, PolyglotBindings bindings) {
        super(context.getImpl(), context.getLanguageInstance());
        this.values = context.context.polyglotBindings;
        this.delegateBindings = context.asValue(bindings);
    }

    @Override
    public Value getMember(Object context, Object receiver, String key) {
        return this.values.get(key);
    }

    @Override
    public Set<String> getMemberKeys(Object context, Object receiver) {
        return this.values.keySet();
    }

    @Override
    public boolean removeMember(Object context, Object receiver, String key) {
        Value result = this.values.remove(key);
        return result != null;
    }

    @Override
    public void putMember(Object context, Object receiver, String key, Object member) {
        this.values.put(key, ((PolyglotLanguageContext)context).context.asValue(member));
    }

    @Override
    public boolean hasMembers(Object context, Object receiver) {
        return true;
    }

    @Override
    public boolean hasMember(Object context, Object receiver, String key) {
        return this.values.containsKey(key);
    }

    @Override
    public <T> T as(Object context, Object receiver, Class<T> targetType) {
        return this.delegateBindings.as(targetType);
    }

    @Override
    public <T> T as(Object context, Object receiver, TypeLiteral<T> targetType) {
        return this.delegateBindings.as(targetType);
    }

    @Override
    public String toStringImpl(Object context, Object receiver) {
        return this.delegateBindings.toString();
    }

    @Override
    public Value getMetaObjectImpl(PolyglotLanguageContext context, Object receiver) {
        return this.delegateBindings.getMetaObject();
    }
}

