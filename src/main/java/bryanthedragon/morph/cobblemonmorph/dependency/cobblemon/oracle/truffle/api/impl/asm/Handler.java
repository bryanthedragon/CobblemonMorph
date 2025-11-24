
package com.oracle.truffle.api.impl.asm;

import com.oracle.truffle.api.impl.asm.ByteVector;
import com.oracle.truffle.api.impl.asm.Label;

final class Handler {
    final Label startPc;
    final Label endPc;
    final Label handlerPc;
    final int catchType;
    final String catchTypeDescriptor;
    Handler nextHandler;

    Handler(Label startPc, Label endPc, Label handlerPc, int catchType, String catchTypeDescriptor) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
        this.catchTypeDescriptor = catchTypeDescriptor;
    }

    Handler(Handler handler, Label startPc, Label endPc) {
        this(startPc, endPc, handler.handlerPc, handler.catchType, handler.catchTypeDescriptor);
        this.nextHandler = handler.nextHandler;
    }

    static Handler removeRange(Handler firstHandler, Label start2, Label end2) {
        int rangeEnd;
        if (firstHandler == null) {
            return null;
        }
        firstHandler.nextHandler = Handler.removeRange(firstHandler.nextHandler, start2, end2);
        int handlerStart = firstHandler.startPc.bytecodeOffset;
        int handlerEnd = firstHandler.endPc.bytecodeOffset;
        int rangeStart = start2.bytecodeOffset;
        int n = rangeEnd = end2 == null ? Integer.MAX_VALUE : end2.bytecodeOffset;
        if (rangeStart >= handlerEnd || rangeEnd <= handlerStart) {
            return firstHandler;
        }
        if (rangeStart <= handlerStart) {
            if (rangeEnd >= handlerEnd) {
                return firstHandler.nextHandler;
            }
            return new Handler(firstHandler, end2, firstHandler.endPc);
        }
        if (rangeEnd >= handlerEnd) {
            return new Handler(firstHandler, firstHandler.startPc, start2);
        }
        firstHandler.nextHandler = new Handler(firstHandler, end2, firstHandler.endPc);
        return new Handler(firstHandler, firstHandler.startPc, start2);
    }

    static int getExceptionTableLength(Handler firstHandler) {
        int length = 0;
        Handler handler = firstHandler;
        while (handler != null) {
            ++length;
            handler = handler.nextHandler;
        }
        return length;
    }

    static int getExceptionTableSize(Handler firstHandler) {
        return 2 + 8 * Handler.getExceptionTableLength(firstHandler);
    }

    static void putExceptionTable(Handler firstHandler, ByteVector output) {
        output.putShort(Handler.getExceptionTableLength(firstHandler));
        Handler handler = firstHandler;
        while (handler != null) {
            output.putShort(handler.startPc.bytecodeOffset).putShort(handler.endPc.bytecodeOffset).putShort(handler.handlerPc.bytecodeOffset).putShort(handler.catchType);
            handler = handler.nextHandler;
        }
    }
}

