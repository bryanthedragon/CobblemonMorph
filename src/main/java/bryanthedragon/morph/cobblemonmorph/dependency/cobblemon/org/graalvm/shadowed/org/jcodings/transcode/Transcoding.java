package org.graalvm.shadowed.org.jcodings.transcode;

import org.graalvm.shadowed.org.jcodings.Ptr;

public class Transcoding implements TranscodingInstruction {
   public final Transcoder transcoder;
   private int flags;
   private int resumePosition;
   private int nextTable;
   private int nextInfo;
   private byte nextByte;
   private int outputIndex;
   int recognizedLength;
   int readAgainLength;
   final byte[] readBuf;
   private int writeBuffOff;
   private int writeBuffLen;
   private final byte[] writeBuf;
   final byte[] state;
   private EConvResult suspendResult;
   private int charStart;
   private byte[] charStartBytes;
   private int inCharStart;
   private byte[] inBytes;
   private int inP;
   private Ptr inPos;
   private static final int SUSPEND = 0;
   private static final int START = 1;
   private static final int RESUME_AFTER_OUTPUT = 2;
   private static final int NEXTBYTE = 3;
   private static final int FOLLOW_BYTE = 4;
   private static final int FOLLOW_INFO = 5;
   private static final int NOMAP_TRANSFER = 35;
   private static final int READ_MORE = 6;
   private static final int CALL_FUN_SIO = 8;
   private static final int RESUME_CALL_FUN_SIO = 44;
   private static final int CALL_FUN_SO = 9;
   private static final int RESUME_CALL_FUN_SO = 43;
   private static final int CALL_FUN_IO = 10;
   private static final int TRANSFER_WRITEBUF = 11;
   private static final int RESUME_TRANSFER_WRITEBUF = 12;
   private static final int ONE_BYTE_1 = 13;
   private static final int TWO_BYTE_1 = 14;
   private static final int TWO_BYTE_2 = 15;
   private static final int FOUR_BYTE_1 = 16;
   private static final int FOUR_BYTE_2 = 17;
   private static final int FOUR_BYTE_3 = 18;
   private static final int FOUR_BYTE_0 = 19;
   private static final int GB_FOUR_BYTE_0 = 20;
   private static final int GB_FOUR_BYTE_1 = 21;
   private static final int GB_FOUR_BYTE_2 = 22;
   private static final int GB_FOUR_BYTE_3 = 23;
   private static final int STRING = 24;
   private static final int RESUME_STRING = 25;
   private static final int RESUME_NOMAP = 26;
   private static final int SELECT_TABLE = 27;
   private static final int REPORT_INVALID = 28;
   private static final int REPORT_INCOMPLETE = 29;
   private static final int REPORT_UNDEF = 30;
   private static final int FINISH_FUNC = 31;
   private static final int RESUME_FINISH_WRITEBUF = 32;
   private static final int FINISHED = 33;
   private static final int CLEANUP = 34;
   private static final int WORDINDEX_SHIFT_BITS = 2;

   public Transcoding(Transcoder transcoder, int flags) {
      this.transcoder = transcoder;
      this.flags = flags;
      this.resumePosition = 1;
      this.recognizedLength = 0;
      this.readAgainLength = 0;
      this.writeBuffLen = 0;
      this.writeBuffOff = 0;
      this.readBuf = new byte[transcoder.maxInput];
      this.writeBuf = new byte[transcoder.maxOutput];
      this.state = new byte[transcoder.stateSize];
      transcoder.stateInit(this.state);
   }

   void close() {
      this.transcoder.stateFinish(this.state);
   }

   @Override
   public String toString() {
      return "Transcoding for transcoder " + this.transcoder.toString();
   }

   int charStart() {
      if (this.recognizedLength > this.inCharStart - this.inPos.p) {
         System.arraycopy(this.inBytes, this.inCharStart, this.readBuf, this.recognizedLength, this.inP - this.inCharStart);
         this.charStart = 0;
         this.charStartBytes = this.readBuf;
      } else {
         this.charStart = this.inCharStart - this.recognizedLength;
         this.charStartBytes = this.inBytes;
      }

      return this.recognizedLength + (this.inP - this.inCharStart);
   }

   EConvResult convert(byte[] in, Ptr inPtr, int inStop, byte[] out, Ptr outPtr, int outStop, int flags) {
      return this.transcodeRestartable(in, inPtr, inStop, out, outPtr, outStop, flags);
   }

   private EConvResult transcodeRestartable(byte[] in, Ptr inStart, int inStop, byte[] out, Ptr outStart, int outStop, int opt) {
      if (this.readAgainLength != 0) {
         byte[] readAgainBuf = new byte[this.readAgainLength];
         Ptr readAgainPos = new Ptr(0);
         int readAgainStop = this.readAgainLength;
         System.arraycopy(this.readBuf, this.recognizedLength, readAgainBuf, readAgainPos.p, this.readAgainLength);
         this.readAgainLength = 0;
         System.arraycopy(readAgainBuf, 0, TRANSCODING_READBUF(this), this.recognizedLength, this.readAgainLength);
         this.readAgainLength = 0;
         EConvResult res = this.transcodeRestartable0(readAgainBuf, readAgainPos, out, outStart, readAgainStop, outStop, opt | 65536);
         if (!res.isSourceBufferEmpty()) {
            System.arraycopy(readAgainBuf, readAgainPos.p, this.readBuf, this.recognizedLength + this.readAgainLength, readAgainStop - readAgainPos.p);
            this.readAgainLength = this.readAgainLength + (readAgainStop - readAgainPos.p);
         }
      }

      return this.transcodeRestartable0(in, inStart, out, outStart, inStop, outStop, opt);
   }

   private static int STR1_LENGTH(byte[] bytes, int byteaddr) {
      return bytes[byteaddr] + 4;
   }

   private static int STR1_BYTEINDEX(int w) {
      return w >>> 6;
   }

   private EConvResult transcodeRestartable0(byte[] in_bytes, Ptr in_pos, byte[] out_bytes, Ptr out_pos, int in_stop, int out_stop, int opt) {
      Transcoder tr = this.transcoder;
      int unitlen = tr.inputUnitLength;
      int readagain_len = 0;
      int inchar_start = in_pos.p;
      int in_p = inchar_start;
      int out_p = out_pos.p;
      int[] char_len = null;
      byte[][] outByteParam = null;
      int ip = this.resumePosition;

      while (true) {
         switch (ip) {
            case 1:
               inchar_start = in_p;
               this.recognizedLength = 0;
               this.nextTable = tr.treeStart;
               if (0 == SUSPEND_AFTER_OUTPUT(this, opt, in_bytes, in_p, in_p, in_pos, out_pos, out_p, readagain_len, 2)) {
                  return this.suspendResult;
               }
            case 2:
               if (in_stop <= in_p) {
                  if ((opt & 65536) != 0) {
                     SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.SourceBufferEmpty, 1);
                     return this.suspendResult;
                  }

                  ip = 34;
                  break;
               }
            case 3:
               this.nextByte = in_bytes[in_p++];
            case 4:
               if ((this.nextByte & 255) >= BL_MIN_BYTE(this) && BL_MAX_BYTE(this) >= (this.nextByte & 255)) {
                  this.nextInfo = BL_ACTION(this, this.nextByte);
               } else {
                  this.nextInfo = 7;
               }
            case 5:
               switch (this.nextInfo & 31) {
                  case 0:
                  case 4:
                  case 8:
                  case 12:
                  case 16:
                  case 20:
                  case 24:
                  case 28:
                     if (0 == SUSPEND_AFTER_OUTPUT(this, opt, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 27)) {
                        return this.suspendResult;
                     }

                     ip = 27;
                     continue;
                  case 1:
                     int p = inchar_start;
                     this.writeBuffOff = 0;

                     while (p < in_p) {
                        this.writeBuf[this.writeBuffOff++] = in_bytes[p++];
                     }

                     this.writeBuffLen = this.writeBuffOff;
                     this.writeBuffOff = 0;
                     ip = 35;
                     continue;
                  case 2:
                     ip = 13;
                     continue;
                  case 3:
                     ip = 14;
                     continue;
                  case 5:
                     ip = 16;
                     continue;
                  case 6:
                     ip = 19;
                     continue;
                  case 7:
                     if (this.recognizedLength + (in_p - inchar_start) <= unitlen) {
                        if (this.recognizedLength + (in_p - inchar_start) < unitlen
                           && 0 == SUSPEND_AFTER_OUTPUT(this, opt, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 6)) {
                           return this.suspendResult;
                        }

                        ip = 6;
                     } else {
                        int invalid_len = this.recognizedLength + (in_p - inchar_start);
                        int discard_len = (invalid_len - 1) / unitlen * unitlen;
                        readagain_len = invalid_len - discard_len;
                        ip = 28;
                     }
                     continue;
                  case 9:
                     ip = 30;
                     continue;
                  case 10:
                     ip = 1;
                     continue;
                  case 11:
                     this.nextInfo = tr.infoToInfo(this.state, this.nextInfo);
                     ip = 5;
                     continue;
                  case 13:
                     char_len = this.PREPARE_CHAR_LEN(char_len);
                     outByteParam = this.PREPARE_OUT_BYTES(outByteParam);
                     int char_start = this.transcode_char_start(in_bytes, in_pos.p, inchar_start, in_p, char_len, outByteParam);
                     this.nextInfo = tr.startToInfo(this.state, outByteParam[0], char_start, char_len[0]);
                     ip = 5;
                     continue;
                  case 14:
                     ip = 10;
                     continue;
                  case 15:
                     ip = 43;
                     continue;
                  case 17:
                     this.outputIndex = 0;
                     ip = 24;
                     continue;
                  case 18:
                     ip = 20;
                     continue;
                  case 19:
                     if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 44)) {
                        return this.suspendResult;
                     }

                     ip = 8;
                     continue;
                  case 21:
                  case 22:
                  case 23:
                  case 25:
                  case 26:
                  case 27:
                  default:
                     throw new RuntimeException("unknown transcoding instruction");
               }
            case 6:
               if ((opt & 65536) != 0 && this.recognizedLength + (in_stop - inchar_start) < unitlen) {
                  SUSPEND(this, in_bytes, in_stop, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.SourceBufferEmpty, 6);
                  return this.suspendResult;
               }

               if (this.recognizedLength + (in_stop - inchar_start) <= unitlen) {
                  in_p = in_stop;
               } else {
                  in_p = inchar_start + (unitlen - this.recognizedLength);
               }

               ip = 28;
            case 7:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            default:
               break;
            case 10:
               if (tr.maxOutput <= out_stop - out_p) {
                  out_p += tr.infoToOutput(this.state, this.nextInfo, out_bytes, out_p, out_stop - out_p);
                  ip = 1;
               } else {
                  this.writeBuffLen = tr.infoToOutput(this.state, this.nextInfo, this.writeBuf, 0, this.writeBuf.length);
                  this.writeBuffOff = 0;
                  ip = 11;
               }
               break;
            case 12:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 12)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++];
            case 11:
               while (this.writeBuffOff < this.writeBuffLen) {
                  if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 12)) {
                     return this.suspendResult;
                  }

                  out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++];
               }

               ip = 1;
               break;
            case 13:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 13)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT1(this.nextInfo);
               ip = 1;
               break;
            case 14:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 14)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT1(this.nextInfo);
            case 15:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 15)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT2(this.nextInfo);
               ip = 1;
               break;
            case 19:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 19)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT0(this.nextInfo);
            case 16:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 16)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT1(this.nextInfo);
            case 17:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 17)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT2(this.nextInfo);
            case 18:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 18)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getBT3(this.nextInfo);
               ip = 1;
               break;
            case 20:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 20)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getGB4bt0(this.nextInfo);
            case 21:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 21)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getGB4bt1(this.nextInfo);
            case 22:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 22)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = getGB4bt2(this.nextInfo);
            case 23:
               out_bytes[out_p++] = getGB4bt3(this.nextInfo);
               ip = 1;
               break;
            case 25:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 25)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = this.transcoder.byteArray[BYTE_ADDR(STR1_BYTEINDEX(this.nextInfo)) + 1 + this.outputIndex];
               this.outputIndex++;
            case 24:
               while (this.outputIndex < STR1_LENGTH(this.transcoder.byteArray, BYTE_ADDR(STR1_BYTEINDEX(this.nextInfo)))) {
                  if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 25)) {
                     return this.suspendResult;
                  }

                  out_bytes[out_p++] = this.transcoder.byteArray[BYTE_ADDR(STR1_BYTEINDEX(this.nextInfo)) + 1 + this.outputIndex];
                  this.outputIndex++;
               }

               ip = 1;
               break;
            case 26:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 26)) {
                  return this.suspendResult;
               }

               out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++];
            case 35:
               while (this.writeBuffOff < this.writeBuffLen) {
                  if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 26)) {
                     return this.suspendResult;
                  }

                  out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++];
               }

               ip = 1;
               break;
            case 27:
               if (in_p >= in_stop) {
                  if ((opt & 65536) != 0) {
                     SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.SourceBufferEmpty, 27);
                     return this.suspendResult;
                  }

                  ip = 29;
               } else {
                  this.nextByte = in_bytes[in_p++];
                  this.nextTable = this.nextInfo;
                  ip = 4;
               }
               break;
            case 28:
               SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.InvalidByteSequence, 1);
               return this.suspendResult;
            case 29:
               SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.IncompleteInput, 1);
               return this.suspendResult;
            case 30:
               SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.UndefinedConversion, 1);
               return this.suspendResult;
            case 31:
               if (tr.maxOutput <= out_stop - out_p) {
                  out_p += tr.finish(this.state, out_bytes, out_p, out_stop - out_p);
               } else {
                  this.writeBuffLen = tr.finish(this.state, this.writeBuf, 0, this.writeBuf.length);

                  for (this.writeBuffOff = 0; this.writeBuffOff < this.writeBuffLen; out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++]) {
                     if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 32)) {
                        return this.suspendResult;
                     }
                  }
               }

               ip = 33;
               break;
            case 32:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 32)) {
                  return this.suspendResult;
               }

               for (out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++];
                  this.writeBuffOff <= this.writeBuffLen;
                  out_bytes[out_p++] = this.writeBuf[this.writeBuffOff++]
               ) {
                  if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 32)) {
                     return this.suspendResult;
                  }
               }

               ip = 33;
               break;
            case 34:
               if (tr.hasFinish()) {
                  if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 34)) {
                     return this.suspendResult;
                  }

                  ip = 31;
                  break;
               }
            case 33:
               SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.Finished, 33);
               return this.suspendResult;
            case 43:
               if (0 == SUSPEND_OBUF(this, out_stop, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, 43)) {
                  return this.suspendResult;
               }
            case 9:
               char_len = this.PREPARE_CHAR_LEN(char_len);
               outByteParam = this.PREPARE_OUT_BYTES(outByteParam);
               if (tr.maxOutput <= out_stop - out_p) {
                  int char_start = this.transcode_char_start(in_bytes, in_pos.p, inchar_start, in_p, char_len, outByteParam);
                  out_p += tr.startToOutput(this.state, outByteParam[0], char_start, char_len[0], out_bytes, out_p, out_stop - out_p);
                  ip = 1;
               } else {
                  int char_start = this.transcode_char_start(in_bytes, in_pos.p, inchar_start, in_p, char_len, outByteParam);
                  this.writeBuffLen = tr.startToOutput(this.state, outByteParam[0], char_start, char_len[0], this.writeBuf, 0, this.writeBuf.length);
                  this.writeBuffOff = 0;
                  ip = 11;
               }
               break;
            case 44:
               if (out_stop - out_p < 1) {
                  SUSPEND(this, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.DestinationBufferFull, 44);
                  return this.suspendResult;
               }
            case 8:
               char_len = this.PREPARE_CHAR_LEN(char_len);
               outByteParam = this.PREPARE_OUT_BYTES(outByteParam);
               if (tr.maxOutput <= out_stop - out_p) {
                  int char_start = this.transcode_char_start(in_bytes, in_pos.p, inchar_start, in_p, char_len, outByteParam);
                  out_p += tr.startInfoToOutput(this.state, outByteParam[0], char_start, char_len[0], this.nextInfo, out_bytes, out_p, out_stop - out_p);
                  ip = 1;
               } else {
                  int char_start = this.transcode_char_start(in_bytes, in_pos.p, inchar_start, in_p, char_len, outByteParam);
                  this.writeBuffLen = tr.startInfoToOutput(
                     this.state, outByteParam[0], char_start, char_len[0], this.nextInfo, this.writeBuf, 0, this.writeBuf.length
                  );
                  this.writeBuffOff = 0;
                  ip = 11;
               }
         }
      }
   }

   private int[] PREPARE_CHAR_LEN(int[] char_len) {
      if (char_len == null) {
         char_len = new int[]{0};
      } else {
         char_len[0] = 0;
      }

      return char_len;
   }

   private byte[][] PREPARE_OUT_BYTES(byte[][] outBytes) {
      if (outBytes == null) {
         outBytes = new byte[1][];
      } else {
         outBytes[0] = null;
      }

      return outBytes;
   }

   private int transcode_char_start(byte[] in_bytes, int in_start, int inchar_start, int in_p, int[] char_len_ptr, byte[][] retBytes) {
      int ptr;
      byte[] bytes;
      if (inchar_start - in_start < this.recognizedLength) {
         System.arraycopy(in_bytes, inchar_start, TRANSCODING_READBUF(this), this.recognizedLength, in_p - inchar_start);
         ptr = 0;
         bytes = TRANSCODING_READBUF(this);
      } else {
         ptr = inchar_start - this.recognizedLength;
         bytes = in_bytes;
      }

      char_len_ptr[0] = this.recognizedLength + (in_p - inchar_start);
      retBytes[0] = bytes;
      return ptr;
   }

   private static int SUSPEND(
      Transcoding tc, byte[] in_bytes, int in_p, int inchar_start, Ptr in_pos, Ptr out_pos, int out_p, int readagain_len, EConvResult ret, int ip
   ) {
      prepareToSuspend(tc, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, ip);
      tc.suspendResult = ret;
      return 0;
   }

   private static void prepareToSuspend(
      Transcoding tc, byte[] in_bytes, int in_p, int inchar_start, Ptr in_pos, Ptr out_pos, int out_p, int readagain_len, int ip
   ) {
      tc.resumePosition = ip;
      int recognizedLength = tc.recognizedLength;
      if (in_p - inchar_start > 0) {
         System.arraycopy(in_bytes, inchar_start, tc.readBuf, recognizedLength, in_p - inchar_start);
      }

      in_pos.p = in_p;
      out_pos.p = out_p;
      recognizedLength += in_p - inchar_start;
      if (readagain_len != 0) {
         recognizedLength -= readagain_len;
         tc.readAgainLength = readagain_len;
      }

      tc.recognizedLength = recognizedLength;
   }

   private static int SUSPEND_OBUF(
      Transcoding tc, int out_stop, byte[] in_bytes, int in_p, int inchar_start, Ptr in_pos, Ptr out_pos, int out_p, int readagain_len, int ip
   ) {
      return out_stop - out_p < 1
         ? SUSPEND(tc, in_bytes, in_p, inchar_start, in_pos, out_pos, out_p, readagain_len, EConvResult.DestinationBufferFull, ip)
         : ip;
   }

   private static int SUSPEND_AFTER_OUTPUT(
      Transcoding tc, int opt, byte[] in_bytes, int in_p_offset, int inchar_start_offset, Ptr in_pos, Ptr out_pos, int out_p_offset, int readagain_len, int ip
   ) {
      return checkAfterOutput(opt, out_pos, out_p_offset)
         ? SUSPEND(tc, in_bytes, in_p_offset, inchar_start_offset, in_pos, out_pos, out_p_offset, readagain_len, EConvResult.AfterOutput, ip)
         : ip;
   }

   private static boolean checkAfterOutput(int opt, Ptr out_pos, int out_p_offset) {
      return (opt & 131072) != 0 && out_pos.p != out_p_offset;
   }

   private static byte[] TRANSCODING_READBUF(Transcoding tc) {
      return tc.readBuf;
   }

   public static int WORDINDEX2INFO(int widx) {
      return widx << 2;
   }

   private static int INFO2WORDINDEX(int info) {
      return info >>> 2;
   }

   private static int BYTE_ADDR(int index) {
      return index;
   }

   private static int WORD_ADDR(int index) {
      return INFO2WORDINDEX(index);
   }

   private static int BL_BASE(Transcoding tc) {
      return BYTE_ADDR(BYTE_LOOKUP_BASE(tc, WORD_ADDR(tc.nextTable)));
   }

   private static int BL_INFO(Transcoding tc) {
      return WORD_ADDR(BYTE_LOOKUP_INFO(tc, WORD_ADDR(tc.nextTable)));
   }

   private static int BYTE_LOOKUP_BASE(Transcoding tc, int bl) {
      return tc.transcoder.intArray[bl];
   }

   private static int BYTE_LOOKUP_INFO(Transcoding tc, int bl) {
      return tc.transcoder.intArray[bl + 1];
   }

   public static int BL_MIN_BYTE(Transcoding tc) {
      return tc.transcoder.byteArray[BL_BASE(tc)] & 0xFF;
   }

   public static int BL_MAX_BYTE(Transcoding tc) {
      return tc.transcoder.byteArray[BL_BASE(tc) + 1] & 0xFF;
   }

   public static int BL_OFFSET(Transcoding tc, int b) {
      return tc.transcoder.byteArray[BL_BASE(tc) + 2 + b - BL_MIN_BYTE(tc)] & 0xFF;
   }

   public static int BL_ACTION(Transcoding tc, byte b) {
      return tc.transcoder.intArray[BL_INFO(tc) + BL_OFFSET(tc, b & 0xFF)];
   }

   public static byte getGB4bt0(int a) {
      return (byte)(a >>> 8);
   }

   public static byte getGB4bt1(int a) {
      return (byte)(a >>> 24 & 15 | 48);
   }

   public static byte getGB4bt2(int a) {
      return (byte)(a >>> 16);
   }

   public static byte getGB4bt3(int a) {
      return (byte)(a >>> 28 & 15 | 48);
   }

   public static byte getBT1(int a) {
      return (byte)(a >>> 8);
   }

   public static byte getBT2(int a) {
      return (byte)(a >>> 16);
   }

   public static byte getBT3(int a) {
      return (byte)(a >>> 24);
   }

   public static byte getBT0(int a) {
      return (byte)(a >>> 5 & 15 | 48);
   }
}
