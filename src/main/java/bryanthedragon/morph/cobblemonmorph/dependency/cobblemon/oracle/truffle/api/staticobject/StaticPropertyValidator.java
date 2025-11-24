
package com.oracle.truffle.api.staticobject;

class StaticPropertyValidator {
    StaticPropertyValidator() {
    }

    static void validate(Class<?> type) {
        throw new InternalError("JDK specific overlay for " + StaticPropertyValidator.class.getName() + " missing");
    }
}

