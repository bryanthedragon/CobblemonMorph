package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Map;
import java.util.StringJoiner;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class GlobalEnvironment extends DerivedEnvironment {
   private static final UnmodifiableEconomicMap<TruffleString, GlobalEnvironment.DeclarationKind> PREDEFINED_IMMUTABLE_GLOBALS = initPredefinedImmutableGlobals();
   private final EconomicMap<TruffleString, GlobalEnvironment.DeclarationKind> declarations = EconomicMap.create(PREDEFINED_IMMUTABLE_GLOBALS);

   public GlobalEnvironment(Environment parent, NodeFactory factory, JSContext context) {
      super(parent, factory, context);
   }

   @Override
   public JSFrameSlot findBlockFrameSlot(Object name) {
      return null;
   }

   public void addLexicalDeclaration(TruffleString name, boolean isConst) {
      this.declarations.putIfAbsent(name, isConst ? GlobalEnvironment.DeclarationKind.Const : GlobalEnvironment.DeclarationKind.Let);
   }

   public boolean hasLexicalDeclaration(TruffleString name) {
      GlobalEnvironment.DeclarationKind decl = this.declarations.get(name);
      return decl != null && decl.isLexical();
   }

   public boolean hasConstDeclaration(TruffleString name) {
      GlobalEnvironment.DeclarationKind decl = this.declarations.get(name);
      return decl != null && decl.isConst();
   }

   public void addVarDeclaration(TruffleString name) {
      this.declarations.putIfAbsent(name, GlobalEnvironment.DeclarationKind.Var);
   }

   public boolean hasVarDeclaration(TruffleString name) {
      GlobalEnvironment.DeclarationKind decl = this.declarations.get(name);
      return decl != null && !decl.isLexical();
   }

   public static boolean isGlobalObjectConstant(TruffleString name) {
      return PREDEFINED_IMMUTABLE_GLOBALS.containsKey(name);
   }

   private static UnmodifiableEconomicMap<TruffleString, GlobalEnvironment.DeclarationKind> initPredefinedImmutableGlobals() {
      EconomicMap<TruffleString, GlobalEnvironment.DeclarationKind> map = EconomicMap.create();
      map.put(Strings.UNDEFINED, GlobalEnvironment.DeclarationKind.Var);
      map.put(Strings.NAN, GlobalEnvironment.DeclarationKind.Var);
      map.put(Strings.INFINITY, GlobalEnvironment.DeclarationKind.Var);
      return map;
   }

   public boolean hasBeenDeclared(TruffleString name) {
      GlobalEnvironment.DeclarationKind decl = this.declarations.get(name);
      return decl != null ? decl.isDeclared() : false;
   }

   public void setHasBeenDeclared(TruffleString name, boolean declared) {
      GlobalEnvironment.DeclarationKind decl = this.declarations.get(name);
      if (decl != null && decl.isLexical() && decl.isDeclared() != declared) {
         this.declarations.put(name, decl.withDeclared(declared));
      }
   }

   @Override
   protected String toStringImpl(Map<String, Integer> state) {
      return "Global" + new StringJoiner(", ", "{", "}").add(joinElements(this.declarations.getKeys())).toString();
   }

   private static enum DeclarationKind {
      Var(false, false, true),
      Let(true, false, false),
      LetDeclared(true, false, true),
      Const(true, true, false),
      ConstDeclared(true, true, true);

      private final boolean isLexical;
      private final boolean isConst;
      private final boolean isDeclared;

      private DeclarationKind(boolean isLexical, boolean isConst, boolean isDeclared) {
         this.isLexical = isLexical;
         this.isConst = isConst;
         this.isDeclared = isDeclared;
      }

      public final boolean isLexical() {
         return this.isLexical;
      }

      public final boolean isConst() {
         return this.isConst;
      }

      public final boolean isDeclared() {
         return this.isDeclared;
      }

      public final GlobalEnvironment.DeclarationKind withDeclared(boolean declared) {
         assert this.isLexical() || declared;

         if (!this.isLexical() || this.isDeclared() == declared) {
            return this;
         } else if (declared) {
            return this.isConst() ? ConstDeclared : LetDeclared;
         } else {
            return this.isConst() ? Const : Let;
         }
      }
   }
}
