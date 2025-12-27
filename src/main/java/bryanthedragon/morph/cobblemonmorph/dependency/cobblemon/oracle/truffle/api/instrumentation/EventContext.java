package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class EventContext {
   private final ProbeNode probeNode;
   private final SourceSection sourceSection;
   @CompilerDirectives.CompilationFinal
   private volatile Object nodeObject;

   EventContext(ProbeNode probeNode, SourceSection sourceSection) {
      this.sourceSection = sourceSection;
      this.probeNode = probeNode;
   }

   boolean validEventContextOnWrapperInsert() {
      Node node = this.getInstrumentedNode();
      if (node instanceof RootNode) {
         throw new IllegalStateException("Instrumentable node must not be a root node.");
      } else {
         boolean foundStandardTag = false;

         for (Class<?> clazz : StandardTags.ALL_TAGS) {
            if (this.hasTag((Class<? extends Tag>)clazz)) {
               assert languageDeclaresTag(this.probeNode.getRootNode(), clazz);

               foundStandardTag = true;
            }
         }

         if (foundStandardTag) {
            RootNode root = this.probeNode.getRootNode();

            assert root == null || root.getSourceSection() == null || this.sourceSection != null : "All nodes tagged with a standard tag and with a root node that has a source section must also have a source section.";
         }

         return true;
      }
   }

   boolean validEventContextOnLazyUpdate() {
      Node node = this.getInstrumentedNode();
      Object object = ((InstrumentableNode)node).getNodeObject();

      assert object == null || this.isValidNodeObject(object);

      return true;
   }

   private static boolean languageDeclaresTag(RootNode root, Class<?> tag) {
      InstrumentationHandler handler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(root);
      Set<Class<?>> providedTags = handler.getProvidedTags(root);
      if (!providedTags.contains(tag)) {
         TruffleLanguage<?> language = InstrumentAccessor.nodesAccess().getLanguage(root);
         throw new AssertionError(
            "An instrumentable node returned true for a tag that was not provided by the language '"
               + root.getLanguageInfo().getId()
               + "'.\nAdd @ProvidedTags with tag  "
               + tag
               + " to "
               + language.getClass().getName()
               + "."
         );
      } else {
         return true;
      }
   }

   ProbeNode getProbeNode() {
      return this.probeNode;
   }

   public boolean hasTag(Class<? extends Tag> tag) {
      if (tag == null) {
         CompilerDirectives.transferToInterpreter();
         throw new NullPointerException();
      } else {
         Node node = this.getInstrumentedNode();
         if (node instanceof InstrumentableNode) {
            boolean has = ((InstrumentableNode)node).hasTag(tag);

            assert !has || languageDeclaresTag(this.probeNode.getRootNode(), tag);

            return has;
         } else {
            return false;
         }
      }
   }

   public Object getNodeObject() {
      Object object = this.nodeObject;
      if (object == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Node node = this.getInstrumentedNode();
         if (!(node instanceof InstrumentableNode)) {
            return null;
         }

         object = ((InstrumentableNode)node).getNodeObject();
         if (object == null) {
            object = InstrumentAccessor.interopAccess().createDefaultNodeObject(node);
         } else {
            assert this.isValidNodeObject(object);
         }

         this.nodeObject = object;
      }

      return object;
   }

   public SourceSection getInstrumentedSourceSection() {
      return this.sourceSection;
   }

   public Node getInstrumentedNode() {
      InstrumentableNode.WrapperNode wrapper = this.probeNode.findWrapper();
      return wrapper != null ? wrapper.getDelegateNode() : null;
   }

   public boolean isLanguageContextInitialized() {
      CompilerAsserts.neverPartOfCompilation();
      Node node = this.getInstrumentedNode();
      if (node == null) {
         return true;
      } else {
         RootNode root = node.getRootNode();
         if (root == null) {
            return true;
         } else {
            LanguageInfo languageInfo = root.getLanguageInfo();
            TruffleLanguage.Env env = InstrumentAccessor.engineAccess().getEnvForInstrument(languageInfo);
            return InstrumentAccessor.langAccess().isContextInitialized(env);
         }
      }
   }

   public ExecutionEventNode lookupExecutionEventNode(EventBinding<? extends ExecutionEventNodeFactory> binding) {
      if (!(binding.getElement() instanceof ExecutionEventNodeFactory)) {
         throw new IllegalArgumentException(String.format("Binding is not a subtype of %s.", ExecutionEventNodeFactory.class.getSimpleName()));
      } else {
         return this.probeNode.lookupExecutionEventNode(binding);
      }
   }

   public Iterator<ExecutionEventNode> lookupExecutionEventNodes(Collection<EventBinding<? extends ExecutionEventNodeFactory>> bindings) {
      return this.probeNode.lookupExecutionEventNodes(bindings);
   }

   public ThreadDeath createUnwind(Object info) {
      return this.createUnwind(info, null);
   }

   public ThreadDeath createUnwind(Object info, EventBinding<?> unwindBinding) {
      return new UnwindException(info, unwindBinding);
   }

   @Deprecated(since = "21.3")
   public RuntimeException createError(RuntimeException e) {
      return e;
   }

   @Override
   public String toString() {
      return "EventContext[source=" + this.getInstrumentedSourceSection() + "]";
   }

   private boolean isValidNodeObject(Object obj) {
      CompilerAsserts.neverPartOfCompilation();
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
      if (!interop.hasMembers(obj)) {
         throw new AssertionError("Invalid node object: must return true for the hasMembers message.");
      } else {
         Object members;
         try {
            members = interop.getMembers(obj);
         } catch (UnsupportedMessageException var17) {
            throw new AssertionError("Invalid node object: must support the getMembers message.", var17);
         }

         InteropLibrary membersInterop = InteropLibrary.getFactory().getUncached(members);
         if (!membersInterop.hasArrayElements(members)) {
            throw new AssertionError("Invalid node object: the returned members object must support hasArrayElements.");
         } else {
            long size;
            try {
               size = membersInterop.getArraySize(members);
            } catch (UnsupportedMessageException var16) {
               throw new AssertionError("Invalid node object: the returned members object must have a size.");
            }

            for (long i = 0L; i < size; i++) {
               Object key;
               try {
                  key = membersInterop.readArrayElement(members, i);
               } catch (UnsupportedMessageException | InvalidArrayIndexException var15) {
                  throw new AssertionError("Invalid node object: the returned members object must be readable at number index " + i);
               }

               InteropLibrary keyInterop = InteropLibrary.getFactory().getUncached(key);
               if (!keyInterop.isString(key)) {
                  throw new AssertionError(
                     "Invalid node object: the returned member must return a string at index " + i + ". But was " + key.getClass().getName() + "."
                  );
               }

               String member;
               try {
                  member = keyInterop.asString(key);
               } catch (UnsupportedMessageException var14) {
                  throw new AssertionError("Invalid node object: the returned member must return a string  ");
               }

               try {
                  interop.readMember(obj, member);
               } catch (UnsupportedMessageException | UnknownIdentifierException var13) {
                  throw new AssertionError("Invalid node object: the returned member must be readable with identifier " + member);
               }

               if (interop.isMemberWritable(obj, member)) {
                  throw new AssertionError("Invalid node object: The member " + member + " is marked as writable but node objects must not be writable.");
               }
            }

            if (interop.hasArrayElements(obj)) {
               throw new AssertionError("Invalid node object: the node object must not return true for hasArrayElements.");
            } else {
               return this.isValidTaggedNodeObject(obj);
            }
         }
      }
   }

   private boolean isValidTaggedNodeObject(Object obj) {
      if (this.hasTag(StandardTags.ReadVariableTag.class)) {
         isValidVarsNodeObject(obj, "readVariableName");
      }

      if (this.hasTag(StandardTags.WriteVariableTag.class)) {
         isValidVarsNodeObject(obj, "writeVariableName");
      }

      return true;
   }

   private static void isValidVarsNodeObject(Object obj, String varNameProperty) {
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(obj);
      if (!interop.isMemberReadable(obj, varNameProperty)) {
         throw new AssertionError("Invalid node object " + obj + ", does not have " + varNameProperty + " member.");
      } else {
         Object varName;
         try {
            varName = interop.readMember(obj, varNameProperty);
         } catch (UnknownIdentifierException | UnsupportedMessageException var12) {
            throw new AssertionError("Invalid node object " + obj + ", can not read " + varNameProperty + " member.", var12);
         }

         if (!(varName instanceof String)) {
            interop = InteropLibrary.getFactory().getUncached(varName);
            if (interop.hasArrayElements(varName)) {
               long size;
               try {
                  size = interop.getArraySize(varName);
               } catch (UnsupportedMessageException var11) {
                  throw new AssertionError("Invalid node object: the returned variable name object must have a size when it's an array.");
               }

               for (long i = 0L; i < size; i++) {
                  Object var;
                  try {
                     var = interop.readArrayElement(varName, i);
                  } catch (UnsupportedMessageException | InvalidArrayIndexException var10) {
                     throw new AssertionError("Invalid node object: the returned variable name object must be readable at number index " + i);
                  }

                  isValidVarObject(var);
               }
            } else {
               isValidVarObject(varName);
            }
         }
      }
   }

   private static void isValidVarObject(Object var) {
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(var);
      if (!interop.isString(var)) {
         throw new AssertionError("Invalid variable object " + var + ", must be interop String.");
      } else {
         try {
            interop.asString(var);
         } catch (UnsupportedMessageException var4) {
            throw new AssertionError("Invalid variable object " + var + ", must be interop String.", var4);
         }

         boolean hasLocation = interop.hasSourceLocation(var);

         try {
            interop.getSourceLocation(var);

            assert hasLocation : String.format("Invalid variable object %s, provides source location that should not have.", var);
         } catch (UnsupportedMessageException var5) {
            assert !hasLocation : String.format("Invalid variable object %s, missing source location.", var);
         }
      }
   }
}
