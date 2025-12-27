package com.oracle.truffle.api.impl;

final class DefaultTVMCI extends TVMCI {
   @Override
   protected Accessor.RuntimeSupport createRuntimeSupport(Object permission) {
      return new DefaultRuntimeAccessor.DefaultRuntimeSupport(permission);
   }
}
