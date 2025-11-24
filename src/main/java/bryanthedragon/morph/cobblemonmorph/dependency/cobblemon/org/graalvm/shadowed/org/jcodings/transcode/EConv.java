
package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.Encoding;
import org.graalvm.shadowed.org.jcodings.Ptr;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.specific.UTF32BEEncoding;
import org.graalvm.shadowed.org.jcodings.transcode.Buffer;
import org.graalvm.shadowed.org.jcodings.transcode.EConvFlags;
import org.graalvm.shadowed.org.jcodings.transcode.EConvResult;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;
import org.graalvm.shadowed.org.jcodings.transcode.TranscoderDB;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoding;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

public final class EConv
implements EConvFlags {
    int flags;
    public byte[] source;
    public byte[] destination;
    boolean started = false;
    public byte[] replacementString;
    public int replacementLength;
    public byte[] replacementEncoding;
    Buffer inBuf = new Buffer();
    public EConvElement[] elements;
    public int numTranscoders;
    int numFinished;
    public Transcoding lastTranscoding;
    public final LastError lastError = new LastError();
    public Encoding sourceEncoding;
    public Encoding destinationEncoding;
    static final byte[] NULL_STRING = new byte[0];
    static final int[] NULL_POINTER = new int[0];

    public String toString() {
        return new String(this.source) + " => " + new String(this.destination);
    }

    EConv(int nHint) {
        if (nHint <= 0) {
            nHint = 1;
        }
        this.elements = new EConvElement[nHint];
        this.lastError.result = EConvResult.SourceBufferEmpty;
    }

    static boolean decorator(byte[] source, byte[] destination) {
        return source.length == 0;
    }

    void addTranscoderAt(Transcoder transcoder, int i) {
        if (this.numTranscoders == this.elements.length) {
            EConvElement[] tmp = new EConvElement[this.elements.length * 2];
            System.arraycopy(this.elements, 0, tmp, 0, i);
            System.arraycopy(this.elements, i, tmp, i + 1, this.elements.length - i);
            this.elements = tmp;
        } else {
            System.arraycopy(this.elements, i, this.elements, i + 1, this.elements.length - i - 1);
        }
        this.elements[i] = new EConvElement(transcoder.transcoding(0));
        this.elements[i].allocate(4096);
        ++this.numTranscoders;
        if (!EConv.decorator(transcoder.source, transcoder.destination)) {
            for (int j = this.numTranscoders - 1; i <= j; --j) {
                Transcoding tc = this.elements[j].transcoding;
                Transcoder tr = tc.transcoder;
                if (EConv.decorator(tr.source, tr.destination)) continue;
                this.lastTranscoding = tc;
                break;
            }
        }
    }

    private int transSweep(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, int start2) {
        boolean try_ = true;
        Ptr ipp = null;
        Ptr opp = null;
        while (try_) {
            try_ = false;
            block6: for (int i = start2; i < this.numTranscoders; ++i) {
                EConvResult res;
                byte[] obytes;
                int os;
                byte[] ibytes;
                int is;
                EConvElement te = this.elements[i];
                EConvElement previousTE = null;
                boolean ippIsStart = false;
                boolean oppIsEnd = false;
                if (i == 0) {
                    ipp = inPtr;
                    is = inStop;
                    ibytes = in;
                } else {
                    previousTE = this.elements[i - 1];
                    ipp = new Ptr(previousTE.dataStart);
                    ippIsStart = true;
                    is = previousTE.dataEnd;
                    ibytes = previousTE.bytes;
                }
                if (i == this.numTranscoders - 1) {
                    opp = outPtr;
                    os = outStop;
                    obytes = out;
                } else {
                    if (te.bufStart != te.dataStart) {
                        int len = te.dataEnd - te.dataStart;
                        int off = te.dataStart - te.bufStart;
                        System.arraycopy(te.bytes, te.dataStart, te.bytes, te.bufStart, len);
                        te.dataStart = te.bufStart;
                        te.dataEnd -= off;
                    }
                    opp = new Ptr(te.dataEnd);
                    oppIsEnd = true;
                    os = te.bufEnd;
                    obytes = te.bytes;
                }
                int f = flags;
                if (this.numFinished != i) {
                    f |= 0x10000;
                }
                if (i == 0 && (flags & 0x20000) != 0) {
                    start2 = 1;
                    flags &= 0xFFFDFFFF;
                }
                if (i != 0) {
                    f &= 0xFFFDFFFF;
                }
                int iold = ipp.p;
                int oold = opp.p;
                te.lastResult = res = te.transcoding.convert(ibytes, ipp, is, obytes, opp, os, f);
                if (ippIsStart) {
                    previousTE.dataStart = ipp.p;
                }
                if (oppIsEnd) {
                    te.dataEnd = opp.p;
                }
                if (iold != ipp.p || oold != opp.p) {
                    try_ = true;
                }
                switch (res) {
                    case InvalidByteSequence: 
                    case IncompleteInput: 
                    case UndefinedConversion: 
                    case AfterOutput: {
                        return i;
                    }
                    case DestinationBufferFull: 
                    case SourceBufferEmpty: {
                        continue block6;
                    }
                    case Finished: {
                        this.numFinished = i + 1;
                    }
                }
            }
        }
        return -1;
    }

    private EConvResult transConv(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, Ptr resultPositionPtr) {
        if (this.elements[0].lastResult == EConvResult.AfterOutput) {
            this.elements[0].lastResult = EConvResult.SourceBufferEmpty;
        }
        block4: for (int i = this.numTranscoders - 1; 0 <= i; --i) {
            switch (this.elements[i].lastResult) {
                case InvalidByteSequence: 
                case IncompleteInput: 
                case UndefinedConversion: 
                case AfterOutput: 
                case Finished: {
                    return this.transConvNeedReport(in, inPtr, inStop, out, outPtr, outStop, flags, resultPositionPtr, i + 1, i);
                }
                case DestinationBufferFull: 
                case SourceBufferEmpty: {
                    continue block4;
                }
                default: {
                    throw new InternalException("unexpected transcode last result");
                }
            }
        }
        if (this.elements[this.numTranscoders - 1].lastResult == EConvResult.DestinationBufferFull && (flags & 0x20000) != 0) {
            EConvResult res = this.transConv(NULL_STRING, Ptr.NULL, 0, out, outPtr, outStop, flags & 0xFFFDFFFF | 0x10000, resultPositionPtr);
            return res.isSourceBufferEmpty() ? EConvResult.AfterOutput : res;
        }
        return this.transConvNeedReport(in, inPtr, inStop, out, outPtr, outStop, flags, resultPositionPtr, 0, -1);
    }

    private EConvResult transConvNeedReport(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, Ptr resultPositionPtr, int sweepStart, int needReportIndex) {
        do {
            needReportIndex = this.transSweep(in, inPtr, inStop, out, outPtr, outStop, flags, sweepStart);
            sweepStart = needReportIndex + 1;
        } while (needReportIndex != -1 && needReportIndex != this.numTranscoders - 1);
        for (int i = this.numTranscoders - 1; i >= 0; --i) {
            if (this.elements[i].lastResult == EConvResult.SourceBufferEmpty) continue;
            EConvResult res = this.elements[i].lastResult;
            switch (res) {
                case InvalidByteSequence: 
                case IncompleteInput: 
                case UndefinedConversion: 
                case AfterOutput: {
                    this.elements[i].lastResult = EConvResult.SourceBufferEmpty;
                }
            }
            if (resultPositionPtr != null) {
                resultPositionPtr.p = i;
            }
            return res;
        }
        if (resultPositionPtr != null) {
            resultPositionPtr.p = -1;
        }
        return EConvResult.SourceBufferEmpty;
    }

    private EConvResult convertInternal(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags) {
        EConvResult res;
        this.lastError.reset();
        if (this.numTranscoders == 0) {
            int len;
            if (this.inBuf.bytes != null && this.inBuf.dataStart != this.inBuf.dataEnd) {
                if (outStop - outPtr.p < this.inBuf.dataEnd - this.inBuf.dataStart) {
                    int len2 = outStop - outPtr.p;
                    System.arraycopy(this.inBuf.bytes, this.inBuf.dataStart, out, outPtr.p, len2);
                    outPtr.p = outStop;
                    this.inBuf.dataStart += len2;
                    return this.convertInternalResult(EConvResult.DestinationBufferFull, null);
                }
                len = this.inBuf.dataEnd - this.inBuf.dataStart;
                System.arraycopy(this.inBuf.bytes, this.inBuf.dataStart, out, outPtr.p, len);
                outPtr.p += len;
                this.inBuf.dataStart = this.inBuf.dataEnd = this.inBuf.bufStart;
                if ((flags & 0x20000) != 0) {
                    return this.convertInternalResult(EConvResult.AfterOutput, null);
                }
            }
            if ((len = outStop - outPtr.p < inStop - inPtr.p ? outStop - outPtr.p : inStop - inPtr.p) > 0 && (flags & 0x20000) != 0) {
                out[outPtr.p++] = in[inPtr.p++];
                return this.convertInternalResult(EConvResult.AfterOutput, null);
            }
            System.arraycopy(in, inPtr.p, out, outPtr.p, len);
            outPtr.p += len;
            inPtr.p += len;
            EConvResult res2 = inPtr.p != inStop ? EConvResult.DestinationBufferFull : ((flags & 0x10000) != 0 ? EConvResult.SourceBufferEmpty : EConvResult.Finished);
            return this.convertInternalResult(res2, null);
        }
        boolean hasOutput = false;
        EConvElement elem = this.elements[this.numTranscoders - 1];
        if (elem.bytes != null) {
            int dataStart = elem.dataStart;
            int dataEnd = elem.dataEnd;
            byte[] data = elem.bytes;
            if (dataStart != dataEnd) {
                if (outStop - outPtr.p < dataEnd - dataStart) {
                    int len = outStop - outPtr.p;
                    System.arraycopy(data, dataStart, out, outPtr.p, len);
                    outPtr.p = outStop;
                    elem.dataStart += len;
                    return this.convertInternalResult(EConvResult.DestinationBufferFull, null);
                }
                int len = dataEnd - dataStart;
                System.arraycopy(data, dataStart, out, outPtr.p, len);
                outPtr.p += len;
                elem.dataStart = elem.dataEnd = elem.bufStart;
                hasOutput = true;
            }
        }
        Ptr resultPosition = new Ptr(0);
        if (this.inBuf != null && this.inBuf.dataStart != this.inBuf.dataEnd) {
            Ptr inDataStartPtr = new Ptr(this.inBuf.dataStart);
            res = this.transConv(this.inBuf.bytes, inDataStartPtr, this.inBuf.dataEnd, out, outPtr, outStop, flags & 0xFFFDFFFF | 0x10000, resultPosition);
            this.inBuf.dataStart = inDataStartPtr.p;
            if (!res.isSourceBufferEmpty()) {
                return this.convertInternalResult(res, resultPosition);
            }
        }
        if (hasOutput && (flags & 0x20000) != 0 && inPtr.p != inStop) {
            inStop = inPtr.p;
            res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition);
            if (res.isSourceBufferEmpty()) {
                res = EConvResult.AfterOutput;
            }
        } else if ((flags & 0x20000) != 0 || this.numTranscoders == 1) {
            res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition);
        } else {
            flags |= 0x20000;
            while ((res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition)).isAfterOutput()) {
            }
        }
        return this.convertInternalResult(res, resultPosition);
    }

    private EConvResult convertInternalResult(EConvResult res, Ptr resultPosition) {
        this.lastError.result = res;
        switch (res) {
            case InvalidByteSequence: 
            case IncompleteInput: 
            case UndefinedConversion: {
                Transcoding errorTranscoding;
                this.lastError.errorTranscoding = errorTranscoding = this.elements[resultPosition.p].transcoding;
                this.lastError.source = errorTranscoding.transcoder.source;
                this.lastError.destination = errorTranscoding.transcoder.destination;
                this.lastError.errorBytes = errorTranscoding.readBuf;
                this.lastError.errorBytesP = 0;
                this.lastError.errorBytesLength = errorTranscoding.recognizedLength;
                this.lastError.readAgainLength = errorTranscoding.readAgainLength;
            }
        }
        return res;
    }

    public EConvResult convert(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags) {
        EConvResult ret;
        this.started = true;
        if (in == null || inPtr == null) {
            in = NULL_STRING;
            inPtr = Ptr.NULL;
            inStop = 0;
        }
        if (out == null || outPtr == null) {
            out = NULL_STRING;
            outPtr = Ptr.NULL;
            outStop = 0;
        }
        block7: while (true) {
            if ((ret = this.convertInternal(in, inPtr, inStop, out, outPtr, outStop, flags)).isInvalidByteSequence() || ret.isIncompleteInput()) {
                switch (this.flags & 0xF) {
                    case 2: {
                        if (this.outputReplacementCharacter() != 0) break;
                        continue block7;
                    }
                }
            }
            if (!ret.isUndefinedConversion()) break;
            switch (this.flags & 0xF0) {
                case 32: {
                    if (this.outputReplacementCharacter() != 0) break block7;
                    continue block7;
                }
                case 48: {
                    if (this.outputHexCharref() != 0) break block7;
                    continue block7;
                }
            }
            break;
        }
        return ret;
    }

    private int outputHexCharref() {
        int utfLen;
        int utfP;
        byte[] utfBytes;
        if (CaseInsensitiveBytesHash.caseInsensitiveEquals(this.lastError.source, "UTF-32BE".getBytes())) {
            utfBytes = this.lastError.errorBytes;
            utfP = this.lastError.errorBytesP;
            utfLen = this.lastError.errorBytesLength;
        } else {
            Ptr utfLenA = new Ptr();
            byte[] utfBuf = new byte[this.lastError.errorBytesLength * UTF32BEEncoding.INSTANCE.maxLength()];
            utfBytes = EConv.allocateConvertedString(this.lastError.source, "UTF-32BE".getBytes(), this.lastError.errorBytes, this.lastError.errorBytesP, this.lastError.errorBytesLength, utfBuf, utfLenA);
            if (utfBytes == null) {
                return -1;
            }
            utfP = 0;
            utfLen = utfLenA.p;
        }
        if (utfLen % 4 != 0) {
            return -1;
        }
        int p = utfP;
        while (4 <= utfLen) {
            int u = 0;
            u += (utfBytes[p] & 0xFF) << 24;
            u += (utfBytes[p + 1] & 0xFF) << 16;
            u += (utfBytes[p + 2] & 0xFF) << 8;
            byte[] charrefbuf = String.format("&#x%X;", u += utfBytes[p + 3] & 0xFF).getBytes();
            if (this.insertOutput(charrefbuf, 0, charrefbuf.length, "US-ASCII".getBytes()) == -1) {
                return -1;
            }
            p += 4;
            utfLen -= 4;
        }
        return 0;
    }

    public byte[] encodingToInsertOutput() {
        Transcoding transcoding = this.lastTranscoding;
        if (transcoding == null) {
            return NULL_STRING;
        }
        Transcoder transcoder = transcoding.transcoder;
        return transcoder.compatibility.isEncoder() ? transcoder.source : transcoder.destination;
    }

    private static byte[] allocateConvertedString(byte[] source, byte[] destination, byte[] str, int strP, int strLen, byte[] callerDstBuf, Ptr dstLenPtr) {
        int dstBufSize = callerDstBuf != null ? callerDstBuf.length : (strLen == 0 ? 1 : strLen);
        EConv ec = TranscoderDB.open(source, destination, 0);
        if (ec == null) {
            return null;
        }
        byte[] dstStr = callerDstBuf != null ? callerDstBuf : new byte[dstBufSize];
        int dstLen = 0;
        Ptr sp = new Ptr(strP);
        Ptr dp = new Ptr(dstLen);
        EConvResult res = ec.convert(str, sp, strP + strLen, dstStr, dp, dstBufSize, 0);
        dstLen = dp.p;
        while (res.isDestinationBufferFull()) {
            byte[] tmp = new byte[dstBufSize *= 2];
            System.arraycopy(dstStr, 0, tmp, 0, dstBufSize / 2);
            dstStr = tmp;
            dp.p = dstLen;
            res = ec.convert(str, sp, strP + strLen, dstStr, dp, dstBufSize, 0);
            dstLen = dp.p;
        }
        if (!res.isFinished()) {
            return null;
        }
        ec.close();
        dstLenPtr.p = dstLen;
        return dstStr;
    }

    public int insertOutput(byte[] str, int strP, int strLen, byte[] strEncoding) {
        Buffer buf;
        Transcoding transcoding;
        int insertLen;
        int insertP;
        byte[] insertStr;
        byte[] insertEncoding = this.encodingToInsertOutput();
        byte[] insertBuf = null;
        this.started = true;
        if (strLen == 0) {
            return 0;
        }
        if (CaseInsensitiveBytesHash.caseInsensitiveEquals(insertEncoding, strEncoding)) {
            insertStr = str;
            insertP = 0;
            insertLen = strLen;
        } else {
            Ptr insertLenP = new Ptr();
            insertBuf = new byte[4096];
            insertStr = EConv.allocateConvertedString(strEncoding, insertEncoding, str, strP, strLen, insertBuf, insertLenP);
            insertLen = insertLenP.p;
            int n = insertP = insertStr == str ? strP : 0;
            if (insertStr == null) {
                return -1;
            }
        }
        int need = insertLen;
        int lastTranscodingIndex = this.numTranscoders - 1;
        if (this.numTranscoders == 0) {
            transcoding = null;
            buf = this.inBuf;
        } else if (this.elements[lastTranscodingIndex].transcoding.transcoder.compatibility.isEncoder()) {
            transcoding = this.elements[lastTranscodingIndex].transcoding;
            if ((need += transcoding.readAgainLength) < insertLen) {
                return -1;
            }
            buf = lastTranscodingIndex == 0 ? this.inBuf : this.elements[lastTranscodingIndex - 1];
        } else {
            transcoding = this.elements[lastTranscodingIndex].transcoding;
            buf = this.elements[lastTranscodingIndex];
        }
        if (buf == null) {
            buf = new Buffer();
            buf.allocate(need);
        } else if (buf.bytes == null) {
            buf.allocate(need);
        } else if (buf.bufEnd - buf.dataEnd < need) {
            System.arraycopy(buf.bytes, buf.dataStart, buf.bytes, buf.bufStart, buf.dataEnd - buf.dataStart);
            buf.dataEnd = buf.bufStart + (buf.dataEnd - buf.dataStart);
            buf.dataStart = buf.bufStart;
            if (buf.bufEnd - buf.dataEnd < need) {
                int s = buf.dataEnd - buf.bufStart + need;
                if (s < need) {
                    return -1;
                }
                Buffer buf2 = buf = new Buffer();
                buf2.allocate(s);
                System.arraycopy(buf.bytes, buf.bufStart, buf2.bytes, 0, s);
                buf2.dataStart = 0;
                buf2.dataEnd = buf.dataEnd - buf.bufStart;
            }
        }
        System.arraycopy(insertStr, insertP, buf.bytes, buf.dataEnd, insertLen);
        buf.dataEnd += insertLen;
        if (transcoding != null && transcoding.transcoder.compatibility.isEncoder()) {
            System.arraycopy(transcoding.readBuf, transcoding.recognizedLength, buf.bytes, buf.dataEnd, transcoding.readAgainLength);
            buf.dataEnd += transcoding.readAgainLength;
            transcoding.readAgainLength = 0;
        }
        return 0;
    }

    public void close() {
        for (int i = 0; i < this.numTranscoders; ++i) {
            this.elements[i].transcoding.close();
        }
    }

    public int putbackable() {
        return this.numTranscoders == 0 ? 0 : this.elements[0].transcoding.readAgainLength;
    }

    public void putback(byte[] bytes, int p, int n) {
        if (this.numTranscoders == 0 || n == 0) {
            return;
        }
        Transcoding transcoding = this.elements[0].transcoding;
        System.arraycopy(transcoding.readBuf, transcoding.recognizedLength + transcoding.readAgainLength - n, bytes, p, n);
        transcoding.readAgainLength -= n;
    }

    public boolean addConverter(byte[] source, byte[] destination, int n) {
        if (this.started) {
            return false;
        }
        TranscoderDB.Entry entry = TranscoderDB.getEntry(source, destination);
        if (entry == null) {
            return false;
        }
        Transcoder transcoder = entry.getTranscoder();
        if (transcoder == null) {
            return false;
        }
        this.addTranscoderAt(transcoder, n);
        return true;
    }

    boolean decorateAt(byte[] decorator, int n) {
        return this.addConverter(NULL_STRING, decorator, n);
    }

    boolean decorateAtFirst(byte[] decorator) {
        if (this.numTranscoders == 0) {
            return this.decorateAt(decorator, 0);
        }
        Transcoder transcoder = this.elements[0].transcoding.transcoder;
        if (!EConv.decorator(transcoder.source, transcoder.destination) && transcoder.compatibility.isDecoder()) {
            return this.decorateAt(decorator, 1);
        }
        return this.decorateAt(decorator, 0);
    }

    boolean decorateAtLast(byte[] decorator) {
        if (this.numTranscoders == 0) {
            return this.decorateAt(decorator, 0);
        }
        Transcoder transcoder = this.elements[this.numTranscoders - 1].transcoding.transcoder;
        if (!EConv.decorator(transcoder.source, transcoder.destination) && transcoder.compatibility.isEncoder()) {
            return this.decorateAt(decorator, this.numTranscoders - 1);
        }
        return this.decorateAt(decorator, this.numTranscoders);
    }

    public void binmode() {
        TranscoderDB.Entry entry;
        Transcoder[] transcoders = new Transcoder[3];
        int n = 0;
        if ((this.flags & 0x100) != 0 && (entry = TranscoderDB.getEntry(NULL_STRING, "universal_newline".getBytes())).getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
        }
        if ((this.flags & 0x1000) != 0 && (entry = TranscoderDB.getEntry(NULL_STRING, "crlf_newline".getBytes())).getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
        }
        if ((this.flags & 0x2000) != 0 && (entry = TranscoderDB.getEntry(NULL_STRING, "cr_newline".getBytes())).getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
        }
        int nTrans = this.numTranscoders;
        int j = 0;
        for (int i = 0; i < nTrans; ++i) {
            int k;
            for (k = 0; k < n && transcoders[k] != this.elements[i].transcoding.transcoder; ++k) {
            }
            if (k == n) {
                this.elements[j] = this.elements[i];
                ++j;
                continue;
            }
            this.elements[i].transcoding.close();
            --this.numTranscoders;
        }
        this.flags &= 0xFFFFC0FF;
    }

    public int makeReplacement() {
        byte[] replacement;
        byte[] replEnc;
        int len;
        if (this.replacementString != null) {
            return 0;
        }
        byte[] insEnc = this.encodingToInsertOutput();
        if (insEnc.length != 0) {
            if (CaseInsensitiveBytesHash.caseInsensitiveEquals(insEnc, "UTF-8".getBytes())) {
                len = 3;
                replEnc = "UTF-8".getBytes();
                replacement = new byte[]{-17, -65, -67};
            } else {
                len = 1;
                replEnc = "US-ASCII".getBytes();
                replacement = new byte[]{63};
            }
        } else {
            len = 1;
            replEnc = NULL_STRING;
            replacement = new byte[]{63};
        }
        this.replacementString = replacement;
        this.replacementLength = len;
        this.replacementEncoding = replEnc;
        return 0;
    }

    public int setReplacement(byte[] str, int p, int len, byte[] encname) {
        int len2;
        byte[] str2;
        byte[] encname2 = this.encodingToInsertOutput();
        boolean p2 = false;
        if (encname2.length == 0 || CaseInsensitiveBytesHash.caseInsensitiveEquals(encname, encname2)) {
            str2 = new byte[len];
            System.arraycopy(str, p, str2, 0, len);
            len2 = len;
            encname2 = encname;
        } else {
            Ptr len2p = new Ptr();
            str2 = EConv.allocateConvertedString(encname, encname2, str, p, len, null, len2p);
            if (str2 == null) {
                return -1;
            }
            len2 = len2p.p;
        }
        this.replacementString = str2;
        this.replacementLength = len2;
        this.replacementEncoding = encname2;
        return 0;
    }

    int outputReplacementCharacter() {
        if (this.makeReplacement() == -1) {
            return -1;
        }
        if (this.insertOutput(this.replacementString, 0, this.replacementLength, this.replacementEncoding) == -1) {
            return -1;
        }
        return 0;
    }

    public String toStringFull() {
        String s = "EConv " + new String(this.source) + " => " + new String(this.destination) + "\n";
        s = s + "  started: " + this.started + "\n";
        s = s + "  replacement string: " + (this.replacementString == null ? "null" : new String(this.replacementString, 0, this.replacementLength)) + "\n";
        s = s + "  replacement encoding: " + (this.replacementEncoding == null ? "null" : new String(this.replacementEncoding)) + "\n";
        s = s + "\n";
        for (int i = 0; i < this.numTranscoders; ++i) {
            s = s + "  element " + i + ": " + this.elements[i].toString() + "\n";
        }
        s = s + "\n";
        s = s + "  lastTranscoding: " + this.lastTranscoding + "\n";
        s = s + "  last error: " + (this.lastError == null ? "null" : this.lastError.toString());
        return s;
    }

    public boolean equals(Object other) {
        if (!(other instanceof EConv)) {
            return false;
        }
        EConv ec1 = this;
        EConv ec2 = (EConv)other;
        if (ec2 == null) {
            return false;
        }
        if (ec1.source != ec2.source && !Arrays.equals(ec1.source, ec2.source)) {
            return false;
        }
        if (ec1.destination != ec2.destination && !Arrays.equals(ec1.destination, ec2.destination)) {
            return false;
        }
        if (ec1.flags != ec2.flags) {
            return false;
        }
        if (ec1.replacementEncoding != ec2.replacementEncoding && !Arrays.equals(ec1.replacementEncoding, ec2.replacementEncoding)) {
            return false;
        }
        if (ec1.replacementLength != ec2.replacementLength) {
            return false;
        }
        if (ec1.replacementString != ec2.replacementString && !EConv.memcmp(ec1.replacementString, ec2.replacementString, ec2.replacementLength)) {
            return false;
        }
        if (ec1.numTranscoders != ec2.numTranscoders) {
            return false;
        }
        for (int i = 0; i < ec1.numTranscoders; ++i) {
            if (ec1.elements[i].transcoding.transcoder == ec2.elements[i].transcoding.transcoder) continue;
            return false;
        }
        return true;
    }

    private static boolean memcmp(byte[] a, byte[] b, int len) {
        for (int i = 0; i < len; ++i) {
            if (a[i] == b[i]) continue;
            return false;
        }
        return true;
    }

    public static final class LastError {
        EConvResult result;
        Transcoding errorTranscoding;
        byte[] source;
        byte[] destination;
        byte[] errorBytes;
        int errorBytesP;
        int errorBytesLength;
        int readAgainLength;

        void reset() {
            this.result = null;
            this.errorTranscoding = null;
            this.destination = null;
            this.source = null;
            this.errorBytes = null;
            this.errorBytesLength = 0;
            this.errorBytesP = 0;
            this.readAgainLength = 0;
        }

        public EConvResult getResult() {
            return this.result;
        }

        public Transcoding getErrorTranscoding() {
            return this.errorTranscoding;
        }

        public byte[] getSource() {
            return this.source;
        }

        public byte[] getDestination() {
            return this.destination;
        }

        public byte[] getErrorBytes() {
            return this.errorBytes;
        }

        public int getErrorBytesP() {
            return this.errorBytesP;
        }

        public int getErrorBytesLength() {
            return this.errorBytesLength;
        }

        public int getReadAgainLength() {
            return this.readAgainLength;
        }

        public String toString() {
            String s = "Last Error " + (this.source == null ? "null" : new String(this.source)) + " => " + (this.destination == null ? "null" : new String(this.destination)) + "\n";
            s = s + "  result: " + this.result.toString() + "\n";
            s = s + "  error bytes: " + (this.errorBytes == null ? "null" : new String(this.errorBytes, this.errorBytesP, this.errorBytesP + this.errorBytesLength)) + "\n";
            s = s + "  read again length: " + this.readAgainLength;
            return s;
        }
    }

    public static final class EConvElement
    extends Buffer {
        public final Transcoding transcoding;
        EConvResult lastResult;

        EConvElement(Transcoding transcoding) {
            this.transcoding = transcoding;
            this.lastResult = EConvResult.SourceBufferEmpty;
        }

        public String toString() {
            String s = "EConv " + this.transcoding.toString() + "\n";
            s = s + "  last result: " + (Object)((Object)this.lastResult);
            return s;
        }
    }
}

