package com.oracle.truffle.api.library;

import com.oracle.truffle.api.nodes.Node;

public abstract class Library extends Node {
   protected Library() {
   }

   public abstract boolean accepts(Object receiver);
}
