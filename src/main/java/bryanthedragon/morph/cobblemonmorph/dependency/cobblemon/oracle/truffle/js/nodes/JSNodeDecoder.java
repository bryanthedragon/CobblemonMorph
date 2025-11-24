
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.codec.BinaryDecoder;
import com.oracle.truffle.js.codec.NodeDecoder;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.NodeFactoryDecoderGen;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.lang.reflect.Array;
import java.nio.Buffer;
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
        block28: while (state.hasRemaining()) {
            Bytecode bc = Bytecode.bcValues[state.getBytecode()];
            switch (bc) {
                case ID_NOP: {
                    continue block28;
                }
                case ID_NODE: {
                    Object node = GEN.decodeNode(state, nodeFactory);
                    int dest = state.getReg();
                    if (dest < 0) continue block28;
                    state.setObjReg(dest, node);
                    continue block28;
                }
                case ID_RETURN: {
                    return state.getObject();
                }
                case ID_LDC_INT: {
                    JSNodeDecoder.storeResult(state, state.getInt());
                    continue block28;
                }
                case ID_LDC_LONG: {
                    JSNodeDecoder.storeResult(state, state.getLong());
                    continue block28;
                }
                case ID_LDC_BOOLEAN: {
                    JSNodeDecoder.storeResult(state, state.getBoolean());
                    continue block28;
                }
                case ID_LDC_DOUBLE: {
                    JSNodeDecoder.storeResult(state, state.getDouble());
                    continue block28;
                }
                case ID_LDC_ENUM: {
                    JSNodeDecoder.storeResult(state, GEN.getClasses()[state.getInt()].getEnumConstants()[state.getInt()]);
                    continue block28;
                }
                case ID_LDC_STRING: {
                    JSNodeDecoder.storeResult(state, state.getString());
                    continue block28;
                }
                case ID_LDC_SINGLETON: {
                    JSNodeDecoder.storeResult(state, SINGLETONS[state.getInt()]);
                    continue block28;
                }
                case ID_LDC_BIGINT: {
                    JSNodeDecoder.storeResult(state, BigInt.valueOf(state.getString().toJavaStringUncached()));
                    continue block28;
                }
                case ID_MOV: {
                    state.setObjReg(state.getReg(), state.getObjReg(state.getReg()));
                    continue block28;
                }
                case ID_LD_ARG: {
                    int argIndex = state.getInt();
                    Object argument = argIndex == -1 ? context : (argIndex == -2 ? source : state.getArgument(argIndex));
                    JSNodeDecoder.storeResult(state, argument);
                    continue block28;
                }
                case ID_COLLECT_ARRAY: {
                    int componentTypeIndex = state.getInt();
                    int length = state.getInt();
                    Object array = Array.newInstance(GEN.getClasses()[componentTypeIndex], length);
                    if (array instanceof Object[]) {
                        Object[] objArray = (Object[])array;
                        for (int i = 0; i < length; ++i) {
                            Object value2;
                            objArray[i] = value2 = state.getObject();
                        }
                    } else {
                        for (int i = 0; i < length; ++i) {
                            Object value3 = state.getObject();
                            Array.set(array, i, value3);
                        }
                    }
                    JSNodeDecoder.storeResult(state, array);
                    continue block28;
                }
                case ID_COLLECT_LIST: {
                    int length = state.getInt();
                    ArrayList<Object> array = new ArrayList<Object>(length);
                    for (int i = 0; i < length; ++i) {
                        array.add(state.getObject());
                    }
                    JSNodeDecoder.storeResult(state, array);
                    continue block28;
                }
                case ID_CALL_TARGET: {
                    JSNodeDecoder.storeResult(state, ((RootNode)state.getObject()).getCallTarget());
                    continue block28;
                }
                case ID_FRAME_DESCRIPTOR: {
                    int i;
                    int numberOfIndexedSlots = state.getInt();
                    FrameDescriptor.Builder b = FrameDescriptor.newBuilder(numberOfIndexedSlots).defaultValue(Undefined.instance);
                    Object[] names = new Object[numberOfIndexedSlots];
                    int[] flags = new int[numberOfIndexedSlots];
                    byte[] tags = new byte[numberOfIndexedSlots];
                    for (i = 0; i < numberOfIndexedSlots; ++i) {
                        names[i] = state.getObject();
                    }
                    for (i = 0; i < numberOfIndexedSlots; ++i) {
                        flags[i] = state.getInt();
                    }
                    for (i = 0; i < numberOfIndexedSlots; ++i) {
                        tags[i] = (byte)state.getInt();
                    }
                    for (i = 0; i < numberOfIndexedSlots; ++i) {
                        b.addSlot(FrameSlotKind.fromTag(tags[i]), names[i], flags[i]);
                    }
                    FrameDescriptor frameDescriptor = b.build();
                    JSNodeDecoder.storeResult(state, frameDescriptor);
                    continue block28;
                }
                case ID_JSFRAME_SLOT: {
                    Object identifier = state.getObject();
                    int index = state.getInt();
                    int flags = state.getInt();
                    int tag = state.getInt();
                    FrameSlotKind kind = FrameSlotKind.fromTag((byte)tag);
                    JSFrameSlot frameSlot = new JSFrameSlot(index, identifier, flags, kind);
                    JSNodeDecoder.storeResult(state, frameSlot);
                    continue block28;
                }
                case ID_SOURCE_SECTION: {
                    Source src = (Source)state.getObject();
                    int charIndex = state.getInt();
                    int charLength = state.getInt();
                    SourceSection sourceSection = charIndex < 0 || charLength < 0 || src.getCharacters().length() == 0 && charIndex + charLength > 0 ? src.createUnavailableSection() : src.createSection(charIndex, charLength);
                    JSNodeDecoder.storeResult(state, sourceSection);
                    continue block28;
                }
                case ID_FUNCTION_DATA: {
                    JSContext ctx = (JSContext)state.getObject();
                    int length = state.getInt();
                    TruffleString functionName = state.getString();
                    int flags = state.getInt32();
                    JSFunctionData functionData = JSFunctionData.create(ctx, null, null, null, length, functionName, flags);
                    JSNodeDecoder.storeResult(state, functionData);
                    continue block28;
                }
                case ID_FUNCTION_DATA_NAME_FIXUP: {
                    JSFunctionData functionData = (JSFunctionData)state.getObject();
                    TruffleString name = state.getString();
                    functionData.setName(name);
                    continue block28;
                }
                case ID_JUMP_TARGET: {
                    JSNodeDecoder.storeResult(state, JSNodeDecoder.createJumpTarget(state.getInt()));
                    continue block28;
                }
                case ID_CALL_EXTRACTED: {
                    int position = state.getInt32();
                    Object[] arguments = JSNodeDecoder.getObjectArray(state);
                    Buffer buffer = state.getBuffer().duplicate().position(position);
                    NodeDecoder.DecoderState extracted = new NodeDecoder.DecoderState(new BinaryDecoder((ByteBuffer)buffer), arguments);
                    JSNodeDecoder.storeResult(state, this.decodeNode(extracted, nodeFactory, context, source));
                    continue block28;
                }
                case ID_CALL_EXTRACTED_LAZY: {
                    int bcPos = state.getBuffer().position() - 1;
                    int position = state.getInt32();
                    JSFunctionData functionData = (JSFunctionData)state.getObject();
                    Object[] arguments = JSNodeDecoder.getObjectArray(state);
                    Buffer buffer = state.getBuffer().duplicate().position(position);
                    functionData.setLazyInit(new JSFunctionData.Initializer((ByteBuffer)buffer, arguments, nodeFactory, context, source){
                        final /* synthetic */ ByteBuffer val$buffer;
                        final /* synthetic */ Object[] val$arguments;
                        final /* synthetic */ NodeFactory val$nodeFactory;
                        final /* synthetic */ JSContext val$context;
                        final /* synthetic */ Source val$source;
                        {
                            this.val$buffer = val$buffer;
                            this.val$arguments = val$arguments;
                            this.val$nodeFactory = val$nodeFactory;
                            this.val$context = val$context;
                            this.val$source = val$source;
                        }

                        @Override
                        public void initializeRoot(JSFunctionData fd) {
                            NodeDecoder.DecoderState extracted = new NodeDecoder.DecoderState(new BinaryDecoder(this.val$buffer), this.val$arguments);
                            JSNodeDecoder.this.decodeNode(extracted, this.val$nodeFactory, this.val$context, this.val$source);
                        }
                    });
                    continue block28;
                }
                case ID_NODE_SOURCE_SECTION_FIXUP: {
                    JavaScriptNode jsnode = (JavaScriptNode)state.getObject();
                    int charIndex = state.getInt();
                    int charLength = state.getInt();
                    if (charIndex < 0 || charLength < 0 || source.getCharacters().length() == 0 && charIndex + charLength > 0) {
                        jsnode.setSourceSection(source.createUnavailableSection());
                        continue block28;
                    }
                    jsnode.setSourceSection(source, charIndex, charLength);
                    continue block28;
                }
                case ID_NODE_TAGS_FIXUP: {
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
                    if (!hasRootBodyTag) continue block28;
                    jsnode.addRootBodyTag();
                    continue block28;
                }
            }
            throw new IllegalStateException("invalid bytecode " + bc);
        }
        throw new IllegalStateException("reached end of buffer without return");
    }

    private static void storeResult(NodeDecoder.DecoderState state, Object value2) {
        state.setObjReg(state.getReg(), value2);
    }

    private static BreakTarget createJumpTarget(int type) {
        switch (type) {
            case 1: {
                return BreakTarget.forLabel(null, -1);
            }
            case 2: {
                return BreakTarget.forSwitch();
            }
            case 3: {
                return ContinueTarget.forLoop(null, -1);
            }
            case 4: {
                return ContinueTarget.forUnlabeledLoop();
            }
        }
        throw new IllegalStateException("invalid jump target");
    }

    private static Object[] getObjectArray(NodeDecoder.DecoderState state) {
        int length = state.getInt();
        Object[] array = new Object[length];
        for (int i = 0; i < length; ++i) {
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

        static final Bytecode[] bcValues;

        static {
            bcValues = Bytecode.values();
        }
    }
}

