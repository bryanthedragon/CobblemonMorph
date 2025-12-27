package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;

@ExportLibrary(value = ReflectionLibrary.class, receiverType = Object.class)
final class ReflectionLibraryDefault {
   static final int LIMIT = 8;

   @ExportMessage
   static class Send {
      @Specialization(guards = {"message == cachedMessage", "cachedLibrary.accepts(receiver)"}, limit = "LIMIT")
      static Object doSendCached(
         Object receiver,
         Message message,
         Object[] args,
         @Cached("message") Message cachedMessage,
         @Cached("createLibrary(message, receiver)") Library cachedLibrary
      ) throws Exception {
         return cachedMessage.getFactory().genericDispatch(cachedLibrary, receiver, cachedMessage, args, 0);
      }

      static Library createLibrary(Message message, Object receiver) {
         return message.getFactory().create(receiver);
      }

      @Specialization(replaces = "doSendCached")
      @CompilerDirectives.TruffleBoundary
      static Object doSendGeneric(Object receiver, Message message, Object[] args) throws Exception {
         LibraryFactory<?> lib = message.getFactory();
         return lib.genericDispatch(lib.getUncached(receiver), receiver, message, args, 0);
      }
   }
}
