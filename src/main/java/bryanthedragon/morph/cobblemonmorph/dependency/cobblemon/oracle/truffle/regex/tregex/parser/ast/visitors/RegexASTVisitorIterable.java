package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;

public interface RegexASTVisitorIterable {
   boolean visitorHasNext();

   RegexASTNode visitorGetNext(boolean reverse);

   void resetVisitorIterator();
}
