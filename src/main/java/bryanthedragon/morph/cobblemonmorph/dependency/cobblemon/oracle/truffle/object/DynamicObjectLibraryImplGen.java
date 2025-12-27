package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DefaultExportProvider;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DynamicObjectLibraryImpl.class)
public final class DynamicObjectLibraryImplGen {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

   private DynamicObjectLibraryImplGen() {
   }

   static {
      LibraryExport.register(DynamicObjectLibraryImpl.class, new DynamicObjectLibraryImplGen.DynamicObjectLibraryExports());
   }

   @GeneratedBy(DynamicObjectLibraryImpl.class)
   private static final class DynamicObjectLibraryExports extends LibraryExport<DynamicObjectLibrary> {
      private DynamicObjectLibraryExports() {
         super(DynamicObjectLibrary.class, DynamicObject.class, false, false, 0);
      }

      protected DynamicObjectLibrary createUncached(Object receiver) {
         assert receiver instanceof DynamicObject;

         DynamicObjectLibrary uncached = new DynamicObjectLibraryImplGen.DynamicObjectLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected DynamicObjectLibrary createCached(Object receiver) {
         assert receiver instanceof DynamicObject;

         return new DynamicObjectLibraryImplGen.DynamicObjectLibraryExports.Cached(receiver);
      }

      @GeneratedBy(DynamicObjectLibraryImpl.class)
      private static final class Cached extends DynamicObjectLibrary {
         private final Class<? extends DynamicObject> receiverClass_;
         @Node.Child
         private DynamicObjectLibrary fallback_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private Shape cachedShape;
         @Node.Child
         private DynamicObjectLibraryImpl.KeyCacheNode keyCache;
         @Node.Child
         private DynamicObjectLibraryImpl.SetDynamicTypeNode setDynamicTypeNode__setDynamicType_setCache_;
         @Node.Child
         private DynamicObjectLibraryImpl.SetFlagsNode setShapeFlagsNode__setShapeFlags_setCache_;
         @Node.Child
         private DynamicObjectLibraryImpl.MakeSharedNode markSharedNode__markShared_setCache_;
         @Node.Child
         private DynamicObjectLibraryImpl.ResetShapeNode resetShapeNode__resetShape_setCache_;

         protected Cached(Object receiver) {
            DynamicObject castReceiver = (DynamicObject)receiver;
            this.cachedShape = castReceiver.getShape();
            this.receiverClass_ = (Class<? extends DynamicObject>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            return CompilerDirectives.isExact(receiver, this.receiverClass_) && this.accepts_(receiver);
         }

         private DynamicObjectLibrary getFallback_(DynamicObject receiver) {
            DynamicObjectLibrary localFallback = this.fallback_;
            if (localFallback == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.fallback_ = localFallback = this.insert(DynamicObjectLibraryImplGen.DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
            }

            return localFallback;
         }

         private boolean accepts_(Object arg0Value_) {
            DynamicObject arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
            return DynamicObjectLibraryImpl.accepts(arg0Value, this.cachedShape);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         @Override
         public Shape getShape(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.getShape(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).getShape(arg0Value);
            }
         }

         @Override
         public Object getOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 1) != 0) {
                  return DynamicObjectLibraryImpl.getOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.getOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).getOrDefault(arg0Value, arg1Value, arg2Value);
            }
         }

         private Object getOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.getOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public int getIntOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 2) != 0) {
                  return DynamicObjectLibraryImpl.getIntOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.getIntOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).getIntOrDefault(arg0Value, arg1Value, arg2Value);
            }
         }

         private int getIntOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.getIntOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public double getDoubleOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 4) != 0) {
                  return DynamicObjectLibraryImpl.getDoubleOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.getDoubleOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).getDoubleOrDefault(arg0Value, arg1Value, arg2Value);
            }
         }

         private double getDoubleOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            double var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var12;
               this.state_0_ = var12 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.getDoubleOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public long getLongOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 8) != 0) {
                  return DynamicObjectLibraryImpl.getLongOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.getLongOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).getLongOrDefault(arg0Value, arg1Value, arg2Value);
            }
         }

         private long getLongOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            long var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var12;
               this.state_0_ = var12 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.getLongOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean containsKey(DynamicObject arg0Value, Object arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 16) != 0) {
                  return DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.containsKeyNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).containsKey(arg0Value, arg1Value);
            }
         }

         private boolean containsKeyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var10;
               this.state_0_ = var10 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public void put(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 32) != 0) {
                  DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               this.getFallback_(arg0Value).put(arg0Value, arg1Value, arg2Value);
            }
         }

         private void putNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void putInt(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 64) != 0) {
                  DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               this.getFallback_(arg0Value).putInt(arg0Value, arg1Value, arg2Value);
            }
         }

         private void putIntNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var10;
               this.state_0_ = var10 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void putLong(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 128) != 0) {
                  DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               this.getFallback_(arg0Value).putLong(arg0Value, arg1Value, arg2Value);
            }
         }

         private void putLongNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void putDouble(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 256) != 0) {
                  DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               this.getFallback_(arg0Value).putDouble(arg0Value, arg1Value, arg2Value);
            }
         }

         private void putDoubleNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean putIfPresent(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 512) != 0) {
                  return DynamicObjectLibraryImpl.putIfPresent(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.putIfPresentNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).putIfPresent(arg0Value, arg1Value, arg2Value);
            }
         }

         private boolean putIfPresentNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.putIfPresent(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public void putWithFlags(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 1024) != 0) {
                  DynamicObjectLibraryImpl.putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putWithFlagsNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
               }
            } else {
               this.getFallback_(arg0Value).putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void putWithFlagsNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public void putConstant(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 2048) != 0) {
                  DynamicObjectLibraryImpl.putConstant(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.putConstantNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
               }
            } else {
               this.getFallback_(arg0Value).putConstant(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         private void putConstantNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.putConstant(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public Property getProperty(DynamicObject arg0Value, Object arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 4096) != 0) {
                  return DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.getPropertyNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).getProperty(arg0Value, arg1Value);
            }
         }

         private Property getPropertyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Property var6;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var10;
               this.state_0_ = var10 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean setPropertyFlags(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 8192) != 0) {
                  return DynamicObjectLibraryImpl.setPropertyFlags(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.setPropertyFlagsNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
               }
            } else {
               return this.getFallback_(arg0Value).setPropertyFlags(arg0Value, arg1Value, arg2Value);
            }
         }

         private boolean setPropertyFlagsNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var11;
               this.state_0_ = var11 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               var7 = DynamicObjectLibraryImpl.setPropertyFlags(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean removeKey(DynamicObject arg0Value, Object arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 16384) != 0) {
                  return DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.removeKeyNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).removeKey(arg0Value, arg1Value);
            }
         }

         private boolean removeKeyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.keyCache = super.insert(
                  this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache
               );
               int var10;
               this.state_0_ = var10 = state_0 | 16384;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object getDynamicType(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.getDynamicType(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).getDynamicType(arg0Value);
            }
         }

         @Override
         public boolean setDynamicType(DynamicObject arg0Value, Object arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 32768) != 0) {
                  return DynamicObjectLibraryImpl.setDynamicType(arg0Value, arg1Value, this.cachedShape, this.setDynamicTypeNode__setDynamicType_setCache_);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.setDynamicTypeNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).setDynamicType(arg0Value, arg1Value);
            }
         }

         private boolean setDynamicTypeNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.setDynamicTypeNode__setDynamicType_setCache_ = super.insert(DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.create());
               int var10;
               this.state_0_ = var10 = state_0 | 32768;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.setDynamicType(arg0Value, arg1Value, this.cachedShape, this.setDynamicTypeNode__setDynamicType_setCache_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public int getShapeFlags(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.getShapeFlags(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).getShapeFlags(arg0Value);
            }
         }

         @Override
         public boolean setShapeFlags(DynamicObject arg0Value, int arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 65536) != 0) {
                  return DynamicObjectLibraryImpl.setShapeFlags(arg0Value, arg1Value, this.cachedShape, this.setShapeFlagsNode__setShapeFlags_setCache_);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.setShapeFlagsNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).setShapeFlags(arg0Value, arg1Value);
            }
         }

         private boolean setShapeFlagsNode_AndSpecialize(DynamicObject arg0Value, int arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.setShapeFlagsNode__setShapeFlags_setCache_ = super.insert(DynamicObjectLibraryImplFactory.SetFlagsNodeGen.create());
               int var10;
               this.state_0_ = var10 = state_0 | 65536;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.setShapeFlags(arg0Value, arg1Value, this.cachedShape, this.setShapeFlagsNode__setShapeFlags_setCache_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean isShared(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.isShared(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).isShared(arg0Value);
            }
         }

         @Override
         public void markShared(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 131072) != 0) {
                  DynamicObjectLibraryImpl.markShared(arg0Value, this.cachedShape, this.markSharedNode__markShared_setCache_);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.markSharedNode_AndSpecialize(arg0Value);
               }
            } else {
               this.getFallback_(arg0Value).markShared(arg0Value);
            }
         }

         private void markSharedNode_AndSpecialize(DynamicObject arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               this.markSharedNode__markShared_setCache_ = super.insert(DynamicObjectLibraryImplFactory.MakeSharedNodeGen.create());
               int var8;
               this.state_0_ = var8 = state_0 | 131072;
               lock.unlock();
               hasLock = false;
               DynamicObjectLibraryImpl.markShared(arg0Value, this.cachedShape, this.markSharedNode__markShared_setCache_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public boolean updateShape(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.updateShape(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).updateShape(arg0Value);
            }
         }

         @Override
         public boolean resetShape(DynamicObject arg0Value, Shape arg1Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               int state_0 = this.state_0_;
               if ((state_0 & 262144) != 0) {
                  return DynamicObjectLibraryImpl.resetShape(arg0Value, arg1Value, this.cachedShape, this.resetShapeNode__resetShape_setCache_);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.resetShapeNode_AndSpecialize(arg0Value, arg1Value);
               }
            } else {
               return this.getFallback_(arg0Value).resetShape(arg0Value, arg1Value);
            }
         }

         private boolean resetShapeNode_AndSpecialize(DynamicObject arg0Value, Shape arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               this.resetShapeNode__resetShape_setCache_ = super.insert(DynamicObjectLibraryImplFactory.ResetShapeNodeGen.create());
               int var10;
               this.state_0_ = var10 = state_0 | 262144;
               lock.unlock();
               hasLock = false;
               var6 = DynamicObjectLibraryImpl.resetShape(arg0Value, arg1Value, this.cachedShape, this.resetShapeNode__resetShape_setCache_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public Object[] getKeyArray(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.getKeyArray(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).getKeyArray(arg0Value);
            }
         }

         @Override
         public Property[] getPropertyArray(DynamicObject arg0Value) {
            assert CompilerDirectives.isExact(arg0Value, this.receiverClass_) : "Invalid library usage. Library does not accept given receiver.";

            if (this.accepts(arg0Value)) {
               assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

               return DynamicObjectLibraryImpl.getPropertyArray(arg0Value, this.cachedShape);
            } else {
               return this.getFallback_(arg0Value).getPropertyArray(arg0Value);
            }
         }
      }

      @GeneratedBy(DynamicObjectLibraryImpl.class)
      @DenyReplace
      private static final class Uncached extends DynamicObjectLibrary {
         private final Class<? extends DynamicObject> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends DynamicObject>)((DynamicObject)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return CompilerDirectives.isExact(receiver, this.receiverClass_) && accepts_(receiver);
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Shape getShape(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getShape(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getOrDefault(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getIntOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getIntOrDefault(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public double getDoubleOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getDoubleOrDefault(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getLongOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getLongOrDefault(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean containsKey(DynamicObject arg0Value, Object arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void put(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putInt(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putLong(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putDouble(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean putIfPresent(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.putIfPresent(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putWithFlags(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.putWithFlags(
               arg0Value, arg1Value, arg2Value, arg3Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void putConstant(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.putConstant(
               arg0Value, arg1Value, arg2Value, arg3Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property getProperty(DynamicObject arg0Value, Object arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setPropertyFlags(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.setPropertyFlags(
               arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean removeKey(DynamicObject arg0Value, Object arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getDynamicType(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getDynamicType(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setDynamicType(DynamicObject arg0Value, Object arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.setDynamicType(
               arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int getShapeFlags(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getShapeFlags(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean setShapeFlags(DynamicObject arg0Value, int arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.setShapeFlags(
               arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.SetFlagsNodeGen.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isShared(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.isShared(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void markShared(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            DynamicObjectLibraryImpl.markShared(arg0Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.MakeSharedNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean updateShape(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.updateShape(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean resetShape(DynamicObject arg0Value, Shape arg1Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.resetShape(
               arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.ResetShapeNodeGen.getUncached()
            );
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object[] getKeyArray(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getKeyArray(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Property[] getPropertyArray(DynamicObject arg0Value) {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return DynamicObjectLibraryImpl.getPropertyArray(arg0Value, arg0Value.getShape());
         }

         @CompilerDirectives.TruffleBoundary
         private static boolean accepts_(Object arg0Value_) {
            DynamicObject arg0Value = (DynamicObject)arg0Value_;
            return DynamicObjectLibraryImpl.accepts(arg0Value, arg0Value.getShape());
         }
      }
   }

   @GeneratedBy(DynamicObjectLibraryImpl.class)
   public static final class DynamicObjectLibraryProvider implements DefaultExportProvider {
      @Override
      public String getLibraryClassName() {
         return "com.oracle.truffle.api.object.DynamicObjectLibrary";
      }

      @Override
      public Class<?> getDefaultExport() {
         return DynamicObjectLibraryImpl.class;
      }

      @Override
      public Class<?> getReceiverClass() {
         return DynamicObject.class;
      }

      @Override
      public int getPriority() {
         return 10;
      }
   }
}
