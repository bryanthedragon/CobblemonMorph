package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.codec.BinaryDecoder;
import com.oracle.truffle.js.codec.NodeDecoder;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class JSNodeDecoder {
   public static final int BREAK_TARGET_LABEL = 1;
   public static final int BREAK_TARGET_SWITCH = 2;
   public static final int CONTINUE_TARGET_LOOP = 3;
   public static final int CONTINUE_TARGET_UNLABELED_LOOP = 4;
   private static final boolean VERBOSE = false;
   private static final NodeDecoder<NodeFactory> GEN = NodeFactoryDecoderGen.create();
   private static final Object[] SINGLETONS = new Object[]{null, Undefined.instance, Null.instance, Dead.instance()};

   public static int getSingletonIndex(Object singleton) {
      return Arrays.asList(SINGLETONS).indexOf(singleton);
   }

   public static int getChecksum() {
      return GEN.getChecksum();
   }

   public Object decodeNode(NodeDecoder.DecoderState state, NodeFactory nodeFactory, JSContext context, Source source) {
      while (state.hasRemaining()) {
         JSNodeDecoder.Bytecode bc = JSNodeDecoder.Bytecode.bcValues[state.getBytecode()];
         switch (bc) {
            case ID_NOP:
               break;
            case ID_NODE:
               Object node = GEN.decodeNode(state, nodeFactory);
               int dest = state.getReg();
               if (dest >= 0) {
                  state.setObjReg(dest, node);
               }
               break;
            case ID_RETURN:
               return state.getObject();
            case ID_LDC_INT:
               storeResult(state, state.getInt());
               break;
            case ID_LDC_LONG:
               storeResult(state, state.getLong());
               break;
            case ID_LDC_BOOLEAN:
               storeResult(state, state.getBoolean());
               break;
            case ID_LDC_DOUBLE:
               storeResult(state, state.getDouble());
               break;
            case ID_LDC_ENUM:
               storeResult(state, GEN.getClasses()[state.getInt()].getEnumConstants()[state.getInt()]);
               break;
            case ID_LDC_STRING:
               storeResult(state, state.getString());
               break;
            case ID_LDC_SINGLETON:
               storeResult(state, SINGLETONS[state.getInt()]);
               break;
            case ID_LDC_BIGINT:
               storeResult(state, BigInt.valueOf(state.getString().toJavaStringUncached()));
               break;
            case ID_MOV:
               state.setObjReg(state.getReg(), state.getObjReg(state.getReg()));
               break;
            case ID_LD_ARG:
               int argIndex = state.getInt();
               Object argument;
               if (argIndex == -1) {
                  argument = context;
               } else if (argIndex == -2) {
                  argument = source;
               } else {
                  argument = state.getArgument(argIndex);
               }

               storeResult(state, argument);
               break;
            case ID_COLLECT_ARRAY: {
               int componentTypeIndex = state.getInt();
               int length = state.getInt();
               Object array = Array.newInstance(GEN.getClasses()[componentTypeIndex], length);
               if (array instanceof Object[]) {
                  Object[] objArray = (Object[])array;

                  for (int i = 0; i < length; i++) {
                     Object value = state.getObject();
                     objArray[i] = value;
                  }
               } else {
                  for (int i = 0; i < length; i++) {
                     Object value = state.getObject();
                     Array.set(array, i, value);
                  }
               }

               storeResult(state, array);
               break;
            }
            case ID_COLLECT_LIST: {
               int length = state.getInt();
               ArrayList<Object> array = new ArrayList<>(length);

               for (int i = 0; i < length; i++) {
                  array.add(state.getObject());
               }

               storeResult(state, array);
               break;
            }
            case ID_CALL_TARGET:
               storeResult(state, ((RootNode)state.getObject()).getCallTarget());
               break;
            case ID_FRAME_DESCRIPTOR: {
               int numberOfIndexedSlots = state.getInt();
               FrameDescriptor.Builder b = FrameDescriptor.newBuilder(numberOfIndexedSlots).defaultValue(Undefined.instance);
               Object[] names = new Object[numberOfIndexedSlots];
               int[] flags = new int[numberOfIndexedSlots];
               byte[] tags = new byte[numberOfIndexedSlots];

               for (int i = 0; i < numberOfIndexedSlots; i++) {
                  names[i] = state.getObject();
               }

               for (int i = 0; i < numberOfIndexedSlots; i++) {
                  flags[i] = state.getInt();
               }

               for (int i = 0; i < numberOfIndexedSlots; i++) {
                  tags[i] = (byte)state.getInt();
               }

               for (int i = 0; i < numberOfIndexedSlots; i++) {
                  b.addSlot(FrameSlotKind.fromTag(tags[i]), names[i], flags[i]);
               }

               FrameDescriptor frameDescriptor = b.build();
               storeResult(state, frameDescriptor);
               break;
            }
            case ID_JSFRAME_SLOT: {
               Object identifier = state.getObject();
               int index = state.getInt();
               int flags = state.getInt();
               int tag = state.getInt();
               FrameSlotKind kind = FrameSlotKind.fromTag((byte)tag);
               JSFrameSlot frameSlot = new JSFrameSlot(index, identifier, flags, kind);
               storeResult(state, frameSlot);
               break;
            }
            case ID_SOURCE_SECTION:
               Source src = (Source)state.getObject();
               int charIndex = state.getInt();
               int charLength = state.getInt();
               SourceSection sourceSection;
               if (charIndex >= 0 && charLength >= 0 && (src.getCharacters().length() != 0 || charIndex + charLength <= 0)) {
                  sourceSection = src.createSection(charIndex, charLength);
               } else {
                  sourceSection = src.createUnavailableSection();
               }

               storeResult(state, sourceSection);
               break;
            case ID_FUNCTION_DATA: {
               JSContext ctx = (JSContext)state.getObject();
               int length = state.getInt();
               TruffleString functionName = state.getString();
               int flags = state.getInt32();
               JSFunctionData functionData = JSFunctionData.create(ctx, null, null, null, length, functionName, flags);
               storeResult(state, functionData);
               break;
            }
            case ID_FUNCTION_DATA_NAME_FIXUP: {
               JSFunctionData functionData = (JSFunctionData)state.getObject();
               TruffleString name = state.getString();
               functionData.setName(name);
               break;
            }
            case ID_JUMP_TARGET:
               storeResult(state, createJumpTarget(state.getInt()));
               break;
            case ID_CALL_EXTRACTED: {
               int position = state.getInt32();
               Object[] arguments = getObjectArray(state);
               ByteBuffer buffer = state.getBuffer().duplicate().position(position);
               NodeDecoder.DecoderState extracted = new NodeDecoder.DecoderState(new BinaryDecoder(buffer), arguments);
               storeResult(state, this.decodeNode(extracted, nodeFactory, context, source));
               break;
            }
            case ID_CALL_EXTRACTED_LAZY: {
               int bcPos = state.getBuffer().position() - 1;
               int position = state.getInt32();
               JSFunctionData functionData = (JSFunctionData)state.getObject();
               final Object[] arguments = getObjectArray(state);
               final ByteBuffer buffer = state.getBuffer().duplicate().position(position);
               functionData.setLazyInit(new JSFunctionData.Initializer() {
                  @Override
                  public void initializeRoot(JSFunctionData fd) {
                     NodeDecoder.DecoderState extractedx = new NodeDecoder.DecoderState(new BinaryDecoder(buffer), arguments);
                     JSNodeDecoder.this.decodeNode(extractedx, nodeFactory, context, source);
                  }
               });
               break;
            }
            case ID_NODE_SOURCE_SECTION_FIXUP:
               JavaScriptNode jsnodex = (JavaScriptNode)state.getObject();
               int charIndex = state.getInt();
               int charLength = state.getInt();
               if (charIndex >= 0 && charLength >= 0 && (source.getCharacters().length() != 0 || charIndex + charLength <= 0)) {
                  jsnodex.setSourceSection(source, charIndex, charLength);
                  break;
               }

               jsnodex.setSourceSection(source.createUnavailableSection());
               break;
            case ID_NODE_TAGS_FIXUP:
               JavaScriptNode jsnode = (JavaScriptNode)state.getObject();
               boolean hasStatementTag = state.getBoolean();
               boolean hasCallTag = state.getBoolean();
               boolean hasExpressionTag = state.getBoolean();
               boolean hasRootBodyTag = state.getBoolean();
               if (hasStatementTag) {
                  jsnode.addStatementTag();
               }

               if (hasCallTag) {
                  jsnode.addCallTag();
               }

               if (hasExpressionTag) {
                  jsnode.addExpressionTag();
               }

               if (hasRootBodyTag) {
                  jsnode.addRootBodyTag();
               }
               break;
            default:
               throw new IllegalStateException("invalid bytecode " + bc);
         }
      }

      throw new IllegalStateException("reached end of buffer without return");
   }

   private static void storeResult(NodeDecoder.DecoderState state, Object value) {
      state.setObjReg(state.getReg(), value);
   }

   private static BreakTarget createJumpTarget(int type) {
      switch (type) {
         case 1:
            return BreakTarget.forLabel(null, -1);
         case 2:
            return BreakTarget.forSwitch();
         case 3:
            return ContinueTarget.forLoop(null, -1);
         case 4:
            return ContinueTarget.forUnlabeledLoop();
         default:
            throw new IllegalStateException("invalid jump target");
      }
   }

   private static Object[] getObjectArray(NodeDecoder.DecoderState state) {
      int length = state.getInt();
      Object[] array = new Object[length];

      for (int i = 0; i < length; i++) {
         array[i] = state.getObject();
      }

      return array;
   }

   public static enum Bytecode {
      ID_NOP,
      ID_NODE,
      ID_RETURN,
      ID_LDC_INT,
      ID_LDC_LONG,
      ID_LDC_BOOLEAN,
      ID_LDC_DOUBLE,
      ID_LDC_ENUM,
      ID_LDC_STRING,
      ID_LDC_SINGLETON,
      ID_LDC_BIGINT,
      ID_LD_ARG,
      ID_MOV,
      ID_COLLECT_ARRAY,
      ID_COLLECT_LIST,
      ID_CALL_TARGET,
      ID_FRAME_DESCRIPTOR,
      ID_JSFRAME_SLOT,
      ID_SOURCE_SECTION,
      ID_FUNCTION_DATA,
      ID_FUNCTION_DATA_NAME_FIXUP,
      ID_JUMP_TARGET,
      ID_CALL_EXTRACTED,
      ID_CALL_EXTRACTED_LAZY,
      ID_NODE_SOURCE_SECTION_FIXUP,
      ID_NODE_TAGS_FIXUP;

      static final JSNodeDecoder.Bytecode[] bcValues = values();
   }
}
