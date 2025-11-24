
package com.oracle.truffle.api;

import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.nodes.Node;
import java.util.Objects;

public final class TruffleStackTraceElement {
    private final Node location;
    private final RootCallTarget target;
    private final Frame frame;

    TruffleStackTraceElement(Node location, RootCallTarget target, Frame frame) {
        assert (target != null);
        this.location = location;
        this.target = target;
        this.frame = frame;
    }

    public static TruffleStackTraceElement create(Node location, RootCallTarget target, Frame frame) {
        Objects.requireNonNull(target, "RootCallTarget must not be null");
        return new TruffleStackTraceElement(location, target, frame);
    }

    public Node getLocation() {
        return this.location;
    }

    public RootCallTarget getTarget() {
        return this.target;
    }

    public Frame getFrame() {
        return this.frame;
    }

    public Object getGuestObject() {
        assert (LanguageAccessor.engineAccess().getCurrentCreatorTruffleContext() != null) : "The TruffleContext must be entered.";
        Object guestObject = LanguageAccessor.nodesAccess().translateStackTraceElement(this);
        assert (LanguageAccessor.EXCEPTIONS.assertGuestObject(guestObject));
        return guestObject;
    }
}

