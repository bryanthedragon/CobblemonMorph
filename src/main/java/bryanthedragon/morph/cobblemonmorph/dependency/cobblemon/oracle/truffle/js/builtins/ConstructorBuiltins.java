
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.api.utilities.AssumedValue;
import com.oracle.truffle.js.builtins.ConstructorBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.JSConstructTypedArrayNodeGen;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.ArrayLiteralNode;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeFromConstructorNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNode;
import com.oracle.truffle.js.nodes.access.InstallErrorCauseNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.OrdinaryCreateFromConstructorNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSNumericToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.ToArrayLengthNode;
import com.oracle.truffle.js.nodes.function.EvalNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.intl.CreateRegExpNode;
import com.oracle.truffle.js.nodes.intl.InitializeCollatorNode;
import com.oracle.truffle.js.nodes.intl.InitializeDateTimeFormatNode;
import com.oracle.truffle.js.nodes.intl.InitializeDisplayNamesNode;
import com.oracle.truffle.js.nodes.intl.InitializeListFormatNode;
import com.oracle.truffle.js.nodes.intl.InitializeLocaleNode;
import com.oracle.truffle.js.nodes.intl.InitializeNumberFormatNode;
import com.oracle.truffle.js.nodes.intl.InitializePluralRulesNode;
import com.oracle.truffle.js.nodes.intl.InitializeRelativeTimeFormatNode;
import com.oracle.truffle.js.nodes.intl.InitializeSegmenterNode;
import com.oracle.truffle.js.nodes.promise.PromiseResolveThenableNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.wasm.ExportByteSourceNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyIndexOrSizeNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSDateObject;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSWeakMap;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.builtins.JSWeakSet;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollatorObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormatObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormatObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobal;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstance;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemory;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTable;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyValueTypes;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.LRUCache;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class ConstructorBuiltins
extends JSBuiltinsContainer.SwitchEnum<Constructor> {
    public static final JSBuiltinsContainer BUILTINS = new ConstructorBuiltins();

    protected ConstructorBuiltins() {
        super(null, Constructor.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Constructor builtinEnum) {
        switch (builtinEnum) {
            case Array: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructArrayNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructArrayNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case Boolean: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructBooleanNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructBooleanNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallBooleanNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case Date: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructDateNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructDateNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallDateNodeGen.create(context, builtin, ConstructorBuiltins.args().createArgumentNodes(context));
            }
            case RegExp: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructRegExpNodeGen.create(context, builtin, false, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructRegExpNodeGen.create(context, builtin, false, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltinsFactory.ConstructRegExpNodeGen.create(context, builtin, true, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context));
            }
            case String: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructStringNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructStringNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallStringNodeGen.create(context, builtin, ConstructorBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case WeakRef: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructWeakRefNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWeakRefNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case FinalizationRegistry: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructFinalizationRegistryNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructFinalizationRegistryNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Collator: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructCollatorNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructCollatorNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallCollatorNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case ListFormat: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructListFormatNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructListFormatNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case NumberFormat: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructNumberFormatNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructNumberFormatNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallNumberFormatNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case PluralRules: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructPluralRulesNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructPluralRulesNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case DateTimeFormat: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructDateTimeFormatNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructDateTimeFormatNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallDateTimeFormatNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case RelativeTimeFormat: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructRelativeTimeFormatNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructRelativeTimeFormatNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Segmenter: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructSegmenterNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructSegmenterNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case DisplayNames: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructDisplayNamesNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructDisplayNamesNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Locale: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructLocaleNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructLocaleNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Object: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructObjectNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructObjectNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case Number: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructNumberNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructNumberNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context))) : ConstructorBuiltinsFactory.CallNumberNodeGen.create(context, builtin, ConstructorBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case BigInt: {
                return construct ? ConstructorBuiltinsFactory.ConstructBigIntNodeGen.create(context, builtin, ConstructorBuiltins.args().createArgumentNodes(context)) : ConstructorBuiltinsFactory.CallBigIntNodeGen.create(context, builtin, ConstructorBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case Function: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, false, false, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, false, false, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case ArrayBuffer: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructArrayBufferNodeGen.create(context, builtin, false, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructArrayBufferNodeGen.create(context, builtin, false, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Error: 
            case RangeError: 
            case TypeError: 
            case ReferenceError: 
            case SyntaxError: 
            case EvalError: 
            case URIError: 
            case CompileError: 
            case LinkError: 
            case RuntimeError: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructErrorNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructErrorNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context));
            }
            case AggregateError: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(3).createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(3).createArgumentNodes(context));
            }
            case TypedArray: {
                return ConstructorBuiltinsFactory.CallTypedArrayNodeGen.create(context, builtin, ConstructorBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case Int8Array: 
            case Uint8Array: 
            case Uint8ClampedArray: 
            case Int16Array: 
            case Uint16Array: 
            case Int32Array: 
            case Uint32Array: 
            case Float32Array: 
            case Float64Array: 
            case BigInt64Array: 
            case BigUint64Array: {
                if (construct) {
                    if (newTarget) {
                        return JSConstructTypedArrayNodeGen.create(context, builtin, ConstructorBuiltins.args().newTarget().fixedArgs(3).createArgumentNodes(context));
                    }
                    return JSConstructTypedArrayNodeGen.create(context, builtin, ConstructorBuiltins.args().function().fixedArgs(3).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case DataView: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructDataViewNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(3).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructDataViewNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(3).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Map: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructMapNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructMapNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Set: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructSetNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructSetNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case WeakMap: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case WeakSet: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructWeakSetNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWeakSetNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case GeneratorFunction: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, true, false, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, true, false, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case SharedArrayBuffer: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructArrayBufferNodeGen.create(context, builtin, true, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructArrayBufferNodeGen.create(context, builtin, true, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case AsyncFunction: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, false, true, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, false, true, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case AsyncGeneratorFunction: {
                if (newTarget) {
                    return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, true, true, true, ConstructorBuiltins.args().newTarget().varArgs().createArgumentNodes(context));
                }
                return ConstructorBuiltinsFactory.ConstructFunctionNodeGen.create(context, builtin, true, true, false, ConstructorBuiltins.args().function().varArgs().createArgumentNodes(context));
            }
            case Symbol: {
                return construct ? ConstructorBuiltinsFactory.ConstructSymbolNodeGen.create(context, builtin, ConstructorBuiltins.args().createArgumentNodes(context)) : ConstructorBuiltinsFactory.CallSymbolNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case Proxy: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructJSProxyNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(3).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructJSProxyNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(3).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Promise: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.PromiseConstructorNodeGen.create(context, builtin, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.PromiseConstructorNodeGen.create(context, builtin, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case PlainTime: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalPlainTimeNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(6).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalPlainTimeNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(6).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case PlainDate: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case PlainDateTime: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(10).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(10).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Duration: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalDurationNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(10).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalDurationNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(10).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Calendar: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalCalendarNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalCalendarNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case PlainYearMonth: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case PlainMonthDay: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Instant: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalInstantNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalInstantNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case TimeZone: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalTimeZoneNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalTimeZoneNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case ZonedDateTime: {
                if (construct) {
                    return newTarget ? ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(4).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(4).createArgumentNodes(context));
                }
                return ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case JSAdapter: {
                return ConstructorBuiltinsFactory.ConstructJSAdapterNodeGen.create(context, builtin, ConstructorBuiltins.args().fixedArgs(3).createArgumentNodes(context));
            }
            case JavaImporter: {
                return ConstructorBuiltinsFactory.ConstructJavaImporterNodeGen.create(context, builtin, ConstructorBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case Global: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructWebAssemblyGlobalNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWebAssemblyGlobalNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Instance: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructWebAssemblyInstanceNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(2).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWebAssemblyInstanceNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(2).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Memory: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructWebAssemblyMemoryNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWebAssemblyMemoryNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Module: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructWebAssemblyModuleNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWebAssemblyModuleNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
            case Table: {
                return construct ? (newTarget ? ConstructorBuiltinsFactory.ConstructWebAssemblyTableNodeGen.create(context, builtin, true, ConstructorBuiltins.args().newTarget().fixedArgs(1).createArgumentNodes(context)) : ConstructorBuiltinsFactory.ConstructWebAssemblyTableNodeGen.create(context, builtin, false, ConstructorBuiltins.args().function().fixedArgs(1).createArgumentNodes(context))) : ConstructorBuiltins.createCallRequiresNew(context, builtin);
            }
        }
        return null;
    }

    private static CallRequiresNewNode createCallRequiresNew(JSContext context, JSBuiltin builtin) {
        return ConstructorBuiltinsFactory.CallRequiresNewNodeGen.create(context, builtin, ConstructorBuiltins.args().createArgumentNodes(context));
    }

    public static abstract class ConstructWebAssemblyGlobalNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        JSToStringNode toStringNode = JSToStringNode.create();
        @Node.Child
        JSToBooleanNode toBooleanNode = JSToBooleanNode.create();
        @Node.Child
        PropertyGetNode getValueNode;
        @Node.Child
        PropertyGetNode getMutableNode;
        @Node.Child
        ToWebAssemblyValueNode toWebAssemblyValueNode;
        @Node.Child
        InteropLibrary globalAllocLib;

        public ConstructWebAssemblyGlobalNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.getValueNode = PropertyGetNode.create(Strings.VALUE, context);
            this.getMutableNode = PropertyGetNode.create(Strings.MUTABLE, context);
            this.toWebAssemblyValueNode = ToWebAssemblyValueNode.create();
            this.globalAllocLib = InteropLibrary.getFactory().createDispatched(5);
        }

        @Specialization
        protected JSDynamicObject constructGlobal(JSDynamicObject newTarget, Object descriptor, Object value2) {
            Object wasmGlobal;
            Object webAssemblyValue;
            if (!this.isObjectNode.executeBoolean(descriptor)) {
                throw Errors.createTypeError("WebAssembly.Global(): Argument 0 must be a global descriptor", (Node)this);
            }
            boolean mutable = this.toBooleanNode.executeBoolean(this.getMutableNode.getValue(descriptor));
            TruffleString valueType = this.toStringNode.executeString(this.getValueNode.getValue(descriptor));
            if (!JSWebAssemblyValueTypes.isValueType(valueType)) {
                throw Errors.createTypeError("WebAssembly.Global(): Descriptor property 'value' must be a WebAssembly type (i32, i64, f32, f64)", (Node)this);
            }
            if (value2 == Undefined.instance) {
                webAssemblyValue = 0;
            } else {
                if (!this.getContext().getContextOptions().isWasmBigInt() && JSWebAssemblyValueTypes.isI64(valueType)) {
                    throw Errors.createTypeError("WebAssembly.Global(): Can't set the value of i64 WebAssembly.Global", (Node)this);
                }
                webAssemblyValue = this.toWebAssemblyValueNode.execute(value2, valueType);
            }
            JSRealm realm = this.getRealm();
            try {
                Object createGlobal = realm.getWASMGlobalAlloc();
                wasmGlobal = this.globalAllocLib.execute(createGlobal, valueType, mutable, webAssemblyValue);
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
            return this.swapPrototype(JSWebAssemblyGlobal.create(this.getContext(), realm, wasmGlobal, valueType, mutable), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWebAssemblyGlobalPrototype();
        }
    }

    public static abstract class ConstructWebAssemblyTableNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        PropertyGetNode getElementNode;
        @Node.Child
        PropertyGetNode getInitialNode;
        @Node.Child
        PropertyGetNode getMaximumNode;
        @Node.Child
        JSToStringNode toStringNode;
        @Node.Child
        ToWebAssemblyIndexOrSizeNode toInitialSizeNode;
        @Node.Child
        ToWebAssemblyIndexOrSizeNode toMaximumSizeNode;
        @Node.Child
        InteropLibrary tableAllocLib;

        public ConstructWebAssemblyTableNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.getElementNode = PropertyGetNode.create(Strings.ELEMENT, context);
            this.getInitialNode = PropertyGetNode.create(Strings.INITIAL, context);
            this.getMaximumNode = PropertyGetNode.create(Strings.MAXIMUM, context);
            this.toStringNode = JSToStringNode.create();
            this.toInitialSizeNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Table(): Property 'initial'");
            this.toMaximumSizeNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Table(): Property 'maximum'");
            this.tableAllocLib = InteropLibrary.getFactory().createDispatched(5);
        }

        @Specialization
        protected JSDynamicObject constructTable(JSDynamicObject newTarget, Object descriptor, @Cached TruffleString.EqualNode stringEqualsNode) {
            Object wasmTable;
            int maximumInt;
            if (!this.isObjectNode.executeBoolean(descriptor)) {
                throw Errors.createTypeError("WebAssembly.Table(): Argument 0 must be a table descriptor", (Node)this);
            }
            TruffleString element = this.toStringNode.executeString(this.getElementNode.getValue(descriptor));
            if (!Strings.equals(stringEqualsNode, Strings.ANYFUNC, element)) {
                throw Errors.createTypeError("WebAssembly.Table(): Descriptor property 'element' must be 'anyfunc'", (Node)this);
            }
            Object initial = this.getInitialNode.getValue(descriptor);
            if (initial == Undefined.instance) {
                throw Errors.createTypeError("WebAssembly.Table(): Property 'initial' is required", (Node)this);
            }
            int initialInt = this.toInitialSizeNode.executeInt(initial);
            if (initialInt > 10000000) {
                throw Errors.createRangeErrorFormat("WebAssembly.Table(): Property 'initial': value %d is above the upper bound %d", this, initialInt, 10000000);
            }
            Object maximum = this.getMaximumNode.getValue(descriptor);
            if (maximum == Undefined.instance) {
                maximumInt = 10000000;
            } else {
                maximumInt = this.toMaximumSizeNode.executeInt(maximum);
                if (initialInt > maximumInt) {
                    throw Errors.createRangeErrorFormat("WebAssembly.Table(): Property 'maximum': value %d is below the lower bound %d", this, maximumInt, initialInt);
                }
                if (maximumInt > 10000000) {
                    throw Errors.createRangeErrorFormat("WebAssembly.Table(): Property 'maximum': value %d is above the upper bound %d", this, maximumInt, 10000000);
                }
            }
            JSRealm realm = this.getRealm();
            try {
                Object createTable = realm.getWASMTableAlloc();
                wasmTable = this.tableAllocLib.execute(createTable, initialInt, maximumInt);
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
            return this.swapPrototype(JSWebAssemblyTable.create(this.getContext(), realm, wasmTable), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWebAssemblyTablePrototype();
        }
    }

    public static abstract class ConstructWebAssemblyMemoryNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        PropertyGetNode getInitialNode;
        @Node.Child
        PropertyGetNode getMaximumNode;
        @Node.Child
        ToWebAssemblyIndexOrSizeNode toInitialSizeNode;
        @Node.Child
        ToWebAssemblyIndexOrSizeNode toMaximumSizeNode;
        @Node.Child
        InteropLibrary memAllocLib;

        public ConstructWebAssemblyMemoryNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.getInitialNode = PropertyGetNode.create(Strings.INITIAL, context);
            this.getMaximumNode = PropertyGetNode.create(Strings.MAXIMUM, context);
            this.toInitialSizeNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Memory(): Property 'initial'");
            this.toMaximumSizeNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Memory(): Property 'maximum'");
            this.memAllocLib = InteropLibrary.getFactory().createDispatched(5);
        }

        @Specialization
        protected JSDynamicObject constructMemory(JSDynamicObject newTarget, Object descriptor) {
            Object wasmMemory;
            int maximumInt;
            if (!this.isObjectNode.executeBoolean(descriptor)) {
                throw Errors.createTypeError("WebAssembly.Memory(): Argument 0 must be a memory descriptor", (Node)this);
            }
            Object initial = this.getInitialNode.getValue(descriptor);
            if (initial == Undefined.instance) {
                throw Errors.createTypeError("WebAssembly.Memory(): Property 'initial' is required", (Node)this);
            }
            int initialInt = this.toInitialSizeNode.executeInt(initial);
            if (initialInt > Short.MAX_VALUE) {
                throw Errors.createRangeErrorFormat("WebAssembly.Memory(): Property 'initial': value %d is above the upper bound %d", this, initialInt, Short.MAX_VALUE);
            }
            Object maximum = this.getMaximumNode.getValue(descriptor);
            if (maximum == Undefined.instance) {
                maximumInt = Short.MAX_VALUE;
            } else {
                maximumInt = this.toMaximumSizeNode.executeInt(maximum);
                if (maximumInt < initialInt) {
                    throw Errors.createRangeErrorFormat("WebAssembly.Memory(): Property 'maximum': value %d is below the lower bound %d", this, maximumInt, initialInt);
                }
                if (maximumInt > Short.MAX_VALUE) {
                    throw Errors.createRangeErrorFormat("WebAssembly.Memory(): Property 'maximum': value %d is above the upper bound %d", this, maximumInt, Short.MAX_VALUE);
                }
            }
            JSRealm realm = this.getRealm();
            try {
                Object createMemory = realm.getWASMMemAlloc();
                wasmMemory = this.memAllocLib.execute(createMemory, initialInt, maximumInt);
            }
            catch (AbstractTruffleException tex) {
                throw Errors.createRangeError("WebAssembly.Memory(): could not allocate memory");
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
            return this.swapPrototype(JSWebAssemblyMemory.create(this.getContext(), realm, wasmMemory), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWebAssemblyMemoryPrototype();
        }
    }

    public static abstract class ConstructWebAssemblyInstanceNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        InteropLibrary instantiateModuleLib = InteropLibrary.getFactory().createDispatched(5);

        public ConstructWebAssemblyInstanceNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructInstanceFromModule(JSDynamicObject newTarget, JSWebAssemblyModuleObject module, Object importObject) {
            Object wasmInstance;
            if (importObject != Undefined.instance && !this.isObjectNode.executeBoolean(importObject)) {
                throw Errors.createTypeError("WebAssembly.Instance(): Argument 1 must be an object", (Node)this);
            }
            Object wasmModule = module.getWASMModule();
            JSRealm realm = this.getRealm();
            try {
                Object wasmImportObject = JSWebAssemblyInstance.transformImportObject(this.getContext(), realm, wasmModule, importObject);
                Object instantiate = realm.getWASMModuleInstantiate();
                try {
                    wasmInstance = this.instantiateModuleLib.execute(instantiate, wasmModule, wasmImportObject);
                }
                catch (GraalJSException jsex) {
                    throw jsex;
                }
                catch (AbstractTruffleException tex) {
                    throw Errors.createLinkError(tex, this);
                }
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
            return this.swapPrototype(JSWebAssemblyInstance.create(this.getContext(), realm, wasmInstance, wasmModule), newTarget);
        }

        @Specialization(guards={"!isJSWebAssemblyModule(other)"})
        protected JSDynamicObject constructInstanceFromOther(JSDynamicObject newTarget, Object other, Object importObject) {
            throw Errors.createTypeError("WebAssembly.Instance(): Argument 0 must be a WebAssembly.Module");
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWebAssemblyInstancePrototype();
        }
    }

    public static abstract class ConstructWebAssemblyModuleNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        ExportByteSourceNode exportByteSourceNode;
        @Node.Child
        InteropLibrary decodeModuleLib;

        public ConstructWebAssemblyModuleNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.exportByteSourceNode = ExportByteSourceNode.create(context, "WebAssembly.Module(): Argument 0 must be a buffer source", "WebAssembly.Module(): BufferSource argument is empty");
            this.decodeModuleLib = InteropLibrary.getFactory().createDispatched(5);
        }

        @Specialization
        protected JSDynamicObject constructModule(JSDynamicObject newTarget, Object bytes) {
            Object wasmModule;
            Object byteSource = this.exportByteSourceNode.execute(bytes);
            JSRealm realm = this.getRealm();
            try {
                Object decode2 = realm.getWASMModuleDecode();
                wasmModule = this.decodeModuleLib.execute(decode2, byteSource);
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
            catch (AbstractTruffleException tex) {
                try {
                    ExceptionType type = InteropLibrary.getUncached(tex).getExceptionType(tex);
                    if (type == ExceptionType.PARSE_ERROR) {
                        throw Errors.createCompileError(tex, (Node)this);
                    }
                }
                catch (UnsupportedMessageException ex) {
                    throw Errors.shouldNotReachHere(ex);
                }
                throw tex;
            }
            return this.swapPrototype(JSWebAssemblyModule.create(this.getContext(), realm, wasmModule), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWebAssemblyModulePrototype();
        }
    }

    public static abstract class PromiseConstructorNode
    extends JSBuiltinNode {
        @Node.Child
        protected IsCallableNode isCallable = IsCallableNode.create();
        @Node.Child
        private PromiseResolveThenableNode promiseResolveThenable;
        @Node.Child
        private OrdinaryCreateFromConstructorNode createPromiseFromConstructor;
        @Node.Child
        private PropertySetNode setPromiseFulfillReactions;
        @Node.Child
        private PropertySetNode setPromiseRejectReactions;
        @Node.Child
        private PropertySetNode setPromiseIsHandled;

        public PromiseConstructorNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.promiseResolveThenable = PromiseResolveThenableNode.create(context);
            this.createPromiseFromConstructor = OrdinaryCreateFromConstructorNode.create(context, null, JSRealm::getPromisePrototype, JSPromise.INSTANCE);
            this.setPromiseFulfillReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_FULFILL_REACTIONS, context);
            this.setPromiseRejectReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_REJECT_REACTIONS, context);
            this.setPromiseIsHandled = PropertySetNode.createSetHidden(JSPromise.PROMISE_IS_HANDLED, context);
        }

        @Specialization(guards={"isCallable.executeBoolean(executor)"})
        protected JSDynamicObject construct(JSDynamicObject newTarget, Object executor) {
            JSDynamicObject promise = this.createPromiseFromConstructor.executeWithConstructor(newTarget);
            JSPromise.setPromiseState(promise, 0);
            this.setPromiseFulfillReactions.setValue(promise, new SimpleArrayList());
            this.setPromiseRejectReactions.setValue(promise, new SimpleArrayList());
            this.setPromiseIsHandled.setValueBoolean(promise, false);
            this.getContext().notifyPromiseHook(0, promise);
            this.promiseResolveThenable.execute(promise, Undefined.instance, executor);
            return promise;
        }

        @Specialization(guards={"!isCallable.executeBoolean(executor)"})
        protected JSDynamicObject notCallable(JSDynamicObject newTarget, Object executor) {
            throw Errors.createTypeError("cannot create promise: executor not callable");
        }
    }

    public static abstract class ConstructSymbolNode
    extends JSBuiltinNode {
        public ConstructSymbolNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static final JSDynamicObject construct() {
            throw Errors.createTypeError("cannot construct a Symbol");
        }
    }

    @ImportStatic(value={Symbol.class})
    public static abstract class CallSymbolNode
    extends JSBuiltinNode
    implements JSBuiltinNode.Inlineable {
        public CallSymbolNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Symbol callSymbolString(TruffleString value2) {
            return Symbol.create(value2);
        }

        @Specialization(guards={"!isString(value)"})
        protected Symbol callSymbolGeneric(Object value2, @Cached JSToStringNode toStringNode) {
            return Symbol.create(value2 == Undefined.instance ? null : toStringNode.executeString(value2));
        }

        @Override
        public Inlined createInlined() {
            return ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.create(this.getContext(), this.getBuiltin(), new JavaScriptNode[0]);
        }

        public static abstract class Inlined
        extends CallSymbolNode
        implements JSBuiltinNode.Inlined {
            public Inlined(JSContext context, JSBuiltin builtin) {
                super(context, builtin);
            }

            protected abstract Object executeWithArguments(Object var1);

            @Specialization(guards={"acceptCache(equalNode, value, cachedValue, symbolUsageMarker)"})
            protected Symbol callSymbolSingleton(TruffleString value2, @Cached(value="value") TruffleString cachedValue, @Cached TruffleString.EqualNode equalNode, @Cached(value="createSymbolUsageMarker()") AtomicReference<Object> symbolUsageMarker, @Cached(value="createCachedSingletonSymbol(value)") Symbol cachedSymbol) {
                return cachedSymbol;
            }

            @Override
            @Specialization
            protected Symbol callSymbolString(TruffleString value2) {
                throw this.rewriteToCall();
            }

            @Specialization
            protected TruffleString callInlinedSymbolGeneric(Object value2) {
                throw this.rewriteToCall();
            }

            @Override
            public Object callInlined(Object[] arguments) {
                if (JSArguments.getUserArgumentCount(arguments) < 1) {
                    throw this.rewriteToCall();
                }
                return this.executeWithArguments(JSArguments.getUserArgument(arguments, 0));
            }

            @CompilerDirectives.TruffleBoundary
            protected boolean acceptCache(TruffleString.EqualNode equalNode, TruffleString value2, TruffleString cachedValue, AtomicReference<Object> symbolUsageMarker) {
                if (this.getContext().isMultiContext() && Strings.equals(equalNode, value2, cachedValue)) {
                    Object oldMarker;
                    Object currentMarker = this.getContext().getSymbolUsageMarker();
                    return currentMarker != (oldMarker = symbolUsageMarker.getAndSet(currentMarker));
                }
                return false;
            }

            protected AtomicReference<Object> createSymbolUsageMarker() {
                return new AtomicReference<Object>();
            }

            protected Symbol createCachedSingletonSymbol(TruffleString value2) {
                return Symbol.create(value2);
            }
        }
    }

    public static abstract class ConstructWeakMapNode
    extends ConstructMapNode {
        public ConstructWeakMapNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Override
        protected JSDynamicObject newMapObject() {
            return JSWeakMap.create(this.getContext(), this.getRealm());
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWeakMapPrototype();
        }
    }

    public static abstract class ConstructWeakSetNode
    extends ConstructSetNode {
        public ConstructWeakSetNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Override
        protected JSDynamicObject newSetObject() {
            return JSWeakSet.create(this.getContext(), this.getRealm());
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWeakSetPrototype();
        }
    }

    public static abstract class ConstructSetNode
    extends JSConstructIterableOperation {
        public ConstructSetNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization(guards={"isNullOrUndefined(iterable)"})
        protected JSDynamicObject constructEmptySet(JSDynamicObject newTarget, Object iterable) {
            JSDynamicObject setObj = this.newSetObject();
            this.swapPrototype(setObj, newTarget);
            return setObj;
        }

        @Specialization(guards={"!isNullOrUndefined(iterable)"})
        protected JSDynamicObject constructSetFromIterable(JSDynamicObject newTarget, Object iterable, @Cached IsCallableNode isCallableNode) {
            JSDynamicObject setObj = this.newSetObject();
            this.swapPrototype(setObj, newTarget);
            Object adder = this.getAdderFn(setObj, Strings.ADD);
            if (!isCallableNode.executeBoolean(adder)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("function add not callable");
            }
            IteratorRecord iter = this.getIterator(iterable);
            try {
                Object next;
                while ((next = this.iteratorStep(iter)) != Boolean.FALSE) {
                    Object nextValue = this.getIteratorValue((JSDynamicObject)next);
                    this.call(setObj, adder, nextValue);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorCloseAbrupt(iter.getIterator());
                throw ex;
            }
            return setObj;
        }

        protected JSDynamicObject newSetObject() {
            return JSSet.create(this.getContext(), this.getRealm());
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getSetPrototype();
        }
    }

    public static abstract class ConstructMapNode
    extends JSConstructIterableOperation {
        public ConstructMapNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization(guards={"isNullOrUndefined(iterable)"})
        protected JSDynamicObject constructEmptyMap(JSDynamicObject newTarget, Object iterable) {
            JSDynamicObject mapObj = this.newMapObject();
            this.swapPrototype(mapObj, newTarget);
            return mapObj;
        }

        @Specialization(guards={"!isNullOrUndefined(iterable)"})
        protected JSDynamicObject constructMapFromIterable(JSDynamicObject newTarget, Object iterable, @Cached(value="create(getContext())") ReadElementNode readElementNode, @Cached IsObjectNode isObjectNode, @Cached IsCallableNode isCallableNode) {
            JSDynamicObject mapObj = this.newMapObject();
            this.swapPrototype(mapObj, newTarget);
            Object adder = this.getAdderFn(mapObj, Strings.SET);
            if (!isCallableNode.executeBoolean(adder)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("function set not callable");
            }
            IteratorRecord iter = this.getIterator(iterable);
            try {
                Object next;
                while ((next = this.iteratorStep(iter)) != Boolean.FALSE) {
                    Object nextItem = this.getIteratorValue((JSDynamicObject)next);
                    if (!isObjectNode.executeBoolean(nextItem)) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorIteratorResultNotObject(nextItem, this);
                    }
                    Object k = readElementNode.executeWithTargetAndIndex(nextItem, 0);
                    Object v = readElementNode.executeWithTargetAndIndex(nextItem, 1);
                    this.call(mapObj, adder, k, v);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorCloseAbrupt(iter.getIterator());
                throw ex;
            }
            return mapObj;
        }

        protected JSDynamicObject newMapObject() {
            return JSMap.create(this.getContext(), this.getRealm());
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getMapPrototype();
        }
    }

    public static abstract class JSConstructIterableOperation
    extends ConstructWithNewTargetNode {
        @Node.Child
        private IteratorCloseNode iteratorCloseNode;
        @Node.Child
        private GetIteratorBaseNode getIteratorNode;
        @Node.Child
        private IteratorValueNode getIteratorValueNode;
        @Node.Child
        private IteratorStepNode iteratorStepNode;
        @Node.Child
        private JSFunctionCallNode callAdderNode;
        @Node.Child
        private PropertyGetNode getAdderFnNode;
        protected final BranchProfile errorBranch = BranchProfile.create();

        public JSConstructIterableOperation(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        protected void iteratorCloseAbrupt(JSDynamicObject iterator) {
            if (this.iteratorCloseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
            }
            this.iteratorCloseNode.executeAbrupt(iterator);
        }

        protected IteratorRecord getIterator(Object iterator) {
            if (this.getIteratorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratorNode = this.insert(GetIteratorBaseNode.create());
            }
            return this.getIteratorNode.execute(iterator);
        }

        protected Object getIteratorValue(JSDynamicObject iteratorResult) {
            if (this.getIteratorValueNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratorValueNode = this.insert(IteratorValueNode.create());
            }
            return this.getIteratorValueNode.execute(iteratorResult);
        }

        protected Object iteratorStep(IteratorRecord iterator) {
            if (this.iteratorStepNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorStepNode = this.insert(IteratorStepNode.create());
            }
            return this.iteratorStepNode.execute(iterator);
        }

        protected Object call(Object target, Object function, Object ... userArguments) {
            if (this.callAdderNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callAdderNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callAdderNode.executeCall(JSArguments.create(target, function, userArguments));
        }

        protected Object getAdderFn(JSDynamicObject obj, TruffleString name) {
            if (this.getAdderFnNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getAdderFnNode = this.insert(PropertyGetNode.create(name, this.getContext()));
            }
            return this.getAdderFnNode.getValue(obj);
        }
    }

    public static abstract class ConstructJavaImporterNode
    extends JSBuiltinNode {
        public ConstructJavaImporterNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject constructJavaImporter(Object[] args) {
            JSRealm realm = this.getRealm();
            TruffleLanguage.Env env = realm.getEnv();
            SimpleArrayList<Object> imports = new SimpleArrayList<Object>(args.length);
            for (Object anImport : args) {
                InteropLibrary interop;
                if (JavaPackage.isJavaPackage(anImport)) {
                    imports.addUnchecked(anImport);
                    continue;
                }
                if (!env.isHostObject(anImport) || !(interop = InteropLibrary.getUncached(anImport)).isMetaObject(anImport)) continue;
                imports.addUnchecked(anImport);
            }
            return JavaImporter.create(this.getContext(), realm, imports.toArray());
        }
    }

    @ImportStatic(value={JSProxy.class})
    public static abstract class ConstructJSProxyNode
    extends ConstructWithNewTargetNode {
        private final ConditionProfile targetNonObject = ConditionProfile.createBinaryProfile();
        private final ConditionProfile handlerNonObject = ConditionProfile.createBinaryProfile();

        public ConstructJSProxyNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructJSProxy(JSDynamicObject newTarget, Object target, Object handler) {
            if (this.targetNonObject.profile(!JSGuards.isTruffleObject(target) || target instanceof Symbol || target == Undefined.instance || target == Null.instance || target instanceof TruffleString || target instanceof SafeInteger || target instanceof BigInt)) {
                throw Errors.createTypeError("target expected to be an object");
            }
            if (this.handlerNonObject.profile(!JSGuards.isJSObject(handler))) {
                throw Errors.createTypeError("handler expected to be an object");
            }
            JSDynamicObject handlerObj = (JSDynamicObject)handler;
            return this.swapPrototype(JSProxy.create(this.getContext(), this.getRealm(), target, handlerObj), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getProxyPrototype();
        }

        public abstract JSDynamicObject execute(JSDynamicObject var1, Object var2, Object var3);
    }

    public static abstract class ConstructJSAdapterNode
    extends JSBuiltinNode {
        public ConstructJSAdapterNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(adaptee)", "isUndefined(undefined1)", "isUndefined(undefined2)"})
        protected JSDynamicObject constructJSAdapter(JSDynamicObject adaptee, Object undefined1, Object undefined2) {
            return JSAdapter.create(this.getContext(), this.getRealm(), adaptee, null, null);
        }

        @Specialization(guards={"isJSObject(overrides)", "isJSObject(adaptee)", "isUndefined(undefined2)"})
        protected JSDynamicObject constructJSAdapter(JSDynamicObject overrides, JSDynamicObject adaptee, Object undefined2) {
            return JSAdapter.create(this.getContext(), this.getRealm(), adaptee, overrides, null);
        }

        @Specialization(guards={"isJSObject(proto)", "isJSObject(overrides)", "isJSObject(adaptee)"})
        protected JSDynamicObject constructJSAdapter(JSDynamicObject proto, JSDynamicObject overrides, JSDynamicObject adaptee) {
            return JSAdapter.create(this.getContext(), this.getRealm(), adaptee, overrides, proto);
        }

        @Fallback
        protected JSDynamicObject constructJSAdapter(Object proto, Object overrides, Object adaptee) {
            Object notAnObject;
            if (!JSRuntime.isObject(proto)) {
                notAnObject = proto;
            } else if (!JSRuntime.isObject(overrides)) {
                notAnObject = overrides;
            } else if (!JSRuntime.isObject(adaptee)) {
                notAnObject = adaptee;
            } else {
                throw Errors.shouldNotReachHere();
            }
            throw Errors.createTypeErrorNotAnObject(notAnObject);
        }
    }

    public static abstract class CallRequiresNewNode
    extends JSBuiltinNode {
        public CallRequiresNewNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected final JSDynamicObject call() {
            throw Errors.createTypeErrorFormat("Constructor %s requires 'new'", this.getBuiltin().getName());
        }
    }

    @ImportStatic(value={JSArrayBuffer.class, JSConfig.class})
    public static abstract class ConstructDataViewNode
    extends ConstructWithNewTargetNode {
        public ConstructDataViewNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization(guards={"isJSHeapArrayBuffer(buffer)"})
        protected final JSDynamicObject ofHeapArrayBuffer(JSDynamicObject newTarget, JSDynamicObject buffer, Object byteOffset, Object byteLength, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached(value="createBinaryProfile()") @Cached.Shared(value="byteLengthCondition") ConditionProfile byteLengthCondition, @Cached @Cached.Shared(value="offsetToIndexNode") JSToIndexNode offsetToIndexNode, @Cached @Cached.Shared(value="lengthToIndexNode") JSToIndexNode lengthToIndexNode) {
            return this.constructDataView(newTarget, buffer, byteOffset, byteLength, false, false, errorBranch, byteLengthCondition, offsetToIndexNode, lengthToIndexNode, null);
        }

        @Specialization(guards={"isJSDirectOrSharedArrayBuffer(buffer)"})
        protected final JSDynamicObject ofDirectArrayBuffer(JSDynamicObject newTarget, JSDynamicObject buffer, Object byteOffset, Object byteLength, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached(value="createBinaryProfile()") @Cached.Shared(value="byteLengthCondition") ConditionProfile byteLengthCondition, @Cached @Cached.Shared(value="offsetToIndexNode") JSToIndexNode offsetToIndexNode, @Cached @Cached.Shared(value="lengthToIndexNode") JSToIndexNode lengthToIndexNode) {
            return this.constructDataView(newTarget, buffer, byteOffset, byteLength, true, false, errorBranch, byteLengthCondition, offsetToIndexNode, lengthToIndexNode, null);
        }

        @Specialization(guards={"isJSInteropArrayBuffer(buffer)"})
        protected final JSDynamicObject ofInteropArrayBuffer(JSDynamicObject newTarget, JSDynamicObject buffer, Object byteOffset, Object byteLength, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached(value="createBinaryProfile()") @Cached.Shared(value="byteLengthCondition") ConditionProfile byteLengthCondition, @Cached @Cached.Shared(value="offsetToIndexNode") JSToIndexNode offsetToIndexNode, @Cached @Cached.Shared(value="lengthToIndexNode") JSToIndexNode lengthToIndexNode, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="bufferInterop") InteropLibrary bufferInterop) {
            return this.constructDataView(newTarget, buffer, byteOffset, byteLength, false, true, errorBranch, byteLengthCondition, offsetToIndexNode, lengthToIndexNode, bufferInterop);
        }

        @Specialization(guards={"!isJSAbstractBuffer(buffer)", "bufferInterop.hasBufferElements(buffer)"})
        protected final JSDynamicObject ofInteropBuffer(JSDynamicObject newTarget, Object buffer, Object byteOffset, Object byteLength, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @Cached(value="createBinaryProfile()") @Cached.Shared(value="byteLengthCondition") ConditionProfile byteLengthCondition, @Cached @Cached.Shared(value="offsetToIndexNode") JSToIndexNode offsetToIndexNode, @Cached @Cached.Shared(value="lengthToIndexNode") JSToIndexNode lengthToIndexNode, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="bufferInterop") InteropLibrary bufferInterop) {
            JSArrayBufferObject arrayBuffer = JSArrayBuffer.createInteropArrayBuffer(this.getContext(), this.getRealm(), buffer);
            return this.ofInteropArrayBuffer(newTarget, arrayBuffer, byteOffset, byteLength, errorBranch, byteLengthCondition, offsetToIndexNode, lengthToIndexNode, bufferInterop);
        }

        @Specialization(guards={"!isJSAbstractBuffer(buffer)", "!bufferInterop.hasBufferElements(buffer)"})
        protected static JSDynamicObject error(JSDynamicObject newTarget, Object buffer, Object byteOffset, Object byteLength, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="bufferInterop") InteropLibrary bufferInterop) {
            throw Errors.createTypeError("Not an ArrayBuffer");
        }

        protected final JSDynamicObject constructDataView(JSDynamicObject newTarget, JSDynamicObject arrayBuffer, Object byteOffset, Object byteLength, boolean direct, boolean isInteropBuffer, BranchProfile errorBranch, ConditionProfile byteLengthCondition, JSToIndexNode offsetToIndexNode, JSToIndexNode lengthToIndexNode, InteropLibrary bufferInterop) {
            long viewByteLength;
            long offset = offsetToIndexNode.executeLong(byteOffset);
            if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
                errorBranch.enter();
                throw Errors.createTypeError("detached buffer cannot be used");
            }
            int bufferByteLength = isInteropBuffer ? ConstructArrayBufferNode.getBufferSizeSafe(JSArrayBuffer.getInteropBuffer(arrayBuffer), bufferInterop, errorBranch) : (direct ? JSArrayBuffer.getDirectByteLength(arrayBuffer) : JSArrayBuffer.getHeapByteLength(arrayBuffer));
            if (offset > (long)bufferByteLength) {
                errorBranch.enter();
                throw Errors.createRangeError("offset > bufferByteLength");
            }
            if (byteLengthCondition.profile(byteLength != Undefined.instance)) {
                viewByteLength = lengthToIndexNode.executeLong(byteLength);
                if (viewByteLength < 0L) {
                    errorBranch.enter();
                    throw Errors.createRangeError("viewByteLength < 0");
                }
                if (offset + viewByteLength > (long)bufferByteLength) {
                    errorBranch.enter();
                    throw Errors.createRangeError("offset + viewByteLength > bufferByteLength");
                }
            } else {
                viewByteLength = (long)bufferByteLength - offset;
            }
            assert (offset >= 0L && offset <= Integer.MAX_VALUE && viewByteLength >= 0L && viewByteLength <= Integer.MAX_VALUE);
            JSDynamicObject result = this.swapPrototype(JSDataView.createDataView(this.getContext(), this.getRealm(), arrayBuffer, (int)offset, (int)viewByteLength), newTarget);
            if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
                errorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
            return result;
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getDataViewPrototype();
        }
    }

    @ImportStatic(value={Strings.class})
    public static abstract class ConstructAggregateErrorNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        private ErrorStackTraceLimitNode stackTraceLimitNode = ErrorStackTraceLimitNode.create();
        @Node.Child
        private InitErrorObjectNode initErrorObjectNode;
        @Node.Child
        private DynamicObjectLibrary setMessage;
        @Node.Child
        private InstallErrorCauseNode installErrorCauseNode;

        public ConstructAggregateErrorNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
            this.initErrorObjectNode = InitErrorObjectNode.create(context);
            this.setMessage = JSObjectUtil.createDispatched(JSError.MESSAGE);
        }

        GetMethodNode createGetIteratorMethod() {
            return GetMethodNode.create(this.getContext(), Symbol.SYMBOL_ITERATOR);
        }

        @Specialization
        protected JSDynamicObject constructError(JSDynamicObject newTarget, Object errorsObj, Object messageObj, Object options, @Cached JSToStringNode toStringNode, @Cached(value="createGetIteratorMethod()") GetMethodNode getIteratorMethodNode, @Cached(value="createCall()") JSFunctionCallNode iteratorCallNode, @Cached IsJSObjectNode isObjectNode, @Cached IterableToListNode iterableToListNode, @Cached(value="create(NEXT, getContext())") PropertyGetNode getNextMethodNode) {
            TruffleString message;
            JSContext context = this.getContext();
            JSRealm realm = this.getRealm();
            JSErrorObject errorObj = JSError.createErrorObject(context, realm, JSErrorType.AggregateError);
            this.swapPrototype(errorObj, newTarget);
            if (messageObj == Undefined.instance) {
                message = null;
            } else {
                message = toStringNode.executeString(messageObj);
                this.setMessage.putWithFlags(errorObj, JSError.MESSAGE, message, JSError.MESSAGE_ATTRIBUTES);
            }
            if (context.getContextOptions().isErrorCauseEnabled() && options != Undefined.instance) {
                this.installErrorCause(errorObj, options);
            }
            Object usingIterator = getIteratorMethodNode.executeWithTarget(errorsObj);
            SimpleArrayList<Object> errors = iterableToListNode.execute(GetIteratorNode.getIterator(errorsObj, usingIterator, iteratorCallNode, isObjectNode, getNextMethodNode, this));
            JSArrayObject errorsArray = JSArray.createConstantObjectArray(context, this.getRealm(), errors.toArray());
            int stackTraceLimit = this.stackTraceLimitNode.executeInt();
            JSDynamicObject errorFunction = realm.getErrorConstructor(JSErrorType.AggregateError);
            JSDynamicObject skipUntil = newTarget == Undefined.instance ? errorFunction : newTarget;
            JSException exception = JSException.createCapture(JSErrorType.AggregateError, Strings.toJavaString(message), errorObj, realm, stackTraceLimit, skipUntil, skipUntil != errorFunction);
            this.initErrorObjectNode.execute(errorObj, exception, null, errorsArray);
            return errorObj;
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getErrorPrototype(JSErrorType.AggregateError);
        }

        @Override
        public boolean countsTowardsStackTraceLimit() {
            return false;
        }

        private void installErrorCause(JSObject errorObj, Object options) {
            if (this.installErrorCauseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.installErrorCauseNode = this.insert(new InstallErrorCauseNode(this.getContext()));
            }
            this.installErrorCauseNode.executeVoid(errorObj, options);
        }
    }

    public static abstract class ConstructErrorNode
    extends ConstructWithNewTargetNode {
        private final JSErrorType errorType = JSErrorType.valueOf(Strings.toJavaString(this.getBuiltin().getName()));
        @Node.Child
        private ErrorStackTraceLimitNode stackTraceLimitNode = ErrorStackTraceLimitNode.create();
        @Node.Child
        private InitErrorObjectNode initErrorObjectNode;

        public ConstructErrorNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
            this.initErrorObjectNode = InitErrorObjectNode.create(context);
            assert (this.errorType != JSErrorType.AggregateError);
        }

        @Specialization
        protected JSDynamicObject constructError(JSDynamicObject newTarget, TruffleString message, Object options) {
            return this.constructErrorImpl(newTarget, message, options);
        }

        @Specialization(guards={"!isString(message)"})
        protected JSDynamicObject constructError(JSDynamicObject newTarget, Object message, Object options, @Cached(value="create()") JSToStringNode toStringNode) {
            return this.constructErrorImpl(newTarget, message == Undefined.instance ? null : toStringNode.executeString(message), options);
        }

        private JSDynamicObject constructErrorImpl(JSDynamicObject newTarget, TruffleString messageOpt, Object options) {
            JSRealm realm = this.getRealm();
            JSErrorObject errorObj = JSError.createErrorObject(this.getContext(), realm, this.errorType);
            this.swapPrototype(errorObj, newTarget);
            int stackTraceLimit = this.stackTraceLimitNode.executeInt();
            JSDynamicObject errorFunction = realm.getErrorConstructor(this.errorType);
            JSDynamicObject skipUntil = newTarget == Undefined.instance ? errorFunction : newTarget;
            JSException exception = JSException.createCapture(this.errorType, Strings.toJavaString(messageOpt), errorObj, realm, stackTraceLimit, skipUntil, skipUntil != errorFunction);
            return this.initErrorObjectNode.execute(errorObj, exception, messageOpt, null, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getErrorPrototype(this.errorType);
        }

        @Override
        public boolean countsTowardsStackTraceLimit() {
            return false;
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ConstructArrayBufferNode
    extends ConstructWithNewTargetNode {
        private final boolean useShared;
        @Node.Child
        private GetPrototypeFromConstructorNode getPrototypeFromConstructorNode;

        public ConstructArrayBufferNode(JSContext context, JSBuiltin builtin, boolean useShared, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
            this.useShared = useShared;
            if (isNewTargetCase) {
                this.getPrototypeFromConstructorNode = GetPrototypeFromConstructorNode.create(context, null, realm -> this.getIntrinsicDefaultProto((JSRealm)realm));
            }
        }

        @Specialization(guards={"!bufferInterop.hasBufferElements(length)"})
        protected JSDynamicObject constructFromLength(JSDynamicObject newTarget, Object length, @Cached(value="create()") JSToIndexNode toIndexNode, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="bufferInterop") InteropLibrary bufferInterop) {
            long byteLength = toIndexNode.executeLong(length);
            JSDynamicObject prototype = null;
            if (this.isNewTargetCase) {
                prototype = this.getPrototypeFromConstructorNode.executeWithConstructor(newTarget);
            }
            if (byteLength > (long)this.getContext().getContextOptions().getMaxTypedArrayLength()) {
                errorBranch.enter();
                throw Errors.createRangeError("Array buffer allocation failed");
            }
            JSContext contextFromNewTarget = this.getContext();
            JSRealm realm = this.getRealm();
            JSArrayBufferObject arrayBuffer = this.useShared ? JSSharedArrayBuffer.createSharedArrayBuffer(contextFromNewTarget, realm, (int)byteLength) : (this.getContext().isOptionDirectByteBuffer() ? JSArrayBuffer.createDirectArrayBuffer(contextFromNewTarget, realm, (int)byteLength) : JSArrayBuffer.createArrayBuffer(contextFromNewTarget, realm, (int)byteLength));
            if (this.isNewTargetCase) {
                JSObject.setPrototype(arrayBuffer, prototype);
            }
            return arrayBuffer;
        }

        @Specialization(guards={"bufferInterop.hasBufferElements(buffer)"})
        protected JSDynamicObject constructFromInteropBuffer(JSDynamicObject newTarget, Object buffer, @Cached @Cached.Shared(value="errorBranch") BranchProfile errorBranch, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="bufferInterop") InteropLibrary bufferInterop) {
            ConstructArrayBufferNode.getBufferSizeSafe(buffer, bufferInterop, errorBranch);
            return this.swapPrototype(JSArrayBuffer.createInteropArrayBuffer(this.getContext(), this.getRealm(), buffer), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return this.useShared ? realm.getSharedArrayBufferPrototype() : realm.getArrayBufferPrototype();
        }

        static int getBufferSizeSafe(Object buffer, InteropLibrary bufferInterop, BranchProfile errorBranch) {
            try {
                long bufferSize = bufferInterop.getBufferSize(buffer);
                if (bufferSize < 0L || bufferSize > Integer.MAX_VALUE) {
                    errorBranch.enter();
                    throw Errors.createRangeErrorInvalidBufferSize();
                }
                return (int)bufferSize;
            }
            catch (UnsupportedMessageException e) {
                return 0;
            }
        }
    }

    public static abstract class CallTypedArrayNode
    extends JSBuiltinNode {
        public CallTypedArrayNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object callTypedArray(Object ... args) {
            throw Errors.createTypeError("wrong");
        }
    }

    static abstract class CreateDynamicFunctionNode
    extends JavaScriptBaseNode {
        private final boolean generatorFunction;
        private final boolean asyncFunction;
        private final JSContext context;

        protected CreateDynamicFunctionNode(JSContext context, boolean generatorFunction, boolean asyncFunction) {
            this.generatorFunction = generatorFunction;
            this.asyncFunction = asyncFunction;
            this.context = context;
        }

        protected abstract JSDynamicObject executeFunction(String var1, String var2, String var3);

        protected static boolean equals(String a, String b) {
            return a.equals(b);
        }

        protected LRUCache<CachedSourceKey, ScriptNode> createCache() {
            return new LRUCache<CachedSourceKey, ScriptNode>(this.context.getContextOptions().getFunctionConstructorCacheSize());
        }

        @Specialization(guards={"equals(cachedParamList, paramList)", "equals(cachedBody, body)", "equals(cachedSourceName, sourceName)"}, limit="1")
        protected final JSDynamicObject doCached(String paramList, String body, String sourceName, @Cached(value="paramList") String cachedParamList, @Cached(value="body") String cachedBody, @Cached(value="sourceName") String cachedSourceName, @Cached(value="createAssumedValue()") AssumedValue<ScriptNode> cachedParsedFunction) {
            ScriptNode parsedFunction = cachedParsedFunction.get();
            if (parsedFunction == null) {
                parsedFunction = this.parseFunction(paramList, body, sourceName);
                cachedParsedFunction.set(parsedFunction);
            }
            return CreateDynamicFunctionNode.evalParsedFunction(this.getRealm(), parsedFunction);
        }

        @Specialization(replaces={"doCached"})
        protected final JSDynamicObject doUncached(String paramList, String body, String sourceName, @Cached(value="createCache()") LRUCache<CachedSourceKey, ScriptNode> cache, @Cached(value="createCountingProfile()") ConditionProfile cacheHit) {
            ScriptNode cached = this.cacheLookup(cache, new CachedSourceKey(paramList, body, sourceName));
            JSRealm realm = this.getRealm();
            if (cacheHit.profile(cached == null)) {
                return this.parseAndEvalFunction(cache, realm, paramList, body, sourceName);
            }
            return CreateDynamicFunctionNode.evalParsedFunction(realm, cached);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        protected ScriptNode cacheLookup(LRUCache<CachedSourceKey, ScriptNode> cache, CachedSourceKey sourceKey) {
            LRUCache<CachedSourceKey, ScriptNode> lRUCache = cache;
            synchronized (lRUCache) {
                return (ScriptNode)cache.get(sourceKey);
            }
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected final ScriptNode parseFunction(String paramList, String body, String sourceName) {
            CompilerAsserts.neverPartOfCompilation();
            return this.context.getEvaluator().parseFunction(this.context, paramList, body, this.generatorFunction, this.asyncFunction, sourceName);
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        private static JSDynamicObject evalParsedFunction(JSRealm realm, ScriptNode parsedFunction) {
            return (JSDynamicObject)parsedFunction.run(realm);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        private JSDynamicObject parseAndEvalFunction(LRUCache<CachedSourceKey, ScriptNode> cache, JSRealm realm, String paramList, String body, String sourceName) {
            ScriptNode parsedBody = this.parseFunction(paramList, body, sourceName);
            LRUCache<CachedSourceKey, ScriptNode> lRUCache = cache;
            synchronized (lRUCache) {
                cache.put(new CachedSourceKey(paramList, body, sourceName), parsedBody);
            }
            return CreateDynamicFunctionNode.evalParsedFunction(realm, parsedBody);
        }

        AssumedValue<ScriptNode> createAssumedValue() {
            return new AssumedValue<Object>("parsedFunction", null);
        }

        protected static class CachedSourceKey {
            private final String body;
            private final String paramList;
            private final String sourceName;

            CachedSourceKey(String paramList, String body, String sourceName) {
                this.body = body;
                this.paramList = paramList;
                this.sourceName = sourceName;
            }

            public boolean equals(Object o) {
                if (!(o instanceof CachedSourceKey)) {
                    return false;
                }
                CachedSourceKey k = (CachedSourceKey)o;
                return k.body.equals(this.body) && k.paramList.equals(this.paramList) && k.sourceName.equals(this.sourceName);
            }

            public int hashCode() {
                return Objects.hash(this.body, this.paramList, this.sourceName);
            }
        }
    }

    public static abstract class ConstructFunctionNode
    extends ConstructWithNewTargetNode {
        private final boolean generatorFunction;
        private final boolean asyncFunction;
        @Node.Child
        private JSToStringNode toStringNode;
        @Node.Child
        private CreateDynamicFunctionNode functionNode;

        public ConstructFunctionNode(JSContext context, JSBuiltin builtin, boolean generatorFunction, boolean asyncFunction, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
            this.generatorFunction = generatorFunction;
            this.asyncFunction = asyncFunction;
            this.toStringNode = JSToStringNode.create();
            this.functionNode = ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.create(context, generatorFunction, asyncFunction);
        }

        @Specialization
        protected final JSDynamicObject constructFunction(JSDynamicObject newTarget, Object[] args, @Cached(value="createBinaryProfile()") ConditionProfile hasArgsProfile, @Cached(value="createBinaryProfile()") ConditionProfile hasParamsProfile) {
            TruffleString body;
            TruffleString[] params;
            int argc = args.length;
            if (hasArgsProfile.profile(argc > 0)) {
                params = new TruffleString[argc - 1];
                for (int i = 0; i < argc - 1; ++i) {
                    params[i] = this.toStringNode.executeString(args[i]);
                }
                body = this.toStringNode.executeString(args[argc - 1]);
            } else {
                params = new TruffleString[]{};
                body = Strings.EMPTY_STRING;
            }
            TruffleString paramList = hasParamsProfile.profile(argc > 1) ? ConstructFunctionNode.join(params) : Strings.EMPTY_STRING;
            return this.swapPrototype(this.functionNode.executeFunction(Strings.toJavaString(paramList), Strings.toJavaString(body), this.getSourceName()), newTarget);
        }

        @CompilerDirectives.TruffleBoundary
        private static TruffleString join(TruffleString[] params) {
            assert (params.length > 0);
            TruffleStringBuilder sb = Strings.builderCreate();
            Strings.builderAppend(sb, params[0]);
            for (int i = 1; i < params.length; ++i) {
                Strings.builderAppend(sb, Strings.COMMA);
                Strings.builderAppend(sb, params[i]);
            }
            return Strings.builderToString(sb);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            if (this.generatorFunction && this.asyncFunction) {
                return realm.getAsyncGeneratorFunctionPrototype();
            }
            if (this.generatorFunction) {
                return realm.getGeneratorFunctionPrototype();
            }
            if (this.asyncFunction) {
                return realm.getAsyncFunctionPrototype();
            }
            return realm.getFunctionPrototype();
        }

        private String getSourceName() {
            String sourceName = null;
            if (this.isCallerSensitive()) {
                sourceName = EvalNode.findAndFormatEvalOrigin(this.getRealm().getCallNode(), this.getContext());
            }
            if (sourceName == null) {
                sourceName = "<function>";
            }
            return sourceName;
        }

        @Override
        public boolean isCallerSensitive() {
            return this.getContext().isOptionV8CompatibilityMode();
        }
    }

    public static abstract class ConstructBigIntNode
    extends JSBuiltinNode {
        public ConstructBigIntNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static final JSDynamicObject construct() {
            throw Errors.createTypeError("BigInt is not a constructor");
        }
    }

    public static abstract class CallBigIntNode
    extends JSBuiltinNode {
        @Node.Child
        JSToPrimitiveNode toPrimitiveNode;

        public CallBigIntNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        private Object toPrimitive(Object target) {
            if (this.toPrimitiveNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPrimitiveNode = this.insert(JSToPrimitiveNode.createHintNumber());
            }
            return this.toPrimitiveNode.execute(target);
        }

        @Specialization(guards={"args.length == 0"})
        protected void callBigIntZero(Object[] args) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, Undefined.instance);
        }

        @Specialization(guards={"args.length > 0"})
        protected Object callBigInt(Object[] args, @Cached(value="create()") JSNumberToBigIntNode numberToBigIntNode, @Cached(value="create()") JSToBigIntNode toBigIntNode) {
            Object value2 = args[0];
            Object primitiveObj = this.toPrimitive(value2);
            if (JSRuntime.isNumber(primitiveObj)) {
                return numberToBigIntNode.executeBigInt(primitiveObj);
            }
            return toBigIntNode.executeBigInteger(primitiveObj);
        }
    }

    public static abstract class ConstructNumberNode
    extends ConstructWithNewTargetNode {
        public ConstructNumberNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization(guards={"args.length == 0"})
        protected JSDynamicObject constructNumberZero(JSDynamicObject newTarget, Object[] args) {
            return this.swapPrototype(JSNumber.create(this.getContext(), this.getRealm(), 0), newTarget);
        }

        @Specialization(guards={"args.length > 0"})
        protected JSDynamicObject constructNumber(JSDynamicObject newTarget, Object[] args, @Cached(value="create()") JSToNumericNode toNumericNode, @Cached(value="create()") JSNumericToNumberNode toNumberFromNumericNode) {
            return this.swapPrototype(JSNumber.create(this.getContext(), this.getRealm(), toNumberFromNumericNode.executeNumeric(toNumericNode.execute(args[0]))), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getNumberPrototype();
        }
    }

    public static abstract class CallNumberNode
    extends JSBuiltinNode {
        public CallNumberNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"args.length == 0"})
        protected int callNumberZero(Object[] args) {
            return 0;
        }

        @Specialization(guards={"args.length > 0"})
        protected Number callNumber(Object[] args, @Cached(value="create()") JSToNumericNode toNumericNode, @Cached(value="create()") JSNumericToNumberNode toNumberFromNumericNode) {
            return toNumberFromNumericNode.executeNumeric(toNumericNode.execute(args[0]));
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ConstructObjectNode
    extends ConstructWithNewTargetNode {
        public ConstructObjectNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        protected static boolean arg0NullOrUndefined(Object[] args) {
            Object arg0 = args[0];
            return arg0 == Undefined.instance || arg0 == Null.instance;
        }

        protected static Object firstArgument(Object[] arguments) {
            return arguments.length == 0 ? Undefined.instance : arguments[0];
        }

        @Specialization(guards={"isNewTargetCase"})
        protected JSDynamicObject constructObjectNewTarget(JSDynamicObject newTarget, Object[] arguments) {
            return this.newObject(newTarget);
        }

        @Specialization(guards={"arguments.length == 0"})
        protected JSDynamicObject constructObject0(JSDynamicObject newTarget, Object[] arguments) {
            return this.newObject(newTarget);
        }

        @Specialization(guards={"!isNewTargetCase", "arguments.length > 0", "!arg0NullOrUndefined(arguments)"}, limit="InteropLibraryLimit")
        protected Object constructObjectJSObject(JSDynamicObject newTarget, Object[] arguments, @Cached(value="createToObject(getContext())") JSToObjectNode toObjectNode, @CachedLibrary(value="firstArgument(arguments)") InteropLibrary interop, @Cached(value="createBinaryProfile()") ConditionProfile isNull) {
            Object arg0 = arguments[0];
            if (isNull.profile(interop.isNull(arg0))) {
                return this.newObject(Null.instance);
            }
            return toObjectNode.execute(arg0);
        }

        @Specialization(guards={"arguments.length > 0", "arg0NullOrUndefined(arguments)"})
        protected JSDynamicObject constructObjectNullOrUndefined(JSDynamicObject newTarget, Object[] arguments) {
            return this.newObject(newTarget);
        }

        private JSDynamicObject newObject(JSDynamicObject newTarget) {
            return this.swapPrototype(JSOrdinary.create(this.getContext(), this.getRealm()), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getObjectPrototype();
        }
    }

    public static abstract class ConstructDateTimeFormatNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeDateTimeFormatNode initializeDateTimeFormatNode;

        public ConstructDateTimeFormatNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "any", "date");
        }

        @Specialization
        protected JSDynamicObject constructDateTimeFormat(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject dateTimeFormat = this.swapPrototype(JSDateTimeFormat.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeDateTimeFormatNode.executeInit(dateTimeFormat, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getDateTimeFormatPrototype();
        }
    }

    public static abstract class CallDateTimeFormatNode
    extends JSBuiltinNode {
        @Node.Child
        InitializeDateTimeFormatNode initializeDateTimeFormatNode;

        public CallDateTimeFormatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initializeDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "any", "date");
        }

        @Specialization
        protected JSDynamicObject callDateTimeFormat(Object locales, Object options) {
            JSDateTimeFormatObject dateTimeFormat = JSDateTimeFormat.create(this.getContext(), this.getRealm());
            return this.initializeDateTimeFormatNode.executeInit(dateTimeFormat, locales, options);
        }
    }

    public static abstract class ConstructPluralRulesNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializePluralRulesNode initializePluralRulesNode;

        public ConstructPluralRulesNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializePluralRulesNode = InitializePluralRulesNode.createInitalizePluralRulesNode(context);
        }

        @Specialization
        protected JSDynamicObject constructPluralRules(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject pluralRules = this.swapPrototype(JSPluralRules.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializePluralRulesNode.executeInit(pluralRules, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getPluralRulesPrototype();
        }
    }

    public static abstract class ConstructNumberFormatNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeNumberFormatNode initializeNumberFormatNode;

        public ConstructNumberFormatNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeNumberFormatNode = InitializeNumberFormatNode.createInitalizeNumberFormatNode(context);
        }

        @Specialization
        protected JSDynamicObject constructNumberFormat(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject numberFormat = this.swapPrototype(JSNumberFormat.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeNumberFormatNode.executeInit(numberFormat, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getNumberFormatPrototype();
        }
    }

    public static abstract class CallNumberFormatNode
    extends JSBuiltinNode {
        @Node.Child
        InitializeNumberFormatNode initializeNumberFormatNode;

        public CallNumberFormatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initializeNumberFormatNode = InitializeNumberFormatNode.createInitalizeNumberFormatNode(context);
        }

        @Specialization
        protected JSDynamicObject callNumberFormat(Object locales, Object options) {
            JSNumberFormatObject numberFormat = JSNumberFormat.create(this.getContext(), this.getRealm());
            return this.initializeNumberFormatNode.executeInit(numberFormat, locales, options);
        }
    }

    public static abstract class ConstructLocaleNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeLocaleNode initializeLocaleNode;

        public ConstructLocaleNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeLocaleNode = InitializeLocaleNode.createInitalizeLocaleNode(context);
        }

        @Specialization
        protected JSDynamicObject constructLocale(JSDynamicObject newTarget, Object tag, Object options) {
            JSDynamicObject locale = this.swapPrototype(JSLocale.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeLocaleNode.executeInit(locale, tag, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getLocalePrototype();
        }
    }

    public static abstract class ConstructDisplayNamesNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeDisplayNamesNode initializeDisplayNamesNode;

        public ConstructDisplayNamesNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeDisplayNamesNode = InitializeDisplayNamesNode.createInitalizeDisplayNamesNode(context);
        }

        @Specialization
        protected JSDynamicObject constructDisplayNames(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject displayNames = this.swapPrototype(JSDisplayNames.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeDisplayNamesNode.executeInit(displayNames, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getDisplayNamesPrototype();
        }
    }

    public static abstract class ConstructSegmenterNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeSegmenterNode initializeSegmenterNode;

        public ConstructSegmenterNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeSegmenterNode = InitializeSegmenterNode.createInitalizeSegmenterNode(context);
        }

        @Specialization
        protected JSDynamicObject constructSegmenter(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject segmenter = this.swapPrototype(JSSegmenter.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeSegmenterNode.executeInit(segmenter, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getSegmenterPrototype();
        }
    }

    public static abstract class ConstructRelativeTimeFormatNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeRelativeTimeFormatNode initializeRelativeTimeFormatNode;

        public ConstructRelativeTimeFormatNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeRelativeTimeFormatNode = InitializeRelativeTimeFormatNode.createInitalizeRelativeTimeFormatNode(context);
        }

        @Specialization
        protected JSDynamicObject constructRelativeTimeFormat(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject listFormat = this.swapPrototype(JSRelativeTimeFormat.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeRelativeTimeFormatNode.executeInit(listFormat, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getRelativeTimeFormatPrototype();
        }
    }

    public static abstract class ConstructListFormatNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeListFormatNode initializeListFormatNode;

        public ConstructListFormatNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeListFormatNode = InitializeListFormatNode.createInitalizeListFormatNode(context);
        }

        @Specialization
        protected JSDynamicObject constructListFormat(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject listFormat = this.swapPrototype(JSListFormat.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeListFormatNode.executeInit(listFormat, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getListFormatPrototype();
        }
    }

    public static abstract class ConstructCollatorNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        InitializeCollatorNode initializeCollatorNode;

        public ConstructCollatorNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
            this.initializeCollatorNode = InitializeCollatorNode.createInitalizeCollatorNode(context);
        }

        @Specialization
        protected JSDynamicObject constructCollator(JSDynamicObject newTarget, Object locales, Object options) {
            JSDynamicObject collator = this.swapPrototype(JSCollator.create(this.getContext(), this.getRealm()), newTarget);
            return this.initializeCollatorNode.executeInit(collator, locales, options);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getCollatorPrototype();
        }
    }

    public static abstract class CallCollatorNode
    extends JSBuiltinNode {
        @Node.Child
        InitializeCollatorNode initializeCollatorNode;

        public CallCollatorNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initializeCollatorNode = InitializeCollatorNode.createInitalizeCollatorNode(context);
        }

        @Specialization
        protected JSDynamicObject callCollator(Object locales, Object options) {
            JSCollatorObject collator = JSCollator.create(this.getContext(), this.getRealm());
            return this.initializeCollatorNode.executeInit(collator, locales, options);
        }
    }

    public static abstract class ConstructFinalizationRegistryNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        protected IsCallableNode isCallableNode = IsCallableNode.create();

        public ConstructFinalizationRegistryNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
        }

        @Specialization(guards={"isCallableNode.executeBoolean(cleanupCallback)"})
        protected JSDynamicObject constructFinalizationRegistry(JSDynamicObject newTarget, Object cleanupCallback) {
            return this.swapPrototype(JSFinalizationRegistry.create(this.getContext(), this.getRealm(), cleanupCallback), newTarget);
        }

        @Specialization(guards={"!isCallableNode.executeBoolean(cleanupCallback)"})
        protected JSDynamicObject constructFinalizationRegistryNonObject(JSDynamicObject newTarget, Object cleanupCallback) {
            throw Errors.createTypeError("FinalizationRegistry: cleanup must be callable");
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getFinalizationRegistryPrototype();
        }
    }

    public static abstract class ConstructWeakRefNode
    extends ConstructWithNewTargetNode {
        public ConstructWeakRefNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
        }

        @Specialization(guards={"isJSObject(target)"})
        protected JSDynamicObject constructWeakRef(JSDynamicObject newTarget, Object target) {
            return this.swapPrototype(JSWeakRef.create(this.getContext(), this.getRealm(), target), newTarget);
        }

        @Specialization(guards={"!isJSObject(target)"})
        protected JSDynamicObject constructWeakRefNonObject(JSDynamicObject newTarget, Object target) {
            throw Errors.createTypeError("WeakRef: target must be an object");
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getWeakRefPrototype();
        }
    }

    public static abstract class ConstructStringNode
    extends ConstructWithNewTargetNode {
        public ConstructStringNode(JSContext context, JSBuiltin builtin, boolean newTargetCase) {
            super(context, builtin, newTargetCase);
        }

        @Specialization(guards={"args.length == 0"})
        protected JSDynamicObject constructStringInt0(JSDynamicObject newTarget, Object[] args) {
            return this.swapPrototype(JSString.create(this.getContext(), this.getRealm(), Strings.EMPTY_STRING), newTarget);
        }

        @Specialization(guards={"args.length != 0"})
        protected JSDynamicObject constructString(JSDynamicObject newTarget, Object[] args, @Cached(value="create()") JSToStringNode toStringNode) {
            return this.swapPrototype(JSString.create(this.getContext(), this.getRealm(), toStringNode.executeString(args[0])), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getStringPrototype();
        }
    }

    public static abstract class CallStringNode
    extends JSBuiltinNode {
        public CallStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"args.length == 0"})
        protected Object callStringInt0(Object[] args) {
            return Strings.EMPTY_STRING;
        }

        @Specialization(guards={"args.length != 0"})
        protected Object callStringGeneric(Object[] args, @Cached(value="createSymbolToString()") JSToStringNode toStringNode) {
            return toStringNode.executeString(args[0]);
        }
    }

    public static abstract class ConstructRegExpNode
    extends ConstructWithNewTargetNode {
        private final boolean isCall;
        @Node.Child
        private JSToStringNode patternToStringNode;
        @Node.Child
        private JSToStringNode flagsToStringNode;
        @Node.Child
        private CompileRegexNode compileRegexNode;
        @Node.Child
        private CreateRegExpNode createRegExpNode;
        @Node.Child
        private PropertyGetNode getConstructorNode;
        @Node.Child
        private PropertyGetNode getSourceNode;
        @Node.Child
        private PropertyGetNode getFlagsNode;
        @Node.Child
        private TRegexUtil.InteropReadStringMemberNode interopReadPatternNode;
        private final BranchProfile regexpObject = BranchProfile.create();
        private final BranchProfile regexpMatcherObject = BranchProfile.create();
        private final BranchProfile regexpNonObject = BranchProfile.create();
        private final BranchProfile regexpObjectNewFlagsBranch = BranchProfile.create();
        private final ConditionProfile callIsRegExpProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile constructorEquivalentProfile = ConditionProfile.createBinaryProfile();

        public ConstructRegExpNode(JSContext context, JSBuiltin builtin, boolean isCall, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
            this.isCall = isCall;
        }

        @Specialization
        protected JSDynamicObject constructRegExp(JSDynamicObject newTarget, Object pattern, Object flags, @Cached(value="create(getContext())") IsRegExpNode isRegExpNode) {
            boolean hasMatchSymbol = isRegExpNode.executeBoolean(pattern);
            if (this.isCall) {
                JSDynamicObject patternObj;
                Object patternConstructor;
                if (this.callIsRegExpProfile.profile(hasMatchSymbol && flags == Undefined.instance && JSDynamicObject.isJSDynamicObject(pattern)) && this.constructorEquivalentProfile.profile((patternConstructor = this.getConstructor(patternObj = (JSDynamicObject)pattern)) == this.getRealm().getRegExpConstructor())) {
                    return patternObj;
                }
                return this.constructRegExpImpl(pattern, flags, hasMatchSymbol, true);
            }
            return this.swapPrototype(this.constructRegExpImpl(pattern, flags, hasMatchSymbol, newTarget == this.getRealm().getRegExpConstructor()), newTarget);
        }

        protected JSDynamicObject constructRegExpImpl(Object patternObj, Object flags, boolean hasMatchSymbol, boolean legacyFeaturesEnabled) {
            Object f;
            Object p;
            boolean isJSRegExp = JSRegExp.isJSRegExp(patternObj);
            if (isJSRegExp) {
                this.regexpObject.enter();
                Object compiledRegex = JSRegExp.getCompiledRegex((JSDynamicObject)patternObj);
                if (flags == Undefined.instance) {
                    return this.getCreateRegExpNode().createRegExp(compiledRegex);
                }
                if (this.getContext().getEcmaScriptVersion() < 6) {
                    throw Errors.createTypeError("Cannot supply flags when constructing one RegExp from another");
                }
                Object flagsStr = this.flagsToString(flags);
                this.regexpObjectNewFlagsBranch.enter();
                Object newCompiledRegex = this.getCompileRegexNode().compile(this.getInteropReadPatternNode().execute(compiledRegex, "pattern"), flagsStr);
                return this.getCreateRegExpNode().createRegExp(newCompiledRegex);
            }
            if (hasMatchSymbol) {
                this.regexpMatcherObject.enter();
                JSDynamicObject patternJSObj = (JSDynamicObject)patternObj;
                p = this.getSource(patternJSObj);
                f = flags == Undefined.instance ? this.getFlags(patternJSObj) : flags;
            } else {
                this.regexpNonObject.enter();
                p = patternObj;
                f = flags;
            }
            TruffleString patternStr = this.getPatternToStringNode().executeString(p);
            Object flagsStr = this.flagsToString(f);
            Object compiledRegex = this.getCompileRegexNode().compile(patternStr, flagsStr);
            JSRegExpObject regExp = this.getCreateRegExpNode().createRegExp(compiledRegex, legacyFeaturesEnabled);
            if (this.getContext().getContextOptions().isTestV8Mode()) {
                JSObjectUtil.putDataProperty(this.getContext(), regExp, Strings.SOURCE, JSRegExp.escapeRegExpPattern(patternStr), JSAttributes.configurableNotEnumerableNotWritable());
            }
            return regExp;
        }

        private JSToStringNode getPatternToStringNode() {
            if (this.patternToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.patternToStringNode = this.insert(JSToStringNode.createUndefinedToEmpty());
            }
            return this.patternToStringNode;
        }

        private TRegexUtil.InteropReadStringMemberNode getInteropReadPatternNode() {
            if (this.interopReadPatternNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.interopReadPatternNode = this.insert(TRegexUtil.InteropReadStringMemberNode.create());
            }
            return this.interopReadPatternNode;
        }

        private CompileRegexNode getCompileRegexNode() {
            if (this.compileRegexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.compileRegexNode = this.insert(CompileRegexNode.create(this.getContext()));
            }
            return this.compileRegexNode;
        }

        private CreateRegExpNode getCreateRegExpNode() {
            if (this.createRegExpNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.createRegExpNode = this.insert(CreateRegExpNode.create(this.getContext()));
            }
            return this.createRegExpNode;
        }

        private Object flagsToString(Object f) {
            if (this.flagsToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.flagsToStringNode = this.insert(JSToStringNode.createUndefinedToEmpty());
            }
            return this.flagsToStringNode.executeString(f);
        }

        private Object getConstructor(JSDynamicObject obj) {
            if (this.getConstructorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getConstructorNode = this.insert(PropertyGetNode.create(JSObject.CONSTRUCTOR, false, this.getContext()));
            }
            return this.getConstructorNode.getValue(obj);
        }

        private Object getSource(JSDynamicObject obj) {
            if (this.getSourceNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getSourceNode = this.insert(PropertyGetNode.create(JSRegExp.SOURCE, false, this.getContext()));
            }
            return this.getSourceNode.getValue(obj);
        }

        private Object getFlags(JSDynamicObject obj) {
            if (this.getFlagsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getFlagsNode = this.insert(PropertyGetNode.create(JSRegExp.FLAGS, false, this.getContext()));
            }
            return this.getFlagsNode.getValue(obj);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getRegExpPrototype();
        }
    }

    public static abstract class ConstructTemporalZonedDateTime
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalZonedDateTime(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalZonedDateTime(JSDynamicObject newTarget, Object epochNanoseconds, Object timeZoneLike, Object calendarLike, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create()") JSToBigIntNode toBigIntNode, @Cached BranchProfile errorBranch) {
            BigInt ns = toBigIntNode.executeBigInteger(epochNanoseconds);
            if (!TemporalUtil.isValidEpochNanoseconds(ns)) {
                errorBranch.enter();
                throw TemporalErrors.createRangeErrorInvalidNanoseconds();
            }
            JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(timeZoneLike);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            return this.swapPrototype(JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), ns, timeZone, calendar), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalZonedDateTimePrototype();
        }
    }

    public static abstract class ConstructTemporalTimeZone
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalTimeZone(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalTimeZone(JSDynamicObject newTarget, Object identifier, @Cached(value="create()") JSToStringNode toStringNode) {
            TruffleString id = toStringNode.executeString(identifier);
            return this.constructTemporalTimeZoneIntl(newTarget, id);
        }

        @CompilerDirectives.TruffleBoundary
        private JSDynamicObject constructTemporalTimeZoneIntl(JSDynamicObject newTarget, TruffleString idParam) {
            TruffleString id = idParam;
            boolean canParse = TemporalUtil.canParseAsTimeZoneNumericUTCOffset(id);
            if (!canParse) {
                if (!TemporalUtil.isValidTimeZoneName(id)) {
                    throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
                }
                id = TemporalUtil.canonicalizeTimeZoneName(id);
            }
            return this.swapPrototype(TemporalUtil.createTemporalTimeZone(this.getContext(), id), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalTimeZonePrototype();
        }
    }

    public static abstract class ConstructTemporalInstant
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalInstant(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalInstant(JSDynamicObject newTarget, Object epochNanoseconds, @Cached BranchProfile errorBranch) {
            BigInt bi = JSRuntime.toBigInt(epochNanoseconds);
            if (!TemporalUtil.isValidEpochNanoseconds(bi)) {
                errorBranch.enter();
                throw TemporalErrors.createRangeErrorInvalidNanoseconds();
            }
            return this.swapPrototype(JSTemporalInstant.create(this.getContext(), this.getRealm(), bi), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalInstantPrototype();
        }
    }

    public static abstract class ConstructTemporalPlainMonthDay
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalPlainMonthDay(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalPlainMonthDay(JSDynamicObject newTarget, Object isoMonth, Object isoDay, Object calendarLike, Object refISOYear, @Cached(value="create()") BranchProfile errorBranch, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toInt, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode) {
            Object referenceISOYear = refISOYear;
            if (referenceISOYear == Undefined.instance || referenceISOYear == null) {
                referenceISOYear = 1972;
            }
            int m = toInt.executeIntOrThrow(isoMonth);
            int d = toInt.executeIntOrThrow(isoDay);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            int ref = toInt.executeIntOrThrow(referenceISOYear);
            return this.swapPrototype(JSTemporalPlainMonthDay.create(this.getContext(), m, d, calendar, ref, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalPlainMonthDayPrototype();
        }
    }

    public static abstract class ConstructTemporalPlainYearMonth
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalPlainYearMonth(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalPlainYearMonth(JSDynamicObject newTarget, Object isoYear, Object isoMonth, Object calendarLike, Object refISODay, @Cached(value="create()") BranchProfile errorBranch, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toInteger, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode) {
            Object referenceISODay = refISODay;
            if (referenceISODay == Undefined.instance || referenceISODay == null) {
                referenceISODay = 1;
            }
            int y = toInteger.executeIntOrThrow(isoYear);
            int m = toInteger.executeIntOrThrow(isoMonth);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            int ref = toInteger.executeIntOrThrow(referenceISODay);
            return this.swapPrototype(JSTemporalPlainYearMonth.create(this.getContext(), y, m, calendar, ref, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalPlainYearMonthPrototype();
        }
    }

    public static abstract class ConstructTemporalCalendar
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalCalendar(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalCalendar(JSDynamicObject newTarget, Object arg, @Cached BranchProfile errorBranch, @Cached(value="create()") JSToStringNode toString) {
            TruffleString id = toString.executeString(arg);
            return this.swapPrototype(JSTemporalCalendar.create(this.getContext(), this.getRealm(), id, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalCalendarPrototype();
        }
    }

    public static abstract class ConstructTemporalDurationNode
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalDurationNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalDuration(JSDynamicObject newTarget, Object yearsObj, Object monthsObj, Object weeksObj, Object daysObj, Object hoursObj, Object minutesObj, Object secondsObj, Object millisecondsObject, Object microsecondsObject, Object nanosecondsObject, @Cached(value="create()") JSToIntegerWithoutRoundingNode toIntegerNode, @Cached BranchProfile errorBranch) {
            double years = toIntegerNode.executeDouble(yearsObj);
            double months = toIntegerNode.executeDouble(monthsObj);
            double weeks = toIntegerNode.executeDouble(weeksObj);
            double days = toIntegerNode.executeDouble(daysObj);
            double hours = toIntegerNode.executeDouble(hoursObj);
            double minutes = toIntegerNode.executeDouble(minutesObj);
            double seconds = toIntegerNode.executeDouble(secondsObj);
            double milliseconds = toIntegerNode.executeDouble(millisecondsObject);
            double microseconds = toIntegerNode.executeDouble(microsecondsObject);
            double nanoseconds = toIntegerNode.executeDouble(nanosecondsObject);
            return this.swapPrototype(JSTemporalDuration.createTemporalDuration(this.getContext(), years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalDurationPrototype();
        }
    }

    public static abstract class ConstructTemporalPlainDateTimeNode
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalPlainDateTimeNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalPlainDateTime(JSDynamicObject newTarget, Object yearObj, Object monthObj, Object dayObj, Object hourObj, Object minuteObj, Object secondObj, Object millisecondObject, Object microsecondObject, Object nanosecondObject, Object calendarLike, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntegerNode, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached BranchProfile errorBranch) {
            int year = toIntegerNode.executeIntOrThrow(yearObj);
            int month = toIntegerNode.executeIntOrThrow(monthObj);
            int day = toIntegerNode.executeIntOrThrow(dayObj);
            int hour = toIntegerNode.executeIntOrThrow(hourObj);
            int minute = toIntegerNode.executeIntOrThrow(minuteObj);
            int second = toIntegerNode.executeIntOrThrow(secondObj);
            int millisecond = toIntegerNode.executeIntOrThrow(millisecondObject);
            int microsecond = toIntegerNode.executeIntOrThrow(microsecondObject);
            int nanosecond = toIntegerNode.executeIntOrThrow(nanosecondObject);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            return this.swapPrototype(JSTemporalPlainDateTime.create(this.getContext(), year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, calendar, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalPlainTimePrototype();
        }
    }

    public static abstract class ConstructTemporalPlainTimeNode
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalPlainTimeNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalPlainTime(JSDynamicObject newTarget, Object hourObj, Object minuteObj, Object secondObj, Object millisecondObject, Object microsecondObject, Object nanosecondObject, @Cached BranchProfile errorBranch, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntegerNode) {
            int hour = toIntegerNode.executeIntOrThrow(hourObj);
            int minute = toIntegerNode.executeIntOrThrow(minuteObj);
            int second = toIntegerNode.executeIntOrThrow(secondObj);
            int millisecond = toIntegerNode.executeIntOrThrow(millisecondObject);
            int microsecond = toIntegerNode.executeIntOrThrow(microsecondObject);
            int nanosecond = toIntegerNode.executeIntOrThrow(nanosecondObject);
            return this.swapPrototype(JSTemporalPlainTime.create(this.getContext(), hour, minute, second, millisecond, microsecond, nanosecond, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalPlainTimePrototype();
        }
    }

    public static abstract class ConstructTemporalPlainDateNode
    extends ConstructWithNewTargetNode {
        protected ConstructTemporalPlainDateNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructTemporalPlainDate(JSDynamicObject newTarget, Object isoYear, Object isoMonth, Object isoDay, Object calendarLike, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntegerNode, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create()") BranchProfile errorBranch) {
            int y = toIntegerNode.executeIntOrThrow(isoYear);
            int m = toIntegerNode.executeIntOrThrow(isoMonth);
            int d = toIntegerNode.executeIntOrThrow(isoDay);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            return this.swapPrototype(JSTemporalPlainDate.create(this.getContext(), y, m, d, calendar, errorBranch), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getTemporalPlainTimePrototype();
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ConstructDateNode
    extends ConstructWithNewTargetNode {
        @Node.Child
        private JSToPrimitiveNode toPrimitiveNode;
        @Node.Child
        private JSToDoubleNode toDoubleNode;
        private final ConditionProfile stringOrNumberProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isDateProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile gotFieldsProfile = ConditionProfile.createBinaryProfile();

        public ConstructDateNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        private Object toPrimitive(Object target) {
            if (this.toPrimitiveNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPrimitiveNode = this.insert(JSToPrimitiveNode.createHintDefault());
            }
            return this.toPrimitiveNode.execute(target);
        }

        protected double toDouble(Object target) {
            if (this.toDoubleNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toDoubleNode = this.insert(JSToDoubleNode.create());
            }
            return this.toDoubleNode.executeDouble(target);
        }

        @Specialization(guards={"args.length == 0"})
        protected JSDynamicObject constructDateZero(JSDynamicObject newTarget, Object[] args) {
            return this.swapPrototype(JSDate.create(this.getContext(), this.getRealm(), this.now()), newTarget);
        }

        @Specialization(guards={"args.length == 1"})
        protected JSDynamicObject constructDateOne(JSDynamicObject newTarget, Object[] args, @Cached(value="createBinaryProfile()") ConditionProfile isSpecialCase, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            double dateValue = this.getDateValue(args[0], interop);
            return this.swapPrototype(JSDate.create(this.getContext(), this.getRealm(), ConstructDateNode.timeClip(dateValue, isSpecialCase)), newTarget);
        }

        @Specialization(guards={"args.length >= 2"})
        protected JSDynamicObject constructDateMult(JSDynamicObject newTarget, Object[] args) {
            double val = this.constructorImpl(args);
            return this.swapPrototype(JSDate.create(this.getContext(), this.getRealm(), val), newTarget);
        }

        private static double timeClip(double dateValue, ConditionProfile isSpecialCase) {
            if (isSpecialCase.profile(Double.isInfinite(dateValue) || Double.isNaN(dateValue) || Math.abs(dateValue) > 8.64E15)) {
                return Double.NaN;
            }
            return (long)dateValue;
        }

        @CompilerDirectives.TruffleBoundary
        private double now() {
            return this.getRealm().currentTimeMillis();
        }

        @CompilerDirectives.TruffleBoundary
        private double parseDate(TruffleString target) {
            Integer[] fields = this.getContext().getEvaluator().parseDate(this.getRealm(), Strings.toJavaString(Strings.lazyTrim(target)), false);
            if (this.gotFieldsProfile.profile(fields != null)) {
                return JSDate.makeDate(fields[0].intValue(), fields[1].intValue(), fields[2].intValue(), fields[3].intValue(), fields[4].intValue(), fields[5].intValue(), fields[6].intValue(), fields[7]);
            }
            return Double.NaN;
        }

        private double getDateValue(Object arg0, InteropLibrary interop) {
            Object value2;
            if (this.getContext().getEcmaScriptVersion() >= 6) {
                if (this.isDateProfile.profile(JSDate.isJSDate(arg0))) {
                    return JSDate.getTimeMillisField((JSDateObject)arg0);
                }
                if (interop.isInstant(arg0)) {
                    return JSDate.getDateValueFromInstant(arg0, interop);
                }
            }
            if (this.stringOrNumberProfile.profile(Strings.isTString(value2 = this.toPrimitive(arg0)))) {
                return this.parseDate(JSRuntime.toStringIsString(value2));
            }
            double dval = this.toDouble(value2);
            if (Double.isInfinite(dval) || Double.isNaN(dval)) {
                return Double.NaN;
            }
            return dval;
        }

        private double constructorImpl(Object[] args) {
            double[] argsEvaluated = new double[args.length];
            boolean isNaN = false;
            for (int i = 0; i < args.length; ++i) {
                double d = this.toDouble(args[i]);
                if (Double.isNaN(d)) {
                    isNaN = true;
                }
                argsEvaluated[i] = d;
            }
            if (isNaN) {
                return Double.NaN;
            }
            return JSDate.executeConstructor(argsEvaluated, false);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getDatePrototype();
        }
    }

    public static abstract class CallDateNode
    extends JSBuiltinNode {
        public CallDateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected Object callDate() {
            JSRealm realm = this.getRealm();
            return JSDate.toString(realm.currentTimeMillis(), realm);
        }
    }

    public static abstract class ConstructBooleanNode
    extends ConstructWithNewTargetNode {
        public ConstructBooleanNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        @Specialization
        protected JSDynamicObject constructBoolean(JSDynamicObject newTarget, Object value2, @Cached(value="create()") JSToBooleanNode toBoolean) {
            return this.swapPrototype(JSBoolean.create(this.getContext(), this.getRealm(), toBoolean.executeBoolean(value2)), newTarget);
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getBooleanPrototype();
        }
    }

    public static abstract class CallBooleanNode
    extends JSBuiltinNode {
        public CallBooleanNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean callBoolean(Object value2, @Cached(value="create()") JSToBooleanNode toBoolean) {
            return toBoolean.executeBoolean(value2);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ConstructArrayNode
    extends ConstructWithNewTargetNode {
        @CompilerDirectives.CompilationFinal
        private ConstructArrayAllocationSite arrayAllocationSite = ConstructArrayNode.createAllocationSite();

        public ConstructArrayNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin, isNewTargetCase);
        }

        protected static boolean isOneNumberArg(Object[] args) {
            return args.length == 1 && JSRuntime.isNumber(args[0]);
        }

        protected static boolean isOneForeignArg(Object[] args) {
            return args.length == 1 && JSRuntime.isForeignObject(args[0]);
        }

        protected static boolean isOneIntegerArg(Object[] args) {
            return args.length == 1 && args[0] instanceof Integer && (Integer)args[0] >= 0;
        }

        @Specialization(guards={"args.length == 0"})
        protected JSDynamicObject constructArray0(JSDynamicObject newTarget, Object[] args) {
            return this.swapPrototype(JSArray.createConstantEmptyArray(this.getContext(), this.getRealm(), this.arrayAllocationSite), newTarget);
        }

        @Specialization(guards={"isOneIntegerArg(args)"})
        protected JSDynamicObject constructArrayWithIntLength(JSDynamicObject newTarget, Object[] args) {
            ScriptArray initialType;
            int length = (Integer)args[0];
            JSRealm realm = this.getRealm();
            if (JSConfig.TrackArrayAllocationSites && this.arrayAllocationSite != null && this.arrayAllocationSite.isTyped() && (initialType = this.arrayAllocationSite.getInitialArrayType()) != null) {
                return this.swapPrototype(JSArray.create(this.getContext(), realm, initialType, ((AbstractWritableArray)initialType).allocateArray(length), length), newTarget);
            }
            return this.swapPrototype(JSArray.createConstantEmptyArray(this.getContext(), realm, this.arrayAllocationSite, length), newTarget);
        }

        @Specialization(guards={"args.length == 1", "toArrayLengthNode.isTypeNumber(len)"}, replaces={"constructArrayWithIntLength"})
        protected JSDynamicObject constructWithLength(JSDynamicObject newTarget, Object[] args, @Cached ToArrayLengthNode toArrayLengthNode, @Cached(value="create(getContext())") ArrayCreateNode arrayCreateNode, @Bind(value="toArrayLengthNode.executeLong(firstArg(args))") long len) {
            JSArrayObject array = arrayCreateNode.execute(len);
            return this.swapPrototype(array, newTarget);
        }

        static Object firstArg(Object[] arguments) {
            return arguments[0];
        }

        @Specialization(guards={"isOneForeignArg(args)"}, limit="InteropLibraryLimit")
        protected JSDynamicObject constructWithForeignArg(JSDynamicObject newTarget, Object[] args, @CachedLibrary(value="firstArg(args)") InteropLibrary interop, @Cached(value="create(getContext())") ArrayCreateNode arrayCreateNode, @Cached(value="createBinaryProfile()") ConditionProfile isNumber, @Cached(value="create()") BranchProfile rangeErrorProfile) {
            Object len = args[0];
            if (isNumber.profile(interop.isNumber(len))) {
                if (interop.fitsInLong(len)) {
                    try {
                        long length = interop.asLong(len);
                        if (JSRuntime.isArrayIndex(length)) {
                            JSArrayObject array = arrayCreateNode.execute(length);
                            return this.swapPrototype(array, newTarget);
                        }
                    }
                    catch (UnsupportedMessageException umex) {
                        rangeErrorProfile.enter();
                        throw Errors.createTypeErrorInteropException(len, umex, "asLong", this);
                    }
                }
                rangeErrorProfile.enter();
                throw Errors.createRangeErrorInvalidArrayLength();
            }
            return this.swapPrototype(JSArray.create(this.getContext(), this.getRealm(), ConstantObjectArray.createConstantObjectArray(), args, 1L), newTarget);
        }

        @Specialization(guards={"!isOneNumberArg(args)", "!isOneForeignArg(args)"})
        protected JSDynamicObject constructArrayVarargs(JSDynamicObject newTarget, Object[] args, @Cached(value="create()") BranchProfile isIntegerCase, @Cached(value="create()") BranchProfile isDoubleCase, @Cached(value="create()") BranchProfile isObjectCase, @Cached(value="createBinaryProfile()") ConditionProfile isLengthZero) {
            JSRealm realm = this.getRealm();
            if (isLengthZero.profile(args == null || args.length == 0)) {
                return this.swapPrototype(JSArray.create(this.getContext(), realm, ScriptArray.createConstantEmptyArray(), args, 0L), newTarget);
            }
            ArrayLiteralNode.ArrayContentType type = ArrayLiteralNode.identifyPrimitiveContentType(args, false);
            if (type == ArrayLiteralNode.ArrayContentType.Integer) {
                isIntegerCase.enter();
                return this.swapPrototype(JSArray.createZeroBasedIntArray(this.getContext(), realm, ArrayLiteralNode.createIntArray(args)), newTarget);
            }
            if (type == ArrayLiteralNode.ArrayContentType.Double) {
                isDoubleCase.enter();
                return this.swapPrototype(JSArray.createZeroBasedDoubleArray(this.getContext(), realm, ArrayLiteralNode.createDoubleArray(args)), newTarget);
            }
            isObjectCase.enter();
            return this.swapPrototype(JSArray.create(this.getContext(), realm, ConstantObjectArray.createConstantObjectArray(), args, args.length), newTarget);
        }

        @Override
        public JavaScriptNode copy() {
            ConstructArrayNode copy = (ConstructArrayNode)super.copy();
            copy.arrayAllocationSite = ConstructArrayNode.createAllocationSite();
            return copy;
        }

        private static ConstructArrayAllocationSite createAllocationSite() {
            return JSConfig.TrackArrayAllocationSites ? new ConstructArrayAllocationSite() : null;
        }

        @Override
        protected JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
            return realm.getArrayPrototype();
        }

        private static final class ConstructArrayAllocationSite
        implements ArrayAllocationSite {
            private static final ScriptArray UNINIT_ARRAY_TYPE = ScriptArray.createConstantEmptyArray();
            @CompilerDirectives.CompilationFinal
            private ScriptArray concreteArrayType = UNINIT_ARRAY_TYPE;
            @CompilerDirectives.CompilationFinal
            private Assumption assumption = Truffle.getRuntime().createAssumption("Array allocation site (untyped)");

            private ConstructArrayAllocationSite() {
            }

            public boolean isTyped() {
                return this.assumption.isValid() && this.concreteArrayType != UNINIT_ARRAY_TYPE && this.concreteArrayType != null;
            }

            @Override
            public void notifyArrayTransition(ScriptArray arrayType, int length) {
                CompilerAsserts.neverPartOfCompilation("do not notify array transitions from compiled code");
                assert (JSConfig.TrackArrayAllocationSites);
                if (arrayType instanceof AbstractWritableArray && length > 0) {
                    if (this.concreteArrayType == UNINIT_ARRAY_TYPE) {
                        this.concreteArrayType = arrayType;
                        this.assumption.invalidate("TypedArray type initialization");
                        this.assumption = Truffle.getRuntime().createAssumption("Array allocation site (typed)");
                    } else if (this.concreteArrayType != arrayType) {
                        this.concreteArrayType = null;
                        this.assumption.invalidate("TypedArray type rewrite");
                    }
                }
            }

            @Override
            public ScriptArray getInitialArrayType() {
                if (this.isTyped()) {
                    return this.concreteArrayType;
                }
                return null;
            }
        }
    }

    public static abstract class ConstructWithNewTargetNode
    extends JSBuiltinNode {
        protected final boolean isNewTargetCase;

        protected ConstructWithNewTargetNode(JSContext context, JSBuiltin builtin, boolean isNewTargetCase) {
            super(context, builtin);
            this.isNewTargetCase = isNewTargetCase;
        }

        protected JSRealm getRealmFromNewTarget(Object newTarget) {
            if (this.isNewTargetCase) {
                return JSRuntime.getFunctionRealm(newTarget, this.getRealm());
            }
            return this.getRealm();
        }

        protected abstract JSDynamicObject getIntrinsicDefaultProto(JSRealm var1);

        protected JSDynamicObject swapPrototype(JSDynamicObject resultObj, JSDynamicObject newTarget) {
            if (this.isNewTargetCase) {
                return this.setPrototypeFromNewTarget(resultObj, newTarget);
            }
            return resultObj;
        }

        protected JSDynamicObject setPrototypeFromNewTarget(JSDynamicObject resultObj, JSDynamicObject newTarget) {
            Object prototype = JSObject.get(newTarget, JSObject.PROTOTYPE);
            if (!JSRuntime.isObject(prototype)) {
                prototype = this.getIntrinsicDefaultProto(this.getRealmFromNewTarget(newTarget));
            }
            JSObject.setPrototype(resultObj, (JSDynamicObject)prototype);
            return resultObj;
        }
    }

    public static enum Constructor implements BuiltinEnum<Constructor>
    {
        Array(1),
        Boolean(1),
        Date(7),
        RegExp(2),
        String(1),
        Object(1),
        Number(1),
        BigInt(1),
        Function(1),
        ArrayBuffer(1),
        Collator(0),
        NumberFormat(0),
        ListFormat(0),
        PluralRules(0),
        DateTimeFormat(0),
        RelativeTimeFormat(0),
        Segmenter(0),
        DisplayNames(2),
        Locale(1),
        Error(1),
        RangeError(1),
        TypeError(1),
        ReferenceError(1),
        SyntaxError(1),
        EvalError(1),
        URIError(1),
        AggregateError(2),
        CompileError(1),
        LinkError(1),
        RuntimeError(1),
        Int8Array(3),
        Uint8Array(3),
        Uint8ClampedArray(3),
        Int16Array(3),
        Uint16Array(3),
        Int32Array(3),
        Uint32Array(3),
        Float32Array(3),
        Float64Array(3),
        BigInt64Array(3),
        BigUint64Array(3),
        DataView(1),
        Map(0),
        Set(0),
        WeakRef(1),
        FinalizationRegistry(1),
        WeakMap(0),
        WeakSet(0),
        GeneratorFunction(1),
        Proxy(2),
        Promise(1),
        AsyncFunction(1),
        SharedArrayBuffer(1),
        AsyncGeneratorFunction(1),
        Global(1),
        Instance(1),
        Memory(1),
        Module(1),
        Table(1),
        PlainTime(0),
        PlainDate(3),
        PlainDateTime(3),
        Duration(0),
        Calendar(1),
        PlainYearMonth(2),
        PlainMonthDay(2),
        Instant(1),
        TimeZone(1),
        ZonedDateTime(2),
        TypedArray(0),
        Symbol(0),
        JSAdapter(1),
        JavaImporter(1);

        private final int length;

        private Constructor(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isConstructor() {
            return true;
        }

        @Override
        public boolean isNewTargetConstructor() {
            return EnumSet.range(Array, ZonedDateTime).contains(this);
        }

        @Override
        public int getECMAScriptVersion() {
            if (AsyncGeneratorFunction == this) {
                return 9;
            }
            if (EnumSet.of(SharedArrayBuffer, AsyncFunction).contains(this)) {
                return 8;
            }
            if (EnumSet.range(Map, Symbol).contains(this)) {
                return 6;
            }
            if (EnumSet.of(PlainTime, new Constructor[]{Calendar, Duration, PlainDate, PlainDateTime, PlainYearMonth, PlainMonthDay, Instant, TimeZone, ZonedDateTime}).contains(this)) {
                return 13;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

