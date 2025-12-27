package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DefaultNodeExports.class)
final class DefaultNodeExportsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultNodeExportsGen() {
   }

   static {
      LibraryExport.register(DefaultNodeExports.class, new DefaultNodeExportsGen.NodeLibraryExports());
   }

   @GeneratedBy(DefaultNodeExports.class)
   private static final class NodeLibraryExports extends LibraryExport<NodeLibrary> {
      private NodeLibraryExports() {
         super(NodeLibrary.class, Node.class, true, false, 0);
      }

      protected NodeLibrary createUncached(Object receiver) {
         assert receiver instanceof Node;

         NodeLibrary uncached = new DefaultNodeExportsGen.NodeLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected NodeLibrary createCached(Object receiver) {
         assert receiver instanceof Node;

         return new DefaultNodeExportsGen.NodeLibraryExports.Cached(receiver);
      }

      @GeneratedBy(DefaultNodeExports.class)
      private static final class Cached extends NodeLibrary {
         private final Class<? extends Node> receiverClass_;

         protected Cached(Object receiver) {
            Node castReceiver = (Node)receiver;
            this.receiverClass_ = (Class<? extends Node>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || DefaultNodeExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public boolean hasScope(Object receiver, Frame frame) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultNodeExports.hasScope(CompilerDirectives.castExact(receiver, this.receiverClass_), frame);
         }

         @Override
         public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultNodeExports.getScope(CompilerDirectives.castExact(receiver, this.receiverClass_), frame, nodeEnter);
         }
      }

      @GeneratedBy(DefaultNodeExports.class)
      @DenyReplace
      private static final class Uncached extends NodeLibrary {
         private final Class<? extends Node> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends Node>)((Node)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || DefaultNodeExportsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean hasScope(Object receiver, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultNodeExports.hasScope((Node)receiver, frame);
         }

         @Override
         public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return DefaultNodeExports.getScope((Node)receiver, frame, nodeEnter);
         }
      }
   }
}
