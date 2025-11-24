
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;

public interface RegexASTVisitorIterable {
    public boolean visitorHasNext();

    public RegexASTNode visitorGetNext(boolean var1);

    public void resetVisitorIterator();
}

