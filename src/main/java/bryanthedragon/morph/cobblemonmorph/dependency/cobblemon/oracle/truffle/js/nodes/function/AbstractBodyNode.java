package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;

public abstract class AbstractBodyNode extends JavaScriptNode {
   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == StandardTags.RootTag.class ? true : super.hasTag(tag);
   }
}
