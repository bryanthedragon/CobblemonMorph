
package com.oracle.truffle.api.impl.asm;

import com.oracle.truffle.api.impl.asm.Frame;
import com.oracle.truffle.api.impl.asm.Label;
import com.oracle.truffle.api.impl.asm.Symbol;
import com.oracle.truffle.api.impl.asm.SymbolTable;

final class CurrentFrame
extends Frame {
    CurrentFrame(Label owner) {
        super(owner);
    }

    void execute(int opcode, int arg, Symbol symbolArg, SymbolTable symbolTable) {
        super.execute(opcode, arg, symbolArg, symbolTable);
        Frame successor = new Frame(null);
        this.merge(symbolTable, successor, 0);
        this.copyFrom(successor);
    }
}

