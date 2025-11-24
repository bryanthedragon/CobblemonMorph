
package org.graalvm.polyglot;

public final class EnvironmentAccess {
    public static final EnvironmentAccess NONE = new EnvironmentAccess("NONE");
    public static final EnvironmentAccess INHERIT = new EnvironmentAccess("INHERIT");
    private final String name;

    private EnvironmentAccess(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}

