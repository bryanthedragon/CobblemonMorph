package com.oracle.js.parser.ir;

import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.MapCursor;

public final class Scope {
   private final Scope parent;
   private final int type;
   private final int flags;
   private static final int BLOCK_SCOPE = 1;
   private static final int FUNCTION_BODY_SCOPE = 2;
   private static final int FUNCTION_PARAMETER_SCOPE = 4;
   private static final int CATCH_PARAMETER_SCOPE = 8;
   private static final int GLOBAL_SCOPE = 16;
   private static final int MODULE_SCOPE = 32;
   private static final int FUNCTION_TOP_SCOPE = 64;
   private static final int SWITCH_BLOCK_SCOPE = 128;
   private static final int CLASS_HEAD_SCOPE = 256;
   private static final int CLASS_BODY_SCOPE = 512;
   private static final int EVAL_SCOPE = 1024;
   private static final int ARROW_FUNCTION_PARAMETER_SCOPE = 2048;
   private static final int IN_FUNCTION = 65536;
   private static final int IN_METHOD = 131072;
   private static final int IN_DERIVED_CONSTRUCTOR = 262144;
   private static final int IS_CLASS_FIELD_INITIALIZER = 524288;
   protected final EconomicMap<String, Symbol> symbols;
   protected EconomicMap<String, Scope.UseInfo> uses;
   private boolean closed;
   private boolean hasBlockScopedOrRedeclaredSymbols;
   private boolean hasPrivateNames;
   private boolean hasClosures;
   private boolean hasEval;
   private boolean hasNestedEval;

   private Scope(Scope parent, int type, int flags) {
      this.parent = parent;
      this.type = type;
      this.symbols = EconomicMap.create();
      this.flags = flags;
   }

   private Scope(Scope parent, int type) {
      this(parent, type, parent == null ? 0 : parent.flags);
   }

   private static int computeFlags(Scope parent, int functionFlags) {
      if ((functionFlags & 65536) != 0) {
         return parent == null ? 0 : parent.flags;
      } else {
         int flags = 0;
         flags |= 65536;
         flags |= (functionFlags & 1048576) != 0 ? 131072 : 0;
         flags |= (functionFlags & 4194304) != 0 ? 262144 : 0;
         return flags | ((functionFlags & 1073741824) != 0 ? 524288 : 0);
      }
   }

   public static Scope createGlobal() {
      return new Scope(null, 82);
   }

   public static Scope createModule() {
      return new Scope(null, 98);
   }

   public static Scope createFunctionBody(Scope parent, int functionFlags, boolean functionTopScope) {
      assert functionTopScope || parent.isFunctionParameterScope() && parent.isFunctionTopScope();

      return new Scope(parent, 2 | (functionTopScope ? 64 : 0), computeFlags(parent, functionFlags));
   }

   public static Scope createFunctionBody(Scope parent) {
      return createFunctionBody(parent, 0, true);
   }

   public static Scope createBlock(Scope parent) {
      return new Scope(parent, 1);
   }

   public static Scope createCatchParameter(Scope parent) {
      return new Scope(parent, 8);
   }

   public static Scope createFunctionParameter(Scope parent, int functionFlags) {
      return new Scope(parent, 68 | ((functionFlags & 65536) != 0 ? 2048 : 0), computeFlags(parent, functionFlags));
   }

   public static Scope createSwitchBlock(Scope parent) {
      return new Scope(parent, 129);
   }

   public static Scope createClassHead(Scope parent) {
      return new Scope(parent, 257);
   }

   public static Scope createClassBody(Scope parent) {
      return new Scope(parent, 512);
   }

   public static Scope createEval(Scope parent, boolean strict) {
      return new Scope(parent, 1024 | (strict ? 66 : 0));
   }

   public Scope getParent() {
      return this.parent;
   }

   public Iterable<Symbol> getSymbols() {
      return this.symbols.getValues();
   }

   public Symbol getExistingSymbol(final String name) {
      return this.symbols.get(name);
   }

   public boolean hasSymbol(final String name) {
      return this.symbols.containsKey(name);
   }

   public int getSymbolCount() {
      return this.symbols.size();
   }

   public Symbol putSymbol(final Symbol symbol) {
      assert !this.closed : "scope is closed";

      Symbol existing = this.symbols.putIfAbsent(symbol.getName(), symbol);
      if (existing != null) {
         assert (existing.getFlags() & 7) == (symbol.getFlags() & 7) : symbol;

         return existing;
      } else {
         if (symbol.isBlockScoped() || symbol.isVarRedeclaredHere()) {
            this.hasBlockScopedOrRedeclaredSymbols = true;
         }

         if (symbol.isPrivateName()) {
            this.hasPrivateNames = true;
         }

         return null;
      }
   }

   public boolean hasBlockScopedOrRedeclaredSymbols() {
      return this.hasBlockScopedOrRedeclaredSymbols;
   }

   public boolean hasPrivateNames() {
      return this.hasPrivateNames;
   }

   public boolean hasDeclarations() {
      return !this.symbols.isEmpty();
   }

   public boolean isLexicallyDeclaredName(final String varName, final boolean annexB, final boolean includeParameters) {
      Scope current = this;

      while (true) {
         label32: {
            if (current != null) {
               Symbol existingSymbol = current.getExistingSymbol(varName);
               if (existingSymbol != null && existingSymbol.isBlockScoped()) {
                  if (!existingSymbol.isCatchParameter() || !annexB) {
                     return true;
                  }
                  break label32;
               }

               if (includeParameters ? !current.isFunctionTopScope() : !current.isFunctionBodyScope()) {
                  break label32;
               }
            }

            return false;
         }

         current = current.getParent();
      }
   }

   public Symbol findBlockScopedSymbolInFunction(String varName) {
      for (Scope current = this; current != null; current = current.getParent()) {
         Symbol existingSymbol = current.getExistingSymbol(varName);
         if (existingSymbol != null) {
            if (existingSymbol.isBlockScoped()) {
               return existingSymbol;
            }
            break;
         }

         if (current.isFunctionTopScope()) {
            break;
         }
      }

      return null;
   }

   public boolean addPrivateName(TruffleString name, int symbolFlags) {
      assert this.isClassBodyScope();

      if (this.hasSymbol(name.toJavaStringUncached())) {
         assert this.getExistingSymbol(name.toJavaStringUncached()).isPrivateName();

         return false;
      } else {
         this.putSymbol(new Symbol(name, 132098 | symbolFlags));
         return true;
      }
   }

   public boolean findPrivateName(String name) {
      for (Scope current = this; current != null; current = current.parent) {
         if (current.hasSymbol(name)) {
            return true;
         }
      }

      return false;
   }

   public boolean isBlockScope() {
      return (this.type & 1) != 0;
   }

   public boolean isFunctionBodyScope() {
      return (this.type & 2) != 0;
   }

   public boolean isFunctionParameterScope() {
      return (this.type & 4) != 0;
   }

   public boolean isCatchParameterScope() {
      return (this.type & 8) != 0;
   }

   public boolean isGlobalScope() {
      return (this.type & 16) != 0;
   }

   public boolean isModuleScope() {
      return (this.type & 32) != 0;
   }

   public boolean isFunctionTopScope() {
      return (this.type & 64) != 0;
   }

   public boolean isSwitchBlockScope() {
      return (this.type & 128) != 0;
   }

   public boolean isClassBodyScope() {
      return (this.type & 512) != 0;
   }

   public boolean isClassHeadScope() {
      return (this.type & 256) != 0;
   }

   public boolean isEvalScope() {
      return (this.type & 1024) != 0;
   }

   public boolean isArrowFunctionParameterScope() {
      return (this.type & 2048) != 0;
   }

   public boolean inFunction() {
      return (this.flags & 65536) != 0;
   }

   public boolean inMethod() {
      return (this.flags & 131072) != 0;
   }

   public boolean inDerivedConstructor() {
      return (this.flags & 262144) != 0;
   }

   public boolean inClassFieldInitializer() {
      return (this.flags & 524288) != 0;
   }

   public void close() {
      if (!this.closed) {
         this.resolveUses();
         this.closed = true;
      }
   }

   public void kill() {
      assert !this.closed : "scope is closed";

      assert this.isKillable() : "must not be killed";

      this.symbols.clear();
      if (this.uses != null) {
         if (this.parent != null && !this.parent.closed) {
            MapCursor<String, Scope.UseInfo> cursor = this.uses.getEntries();

            while (cursor.advance()) {
               String usedName = cursor.getKey();
               Scope.UseInfo useInfo = cursor.getValue();

               assert useInfo.isUnresolved();

               if (useInfo.use == this) {
                  this.parent.addLocalUse(usedName);
               }

               if (useInfo.hasInnerUse()) {
                  useInfo.use = null;
                  this.parent.addUsesFromInnerScope(usedName, useInfo);
               }
            }
         }

         this.uses = null;
      }

      this.closed = true;
   }

   public void addIdentifierReference(String name) {
      this.addLocalUse(name);
   }

   private Scope.UseInfo getUseInfo(String name) {
      return this.uses == null ? null : this.uses.get(name);
   }

   private void putUseInfo(String name, Scope.UseInfo useInfo) {
      if (this.uses == null) {
         this.uses = EconomicMap.create();
      }

      this.uses.put(name, useInfo);
   }

   private void removeUseInfo(String name) {
      if (this.uses != null) {
         this.uses.removeKey(name);
      }
   }

   private void addLocalUse(String name) {
      assert !this.closed : "scope is closed";

      Scope.UseInfo foundUse = this.getUseInfo(name);
      Symbol foundSymbol = this.symbols.get(name);
      if (foundSymbol != null) {
         if (foundUse == null) {
            foundUse = Scope.UseInfo.resolvedLocal(name, this);
            this.putUseInfo(name, foundUse);

            assert foundUse.use == this;
         } else {
            assert foundUse.use == this || foundUse.use == null;

            foundUse.use = this;
         }

         resolveUse(foundUse, this, foundSymbol);
      } else {
         if (foundUse == null) {
            foundUse = Scope.UseInfo.unresolved(name);
            this.putUseInfo(name, foundUse);

            assert foundUse.use == null;
         } else {
            assert foundUse.def == null;

            assert foundUse.use == this || foundUse.use == null;
         }

         foundUse.use = this;
      }
   }

   private void addUsesFromInnerScope(String name, Scope.UseInfo useInfo) {
      assert !this.closed : "scope is closed";

      if (useInfo.use != null || useInfo.innerUseScopes != null) {
         Symbol foundSymbol = this.symbols.get(name);
         if (foundSymbol != null && !this.isKillable()) {
            resolveUse(useInfo, this, foundSymbol);
         } else {
            Scope.UseInfo foundUse = this.getUseInfo(name);
            if (foundUse == null) {
               foundUse = Scope.UseInfo.unresolved(name);
               this.putUseInfo(name, foundUse);
            } else {
               assert foundUse.use == this || foundUse.use == null;
            }

            if (useInfo.innerUseScopes != null) {
               for (Scope innerScope : useInfo.innerUseScopes) {
                  foundUse.addInnerUse(innerScope);
               }
            }

            if (useInfo.use != null) {
               foundUse.addInnerUse(useInfo.use);
            }
         }
      }
   }

   private boolean isKillable() {
      return this.isArrowFunctionParameterScope();
   }

   public void resolveUses() {
      if (this.uses != null && !this.uses.isEmpty()) {
         boolean hasDeclarations = this.hasDeclarations();
         MapCursor<String, Scope.UseInfo> cursor = this.uses.getEntries();

         while (cursor.advance()) {
            String usedName = cursor.getKey();
            Scope.UseInfo useInfo = cursor.getValue();
            if (useInfo.isUnresolved()) {
               Symbol foundSymbol;
               if (hasDeclarations && (foundSymbol = this.symbols.get(usedName)) != null) {
                  resolveUse(useInfo, this, foundSymbol);
               } else {
                  if (this.parent != null && !this.parent.closed) {
                     this.parent.addUsesFromInnerScope(usedName, useInfo);
                  } else {
                     markUseUnresolvable(useInfo);
                  }

                  if (useInfo.use == null) {
                     cursor.remove();
                  }
               }
            }
         }

         if (this.uses.isEmpty()) {
            this.uses = null;
         }
      }
   }

   private static void resolveUse(Scope.UseInfo useInfo, Scope defScope, Symbol foundSymbol) {
      String name = useInfo.name;

      assert useInfo.def == null || useInfo.def == defScope;

      assert name.equals(foundSymbol.getName());

      assert defScope.hasSymbol(foundSymbol.getName());

      if (useInfo.use != null) {
         markSymbolUsed(foundSymbol, defScope, useInfo.use);
      }

      if (useInfo.innerUseScopes != null) {
         for (Scope inner : useInfo.innerUseScopes) {
            Scope.UseInfo innerUse = inner.getUseInfo(name);
            if (innerUse == null) {
               innerUse = Scope.UseInfo.unresolved(name);
               innerUse.use = inner;
               inner.putUseInfo(name, innerUse);
            }

            innerUse.def = defScope;
            innerUse.innerUseScopes = null;
            markSymbolUsed(foundSymbol, defScope, inner);
         }

         useInfo.innerUseScopes = null;
      }

      useInfo.def = defScope;
   }

   private static void markSymbolUsed(Symbol foundSymbol, Scope defScope, Scope useScope) {
      foundSymbol.setUsed();
      if (defScope != useScope) {
         boolean inClosure = isInClosure(defScope, useScope);
         if (inClosure) {
            foundSymbol.setClosedOver();
            defScope.hasClosures = true;
         } else {
            foundSymbol.setUsedInInnerScope();
         }
      }
   }

   private static void markUseUnresolvable(Scope.UseInfo useInfo) {
      String name = useInfo.name;

      assert useInfo.def == null : name;

      if (useInfo.innerUseScopes != null) {
         for (Scope inner : useInfo.innerUseScopes) {
            Scope.UseInfo innerUse = inner.getUseInfo(name);
            if (innerUse != null) {
               assert innerUse.def == null : name;

               if (innerUse.use != null) {
                  innerUse.use.removeUseInfo(name);
               }
            }
         }

         useInfo.innerUseScopes = null;
      }
   }

   private static boolean isInClosure(Scope outer, Scope inner) {
      assert inner != null && outer != null;

      for (Scope current = inner; current != null && current != outer; current = current.getParent()) {
         if (current.isFunctionTopScope()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasClosures() {
      return this.hasClosures;
   }

   public boolean hasEval() {
      return this.hasEval;
   }

   public void setHasEval() {
      if (!this.hasEval) {
         this.hasEval = true;
         this.setHasNestedEval();
      }
   }

   public boolean hasNestedEval() {
      return this.hasNestedEval;
   }

   public void setHasNestedEval() {
      for (Scope current = this; current != null && !current.hasNestedEval && !current.closed; current = current.parent) {
         current.hasNestedEval = true;
      }
   }

   @Override
   public String toString() {
      StringJoiner names = new StringJoiner(",", "(", ")");

      for (Symbol symbol : this.symbols.getValues()) {
         String name = symbol.getName();
         String mark = "";
         if (symbol.isClosedOver()) {
            mark = mark + "'";
         } else if (symbol.isUsedInInnerScope()) {
            mark = mark + "\"";
         } else if (this.hasNestedEval) {
            if (!symbol.isUsed()) {
               mark = mark + "*";
            }
         } else if (!symbol.isUsed()) {
            mark = mark + "-";
         }

         names.add(name + mark);
      }

      String usedNames = "";
      if (this.uses != null) {
         StringJoiner sj = new StringJoiner(",", ">(", ")").setEmptyValue("");

         for (String use : this.uses.getKeys()) {
            if (!this.symbols.containsKey(use)) {
               sj.add(use);
            }
         }

         usedNames = sj.toString();
      }

      String taint = "";
      if (this.hasClosures) {
         taint = taint + "'";
      } else if (this.hasNestedEval) {
         taint = taint + "*";
      }

      return "[" + this.getScopeKindName() + "Scope" + taint + names + usedNames + (this.parent == null ? "" : ", " + this.parent) + "]";
   }

   private String getScopeKindName() {
      if (this.isGlobalScope()) {
         return "Global";
      } else if (this.isModuleScope()) {
         return "Module";
      } else if (this.isFunctionBodyScope()) {
         return "Var";
      } else if (this.isFunctionParameterScope()) {
         return "Param";
      } else if (this.isCatchParameterScope()) {
         return "Catch";
      } else if (this.isSwitchBlockScope()) {
         return "Switch";
      } else if (this.isClassHeadScope()) {
         return "Class";
      } else if (this.isClassBodyScope()) {
         return "Private";
      } else {
         return this.isEvalScope() ? "Eval" : "";
      }
   }

   static final class UseInfo {
      String name;
      Scope def;
      Scope use;
      List<Scope> innerUseScopes;

      private UseInfo(String name, Scope use, Scope def) {
         this.name = Objects.requireNonNull(name);
         this.use = use;
         this.def = def;
      }

      static Scope.UseInfo resolvedLocal(String name, Scope local) {
         return new Scope.UseInfo(name, local, local);
      }

      static Scope.UseInfo unresolved(String name) {
         return new Scope.UseInfo(name, null, null);
      }

      void addInnerUse(Scope useScope) {
         if (this.innerUseScopes == null) {
            this.innerUseScopes = new ArrayList<>();
         }

         assert this.innerUseScopes.stream().noneMatch(s -> s == useScope) : this.name;

         this.innerUseScopes.add(useScope);
      }

      boolean isResolved() {
         return !this.isUnresolved();
      }

      boolean isUnresolved() {
         return this.def == null || this.innerUseScopes != null;
      }

      boolean hasInnerUse() {
         return this.innerUseScopes != null;
      }
   }
}
