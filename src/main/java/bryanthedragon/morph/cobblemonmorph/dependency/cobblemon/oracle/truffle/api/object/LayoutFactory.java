package com.oracle.truffle.api.object;

@Deprecated(since = "22.2")
public interface LayoutFactory {
   @Deprecated(since = "22.2")
   default Layout createLayout(Layout.Builder layoutBuilder) {
      throw new UnsupportedOperationException();
   }

   @Deprecated(since = "22.2")
   Property createProperty(Object id, Location location);

   @Deprecated(since = "22.2")
   Property createProperty(Object id, Location location, int flags);

   default Shape createShape(Object arg0) {
      throw new UnsupportedOperationException();
   }

   int getPriority();
}
