
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.Breakpoint;
import com.oracle.truffle.api.debug.DebugException;
import com.oracle.truffle.api.debug.DebugScope;
import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.ValueInteropList;
import com.oracle.truffle.api.debug.ValuePropertiesCollection;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class DebugValue {
    static final InteropLibrary INTEROP = InteropLibrary.getFactory().getUncached();
    final LanguageInfo preferredLanguage;

    abstract Object get() throws DebugException;

    DebugValue(LanguageInfo preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public abstract void set(DebugValue var1) throws DebugException;

    @Deprecated(since="21.2")
    public abstract void set(Object var1) throws DebugException;

    @Deprecated(since="20.1")
    public abstract <T> T as(Class<T> var1) throws DebugException;

    public abstract String getName();

    public abstract boolean isReadable();

    public abstract boolean hasReadSideEffects();

    public abstract boolean hasWriteSideEffects();

    public abstract boolean isWritable();

    public abstract boolean isInternal();

    public DebugScope getScope() {
        return null;
    }

    public final boolean isNull() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isNull(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean isString() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isString(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public final String asString() throws DebugException {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object val = this.get();
            if (INTEROP.isString(val)) {
                return INTEROP.asString(val);
            }
            return null;
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInInt() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInInt(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public int asInt() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asInt(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not an int", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean isBoolean() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isBoolean(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean asBoolean() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asBoolean(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a boolean", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean isNumber() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isNumber(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInLong() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInLong(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public long asLong() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asLong(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a long", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInDouble() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInDouble(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public double asDouble() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asDouble(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a double", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInFloat() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInFloat(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public float asFloat() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asFloat(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a float", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInByte() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInByte(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public byte asByte() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asByte(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a byte", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean fitsInShort() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.fitsInShort(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public short asShort() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asShort(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a short", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage());
        }
    }

    public boolean isDate() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isDate(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public LocalDate asDate() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asDate(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a date", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isTime() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isTime(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public LocalTime asTime() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asTime(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a time", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isInstant() {
        return this.isDate() && this.isTime() && this.isTimeZone();
    }

    public Instant asInstant() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asInstant(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not an instant", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isTimeZone() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isTimeZone(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public ZoneId asTimeZone() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asTimeZone(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a time", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isDuration() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isDuration(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public Duration asDuration() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asDuration(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a time", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isMetaObject() {
        if (!this.isReadable()) {
            return false;
        }
        try {
            Object value2 = this.get();
            return INTEROP.isMetaObject(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public String getMetaQualifiedName() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asString(INTEROP.getMetaQualifiedName(value2));
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a metaobject", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public String getMetaSimpleName() {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.asString(INTEROP.getMetaSimpleName(value2));
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a metaobject", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isMetaInstance(DebugValue instance) {
        if (!this.isReadable()) {
            throw new UnsupportedOperationException("Value is not readable");
        }
        try {
            Object value2 = this.get();
            return INTEROP.isMetaInstance(value2, instance.get());
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (UnsupportedMessageException uex) {
            throw new UnsupportedOperationException("Not a metaobject", uex);
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public final List<Breakpoint> getRootInstanceBreakpoints() {
        final Object value2 = this.get();
        final List[] breakpoints = new List[]{null};
        this.getSession().visitBreakpoints(new Consumer<Breakpoint>(){

            @Override
            public void accept(Breakpoint b) {
                if (b.getRootInstance() == value2) {
                    if (breakpoints[0] == null) {
                        breakpoints[0] = new LinkedList();
                    }
                    breakpoints[0].add(b);
                }
            }
        });
        return breakpoints[0] != null ? breakpoints[0] : Collections.emptyList();
    }

    public final Collection<DebugValue> getProperties() throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        Object value2 = this.get();
        try {
            return DebugValue.getProperties(value2, null, this.getSession(), this.resolveLanguage(), null);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    static ValuePropertiesCollection getProperties(Object value2, String receiverName, DebuggerSession session, LanguageInfo language, DebugScope scope) {
        if (INTEROP.hasMembers(value2)) {
            Object keys;
            try {
                keys = INTEROP.getMembers(value2, true);
            }
            catch (UnsupportedMessageException e) {
                return null;
            }
            return new ValuePropertiesCollection(session, language, value2, keys, receiverName, scope);
        }
        return null;
    }

    public final DebugValue getProperty(String name) throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        Object value2 = this.get();
        if (value2 != null) {
            try {
                if (!INTEROP.isMemberExisting(value2, name)) {
                    return null;
                }
                return new ObjectMemberValue(this.getSession(), this.resolveLanguage(), null, value2, name);
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }
        return null;
    }

    public final boolean isArray() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        return INTEROP.hasArrayElements(this.get());
    }

    public List<DebugValue> getArray() throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        Object value2 = this.get();
        if (INTEROP.hasArrayElements(value2)) {
            return new ValueInteropList(this.getSession(), this.resolveLanguage(), value2);
        }
        return null;
    }

    public Object getRawValue(Class<? extends TruffleLanguage<?>> languageClass) {
        Objects.requireNonNull(languageClass);
        RootNode rootNode = this.getScope().getRoot();
        if (rootNode == null) {
            return null;
        }
        TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
        return language != null && language.getClass() == languageClass ? this.get() : null;
    }

    public final String toDisplayString() {
        return this.toDisplayString(true);
    }

    public final String toDisplayString(boolean allowSideEffects) throws DebugException {
        if (!this.isReadable()) {
            return "<not readable>";
        }
        try {
            Object stringValue = INTEROP.toDisplayString(this.getLanguageView(), allowSideEffects);
            return INTEROP.asString(stringValue);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    final Object getLanguageView() {
        LanguageInfo language = this.resolveLanguage();
        Object value2 = this.get();
        if (language == null) {
            return value2;
        }
        return this.getDebugger().getEnv().getLanguageView(language, value2);
    }

    final LanguageInfo resolveLanguage() {
        LanguageInfo languageInfo = this.preferredLanguage != null ? this.preferredLanguage : (this.getScope() != null && this.getScope().getLanguage() != null ? this.getScope().getLanguage() : this.getOriginalLanguage());
        return languageInfo;
    }

    public final DebugValue getMetaObject() throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        Object view = this.getLanguageView();
        try {
            if (INTEROP.hasMetaObject(view)) {
                return new HeapValue(this.getSession(), this.resolveLanguage(), null, INTEROP.getMetaObject(view));
            }
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return null;
    }

    public final SourceSection getSourceLocation() throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        try {
            Object obj = this.getLanguageView();
            if (INTEROP.hasSourceLocation(obj)) {
                return this.getSession().resolveSection(INTEROP.getSourceLocation(obj));
            }
            return null;
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public final boolean canExecute() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        Object value2 = this.get();
        try {
            return INTEROP.isExecutable(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public final DebugValue execute(DebugValue ... arguments) throws DebugException {
        Object value2 = this.get();
        Object[] args = new Object[arguments.length];
        for (int i = 0; i < arguments.length; ++i) {
            args[i] = arguments[i].get();
        }
        try {
            Object retValue = INTEROP.execute(value2, args);
            return new HeapValue(this.getSession(), this.resolveLanguage(), null, retValue);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean hasIterator() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        Object value2 = this.get();
        try {
            return INTEROP.hasIterator(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public DebugValue getIterator() throws DebugException {
        Object iterator;
        Object value2 = this.get();
        try {
            iterator = INTEROP.getIterator(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return new HeapValue(this.getSession(), this.preferredLanguage, null, iterator);
    }

    public boolean isIterator() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        Object value2 = this.get();
        try {
            return INTEROP.isIterator(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean hasIteratorNextElement() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        Object value2 = this.get();
        try {
            return INTEROP.hasIteratorNextElement(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public DebugValue getIteratorNextElement() throws NoSuchElementException, DebugException {
        Object next;
        Object value2 = this.get();
        try {
            next = INTEROP.getIteratorNextElement(value2);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (StopIterationException ex) {
            throw new NoSuchElementException(ex.getLocalizedMessage());
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return new HeapValue(this.getSession(), this.preferredLanguage, null, next);
    }

    public boolean hasHashEntries() throws DebugException {
        if (!this.isReadable()) {
            return false;
        }
        Object hash = this.get();
        try {
            return INTEROP.hasHashEntries(hash);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public long getHashSize() throws DebugException {
        Object hash = this.get();
        try {
            return INTEROP.getHashSize(hash);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isHashEntryReadable(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryReadable(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public DebugValue getHashValue(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        HashEntryValue value2 = HashEntryValue.getValueOrNull(this.getSession(), this.preferredLanguage, hash, keyObject);
        return value2;
    }

    public DebugValue getHashValueOrDefault(DebugValue key, DebugValue defaultValue) throws DebugException {
        Object v;
        Object hash = this.get();
        Object keyObject = key.get();
        Object defaultObject = defaultValue.get();
        try {
            v = INTEROP.readHashValueOrDefault(hash, keyObject, defaultObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        if (v == defaultObject) {
            return defaultValue;
        }
        HashEntryValue value2 = new HashEntryValue(this.getSession(), this.preferredLanguage, hash, keyObject, HashEntryValue.EntryKind.VALUE);
        value2.setCachedValue(v);
        return value2;
    }

    public boolean isHashEntryModifiable(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryModifiable(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isHashEntryInsertable(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryInsertable(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isHashEntryWritable(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryWritable(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public void putHashEntry(DebugValue key, DebugValue value2) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        Object valueObject = value2.get();
        try {
            INTEROP.writeHashEntry(hash, keyObject, valueObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isHashEntryRemovable(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryRemovable(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean removeHashEntry(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            INTEROP.removeHashEntry(hash, keyObject);
            return true;
        }
        catch (UnknownKeyException ukex) {
            return false;
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public boolean isHashEntryExisting(DebugValue key) throws DebugException {
        Object hash = this.get();
        Object keyObject = key.get();
        try {
            return INTEROP.isHashEntryExisting(hash, keyObject);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    public DebugValue getHashEntriesIterator() throws DebugException {
        Object entriesIterator;
        Object hash = this.get();
        try {
            entriesIterator = INTEROP.getHashEntriesIterator(hash);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return new HashEntriesIteratorValue(this.getSession(), this.preferredLanguage, null, hash, entriesIterator, null);
    }

    public DebugValue getHashKeysIterator() throws DebugException {
        Object keysIterator;
        Object hash = this.get();
        try {
            keysIterator = INTEROP.getHashKeysIterator(hash);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return new HeapValue(this.getSession(), this.preferredLanguage, null, keysIterator);
    }

    public DebugValue getHashValuesIterator() throws DebugException {
        Object valuesIterator;
        Object hash = this.get();
        try {
            valuesIterator = INTEROP.getHashValuesIterator(hash);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return new HeapValue(this.getSession(), this.preferredLanguage, null, valuesIterator);
    }

    public int hashCode() throws DebugException {
        if (this.isReadable()) {
            Object value2 = this.get();
            return this.valueHashCode(value2);
        }
        return this.unreadableHashCode();
    }

    public boolean equals(Object obj) throws DebugException {
        if (!(obj instanceof DebugValue)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        DebugValue other = (DebugValue)obj;
        boolean thisReadable = this.isReadable();
        boolean otherReadable = other.isReadable();
        if (!thisReadable || !otherReadable) {
            if (thisReadable != otherReadable) {
                return false;
            }
            return this.unreadableEquals(other);
        }
        Object value1 = this.get();
        Object value2 = other.get();
        return this.valueEquals(value1, value2);
    }

    int valueHashCode(Object value2) throws DebugException {
        try {
            if (INTEROP.hasIdentity(value2)) {
                return INTEROP.identityHashCode(value2);
            }
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
        return System.identityHashCode(value2);
    }

    boolean valueEquals(Object value1, Object value2) throws DebugException {
        if (value1 == value2) {
            return true;
        }
        try {
            return INTEROP.isIdentical(value1, value2, INTEROP);
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
        }
    }

    abstract int unreadableHashCode();

    abstract boolean unreadableEquals(DebugValue var1);

    public final LanguageInfo getOriginalLanguage() throws DebugException {
        if (!this.isReadable()) {
            return null;
        }
        Object obj = this.get();
        if (obj == null) {
            return null;
        }
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(obj);
        if (lib.hasLanguage(obj)) {
            try {
                return this.getSession().getDebugger().getEnv().getLanguageInfo(lib.getLanguage(obj));
            }
            catch (UnsupportedMessageException e) {
                CompilerDirectives.transferToInterpreter();
                throw new AssertionError((Object)e);
            }
        }
        return null;
    }

    public final DebugValue asInLanguage(LanguageInfo language) {
        if (this.preferredLanguage == language) {
            return this;
        }
        return this.createAsInLanguage(language);
    }

    abstract DebugValue createAsInLanguage(LanguageInfo var1);

    public abstract DebuggerSession getSession();

    final Debugger getDebugger() {
        return this.getSession().getDebugger();
    }

    public String toString() {
        return "DebugValue(name=" + this.getName() + ", value = " + (this.isReadable() ? this.toDisplayString() : "<not readable>") + ")";
    }

    static void checkPrimitive(Object value2) {
        Class<?> clazz;
        if (value2 == null || (clazz = value2.getClass()) != Byte.class && clazz != Short.class && clazz != Integer.class && clazz != Long.class && clazz != Float.class && clazz != Double.class && clazz != Character.class && clazz != Boolean.class && clazz != String.class) {
            throw new IllegalArgumentException(value2 + " is not primitive.");
        }
    }

    private static final class HashEntryValue
    extends AbstractDebugCachedValue {
        private final Object hashMap;
        private final Object key;
        private final EntryKind kind;

        HashEntryValue(DebuggerSession session, LanguageInfo preferredLanguage, Object map, Object key, EntryKind kind) {
            super(session, preferredLanguage);
            this.hashMap = map;
            this.key = key;
            this.kind = kind;
        }

        static HashEntryValue getValueOrNull(DebuggerSession session, LanguageInfo preferredLanguage, Object map, Object key) throws DebugException {
            Object valueObject;
            try {
                valueObject = INTEROP.readHashValue(map, key);
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (UnknownKeyException ex) {
                return null;
            }
            catch (Throwable ex) {
                throw DebugException.create(session, ex, preferredLanguage, null, true, null);
            }
            HashEntryValue value2 = new HashEntryValue(session, preferredLanguage, map, key, EntryKind.VALUE);
            value2.setCachedValue(valueObject);
            return value2;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean isReadable() {
            switch (this.kind) {
                case KEY: {
                    return true;
                }
                case VALUE: {
                    try {
                        return INTEROP.isHashEntryReadable(this.hashMap, this.key);
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public boolean isWritable() {
            switch (this.kind) {
                case KEY: {
                    try {
                        return INTEROP.isHashEntryRemovable(this.hashMap, this.key);
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
                case VALUE: {
                    try {
                        return INTEROP.isHashEntryWritable(this.hashMap, this.key);
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        Object readValue() {
            switch (this.kind) {
                case KEY: {
                    return this.key;
                }
                case VALUE: {
                    try {
                        return INTEROP.readHashValue(this.hashMap, this.key);
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public boolean hasReadSideEffects() {
            return true;
        }

        @Override
        public boolean hasWriteSideEffects() {
            return true;
        }

        @Override
        public boolean isInternal() {
            return false;
        }

        @Override
        public DebugScope getScope() {
            return null;
        }

        @Override
        public void set(DebugValue value2) {
            Object newValue = value2.get();
            this.setNewValue(newValue);
        }

        @Override
        public void set(Object primitiveValue) {
            HashEntryValue.checkPrimitive(primitiveValue);
            this.setNewValue(primitiveValue);
        }

        private void setNewValue(Object newValue) {
            switch (this.kind) {
                case KEY: {
                    try {
                        Object value2 = INTEROP.readHashValue(this.hashMap, this.key);
                        INTEROP.removeHashEntry(this.hashMap, this.key);
                        INTEROP.writeHashEntry(this.hashMap, newValue, value2);
                        return;
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
                case VALUE: {
                    try {
                        INTEROP.writeHashEntry(this.hashMap, this.key, newValue);
                        return;
                    }
                    catch (ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
                    }
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        DebugValue createAsInLanguage(LanguageInfo language) {
            return new HashEntryValue(this.session, language, this.hashMap, this.key, this.kind);
        }

        @Override
        int unreadableHashCode() {
            int hash = 7;
            hash = 29 * hash + this.valueHashCode(this.hashMap);
            hash = 29 * hash + this.valueHashCode(this.key);
            hash = 29 * hash + this.kind.hashCode();
            return hash;
        }

        @Override
        boolean unreadableEquals(DebugValue var) {
            if (!(var instanceof HashEntryValue)) {
                return false;
            }
            HashEntryValue other = (HashEntryValue)var;
            return this.valueEquals(this.hashMap, other.hashMap) && this.valueEquals(this.key, other.key) && this.kind == other.kind;
        }

        static enum EntryKind {
            KEY,
            VALUE;

        }
    }

    private static final class HashEntryArrayValue
    extends HeapValue {
        private final Object hashMap;

        HashEntryArrayValue(DebuggerSession session, LanguageInfo preferredLanguage, String name, Object hashMap, Object value2) {
            super(session, preferredLanguage, name, value2);
            this.hashMap = hashMap;
        }

        @Override
        public List<DebugValue> getArray() throws DebugException {
            return new HashEntriesList(this.get());
        }

        final class HashEntriesList
        extends AbstractList<DebugValue> {
            private final Object list;

            HashEntriesList(Object list) {
                this.list = list;
            }

            @Override
            public DebugValue get(int index) {
                Object key;
                HashEntryValue.EntryKind kind;
                switch (index) {
                    case 0: {
                        kind = HashEntryValue.EntryKind.KEY;
                        break;
                    }
                    case 1: {
                        kind = HashEntryValue.EntryKind.VALUE;
                        break;
                    }
                    default: {
                        throw DebugException.create(HashEntryArrayValue.this.getSession(), InvalidArrayIndexException.create(index), HashEntryArrayValue.this.resolveLanguage(), null, true, null);
                    }
                }
                try {
                    key = INTEROP.readArrayElement(this.list, 0L);
                }
                catch (ThreadDeath td) {
                    throw td;
                }
                catch (Throwable ex) {
                    throw DebugException.create(HashEntryArrayValue.this.getSession(), ex, HashEntryArrayValue.this.resolveLanguage(), null, true, null);
                }
                return new HashEntryValue(HashEntryArrayValue.this.session, HashEntryArrayValue.this.preferredLanguage, HashEntryArrayValue.this.hashMap, key, kind);
            }

            @Override
            public DebugValue set(int index, DebugValue newValue) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int size() {
                try {
                    return (int)INTEROP.getArraySize(this.list);
                }
                catch (UnsupportedMessageException e) {
                    return 0;
                }
            }
        }
    }

    private static final class HashEntriesIteratorValue
    extends HeapValue {
        private final Object hashMap;
        private final HashEntryValue.EntryKind kind;

        HashEntriesIteratorValue(DebuggerSession session, LanguageInfo preferredLanguage, String name, Object hashMap, Object value2, HashEntryValue.EntryKind kind) {
            super(session, preferredLanguage, name, value2);
            this.hashMap = hashMap;
            assert (kind == null || kind == HashEntryValue.EntryKind.KEY);
            this.kind = kind;
        }

        @Override
        public DebugValue getIteratorNextElement() {
            Object value2 = this.get();
            try {
                Object next = INTEROP.getIteratorNextElement(value2);
                if (HashEntryValue.EntryKind.KEY == this.kind) {
                    return new HashEntryValue(this.getSession(), this.resolveLanguage(), this.hashMap, next, this.kind);
                }
                assert (this.kind == null);
                return new HashEntryArrayValue(this.getSession(), this.resolveLanguage(), null, this.hashMap, next);
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (StopIterationException ex) {
                throw new NoSuchElementException(ex.getLocalizedMessage());
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }
    }

    static final class ArrayElementValue
    extends AbstractDebugCachedValue {
        private final Object array;
        private final long index;
        private final DebugScope scope;

        ArrayElementValue(DebuggerSession session, LanguageInfo preferredLanguage, DebugScope scope, Object array, long index) {
            super(session, preferredLanguage);
            this.array = array;
            this.index = index;
            this.scope = scope;
        }

        @Override
        Object readValue() {
            this.checkValid();
            try {
                return INTEROP.readArrayElement(this.array, this.index);
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        public String getName() {
            return String.valueOf(this.index);
        }

        @Override
        public boolean isReadable() {
            this.checkValid();
            return INTEROP.isArrayElementReadable(this.array, this.index);
        }

        @Override
        public boolean isWritable() {
            this.checkValid();
            return INTEROP.isArrayElementWritable(this.array, this.index);
        }

        @Override
        public boolean hasReadSideEffects() {
            this.checkValid();
            return false;
        }

        @Override
        public boolean hasWriteSideEffects() {
            this.checkValid();
            return false;
        }

        @Override
        public boolean isInternal() {
            this.checkValid();
            return false;
        }

        @Override
        public DebugScope getScope() {
            this.checkValid();
            return this.scope;
        }

        @Override
        public void set(DebugValue value2) {
            this.checkValid();
            try {
                Object newValue = value2.get();
                INTEROP.writeArrayElement(this.array, this.index, newValue);
                this.resetCachedValue();
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        public void set(Object primitiveValue) {
            this.checkValid();
            ArrayElementValue.checkPrimitive(primitiveValue);
            try {
                INTEROP.writeArrayElement(this.array, this.index, primitiveValue);
                this.resetCachedValue();
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        DebugValue createAsInLanguage(LanguageInfo language) {
            return new ArrayElementValue(this.session, language, this.scope, this.array, this.index);
        }

        @Override
        int unreadableHashCode() {
            int hash = 7;
            hash = 29 * hash + this.valueHashCode(this.array);
            hash = 29 * hash + Long.hashCode(this.index);
            return hash;
        }

        @Override
        boolean unreadableEquals(DebugValue var) {
            if (!(var instanceof ArrayElementValue)) {
                return false;
            }
            ArrayElementValue other = (ArrayElementValue)var;
            return this.valueEquals(this.array, other.array) && this.index == other.index;
        }

        private void checkValid() {
            if (this.scope != null) {
                this.scope.verifyValidState();
            }
        }
    }

    static final class ObjectMemberValue
    extends AbstractDebugCachedValue {
        private final Object object;
        private final String member;
        private final DebugScope scope;

        ObjectMemberValue(DebuggerSession session, LanguageInfo preferredLanguage, DebugScope scope, Object object, String member) {
            super(session, preferredLanguage);
            this.object = object;
            this.member = member;
            this.scope = scope;
        }

        @Override
        Object readValue() {
            this.checkValid();
            try {
                return INTEROP.readMember(this.object, this.member);
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        public String getName() {
            return String.valueOf(this.member);
        }

        @Override
        public boolean isReadable() {
            this.checkValid();
            return INTEROP.isMemberReadable(this.object, this.member);
        }

        @Override
        public boolean isWritable() {
            this.checkValid();
            return INTEROP.isMemberWritable(this.object, this.member);
        }

        @Override
        public boolean hasReadSideEffects() {
            this.checkValid();
            return INTEROP.hasMemberReadSideEffects(this.object, this.member);
        }

        @Override
        public boolean hasWriteSideEffects() {
            this.checkValid();
            return INTEROP.hasMemberWriteSideEffects(this.object, this.member);
        }

        @Override
        public boolean isInternal() {
            this.checkValid();
            return INTEROP.isMemberInternal(this.object, this.member);
        }

        @Override
        public DebugScope getScope() {
            this.checkValid();
            return this.scope;
        }

        @Override
        public void set(DebugValue value2) {
            this.checkValid();
            try {
                Object newValue = value2.get();
                INTEROP.writeMember(this.object, this.member, newValue);
                this.resetCachedValue();
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        public void set(Object primitiveValue) {
            this.checkValid();
            ObjectMemberValue.checkPrimitive(primitiveValue);
            try {
                INTEROP.writeMember(this.object, this.member, primitiveValue);
                this.resetCachedValue();
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
        }

        @Override
        DebugValue createAsInLanguage(LanguageInfo language) {
            return new ObjectMemberValue(this.session, language, this.scope, this.object, this.member);
        }

        @Override
        int unreadableHashCode() {
            int hash = 7;
            hash = 29 * hash + this.valueHashCode(this.object);
            hash = 29 * hash + this.member.hashCode();
            return hash;
        }

        @Override
        boolean unreadableEquals(DebugValue var) {
            if (!(var instanceof ObjectMemberValue)) {
                return false;
            }
            ObjectMemberValue other = (ObjectMemberValue)var;
            return this.valueEquals(this.object, other.object) && this.member.equals(other.member);
        }

        private void checkValid() {
            if (this.scope != null) {
                this.scope.verifyValidState();
            }
        }
    }

    static abstract class AbstractDebugCachedValue
    extends AbstractDebugValue {
        private volatile Object cachedValue;

        AbstractDebugCachedValue(DebuggerSession session, LanguageInfo preferredLanguage) {
            super(session, preferredLanguage);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        final Object get() {
            Object value2 = this.cachedValue;
            if (value2 == null) {
                AbstractDebugCachedValue abstractDebugCachedValue = this;
                synchronized (abstractDebugCachedValue) {
                    value2 = this.cachedValue;
                    if (value2 == null) {
                        this.cachedValue = value2 = this.readValue();
                    }
                }
            }
            return value2;
        }

        abstract Object readValue();

        final void setCachedValue(Object newCachedValue) {
            this.cachedValue = newCachedValue;
        }

        final void resetCachedValue() {
            this.cachedValue = null;
        }
    }

    static class HeapValue
    extends AbstractDebugValue {
        private final String name;
        private final Object value;

        HeapValue(DebuggerSession session, String name, Object value2) {
            this(session, null, name, value2);
        }

        HeapValue(DebuggerSession session, LanguageInfo preferredLanguage, String name, Object value2) {
            super(session, preferredLanguage);
            this.name = name;
            this.value = value2;
            assert (value2 != null);
        }

        @Override
        Object get() {
            return this.value;
        }

        @Override
        public void set(DebugValue expression) {
            throw DebugException.create(this.getSession(), "Can not modify read-only value.");
        }

        @Override
        public void set(Object primitiveValue) {
            throw DebugException.create(this.getSession(), "Can not modify read-only value.");
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public boolean isReadable() {
            return true;
        }

        @Override
        public boolean isWritable() {
            return false;
        }

        @Override
        public boolean hasReadSideEffects() {
            return false;
        }

        @Override
        public boolean hasWriteSideEffects() {
            return false;
        }

        @Override
        public boolean isInternal() {
            return false;
        }

        @Override
        DebugValue createAsInLanguage(LanguageInfo language) {
            return new HeapValue(this.session, language, this.name, this.value);
        }

        @Override
        int unreadableHashCode() {
            throw new UnsupportedOperationException("HeapValue is always readable.");
        }

        @Override
        boolean unreadableEquals(DebugValue var) {
            throw new UnsupportedOperationException("HeapValue is always readable.");
        }
    }

    static abstract class AbstractDebugValue
    extends DebugValue {
        final DebuggerSession session;

        AbstractDebugValue(DebuggerSession session, LanguageInfo preferredLanguage) {
            super(preferredLanguage);
            this.session = session;
        }

        @Override
        public final <T> T as(Class<T> clazz) throws DebugException {
            if (!this.isReadable()) {
                throw new IllegalStateException("Value is not readable");
            }
            try {
                if (clazz == String.class) {
                    Object val = this.get();
                    Object stringValue = INTEROP.isMetaObject(val) ? INTEROP.getMetaQualifiedName(val) : INTEROP.toDisplayString(this.getLanguageView());
                    return clazz.cast(INTEROP.asString(stringValue));
                }
                if (clazz == Number.class || clazz == Boolean.class) {
                    return this.convertToPrimitive(clazz);
                }
            }
            catch (ThreadDeath td) {
                throw td;
            }
            catch (Throwable ex) {
                throw DebugException.create(this.getSession(), ex, this.resolveLanguage(), null, true, null);
            }
            throw new UnsupportedOperationException();
        }

        private <T> T convertToPrimitive(Class<T> clazz) {
            Object val = this.get();
            if (clazz.isInstance(val)) {
                return clazz.cast(val);
            }
            return clazz.cast(Debugger.ACCESSOR.hostSupport().convertPrimitiveLossLess(val, clazz));
        }

        @Override
        public final DebuggerSession getSession() {
            return this.session;
        }
    }
}

