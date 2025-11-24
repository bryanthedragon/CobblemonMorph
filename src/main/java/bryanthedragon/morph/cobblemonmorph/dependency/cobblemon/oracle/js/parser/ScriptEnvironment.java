
package com.oracle.js.parser;

import java.io.PrintWriter;

public final class ScriptEnvironment {
    private final PrintWriter err;
    final boolean constAsVar;
    final boolean dumpOnError;
    final boolean emptyStatements;
    final int ecmaScriptVersion;
    final FunctionStatementBehavior functionStatement;
    final boolean syntaxExtensions;
    final boolean scripting;
    final boolean shebang;
    final boolean strict;
    final boolean allowBigInt;
    final boolean annexB;
    final boolean classFields;
    final boolean importAssertions;
    final boolean privateFieldsIn;
    final boolean topLevelAwait;

    private ScriptEnvironment(boolean strict, int ecmaScriptVersion, boolean emptyStatements, boolean syntaxExtensions, boolean scripting, boolean shebang, boolean constAsVar, boolean allowBigInt, boolean annexB, boolean classFields, boolean importAssertions, boolean privateFieldsIn, boolean topLevelAwait, FunctionStatementBehavior functionStatementBehavior, PrintWriter dumpOnError) {
        this.err = dumpOnError;
        this.constAsVar = constAsVar;
        this.dumpOnError = dumpOnError != null;
        this.emptyStatements = emptyStatements;
        this.functionStatement = functionStatementBehavior;
        this.syntaxExtensions = syntaxExtensions;
        this.strict = strict;
        this.scripting = scripting;
        this.shebang = shebang;
        this.ecmaScriptVersion = ecmaScriptVersion;
        this.allowBigInt = allowBigInt;
        this.annexB = annexB;
        this.classFields = classFields;
        this.importAssertions = importAssertions;
        this.privateFieldsIn = privateFieldsIn;
        this.topLevelAwait = topLevelAwait;
    }

    PrintWriter getErr() {
        return this.err;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int ecmaScriptVersion = 6;
        private boolean constAsVar;
        private boolean emptyStatements;
        private boolean syntaxExtensions = true;
        private boolean scripting;
        private boolean shebang;
        private boolean strict;
        private boolean allowBigInt;
        private boolean annexB = true;
        private boolean classFields = true;
        private boolean importAssertions = false;
        private boolean privateFieldsIn = false;
        private boolean topLevelAwait = false;
        private FunctionStatementBehavior functionStatementBehavior = FunctionStatementBehavior.ERROR;
        private PrintWriter dumpOnError;

        private Builder() {
        }

        public Builder ecmaScriptVersion(int ecmaScriptVersion) {
            this.ecmaScriptVersion = ecmaScriptVersion;
            return this;
        }

        public Builder constAsVar(boolean constAsVar) {
            this.constAsVar = constAsVar;
            return this;
        }

        public Builder emptyStatements(boolean emptyStatements) {
            this.emptyStatements = emptyStatements;
            return this;
        }

        public Builder syntaxExtensions(boolean syntaxExtensions) {
            this.syntaxExtensions = syntaxExtensions;
            return this;
        }

        public Builder scripting(boolean scripting) {
            this.scripting = scripting;
            return this;
        }

        public Builder shebang(boolean shebang) {
            this.shebang = shebang;
            return this;
        }

        public Builder strict(boolean strict) {
            this.strict = strict;
            return this;
        }

        public Builder allowBigInt(boolean allowBigInt) {
            this.allowBigInt = allowBigInt;
            return this;
        }

        public Builder annexB(boolean annexB) {
            this.annexB = annexB;
            return this;
        }

        public Builder classFields(boolean classFields) {
            this.classFields = classFields;
            return this;
        }

        public Builder importAssertions(boolean importAssertions) {
            this.importAssertions = importAssertions;
            return this;
        }

        public Builder privateFieldsIn(boolean privateFieldsIn) {
            this.privateFieldsIn = privateFieldsIn;
            return this;
        }

        public Builder topLevelAwait(boolean topLevelAwait) {
            this.topLevelAwait = topLevelAwait;
            return this;
        }

        public Builder functionStatementBehavior(FunctionStatementBehavior functionStatementBehavior) {
            this.functionStatementBehavior = functionStatementBehavior;
            return this;
        }

        public Builder dumpOnError(PrintWriter dumpOnError) {
            this.dumpOnError = dumpOnError;
            return this;
        }

        public ScriptEnvironment build() {
            return new ScriptEnvironment(this.strict, this.ecmaScriptVersion, this.emptyStatements, this.syntaxExtensions, this.scripting, this.shebang, this.constAsVar, this.allowBigInt, this.annexB, this.classFields, this.importAssertions, this.privateFieldsIn, this.topLevelAwait, this.functionStatementBehavior, this.dumpOnError);
        }
    }

    public static enum FunctionStatementBehavior {
        ACCEPT,
        WARNING,
        ERROR;

    }
}

