package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

@GenerateLibrary(assertions = NodeLibrary.Asserts.class, receiverType = Node.class)
@GenerateLibrary.DefaultExport(DefaultNodeExports.class)
public abstract class NodeLibrary extends Library {
   static final LibraryFactory<NodeLibrary> FACTORY = LibraryFactory.resolve(NodeLibrary.class);

   protected NodeLibrary() {
   }

   @GenerateLibrary.Abstract(ifExported = "getScope")
   public boolean hasScope(Object node, Frame frame) {
      return false;
   }

   @GenerateLibrary.Abstract(ifExported = {"hasScope", "getReceiverMember"})
   public Object getScope(Object node, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @GenerateLibrary.Abstract(ifExported = "getReceiverMember")
   public boolean hasReceiverMember(Object node, Frame frame) {
      return false;
   }

   @GenerateLibrary.Abstract(ifExported = "hasReceiverMember")
   public Object getReceiverMember(Object node, Frame frame) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   @GenerateLibrary.Abstract(ifExported = "getRootInstance")
   public boolean hasRootInstance(Object node, Frame frame) {
      return false;
   }

   @GenerateLibrary.Abstract(ifExported = "hasRootInstance")
   public Object getRootInstance(Object node, Frame frame) throws UnsupportedMessageException {
      throw UnsupportedMessageException.create();
   }

   public Object getView(Object node, Frame frame, Object value) {
      return value;
   }

   public static LibraryFactory<NodeLibrary> getFactory() {
      return FACTORY;
   }

   public static NodeLibrary getUncached() {
      return getFactory().getUncached();
   }

   public static NodeLibrary getUncached(Object node) {
      return getFactory().getUncached(node);
   }

   static class Asserts extends NodeLibrary {
      @Node.Child
      private NodeLibrary delegate;
      private final boolean isDefaultDelegate;

      Asserts(NodeLibrary delegate) {
         this.delegate = delegate;
         this.isDefaultDelegate = delegate.getClass().getName().startsWith(DefaultNodeExports.class.getName());
      }

      @Override
      public boolean accepts(Object receiver) {
         assert receiver instanceof Node;

         return this.delegate.accepts(receiver);
      }

      @Override
      public boolean hasScope(Object node, Frame frame) {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.hasScope(node, frame);
         } else {
            assert this.validReceiver(node);

            boolean result = this.delegate.hasScope(node, frame);
            if (result) {
               try {
                  assert AssertUtils.validScope(this.delegate.getScope(node, frame, true));
               } catch (InteropException var6) {
                  assert false : AssertUtils.violationInvariant(node);
               } catch (Exception var7) {
               }
            } else {
               try {
                  this.delegate.getScope(node, frame, true);

                  assert false : AssertUtils.violationInvariant(node);
               } catch (UnsupportedMessageException var5) {
               }
            }

            return result;
         }
      }

      @Override
      public Object getScope(Object node, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.getScope(node, frame, nodeEnter);
         } else {
            assert this.validReceiver(node);

            boolean hadScope = this.delegate.hasScope(node, frame);

            try {
               Object scope = this.delegate.getScope(node, frame, nodeEnter);

               assert hadScope : AssertUtils.violationInvariant(node);

               assert AssertUtils.validScope(scope);

               return scope;
            } catch (InteropException var6) {
               assert var6 instanceof UnsupportedMessageException : AssertUtils.violationInvariant(node);

               assert !hadScope : AssertUtils.violationInvariant(node);

               throw var6;
            }
         }
      }

      @Override
      public boolean hasReceiverMember(Object node, Frame frame) {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.hasReceiverMember(node, frame);
         } else {
            assert this.validReceiver(node);

            boolean result = this.delegate.hasReceiverMember(node, frame);
            if (result) {
               try {
                  assert AssertUtils.assertString(node, this.delegate.getReceiverMember(node, frame));
               } catch (ClassCastException | InteropException var6) {
                  assert false : AssertUtils.violationInvariant(node);
               } catch (Exception var7) {
               }
            } else {
               try {
                  this.delegate.getReceiverMember(node, frame);

                  assert false : AssertUtils.violationInvariant(node);
               } catch (UnsupportedMessageException var5) {
               }
            }

            return result;
         }
      }

      @Override
      public Object getReceiverMember(Object node, Frame frame) throws UnsupportedMessageException {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.getReceiverMember(node, frame);
         } else {
            boolean hadReceiver = this.delegate.hasReceiverMember(node, frame);

            try {
               Object name = this.delegate.getReceiverMember(node, frame);

               assert hadReceiver : AssertUtils.violationInvariant(node);

               assert AssertUtils.assertString(node, name);

               return name;
            } catch (InteropException var5) {
               assert var5 instanceof UnsupportedMessageException : AssertUtils.violationInvariant(node);

               assert !hadReceiver : AssertUtils.violationInvariant(node);

               throw var5;
            }
         }
      }

      @Override
      public boolean hasRootInstance(Object node, Frame frame) {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.hasRootInstance(node, frame);
         } else {
            assert this.validReceiver(node);

            boolean result = this.delegate.hasRootInstance(node, frame);
            if (result) {
               try {
                  Object instance = this.delegate.getRootInstance(node, frame);
                  assertValidRootInstance(instance);
               } catch (InteropException var6) {
                  assert false : AssertUtils.violationInvariant(node);
               } catch (Exception var7) {
               }
            } else {
               try {
                  this.delegate.getRootInstance(node, frame);

                  assert false : AssertUtils.violationInvariant(node);
               } catch (UnsupportedMessageException var5) {
               }
            }

            return result;
         }
      }

      @Override
      public Object getRootInstance(Object node, Frame frame) throws UnsupportedMessageException {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.getRootInstance(node, frame);
         } else {
            assert this.validReceiver(node);

            boolean hadRootinstance = this.delegate.hasRootInstance(node, frame);

            try {
               Object instance = this.delegate.getRootInstance(node, frame);

               assert hadRootinstance : AssertUtils.violationInvariant(node);

               assertValidRootInstance(instance);
               return instance;
            } catch (InteropException var5) {
               assert var5 instanceof UnsupportedMessageException : AssertUtils.violationInvariant(node);

               assert !hadRootinstance : AssertUtils.violationInvariant(node);

               throw var5;
            }
         }
      }

      private static void assertValidRootInstance(Object instance) {
         assert InteropLibrary.isValidValue(instance) : AssertUtils.violationInvariant(instance);

         assert InteropLibrary.getUncached().isExecutable(instance) : String.format("The root instance '%s' is not executable.", instance);
      }

      @Override
      public Object getView(Object node, Frame frame, Object value) {
         if (CompilerDirectives.inCompiledCode()) {
            return this.delegate.getView(node, frame, value);
         } else {
            assert this.validReceiver(node);

            assert InteropLibrary.isValidValue(value) : AssertUtils.violationInvariant(value);

            Class<?> languageClass = validateLocationAndFrame((Node)node, frame, value);
            Object view = this.delegate.getView(node, frame, value);

            assert InteropLibrary.isValidValue(view) : AssertUtils.violationInvariant(view);

            InteropLibrary lib = InteropLibrary.getUncached(view);

            try {
               assert lib.hasLanguage(view) && lib.getLanguage(view) == languageClass : String.format(
                  "The returned scoped view of language '%s' must return the class '%s' for InteropLibrary.getLanguage.Fix the implementation of %s.getView to resolve this.",
                  languageClass.getTypeName(),
                  languageClass.getTypeName(),
                  node.getClass().getTypeName()
               );

               return view;
            } catch (UnsupportedMessageException var8) {
               throw CompilerDirectives.shouldNotReachHere(var8);
            }
         }
      }

      private static Class<?> validateLocationAndFrame(Node location, Frame frame, Object value) {
         InteropLibrary interop = InteropLibrary.getUncached(value);

         assert interop.hasLanguage(value) : String.format("The value '%s' is not associated with any language.", value);

         Class<? extends TruffleLanguage<?>> valueLanguage;
         try {
            valueLanguage = interop.getLanguage(value);
         } catch (UnsupportedMessageException var8) {
            throw CompilerDirectives.shouldNotReachHere(var8);
         }

         RootNode rootNode = location.getRootNode();

         assert rootNode != null : String.format("The location '%s' does not have a RootNode.", location);

         LanguageInfo nodeLanguageInfo = rootNode.getLanguageInfo();

         assert nodeLanguageInfo != null : String.format("The location '%s' does not have a language associated.", location);

         Class<?> nodeLanguage = InteropAccessor.ACCESSOR
            .languageSupport()
            .getSPI(InteropAccessor.ACCESSOR.engineSupport().getEnvForInstrument(nodeLanguageInfo))
            .getClass();

         assert nodeLanguage == valueLanguage : String.format(
            "The value language '%s' must match the language of the location %s.", valueLanguage, nodeLanguage
         );

         assert frame != null : "Frame argument must not be null.";

         assert rootNode.getFrameDescriptor().equals(frame.getFrameDescriptor()) : String.format(
            "The frame provided does not originate from the location. Expected frame descriptor '%s' but was '%s'.",
            rootNode.getFrameDescriptor(),
            frame.getFrameDescriptor()
         );

         return nodeLanguage;
      }

      private boolean validReceiver(Object nodeObject) {
         if (nodeObject == null) {
            throw new NullPointerException("A non-null receiver is required.");
         } else {
            assert nodeObject instanceof Node : String.format("The node '%s' does not extend Node.", nodeObject);

            Node node = (Node)nodeObject;

            assert node.getRootNode() != null : String.format("The node '%s' does not have a RootNode.", node);

            assert InteropAccessor.ACCESSOR.instrumentSupport().isInstrumentable(node) || this.isDefaultDelegate : String.format(
               "The node '%s' is not instrumentable. Implement InstrumentableNode and return true from isInstrumentable()", node
            );

            return true;
         }
      }
   }
}
