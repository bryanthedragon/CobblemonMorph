package com.oracle.truffle.api.object;

import com.oracle.truffle.api.nodes.SlowPathException;

@Deprecated(since = "22.2")
public final class FinalLocationException extends SlowPathException {
   private static final long serialVersionUID = -30188494510914293L;
   private static final FinalLocationException INSTANCE = new FinalLocationException();

   private FinalLocationException() {
   }

   static FinalLocationException instance() {
      return INSTANCE;
   }
}
