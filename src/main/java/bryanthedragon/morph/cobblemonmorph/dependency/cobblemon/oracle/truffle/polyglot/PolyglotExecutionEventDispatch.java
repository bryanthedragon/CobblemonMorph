
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.polyglot.PolyglotExecutionListenerDispatch;
import com.oracle.truffle.polyglot.PolyglotImpl;
import java.util.List;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotExecutionEventDispatch
extends AbstractPolyglotImpl.AbstractExecutionEventDispatch {
    PolyglotExecutionEventDispatch(PolyglotImpl polyglot) {
        super(polyglot);
    }

    @Override
    public SourceSection getExecutionEventLocation(Object impl) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getLocation();
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    @Override
    public PolyglotException getExecutionEventException(Object impl) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getException();
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    @Override
    public boolean isExecutionEventExpression(Object impl) {
        return PolyglotExecutionEventDispatch.hasTag(impl, StandardTags.ExpressionTag.class);
    }

    @Override
    public boolean isExecutionEventStatement(Object impl) {
        return PolyglotExecutionEventDispatch.hasTag(impl, StandardTags.StatementTag.class);
    }

    @Override
    public boolean isExecutionEventRoot(Object impl) {
        return PolyglotExecutionEventDispatch.hasTag(impl, StandardTags.RootTag.class);
    }

    @Override
    public List<Value> getExecutionEventInputValues(Object impl) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getInputValues();
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    @Override
    public String getExecutionEventRootName(Object impl) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getRootName();
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    @Override
    public Value getExecutionEventReturnValue(Object impl) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getReturnValue();
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    private static boolean hasTag(Object impl, Class<? extends Tag> tag) {
        try {
            return ((PolyglotExecutionListenerDispatch.Event)impl).getContext().hasTag(tag);
        }
        catch (Throwable t) {
            throw PolyglotExecutionEventDispatch.wrapException(impl, t);
        }
    }

    private static RuntimeException wrapException(Object impl, Throwable t) {
        return PolyglotImpl.guestToHostException(((PolyglotExecutionListenerDispatch.Event)impl).getEngine(), t);
    }
}

