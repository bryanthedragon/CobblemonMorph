
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexBodyNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.string.Encodings;

public abstract class RegexExecNode
extends RegexBodyNode {
    private final boolean mustCheckUTF16Surrogates;
    @Node.Child
    private InputLengthNode lengthNode;
    @Node.Child
    private InputReadNode charAtNode;

    public RegexExecNode(RegexLanguage language, RegexSource source, boolean mustCheckUTF16Surrogates) {
        super(language, source);
        this.mustCheckUTF16Surrogates = this.getEncoding() == Encodings.UTF_16 && mustCheckUTF16Surrogates;
    }

    @Override
    public final RegexResult execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        assert (args.length == 2);
        return this.adjustIndexAndRun(frame, args[0], (Integer)args[1]);
    }

    private int adjustFromIndex(int fromIndex, Object input) {
        if (this.mustCheckUTF16Surrogates && fromIndex > 0 && fromIndex < this.inputLength(input)) {
            assert (this.getEncoding() == Encodings.UTF_16);
            if (Character.isLowSurrogate((char)this.inputRead(input, fromIndex)) && Character.isHighSurrogate((char)this.inputRead(input, fromIndex - 1))) {
                return fromIndex - 1;
            }
        }
        return fromIndex;
    }

    public int inputLength(Object input) {
        if (this.lengthNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.lengthNode = this.insert(InputLengthNode.create());
        }
        return this.lengthNode.execute(input, this.getEncoding());
    }

    public int inputRead(Object input, int i) {
        if (this.charAtNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.charAtNode = this.insert(InputReadNode.create());
        }
        return this.charAtNode.execute(input, i, this.getEncoding());
    }

    private RegexResult adjustIndexAndRun(VirtualFrame frame, Object input, int fromIndex) {
        if (fromIndex < 0 || fromIndex > this.inputLength(input)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException(String.format("got illegal fromIndex value: %d. fromIndex must be >= 0 and <= input length (%d)", fromIndex, this.inputLength(input)));
        }
        return this.execute(frame, input, this.adjustFromIndex(fromIndex, input));
    }

    public boolean isBacktracking() {
        return false;
    }

    protected abstract RegexResult execute(VirtualFrame var1, Object var2, int var3);
}

