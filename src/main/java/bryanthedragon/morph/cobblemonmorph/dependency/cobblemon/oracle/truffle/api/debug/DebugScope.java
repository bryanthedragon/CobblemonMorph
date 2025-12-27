package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Objects;

public final class DebugScope {
   private static final InteropLibrary INTEROP = InteropLibrary.getUncached();
   private static final NodeLibrary NODE = NodeLibrary.getUncached();
   private final Object scope;
   private final DebuggerSession session;
   private final SuspendedEvent event;
   private final Node node;
   private final Frame frame;
   private final RootNode root;
   private final LanguageInfo language;
   private DebugScope parent;
   private ValuePropertiesCollection variables;

   DebugScope(Object scope, DebuggerSession session, SuspendedEvent event, Node node, Frame frame, RootNode root) {
      this(scope, session, event, node, frame, root, null);
   }

   DebugScope(Object scope, DebuggerSession session, LanguageInfo language) {
      this(scope, session, null, null, null, null, language);
   }

   private DebugScope(Object scope, DebuggerSession session, SuspendedEvent event, Node node, Frame frame, RootNode root, LanguageInfo language) {
      this.scope = scope;
      this.session = session;
      this.event = event;
      this.node = node;
      this.frame = frame;
      this.root = root;
      this.language = language;
   }

   public String getName() {
      try {
         return INTEROP.asString(INTEROP.toDisplayString(this.scope));
      } catch (ThreadDeath var2) {
         throw var2;
      } catch (Throwable var3) {
         throw DebugException.create(this.session, var3, this.language, this.node, true, null);
      }
   }

   public DebugScope getParent() throws DebugException {
      this.verifyValidState();

      try {
         if (this.parent == null && INTEROP.hasScopeParent(this.scope)) {
            this.parent = new DebugScope(INTEROP.getScopeParent(this.scope), this.session, this.event, this.node, this.frame, this.root, this.language);
         }
      } catch (ThreadDeath var2) {
         throw var2;
      } catch (Throwable var3) {
         throw DebugException.create(this.session, var3, this.language);
      }

      return this.parent;
   }

   public boolean isFunctionScope() {
      SourceSection rootSourceSection = this.getRootSourceSection();

      try {
         return rootSourceSection != null && INTEROP.hasSourceLocation(this.scope) && rootSourceSection.equals(INTEROP.getSourceLocation(this.scope));
      } catch (UnsupportedMessageException var3) {
         return false;
      }
   }

   private SourceSection getRootSourceSection() {
      if (this.root == null) {
         return null;
      } else {
         SourceSection rootSourceSection = this.root.getSourceSection();
         if (rootSourceSection == null) {
            final SourceSection[] rootSection = new SourceSection[]{null};
            this.root.accept(new NodeVisitor() {
               @Override
               public boolean visit(Node n) {
                  if (n instanceof InstrumentableNode) {
                     InstrumentableNode inode = (InstrumentableNode)n;
                     if (inode.isInstrumentable() && inode.hasTag(StandardTags.RootTag.class)) {
                        rootSection[0] = n.getSourceSection();
                        return false;
                     }
                  }

                  return true;
               }
            });
            rootSourceSection = rootSection[0];
         }

         return rootSourceSection;
      }
   }

   public SourceSection getSourceSection() throws DebugException {
      try {
         if (!INTEROP.hasSourceLocation(this.scope)) {
            return null;
         } else {
            SourceSection location = INTEROP.getSourceLocation(this.scope);
            return location != null ? this.session.resolveSection(location) : null;
         }
      } catch (ThreadDeath var2) {
         throw var2;
      } catch (Throwable var3) {
         throw DebugException.create(this.session, var3, this.language);
      }
   }

   @Deprecated(since = "20.3")
   public Iterable<DebugValue> getArguments() throws DebugException {
      this.verifyValidState();
      if (this.node == null) {
         return null;
      } else {
         try {
            Node argNode = this.node;

            while (argNode != null && (!(argNode instanceof InstrumentableNode) || !((InstrumentableNode)argNode).hasTag(StandardTags.RootTag.class))) {
               argNode = argNode.getParent();
            }

            if (argNode != null && NODE.hasScope(argNode, this.frame)) {
               Object argumentsObj;
               try {
                  argumentsObj = NODE.getScope(argNode, this.frame, true);
                  if (INTEROP.hasScopeParent(argumentsObj)) {
                     argumentsObj = new DebugScope.SubtractedVariables(argumentsObj, INTEROP.getScopeParent(argumentsObj));
                  }
               } catch (UnsupportedMessageException var5) {
                  return null;
               }

               if (argumentsObj != null) {
                  String receiverName = null;
                  if (NODE.hasReceiverMember(argNode, this.frame)) {
                     receiverName = INTEROP.asString(NODE.getReceiverMember(argNode, this.frame));
                  }

                  ValuePropertiesCollection properties = DebugValue.getProperties(argumentsObj, receiverName, this.session, this.getLanguage(), this);
                  if (properties != null) {
                     return properties;
                  }

                  if (ValueInteropList.INTEROP.hasArrayElements(argumentsObj)) {
                     return new ValueInteropList(this.session, this.getLanguage(), argumentsObj);
                  }
               }

               return null;
            } else {
               return null;
            }
         } catch (ThreadDeath var6) {
            throw var6;
         } catch (Throwable var7) {
            throw DebugException.create(this.session, var7, this.language);
         }
      }
   }

   public DebugValue getReceiver() {
      this.verifyValidState();
      DebugValue receiverValue = null;

      try {
         if (this.node != null && NODE.hasReceiverMember(this.node, this.frame)) {
            String name = INTEROP.asString(NODE.getReceiverMember(this.node, this.frame));
            return INTEROP.isMemberReadable(this.scope, name) && this.isDeclaredInScope(name)
               ? new DebugValue.ObjectMemberValue(this.session, this.getLanguage(), this, this.scope, name)
               : null;
         } else {
            return null;
         }
      } catch (ThreadDeath var3) {
         throw var3;
      } catch (Throwable var4) {
         throw DebugException.create(this.session, var4, this.language);
      }
   }

   public DebugValue getRootInstance() {
      this.verifyValidState();
      DebugValue functionValue = null;

      try {
         if (this.node != null && NODE.hasRootInstance(this.node, this.frame)) {
            Object function = NODE.hasRootInstance(this.node, this.frame);
            if (function != null) {
               String name;
               if (INTEROP.hasExecutableName(function)) {
                  name = INTEROP.asString(INTEROP.getExecutableName(function));
               } else {
                  name = this.root.getName();
               }

               functionValue = new DebugValue.HeapValue(this.session, this.getLanguage(), name, function);
            }

            return functionValue;
         } else {
            return null;
         }
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (Throwable var5) {
         throw DebugException.create(this.session, var5, this.language);
      }
   }

   public Iterable<DebugValue> getDeclaredValues() throws DebugException {
      return this.getVariables();
   }

   public DebugValue getDeclaredValue(String name) throws DebugException {
      return this.getVariables().get(name);
   }

   RootNode getRoot() {
      return this.root;
   }

   private ValuePropertiesCollection getVariables() {
      this.verifyValidState();

      try {
         if (this.variables == null) {
            Object scopeParent = null;
            if (INTEROP.hasScopeParent(this.scope)) {
               try {
                  scopeParent = INTEROP.getScopeParent(this.scope);
               } catch (UnsupportedMessageException var4) {
                  throw CompilerDirectives.shouldNotReachHere(var4);
               }
            }

            Object variablesObj;
            if (scopeParent != null) {
               variablesObj = new DebugScope.SubtractedVariables(this.scope, scopeParent);
            } else {
               variablesObj = this.scope;
            }

            String receiverName = null;
            if (this.node != null && NODE.hasReceiverMember(this.node, this.frame)) {
               receiverName = INTEROP.asString(NODE.getReceiverMember(this.node, this.frame));
            }

            this.variables = DebugValue.getProperties(variablesObj, receiverName, this.session, this.getLanguage(), this);
         }
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.session, var6, this.language);
      }

      return this.variables;
   }

   private boolean isDeclaredInScope(String name) {
      Object scopeParent = null;
      if (INTEROP.hasScopeParent(this.scope)) {
         try {
            scopeParent = INTEROP.getScopeParent(this.scope);
         } catch (UnsupportedMessageException var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      return scopeParent == null ? true : new DebugScope.SubtractedVariables(this.scope, scopeParent).isMemberReadable(name);
   }

   public DebugValue convertRawValue(Class<? extends TruffleLanguage<?>> languageClass, Object rawValue) {
      Objects.requireNonNull(languageClass);
      RootNode rootNode = this.getRoot();
      if (rootNode == null) {
         return null;
      } else if (!InteropLibrary.isValidValue(rawValue)) {
         throw new IllegalArgumentException("raw value is not an Interop value");
      } else {
         TruffleLanguage<?> truffleLanguage = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
         return truffleLanguage != null && truffleLanguage.getClass() == languageClass ? new DebugValue.HeapValue(this.session, null, rawValue) : null;
      }
   }

   LanguageInfo getLanguage() {
      return this.root != null ? this.root.getLanguageInfo() : this.language;
   }

   void verifyValidState() {
      if (this.event != null) {
         this.event.verifyValidState(false);
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class SubtractedKeys implements TruffleObject {
      private final Object allKeys;
      private final long allSize;
      private final long removedSize;

      SubtractedKeys(Object allKeys, Object removedKeys) throws UnsupportedMessageException {
         this.allKeys = allKeys;
         this.allSize = DebugScope.INTEROP.getArraySize(allKeys);
         this.removedSize = DebugScope.INTEROP.getArraySize(removedKeys);
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.allSize - this.removedSize;
      }

      @ExportMessage
      Object readArrayElement(long index) throws InvalidArrayIndexException, UnsupportedMessageException {
         if (0L <= index && index < this.getArraySize()) {
            return DebugScope.INTEROP.readArrayElement(this.allKeys, index);
         } else {
            throw InvalidArrayIndexException.create(index);
         }
      }

      @ExportMessage
      boolean isArrayElementReadable(long index) {
         return 0L <= index && index < this.getArraySize() ? DebugScope.INTEROP.isArrayElementReadable(this.allKeys, index) : false;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static class SubtractedVariables implements TruffleObject {
      private final Object allVariables;
      private final InteropLibrary allLibrary;
      private final Object removedVariables;
      private final InteropLibrary removedLibrary;

      SubtractedVariables(Object allVariables, Object removedVariables) {
         this.allVariables = allVariables;
         this.allLibrary = InteropLibrary.getUncached(allVariables);
         this.removedVariables = removedVariables;
         this.removedLibrary = InteropLibrary.getUncached(removedVariables);
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final boolean hasMembers() {
         return this.allLibrary.hasMembers(this.allVariables) && this.removedLibrary.hasMembers(this.removedVariables);
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final Object getMembers(boolean includeInternal) throws UnsupportedMessageException {
         return new DebugScope.SubtractedKeys(
            this.allLibrary.getMembers(this.allVariables, includeInternal), this.removedLibrary.getMembers(this.removedVariables, includeInternal)
         );
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final boolean isMemberReadable(String member) {
         if (!this.allLibrary.isMemberReadable(this.allVariables, member)) {
            return false;
         } else {
            return !this.removedLibrary.isMemberReadable(this.removedVariables, member) ? true : this.isAmongMembers(member);
         }
      }

      private boolean isAmongMembers(String member) {
         try {
            Object members = this.getMembers(true);
            InteropLibrary membersLibrary = InteropLibrary.getUncached(members);
            long n = membersLibrary.getArraySize(members);

            for (long i = 0L; i < n; i++) {
               String m = DebugScope.INTEROP.asString(membersLibrary.readArrayElement(members, i));
               if (member.equals(m)) {
                  return true;
               }
            }

            return false;
         } catch (InvalidArrayIndexException | UnsupportedMessageException var9) {
            throw CompilerDirectives.shouldNotReachHere(var9);
         }
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final Object readMember(String member) throws UnknownIdentifierException, UnsupportedMessageException {
         if (this.isMemberReadable(member)) {
            return this.allLibrary.readMember(this.allVariables, member);
         } else {
            throw UnknownIdentifierException.create(member);
         }
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final boolean isMemberModifiable(String member) {
         if (!this.allLibrary.isMemberModifiable(this.allVariables, member)) {
            return false;
         } else {
            return !this.removedLibrary.isMemberModifiable(this.removedVariables, member) ? true : this.isAmongMembers(member);
         }
      }

      @ExportMessage
      final boolean isMemberInsertable(String member) {
         return false;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      final void writeMember(String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
         if (this.isMemberModifiable(member)) {
            this.allLibrary.writeMember(this.allVariables, member, value);
         } else {
            throw UnknownIdentifierException.create(member);
         }
      }

      @ExportMessage
      final boolean hasMemberReadSideEffects(String member) {
         return this.isMemberReadable(member) && this.allLibrary.hasMemberReadSideEffects(this.allVariables, member);
      }

      @ExportMessage
      final boolean hasMemberWriteSideEffects(String member) {
         return this.isMemberModifiable(member) && this.allLibrary.hasMemberWriteSideEffects(this.allVariables, member);
      }
   }
}
