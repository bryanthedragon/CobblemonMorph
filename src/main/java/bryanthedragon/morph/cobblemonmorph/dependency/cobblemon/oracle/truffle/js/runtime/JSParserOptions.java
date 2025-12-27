package com.oracle.truffle.js.runtime;

import org.graalvm.options.OptionValues;

public final class JSParserOptions {
   private final boolean strict;
   private final boolean scripting;
   private final boolean shebang;
   private final int ecmaScriptVersion;
   private final boolean syntaxExtensions;
   private final boolean constAsVar;
   private final boolean functionStatementError;
   private final boolean dumpOnError;
   private final boolean emptyStatements;
   private final boolean annexB;
   private final boolean allowBigInt;
   private final boolean classFields;
   private final boolean importAssertions;
   private final boolean privateFieldsIn;
   private final boolean topLevelAwait;

   public JSParserOptions() {
      this.strict = false;
      this.scripting = false;
      this.shebang = false;
      this.ecmaScriptVersion = 13;
      this.syntaxExtensions = false;
      this.constAsVar = false;
      this.functionStatementError = false;
      this.dumpOnError = false;
      this.emptyStatements = false;
      this.annexB = true;
      this.allowBigInt = true;
      this.classFields = true;
      this.importAssertions = false;
      this.privateFieldsIn = false;
      this.topLevelAwait = false;
   }

   private JSParserOptions(
      boolean strict,
      boolean scripting,
      boolean shebang,
      int ecmaScriptVersion,
      boolean syntaxExtensions,
      boolean constAsVar,
      boolean functionStatementError,
      boolean dumpOnError,
      boolean emptyStatements,
      boolean annexB,
      boolean allowBigInt,
      boolean classFields,
      boolean importAssertions,
      boolean privateFieldsIn,
      boolean topLevelAwait
   ) {
      this.strict = strict;
      this.scripting = scripting;
      this.shebang = shebang;
      this.ecmaScriptVersion = ecmaScriptVersion;
      this.syntaxExtensions = syntaxExtensions;
      this.constAsVar = constAsVar;
      this.functionStatementError = functionStatementError;
      this.dumpOnError = dumpOnError;
      this.emptyStatements = emptyStatements;
      this.annexB = annexB;
      this.allowBigInt = allowBigInt;
      this.classFields = classFields;
      this.importAssertions = importAssertions;
      this.privateFieldsIn = privateFieldsIn;
      this.topLevelAwait = topLevelAwait;
   }

   public boolean isStrict() {
      return this.strict;
   }

   public boolean isScripting() {
      return this.scripting;
   }

   public boolean isShebang() {
      return this.shebang;
   }

   public boolean isSyntaxExtensions() {
      return this.syntaxExtensions;
   }

   public boolean isConstAsVar() {
      return this.constAsVar;
   }

   public int getEcmaScriptVersion() {
      return this.ecmaScriptVersion;
   }

   public boolean isES6() {
      return this.ecmaScriptVersion >= 6;
   }

   public boolean isES8() {
      return this.ecmaScriptVersion >= 8;
   }

   public boolean isFunctionStatementError() {
      return this.functionStatementError;
   }

   public boolean isDumpOnError() {
      return this.dumpOnError;
   }

   public boolean isEmptyStatements() {
      return this.emptyStatements;
   }

   public boolean isAnnexB() {
      return this.annexB;
   }

   public boolean isAllowBigInt() {
      return this.allowBigInt;
   }

   public boolean isClassFields() {
      return this.classFields;
   }

   public boolean isImportAssertions() {
      return this.importAssertions;
   }

   public boolean isPrivateFieldsIn() {
      return this.privateFieldsIn;
   }

   public boolean isTopLevelAwait() {
      return this.topLevelAwait;
   }

   public JSParserOptions putOptions(OptionValues optionValues) {
      int ecmaScriptVersion = JSContextOptions.ECMASCRIPT_VERSION.getValue(optionValues);
      JSParserOptions opts = this.putEcmaScriptVersion(ecmaScriptVersion);
      opts = opts.putSyntaxExtensions(
         JSContextOptions.SYNTAX_EXTENSIONS.hasBeenSet(optionValues)
            ? JSContextOptions.SYNTAX_EXTENSIONS.getValue(optionValues)
            : JSContextOptions.NASHORN_COMPATIBILITY_MODE.getValue(optionValues)
      );
      opts = opts.putScripting(JSContextOptions.SCRIPTING.getValue(optionValues));
      opts = opts.putShebang(JSContextOptions.SHEBANG.hasBeenSet(optionValues) ? JSContextOptions.SHEBANG.getValue(optionValues) : ecmaScriptVersion >= 11);
      opts = opts.putStrict(JSContextOptions.STRICT.getValue(optionValues));
      opts = opts.putConstAsVar(JSContextOptions.CONST_AS_VAR.getValue(optionValues));
      opts = opts.putFunctionStatementError(JSContextOptions.FUNCTION_STATEMENT_ERROR.getValue(optionValues));
      opts = opts.putAnnexB(JSContextOptions.ANNEX_B.getValue(optionValues));
      opts = opts.putAllowBigInt(JSContextOptions.BIGINT.getValue(optionValues));
      opts = opts.putClassFields(
         JSContextOptions.CLASS_FIELDS.hasBeenSet(optionValues) ? JSContextOptions.CLASS_FIELDS.getValue(optionValues) : ecmaScriptVersion >= 12
      );
      opts = opts.putImportAssertions(JSContextOptions.IMPORT_ASSERTIONS.getValue(optionValues));
      opts = opts.putPrivateFieldsIn(
         JSContextOptions.PRIVATE_FIELDS_IN.hasBeenSet(optionValues) ? JSContextOptions.PRIVATE_FIELDS_IN.getValue(optionValues) : ecmaScriptVersion >= 13
      );
      return opts.putTopLevelAwait(
         JSContextOptions.TOP_LEVEL_AWAIT.hasBeenSet(optionValues) ? JSContextOptions.TOP_LEVEL_AWAIT.getValue(optionValues) : ecmaScriptVersion >= 13
      );
   }

   public static JSParserOptions fromOptions(OptionValues optionValues) {
      return new JSParserOptions().putOptions(optionValues);
   }

   public JSParserOptions putStrict(boolean strict) {
      return strict != this.strict
         ? new JSParserOptions(
            strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putScripting(boolean scripting) {
      return scripting != this.scripting
         ? new JSParserOptions(
            this.strict,
            scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putShebang(boolean shebang) {
      return shebang != this.shebang
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putEcmaScriptVersion(int ecmaScriptVersion) {
      return ecmaScriptVersion != this.ecmaScriptVersion
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putSyntaxExtensions(boolean syntaxExtensions) {
      return syntaxExtensions != this.syntaxExtensions
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putConstAsVar(boolean constAsVar) {
      return constAsVar != this.constAsVar
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putFunctionStatementError(boolean functionStatementError) {
      return functionStatementError != this.functionStatementError
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putAnnexB(boolean annexB) {
      return annexB != this.annexB
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putAllowBigInt(boolean allowBigInt) {
      return allowBigInt != this.allowBigInt
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putClassFields(boolean classFields) {
      return classFields != this.classFields
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            classFields,
            this.importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putImportAssertions(boolean importAssertions) {
      return importAssertions != this.importAssertions
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            importAssertions,
            this.privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putPrivateFieldsIn(boolean privateFieldsIn) {
      return privateFieldsIn != this.privateFieldsIn
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            privateFieldsIn,
            this.topLevelAwait
         )
         : this;
   }

   public JSParserOptions putTopLevelAwait(boolean topLevelAwait) {
      return topLevelAwait != this.topLevelAwait
         ? new JSParserOptions(
            this.strict,
            this.scripting,
            this.shebang,
            this.ecmaScriptVersion,
            this.syntaxExtensions,
            this.constAsVar,
            this.functionStatementError,
            this.dumpOnError,
            this.emptyStatements,
            this.annexB,
            this.allowBigInt,
            this.classFields,
            this.importAssertions,
            this.privateFieldsIn,
            topLevelAwait
         )
         : this;
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + (this.annexB ? 1231 : 1237);
      result = 31 * result + (this.constAsVar ? 1231 : 1237);
      result = 31 * result + (this.dumpOnError ? 1231 : 1237);
      result = 31 * result + this.ecmaScriptVersion;
      result = 31 * result + (this.emptyStatements ? 1231 : 1237);
      result = 31 * result + (this.functionStatementError ? 1231 : 1237);
      result = 31 * result + (this.scripting ? 1231 : 1237);
      result = 31 * result + (this.shebang ? 1231 : 1237);
      result = 31 * result + (this.strict ? 1231 : 1237);
      result = 31 * result + (this.syntaxExtensions ? 1231 : 1237);
      result = 31 * result + (this.allowBigInt ? 1231 : 1237);
      result = 31 * result + (this.classFields ? 1231 : 1237);
      result = 31 * result + (this.importAssertions ? 1231 : 1237);
      result = 31 * result + (this.privateFieldsIn ? 1231 : 1237);
      return 31 * result + (this.topLevelAwait ? 1231 : 1237);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof JSParserOptions)) {
         return false;
      } else {
         JSParserOptions other = (JSParserOptions)obj;
         if (this.annexB != other.annexB) {
            return false;
         } else if (this.constAsVar != other.constAsVar) {
            return false;
         } else if (this.dumpOnError != other.dumpOnError) {
            return false;
         } else if (this.ecmaScriptVersion != other.ecmaScriptVersion) {
            return false;
         } else if (this.emptyStatements != other.emptyStatements) {
            return false;
         } else if (this.functionStatementError != other.functionStatementError) {
            return false;
         } else if (this.scripting != other.scripting) {
            return false;
         } else if (this.shebang != other.shebang) {
            return false;
         } else if (this.strict != other.strict) {
            return false;
         } else if (this.syntaxExtensions != other.syntaxExtensions) {
            return false;
         } else if (this.allowBigInt != other.allowBigInt) {
            return false;
         } else if (this.classFields != other.classFields) {
            return false;
         } else if (this.importAssertions != other.importAssertions) {
            return false;
         } else {
            return this.privateFieldsIn != other.privateFieldsIn ? false : this.topLevelAwait == other.topLevelAwait;
         }
      }
   }
}
