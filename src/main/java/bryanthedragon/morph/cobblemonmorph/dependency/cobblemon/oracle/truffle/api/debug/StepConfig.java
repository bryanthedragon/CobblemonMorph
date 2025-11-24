
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.SourceElement;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.instrumentation.EventContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class StepConfig {
    private static final StepConfig EMPTY = new StepConfig(null, null, 0);
    private static final Collection<SourceElement> allElements = Arrays.asList(SourceElement.values());
    private static final Map<SourceElement, Set<SuspendAnchor>> defaultAnchors;
    private final Set<SourceElement> sourceElements;
    private final Map<SourceElement, Set<SuspendAnchor>> preferredAnchors;
    private final int stepCount;

    StepConfig(Set<SourceElement> sourceElements, Map<SourceElement, Set<SuspendAnchor>> preferredAnchors, int count) {
        this.sourceElements = sourceElements;
        this.preferredAnchors = preferredAnchors;
        this.stepCount = count;
    }

    public static Builder newBuilder() {
        return EMPTY.new Builder();
    }

    Set<SourceElement> getSourceElements() {
        return this.sourceElements;
    }

    boolean matches(DebuggerSession session, EventContext context, SuspendAnchor anchor) {
        Set<SourceElement> elements2 = this.sourceElements;
        if (elements2 == null) {
            elements2 = session.getSourceElements();
        }
        for (SourceElement se : elements2) {
            if (!context.hasTag(se.getTag()) || !this.preferredAnchors.get((Object)se).contains((Object)anchor)) continue;
            return true;
        }
        return false;
    }

    boolean containsSourceElement(DebuggerSession session, SourceElement sourceElement) {
        Set<SourceElement> elements2 = this.sourceElements;
        if (elements2 == null) {
            elements2 = session.getSourceElements();
        }
        return elements2.contains((Object)sourceElement);
    }

    int getCount() {
        return this.stepCount;
    }

    static {
        EnumMap<SourceElement, Set<SuspendAnchor>> anchors = new EnumMap<SourceElement, Set<SuspendAnchor>>(SourceElement.class);
        anchors.put(SourceElement.ROOT, DebuggerSession.ANCHOR_SET_ALL);
        anchors.put(SourceElement.STATEMENT, DebuggerSession.ANCHOR_SET_BEFORE);
        anchors.put(SourceElement.EXPRESSION, DebuggerSession.ANCHOR_SET_ALL);
        assert (anchors.keySet().containsAll(allElements));
        defaultAnchors = Collections.unmodifiableMap(anchors);
    }

    public final class Builder {
        private Set<SourceElement> stepElements;
        private Map<SourceElement, Set<SuspendAnchor>> preferredAnchors;
        private int stepCount = -1;

        private Builder() {
        }

        public Builder sourceElements(SourceElement ... elements2) {
            if (this.stepElements != null) {
                throw new IllegalStateException("Step source elements can only be set once per the builder.");
            }
            if (elements2.length == 0) {
                throw new IllegalArgumentException("At least one source element needs to be provided.");
            }
            this.stepElements = elements2.length == 1 ? Collections.singleton(elements2[0]) : Collections.unmodifiableSet(new HashSet<SourceElement>(Arrays.asList(elements2)));
            return this;
        }

        public Builder suspendAnchors(SourceElement element, SuspendAnchor ... anchors) {
            if (anchors.length == 0) {
                throw new IllegalArgumentException("At least one anchor needs to be provided.");
            }
            Objects.requireNonNull(element, "SourceElement must not be null.");
            if (this.preferredAnchors == null) {
                this.preferredAnchors = new EnumMap<SourceElement, Set<SuspendAnchor>>(SourceElement.class);
            }
            if (anchors.length == 1) {
                if (anchors[0] == SuspendAnchor.BEFORE) {
                    this.preferredAnchors.put(element, DebuggerSession.ANCHOR_SET_BEFORE);
                } else {
                    this.preferredAnchors.put(element, DebuggerSession.ANCHOR_SET_AFTER);
                }
            } else {
                this.preferredAnchors.put(element, DebuggerSession.ANCHOR_SET_ALL);
            }
            return this;
        }

        public Builder count(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Step count must be > 0");
            }
            if (this.stepCount > 0) {
                throw new IllegalStateException("Step count can only be set once per the builder.");
            }
            this.stepCount = count;
            return this;
        }

        public StepConfig build() {
            if (this.stepCount < 0) {
                this.stepCount = 1;
            }
            if (this.preferredAnchors == null) {
                this.preferredAnchors = defaultAnchors;
            } else {
                Collection<SourceElement> possibleElements;
                Collection<SourceElement> collection = possibleElements = this.stepElements != null ? this.stepElements : allElements;
                if (!this.preferredAnchors.keySet().containsAll(possibleElements)) {
                    for (SourceElement elem : possibleElements) {
                        if (this.preferredAnchors.containsKey((Object)elem)) continue;
                        this.preferredAnchors.put(elem, defaultAnchors.get((Object)elem));
                    }
                }
            }
            return new StepConfig(this.stepElements, this.preferredAnchors, this.stepCount);
        }
    }
}

