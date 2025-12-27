package com.oracle.js.parser.ir;

import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class FunctionNode extends LexicalContextExpression implements Flags<FunctionNode> {
   private final Source source;
   private final Object endParserState;
   private final IdentNode ident;
   private final Block body;
   private final TruffleString name;
   private final List<IdentNode> parameters;
   private final long firstToken;
   private final long lastToken;
   private final int flags;
   private final int lineNumber;
   private final int numOfParams;
   private final int length;
   private final Module module;
   private final TruffleString internalName;
   private boolean usesAncestorScope;
   public static final int IS_ANONYMOUS = 1;
   public static final int IS_DECLARED = 2;
   public static final int IS_STRICT = 4;
   public static final int USES_ARGUMENTS = 8;
   public static final int IS_STATEMENT = 16;
   public static final int HAS_EVAL = 32;
   public static final int HAS_NESTED_EVAL = 64;
   public static final int HAS_SCOPE_BLOCK = 128;
   public static final int DEFINES_ARGUMENTS = 256;
   public static final int USES_ANCESTOR_SCOPE = 512;
   public static final int IS_SCRIPT = 1024;
   public static final int IS_GETTER = 2048;
   public static final int IS_SETTER = 4096;
   public static final int IS_PROGRAM = 8192;
   public static final int HAS_CLOSURES = 16384;
   public static final int USES_THIS = 32768;
   private static final int HAS_DEEP_EVAL = 96;
   private static final int MAYBE_NEEDS_ARGUMENTS = 40;
   public static final int NEEDS_PARENT_SCOPE = 8800;
   public static final int IS_ARROW = 65536;
   public static final int IS_MODULE = 131072;
   public static final int HAS_DIRECT_SUPER = 262144;
   public static final int USES_SUPER = 524288;
   public static final int IS_METHOD = 1048576;
   public static final int NO_FUNCTION_SELF = 1056771;
   public static final int IS_CLASS_CONSTRUCTOR = 2097152;
   public static final int IS_DERIVED_CONSTRUCTOR = 4194304;
   public static final int USES_NEW_TARGET = 8388608;
   public static final int IS_GENERATOR = 16777216;
   public static final int IS_ASYNC = 33554432;
   public static final int HAS_NON_SIMPLE_PARAMETER_LIST = 67108864;
   public static final int HAS_ARROW_EVAL = 134217728;
   public static final int HAS_FUNCTION_DECLARATIONS = 268435456;
   public static final int HAS_APPLY_ARGUMENTS_CALL = 536870912;
   public static final int IS_CLASS_FIELD_INITIALIZER = 1073741824;
   public static final int ARROW_HEAD_FLAGS = 134791400;

   public FunctionNode(
      final Source source,
      final int lineNumber,
      final long token,
      final int finish,
      final long firstToken,
      final long lastToken,
      final IdentNode ident,
      final TruffleString name,
      final int length,
      final int numOfParams,
      final List<IdentNode> parameters,
      final int flags,
      final Block body,
      final Object endParserState,
      final Module module,
      final TruffleString internalName
   ) {
      super(token, Token.descPosition(firstToken), finish);
      this.source = source;
      this.lineNumber = lineNumber;
      this.ident = ident;
      this.name = Objects.requireNonNull(name);
      this.length = length;
      this.numOfParams = numOfParams;
      this.parameters = List.copyOf(parameters);
      this.firstToken = firstToken;
      this.lastToken = lastToken;
      this.flags = flags;
      this.body = body;
      this.endParserState = endParserState;
      this.module = module;
      this.internalName = internalName;
   }

   private FunctionNode(
      final FunctionNode functionNode,
      final long lastToken,
      final Object endParserState,
      final int flags,
      final TruffleString name,
      final Block body,
      final List<IdentNode> parameters,
      final Source source
   ) {
      super(functionNode);
      this.endParserState = endParserState;
      this.lineNumber = functionNode.lineNumber;
      this.flags = flags;
      this.name = Objects.requireNonNull(name);
      this.lastToken = lastToken;
      this.body = body;
      this.parameters = parameters;
      this.source = source;
      this.ident = functionNode.ident;
      this.firstToken = functionNode.firstToken;
      this.length = functionNode.length;
      this.numOfParams = functionNode.numOfParams;
      this.module = functionNode.module;
      this.internalName = functionNode.internalName;
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterFunctionNode(this) ? visitor.leaveFunctionNode(this.setBody(lc, (Block)this.body.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterFunctionNode(this);
   }

   public Source getSource() {
      return this.source;
   }

   public int getId() {
      return this.isProgram() ? -1 : Token.descPosition(this.firstToken);
   }

   public String getSourceName() {
      return getSourceName(this.source);
   }

   public static String getSourceName(final Source source) {
      String explicitURL = source.getExplicitURL();
      return explicitURL != null ? explicitURL : source.getName();
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      if (this.isAsync()) {
         sb.append("async ");
      }

      sb.append("function");
      if (this.isGenerator()) {
         sb.append('*');
      }

      if (this.ident != null) {
         sb.append(' ');
         this.ident.toString(sb, printTypes);
      } else if (!this.name.isEmpty()) {
         sb.append(' ').append(this.name);
      } else if (this.internalName != null && !this.internalName.isEmpty()) {
         sb.append(' ').append(this.internalName);
      }

      this.toStringTail(sb, printTypes);
   }

   void toStringTail(final StringBuilder sb, final boolean printTypes) {
      sb.append('(');
      Iterator<IdentNode> iter = this.parameters.iterator();

      while (iter.hasNext()) {
         IdentNode parameter = iter.next();
         parameter.toString(sb, printTypes);
         if (iter.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append(')');
   }

   @Override
   public int getFlags() {
      return this.flags;
   }

   @Override
   public boolean getFlag(final int flag) {
      return (this.flags & flag) != 0;
   }

   public FunctionNode setFlags(final LexicalContext lc, final int flags) {
      return this.flags == flags
         ? this
         : Node.replaceInLexicalContext(
            lc, this, new FunctionNode(this, this.lastToken, this.endParserState, flags, this.name, this.body, this.parameters, this.source)
         );
   }

   public FunctionNode setFlag(final LexicalContext lc, final int flag) {
      return this.setFlags(lc, this.flags | flag);
   }

   public boolean isProgram() {
      return this.getFlag(8192);
   }

   public boolean hasEval() {
      return this.getFlag(32);
   }

   public long getFirstToken() {
      return this.firstToken;
   }

   public boolean usesThis() {
      return this.getFlag(32768);
   }

   public IdentNode getIdent() {
      return this.ident;
   }

   public Block getBody() {
      return this.body;
   }

   public Block getVarDeclarationBlock() {
      return this.body.isParameterBlock() ? ((BlockStatement)this.body.getLastStatement()).getBlock() : this.body;
   }

   public FunctionNode setBody(final LexicalContext lc, final Block body) {
      return this.body == body
         ? this
         : Node.replaceInLexicalContext(
            lc,
            this,
            new FunctionNode(
               this, this.lastToken, this.endParserState, this.flags | (body.needsScope() ? 128 : 0), this.name, body, this.parameters, this.source
            )
         );
   }

   public boolean needsDynamicScope() {
      return this.hasEval() && !this.isStrict();
   }

   public boolean needsArguments() {
      return this.getFlag(40) && !this.getFlag(1073807616) && !this.isProgram();
   }

   public long getLastToken() {
      return this.lastToken;
   }

   public Object getEndParserState() {
      return this.endParserState;
   }

   public String getName() {
      return !this.isAnonymous() ? this.getIdent().getName() : this.name.toJavaStringUncached();
   }

   public TruffleString getNameTS() {
      return this.name;
   }

   public FunctionNode setName(final LexicalContext lc, final TruffleString name) {
      return this.name.equals(name)
         ? this
         : Node.replaceInLexicalContext(
            lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, name, this.body, this.parameters, this.source)
         );
   }

   public String getInternalName() {
      return this.internalName.toJavaStringUncached();
   }

   public TruffleString getInternalNameTS() {
      return this.internalName;
   }

   public List<IdentNode> getParameters() {
      return this.parameters;
   }

   public int getNumOfParams() {
      return this.numOfParams;
   }

   public int getLength() {
      return this.length;
   }

   public boolean isDeclared() {
      return this.getFlag(2);
   }

   public boolean isAnonymous() {
      return this.getFlag(1);
   }

   public boolean isNamedFunctionExpression() {
      return !this.getFlag(1056771);
   }

   public boolean isStrict() {
      return this.getFlag(4);
   }

   public boolean isMethod() {
      return this.getFlag(1048576);
   }

   public boolean usesSuper() {
      return this.getFlag(524288);
   }

   public boolean hasDirectSuper() {
      return this.getFlag(262144);
   }

   public boolean isClassConstructor() {
      return this.getFlag(2097152);
   }

   public boolean isDerivedConstructor() {
      return this.getFlag(4194304);
   }

   public boolean usesNewTarget() {
      return this.getFlag(8388608);
   }

   public boolean isScript() {
      return this.getFlag(1024);
   }

   public boolean isGetter() {
      return this.getFlag(2048);
   }

   public boolean isSetter() {
      return this.getFlag(4096);
   }

   public boolean isArrow() {
      return this.getFlag(65536);
   }

   public boolean isGenerator() {
      return this.getFlag(16777216);
   }

   public boolean isModule() {
      return this.getFlag(131072);
   }

   public Module getModule() {
      return this.module;
   }

   public boolean isStatement() {
      return this.getFlag(16);
   }

   public boolean isAsync() {
      return this.getFlag(33554432);
   }

   public boolean hasSimpleParameterList() {
      return !this.getFlag(67108864);
   }

   public boolean usesAncestorScope() {
      return this.usesAncestorScope;
   }

   public void setUsesAncestorScope(boolean usesAncestorScope) {
      this.usesAncestorScope = usesAncestorScope;
   }

   public boolean isNormal() {
      return !this.getFlag(51584000);
   }

   boolean isFunctionDeclaration() {
      return this.isDeclared() && this.isNormal();
   }

   public boolean hasApplyArgumentsCall() {
      return this.getFlag(536870912);
   }

   public boolean hasArrowEval() {
      return this.getFlag(134217728);
   }

   public boolean needsThis() {
      return this.usesThis() || this.hasDirectSuper() || this.hasEval() || this.hasArrowEval();
   }

   public boolean needsNewTarget() {
      return this.usesNewTarget() || this.hasDirectSuper() || !this.isArrow() && !this.isProgram() && (this.hasEval() || this.hasArrowEval());
   }

   public boolean needsSuper() {
      return this.usesSuper() || this.isMethod() && (this.hasEval() || this.hasArrowEval());
   }

   public boolean isClassFieldInitializer() {
      return this.getFlag(1073741824);
   }

   public boolean hasClosures() {
      return this.getFlag(16384);
   }
}
