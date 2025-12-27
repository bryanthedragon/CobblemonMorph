package com.oracle.truffle.js.runtime.objects;

public interface JSCopyableObject {
   default JSObject copy() {
      JSObject thisObj = (JSObject)this;
      return JSObjectUtil.copyProperties(thisObj.copyWithoutProperties(thisObj.getShape().getRoot()), thisObj);
   }
}
