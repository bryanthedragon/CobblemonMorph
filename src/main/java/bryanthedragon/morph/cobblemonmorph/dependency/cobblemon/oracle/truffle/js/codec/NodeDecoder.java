
package com.oracle.truffle.js.codec;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.codec.BinaryDecoder;
import java.nio.ByteBuffer;

public interface NodeDecoder<F> {
    default public Class<?>[] getClasses() {
        return new Class[0];
    }

    public Object decodeNode(DecoderState var1, F var2);

    public int getMethodIdFromSignature(String var1);

    public int getChecksum();

    public static class DecoderState {
        private final BinaryDecoder decoder;
        private final Object[] objRegs;
        private final Object[] arguments;

        public DecoderState(BinaryDecoder decoder, Object[] arguments) {
            this.decoder = decoder;
            int nRegs = this.getInt32();
            this.objRegs = new Object[nRegs];
            this.arguments = arguments;
        }

        public DecoderState(BinaryDecoder decoder) {
            this(decoder, new Object[0]);
        }

        public Object getObjReg(int index) {
            return this.objRegs[index];
        }

        public void setObjReg(int index, Object value2) {
            this.objRegs[index] = value2;
        }

        public Object getObject() {
            return this.getObjReg(this.getReg());
        }

        public int getInt() {
            return this.decoder.getInt();
        }

        public int getUInt() {
            return this.decoder.getUInt();
        }

        public long getLong() {
            return this.decoder.getLong();
        }

        public boolean getBoolean() {
            return this.decoder.getInt() != 0;
        }

        public double getDouble() {
            return this.decoder.getDouble();
        }

        public TruffleString getString() {
            return this.decoder.getString();
        }

        public boolean hasRemaining() {
            return this.decoder.hasRemaining();
        }

        public int getInt32() {
            return this.decoder.getInt32();
        }

        public int getReg() {
            return this.decoder.getUInt();
        }

        public int getBytecode() {
            return this.decoder.getUInt();
        }

        public ByteBuffer getBuffer() {
            return this.decoder.getBuffer();
        }

        public Object getArgument(int index) {
            return this.arguments[index];
        }
    }
}

