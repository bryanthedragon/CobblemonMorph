package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.Encoding;
import org.graalvm.shadowed.org.jcodings.Ptr;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.specific.UTF32BEEncoding;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

public final class EConv implements EConvFlags {
   int flags;
   public byte[] source;
   public byte[] destination;
   boolean started = false;
   public byte[] replacementString;
   public int replacementLength;
   public byte[] replacementEncoding;
   Buffer inBuf = new Buffer();
   public EConv.EConvElement[] elements;
   public int numTranscoders;
   int numFinished;
   public Transcoding lastTranscoding;
   public final EConv.LastError lastError = new EConv.LastError();
   public Encoding sourceEncoding;
   public Encoding destinationEncoding;
   static final byte[] NULL_STRING = new byte[0];
   static final int[] NULL_POINTER = new int[0];

   @Override
   public String toString() {
      return new String(this.source) + " => " + new String(this.destination);
   }

   EConv(int nHint) {
      if (nHint <= 0) {
         nHint = 1;
      }

      this.elements = new EConv.EConvElement[nHint];
      this.lastError.result = EConvResult.SourceBufferEmpty;
   }

   static boolean decorator(byte[] source, byte[] destination) {
      return source.length == 0;
   }

   void addTranscoderAt(Transcoder transcoder, int i) {
      if (this.numTranscoders == this.elements.length) {
         EConv.EConvElement[] tmp = new EConv.EConvElement[this.elements.length * 2];
         System.arraycopy(this.elements, 0, tmp, 0, i);
         System.arraycopy(this.elements, i, tmp, i + 1, this.elements.length - i);
         this.elements = tmp;
      } else {
         System.arraycopy(this.elements, i, this.elements, i + 1, this.elements.length - i - 1);
      }

      this.elements[i] = new EConv.EConvElement(transcoder.transcoding(0));
      this.elements[i].allocate(4096);
      this.numTranscoders++;
      if (!decorator(transcoder.source, transcoder.destination)) {
         for (int j = this.numTranscoders - 1; i <= j; j--) {
            Transcoding tc = this.elements[j].transcoding;
            Transcoder tr = tc.transcoder;
            if (!decorator(tr.source, tr.destination)) {
               this.lastTranscoding = tc;
               break;
            }
         }
      }
   }

   private int transSweep(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, int start) {
      boolean try_ = true;
      Ptr ipp = null;
      Ptr opp = null;

      while (try_) {
         try_ = false;

         for (int i = start; i < this.numTranscoders; i++) {
            EConv.EConvElement te = this.elements[i];
            EConv.EConvElement previousTE = null;
            boolean ippIsStart = false;
            boolean oppIsEnd = false;
            int is;
            byte[] ibytes;
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

            int os;
            byte[] obytes;
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
               f = flags | 65536;
            }

            if (i == 0 && (flags & 131072) != 0) {
               start = 1;
               flags &= -131073;
            }

            if (i != 0) {
               f &= -131073;
            }

            int iold = ipp.p;
            int oold = opp.p;
            EConvResult res;
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
               case AfterOutput:
                  return i;
               case Finished:
                  this.numFinished = i + 1;
                  break;
               case DestinationBufferFull:
               case SourceBufferEmpty:
            }
         }
      }

      return -1;
   }

   private EConvResult transConv(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, Ptr resultPositionPtr) {
      if (this.elements[0].lastResult == EConvResult.AfterOutput) {
         this.elements[0].lastResult = EConvResult.SourceBufferEmpty;
      }

      for (int i = this.numTranscoders - 1; 0 <= i; i--) {
         switch (this.elements[i].lastResult) {
            case InvalidByteSequence:
            case IncompleteInput:
            case UndefinedConversion:
            case AfterOutput:
            case Finished:
               return this.transConvNeedReport(in, inPtr, inStop, out, outPtr, outStop, flags, resultPositionPtr, i + 1, i);
            case DestinationBufferFull:
            case SourceBufferEmpty:
            default:
               throw new InternalException("unexpected transcode last result");
         }
      }

      if (this.elements[this.numTranscoders - 1].lastResult == EConvResult.DestinationBufferFull && (flags & 131072) != 0) {
         EConvResult res = this.transConv(NULL_STRING, Ptr.NULL, 0, out, outPtr, outStop, flags & -131073 | 65536, resultPositionPtr);
         return res.isSourceBufferEmpty() ? EConvResult.AfterOutput : res;
      } else {
         return this.transConvNeedReport(in, inPtr, inStop, out, outPtr, outStop, flags, resultPositionPtr, 0, -1);
      }
   }

   private EConvResult transConvNeedReport(
      byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags, Ptr resultPositionPtr, int sweepStart, int needReportIndex
   ) {
      do {
         needReportIndex = this.transSweep(in, inPtr, inStop, out, outPtr, outStop, flags, sweepStart);
         sweepStart = needReportIndex + 1;
      } while (needReportIndex != -1 && needReportIndex != this.numTranscoders - 1);

      for (int i = this.numTranscoders - 1; i >= 0; i--) {
         if (this.elements[i].lastResult != EConvResult.SourceBufferEmpty) {
            EConvResult res = this.elements[i].lastResult;
            switch (res) {
               case InvalidByteSequence:
               case IncompleteInput:
               case UndefinedConversion:
               case AfterOutput:
                  this.elements[i].lastResult = EConvResult.SourceBufferEmpty;
               default:
                  if (resultPositionPtr != null) {
                     resultPositionPtr.p = i;
                  }

                  return res;
            }
         }
      }

      if (resultPositionPtr != null) {
         resultPositionPtr.p = -1;
      }

      return EConvResult.SourceBufferEmpty;
   }

   private EConvResult convertInternal(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags) {
      this.lastError.reset();
      if (this.numTranscoders == 0) {
         if (this.inBuf.bytes != null && this.inBuf.dataStart != this.inBuf.dataEnd) {
            if (outStop - outPtr.p < this.inBuf.dataEnd - this.inBuf.dataStart) {
               int len = outStop - outPtr.p;
               System.arraycopy(this.inBuf.bytes, this.inBuf.dataStart, out, outPtr.p, len);
               outPtr.p = outStop;
               this.inBuf.dataStart += len;
               return this.convertInternalResult(EConvResult.DestinationBufferFull, null);
            }

            int len = this.inBuf.dataEnd - this.inBuf.dataStart;
            System.arraycopy(this.inBuf.bytes, this.inBuf.dataStart, out, outPtr.p, len);
            outPtr.p += len;
            this.inBuf.dataStart = this.inBuf.dataEnd = this.inBuf.bufStart;
            if ((flags & 131072) != 0) {
               return this.convertInternalResult(EConvResult.AfterOutput, null);
            }
         }

         int len;
         if (outStop - outPtr.p < inStop - inPtr.p) {
            len = outStop - outPtr.p;
         } else {
            len = inStop - inPtr.p;
         }

         if (len > 0 && (flags & 131072) != 0) {
            out[outPtr.p++] = in[inPtr.p++];
            return this.convertInternalResult(EConvResult.AfterOutput, null);
         } else {
            System.arraycopy(in, inPtr.p, out, outPtr.p, len);
            outPtr.p += len;
            inPtr.p += len;
            EConvResult res;
            if (inPtr.p != inStop) {
               res = EConvResult.DestinationBufferFull;
            } else if ((flags & 65536) != 0) {
               res = EConvResult.SourceBufferEmpty;
            } else {
               res = EConvResult.Finished;
            }

            return this.convertInternalResult(res, null);
         }
      } else {
         boolean hasOutput = false;
         EConv.EConvElement elem = this.elements[this.numTranscoders - 1];
         if (elem.bytes != null) {
            int dataStart = elem.dataStart;
            int dataEnd = elem.dataEnd;
            byte[] data = elem.bytes;
            if (dataStart != dataEnd) {
               if (outStop - outPtr.p < dataEnd - dataStart) {
                  int lenx = outStop - outPtr.p;
                  System.arraycopy(data, dataStart, out, outPtr.p, lenx);
                  outPtr.p = outStop;
                  elem.dataStart += lenx;
                  return this.convertInternalResult(EConvResult.DestinationBufferFull, null);
               }

               int lenx = dataEnd - dataStart;
               System.arraycopy(data, dataStart, out, outPtr.p, lenx);
               outPtr.p += lenx;
               elem.dataStart = elem.dataEnd = elem.bufStart;
               hasOutput = true;
            }
         }

         Ptr resultPosition = new Ptr(0);
         if (this.inBuf != null && this.inBuf.dataStart != this.inBuf.dataEnd) {
            Ptr inDataStartPtr = new Ptr(this.inBuf.dataStart);
            EConvResult res = this.transConv(
               this.inBuf.bytes, inDataStartPtr, this.inBuf.dataEnd, out, outPtr, outStop, flags & -131073 | 65536, resultPosition
            );
            this.inBuf.dataStart = inDataStartPtr.p;
            if (!res.isSourceBufferEmpty()) {
               return this.convertInternalResult(res, resultPosition);
            }
         }

         EConvResult res;
         if (hasOutput && (flags & 131072) != 0 && inPtr.p != inStop) {
            inStop = inPtr.p;
            res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition);
            if (res.isSourceBufferEmpty()) {
               res = EConvResult.AfterOutput;
            }
         } else if ((flags & 131072) == 0 && this.numTranscoders != 1) {
            flags |= 131072;

            do {
               res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition);
            } while (res.isAfterOutput());
         } else {
            res = this.transConv(in, inPtr, inStop, out, outPtr, outStop, flags, resultPosition);
         }

         return this.convertInternalResult(res, resultPosition);
      }
   }

   private EConvResult convertInternalResult(EConvResult res, Ptr resultPosition) {
      this.lastError.result = res;
      switch (res) {
         case InvalidByteSequence:
         case IncompleteInput:
         case UndefinedConversion:
            Transcoding errorTranscoding = this.elements[resultPosition.p].transcoding;
            this.lastError.errorTranscoding = errorTranscoding;
            this.lastError.source = errorTranscoding.transcoder.source;
            this.lastError.destination = errorTranscoding.transcoder.destination;
            this.lastError.errorBytes = errorTranscoding.readBuf;
            this.lastError.errorBytesP = 0;
            this.lastError.errorBytesLength = errorTranscoding.recognizedLength;
            this.lastError.readAgainLength = errorTranscoding.readAgainLength;
         default:
            return res;
      }
   }

   public EConvResult convert(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags) {
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

      while (true) {
         EConvResult ret;
         label29:
         while (true) {
            ret = this.convertInternal(in, inPtr, inStop, out, outPtr, outStop, flags);
            if (!ret.isInvalidByteSequence() && !ret.isIncompleteInput()) {
               break;
            }

            switch (this.flags & 15) {
               case 2:
                  if (this.outputReplacementCharacter() == 0) {
                     break;
                  }
               default:
                  break label29;
            }
         }

         if (!ret.isUndefinedConversion()) {
            return ret;
         }

         switch (this.flags & 240) {
            case 32:
               if (this.outputReplacementCharacter() == 0) {
                  break;
               }

               return ret;
            case 48:
               if (this.outputHexCharref() == 0) {
                  break;
               }

               return ret;
            default:
               return ret;
         }
      }
   }

   private int outputHexCharref() {
      byte[] utfBytes;
      int utfP;
      int utfLen;
      if (CaseInsensitiveBytesHash.caseInsensitiveEquals(this.lastError.source, "UTF-32BE".getBytes())) {
         utfBytes = this.lastError.errorBytes;
         utfP = this.lastError.errorBytesP;
         utfLen = this.lastError.errorBytesLength;
      } else {
         Ptr utfLenA = new Ptr();
         byte[] utfBuf = new byte[this.lastError.errorBytesLength * UTF32BEEncoding.INSTANCE.maxLength()];
         utfBytes = allocateConvertedString(
            this.lastError.source,
            "UTF-32BE".getBytes(),
            this.lastError.errorBytes,
            this.lastError.errorBytesP,
            this.lastError.errorBytesLength,
            utfBuf,
            utfLenA
         );
         if (utfBytes == null) {
            return -1;
         }

         utfP = 0;
         utfLen = utfLenA.p;
      }

      if (utfLen % 4 != 0) {
         return -1;
      } else {
         for (int p = utfP; 4 <= utfLen; utfLen -= 4) {
            int u = 0;
            u += (utfBytes[p] & 255) << 24;
            u += (utfBytes[p + 1] & 255) << 16;
            u += (utfBytes[p + 2] & 255) << 8;
            u += utfBytes[p + 3] & 255;
            byte[] charrefbuf = String.format("&#x%X;", u).getBytes();
            if (this.insertOutput(charrefbuf, 0, charrefbuf.length, "US-ASCII".getBytes()) == -1) {
               return -1;
            }

            p += 4;
         }

         return 0;
      }
   }

   public byte[] encodingToInsertOutput() {
      Transcoding transcoding = this.lastTranscoding;
      if (transcoding == null) {
         return NULL_STRING;
      } else {
         Transcoder transcoder = transcoding.transcoder;
         return transcoder.compatibility.isEncoder() ? transcoder.source : transcoder.destination;
      }
   }

   private static byte[] allocateConvertedString(byte[] source, byte[] destination, byte[] str, int strP, int strLen, byte[] callerDstBuf, Ptr dstLenPtr) {
      int dstBufSize;
      if (callerDstBuf != null) {
         dstBufSize = callerDstBuf.length;
      } else if (strLen == 0) {
         dstBufSize = 1;
      } else {
         dstBufSize = strLen;
      }

      EConv ec = TranscoderDB.open(source, destination, 0);
      if (ec == null) {
         return null;
      } else {
         byte[] dstStr;
         if (callerDstBuf != null) {
            dstStr = callerDstBuf;
         } else {
            dstStr = new byte[dstBufSize];
         }

         int dstLen = 0;
         Ptr sp = new Ptr(strP);
         Ptr dp = new Ptr(dstLen);
         EConvResult res = ec.convert(str, sp, strP + strLen, dstStr, dp, dstBufSize, 0);

         for (dstLen = dp.p; res.isDestinationBufferFull(); dstLen = dp.p) {
            dstBufSize *= 2;
            byte[] tmp = new byte[dstBufSize];
            System.arraycopy(dstStr, 0, tmp, 0, dstBufSize / 2);
            dstStr = tmp;
            dp.p = dstLen;
            res = ec.convert(str, sp, strP + strLen, tmp, dp, dstBufSize, 0);
         }

         if (!res.isFinished()) {
            return null;
         } else {
            ec.close();
            dstLenPtr.p = dstLen;
            return dstStr;
         }
      }
   }

   public int insertOutput(byte[] str, int strP, int strLen, byte[] strEncoding) {
      byte[] insertEncoding = this.encodingToInsertOutput();
      byte[] insertBuf = null;
      this.started = true;
      if (strLen == 0) {
         return 0;
      } else {
         byte[] insertStr;
         int insertP;
         int insertLen;
         if (CaseInsensitiveBytesHash.caseInsensitiveEquals(insertEncoding, strEncoding)) {
            insertStr = str;
            insertP = 0;
            insertLen = strLen;
         } else {
            Ptr insertLenP = new Ptr();
            insertBuf = new byte[4096];
            insertStr = allocateConvertedString(strEncoding, insertEncoding, str, strP, strLen, insertBuf, insertLenP);
            insertLen = insertLenP.p;
            insertP = insertStr == str ? strP : 0;
            if (insertStr == null) {
               return -1;
            }
         }

         int need = insertLen;
         int lastTranscodingIndex = this.numTranscoders - 1;
         Transcoding transcoding;
         Buffer buf;
         if (this.numTranscoders == 0) {
            transcoding = null;
            buf = this.inBuf;
         } else if (this.elements[lastTranscodingIndex].transcoding.transcoder.compatibility.isEncoder()) {
            transcoding = this.elements[lastTranscodingIndex].transcoding;
            need = insertLen + transcoding.readAgainLength;
            if (need < insertLen) {
               return -1;
            }

            if (lastTranscodingIndex == 0) {
               buf = this.inBuf;
            } else {
               buf = this.elements[lastTranscodingIndex - 1];
            }
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
            buf.dataEnd = buf.dataEnd + transcoding.readAgainLength;
            transcoding.readAgainLength = 0;
         }

         return 0;
      }
   }

   public void close() {
      for (int i = 0; i < this.numTranscoders; i++) {
         this.elements[i].transcoding.close();
      }
   }

   public int putbackable() {
      return this.numTranscoders == 0 ? 0 : this.elements[0].transcoding.readAgainLength;
   }

   public void putback(byte[] bytes, int p, int n) {
      if (this.numTranscoders != 0 && n != 0) {
         Transcoding transcoding = this.elements[0].transcoding;
         System.arraycopy(transcoding.readBuf, transcoding.recognizedLength + transcoding.readAgainLength - n, bytes, p, n);
         transcoding.readAgainLength -= n;
      }
   }

   public boolean addConverter(byte[] source, byte[] destination, int n) {
      if (this.started) {
         return false;
      } else {
         TranscoderDB.Entry entry = TranscoderDB.getEntry(source, destination);
         if (entry == null) {
            return false;
         } else {
            Transcoder transcoder = entry.getTranscoder();
            if (transcoder == null) {
               return false;
            } else {
               this.addTranscoderAt(transcoder, n);
               return true;
            }
         }
      }
   }

   boolean decorateAt(byte[] decorator, int n) {
      return this.addConverter(NULL_STRING, decorator, n);
   }

   boolean decorateAtFirst(byte[] decorator) {
      if (this.numTranscoders == 0) {
         return this.decorateAt(decorator, 0);
      } else {
         Transcoder transcoder = this.elements[0].transcoding.transcoder;
         return !decorator(transcoder.source, transcoder.destination) && transcoder.compatibility.isDecoder()
            ? this.decorateAt(decorator, 1)
            : this.decorateAt(decorator, 0);
      }
   }

   boolean decorateAtLast(byte[] decorator) {
      if (this.numTranscoders == 0) {
         return this.decorateAt(decorator, 0);
      } else {
         Transcoder transcoder = this.elements[this.numTranscoders - 1].transcoding.transcoder;
         return !decorator(transcoder.source, transcoder.destination) && transcoder.compatibility.isEncoder()
            ? this.decorateAt(decorator, this.numTranscoders - 1)
            : this.decorateAt(decorator, this.numTranscoders);
      }
   }

   public void binmode() {
      Transcoder[] transcoders = new Transcoder[3];
      int n = 0;
      if ((this.flags & 256) != 0) {
         TranscoderDB.Entry entry = TranscoderDB.getEntry(NULL_STRING, "universal_newline".getBytes());
         if (entry.getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
         }
      }

      if ((this.flags & 4096) != 0) {
         TranscoderDB.Entry entry = TranscoderDB.getEntry(NULL_STRING, "crlf_newline".getBytes());
         if (entry.getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
         }
      }

      if ((this.flags & 8192) != 0) {
         TranscoderDB.Entry entry = TranscoderDB.getEntry(NULL_STRING, "cr_newline".getBytes());
         if (entry.getTranscoder() != null) {
            transcoders[n++] = entry.getTranscoder();
         }
      }

      int nTrans = this.numTranscoders;
      int j = 0;

      for (int i = 0; i < nTrans; i++) {
         int k = 0;

         while (k < n && transcoders[k] != this.elements[i].transcoding.transcoder) {
            k++;
         }

         if (k == n) {
            this.elements[j] = this.elements[i];
            j++;
         } else {
            this.elements[i].transcoding.close();
            this.numTranscoders--;
         }
      }

      this.flags &= -16129;
   }

   public int makeReplacement() {
      if (this.replacementString != null) {
         return 0;
      } else {
         byte[] insEnc = this.encodingToInsertOutput();
         byte[] replEnc;
         int len;
         byte[] replacement;
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
   }

   public int setReplacement(byte[] str, int p, int len, byte[] encname) {
      byte[] encname2 = this.encodingToInsertOutput();
      int p2 = 0;
      byte[] str2;
      int len2;
      if (encname2.length != 0 && !CaseInsensitiveBytesHash.caseInsensitiveEquals(encname, encname2)) {
         Ptr len2p = new Ptr();
         str2 = allocateConvertedString(encname, encname2, str, p, len, null, len2p);
         if (str2 == null) {
            return -1;
         }

         len2 = len2p.p;
      } else {
         str2 = new byte[len];
         System.arraycopy(str, p, str2, 0, len);
         len2 = len;
         encname2 = encname;
      }

      this.replacementString = str2;
      this.replacementLength = len2;
      this.replacementEncoding = encname2;
      return 0;
   }

   int outputReplacementCharacter() {
      if (this.makeReplacement() == -1) {
         return -1;
      } else {
         return this.insertOutput(this.replacementString, 0, this.replacementLength, this.replacementEncoding) == -1 ? -1 : 0;
      }
   }

   public String toStringFull() {
      String s = "EConv " + new String(this.source) + " => " + new String(this.destination) + "\n";
      s = s + "  started: " + this.started + "\n";
      s = s + "  replacement string: " + (this.replacementString == null ? "null" : new String(this.replacementString, 0, this.replacementLength)) + "\n";
      s = s + "  replacement encoding: " + (this.replacementEncoding == null ? "null" : new String(this.replacementEncoding)) + "\n";
      s = s + "\n";

      for (int i = 0; i < this.numTranscoders; i++) {
         s = s + "  element " + i + ": " + this.elements[i].toString() + "\n";
      }

      s = s + "\n";
      s = s + "  lastTranscoding: " + this.lastTranscoding + "\n";
      return s + "  last error: " + (this.lastError == null ? "null" : this.lastError.toString());
   }

   @Override
   public boolean equals(Object other) {
      if (!(other instanceof EConv)) {
         return false;
      } else {
         EConv ec1 = this;
         EConv ec2 = (EConv)other;
         if (ec2 == null) {
            return false;
         } else if (this.source != ec2.source && !Arrays.equals(this.source, ec2.source)) {
            return false;
         } else if (this.destination != ec2.destination && !Arrays.equals(this.destination, ec2.destination)) {
            return false;
         } else if (this.flags != ec2.flags) {
            return false;
         } else if (this.replacementEncoding != ec2.replacementEncoding && !Arrays.equals(this.replacementEncoding, ec2.replacementEncoding)) {
            return false;
         } else if (this.replacementLength != ec2.replacementLength) {
            return false;
         } else if (this.replacementString != ec2.replacementString && !memcmp(this.replacementString, ec2.replacementString, ec2.replacementLength)) {
            return false;
         } else if (this.numTranscoders != ec2.numTranscoders) {
            return false;
         } else {
            for (int i = 0; i < ec1.numTranscoders; i++) {
               if (ec1.elements[i].transcoding.transcoder != ec2.elements[i].transcoding.transcoder) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean memcmp(byte[] a, byte[] b, int len) {
      for (int i = 0; i < len; i++) {
         if (a[i] != b[i]) {
            return false;
         }
      }

      return true;
   }

   public static final class EConvElement extends Buffer {
      public final Transcoding transcoding;
      EConvResult lastResult;

      EConvElement(Transcoding transcoding) {
         this.transcoding = transcoding;
         this.lastResult = EConvResult.SourceBufferEmpty;
      }

      @Override
      public String toString() {
         String s = "EConv " + this.transcoding.toString() + "\n";
         return s + "  last result: " + this.lastResult;
      }
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
         this.source = this.destination = null;
         this.errorBytes = null;
         this.errorBytesP = this.errorBytesLength = 0;
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

      @Override
      public String toString() {
         String s = "Last Error "
            + (this.source == null ? "null" : new String(this.source))
            + " => "
            + (this.destination == null ? "null" : new String(this.destination))
            + "\n";
         s = s + "  result: " + this.result.toString() + "\n";
         s = s
            + "  error bytes: "
            + (this.errorBytes == null ? "null" : new String(this.errorBytes, this.errorBytesP, this.errorBytesP + this.errorBytesLength))
            + "\n";
         return s + "  read again length: " + this.readAgainLength;
      }
   }
}
