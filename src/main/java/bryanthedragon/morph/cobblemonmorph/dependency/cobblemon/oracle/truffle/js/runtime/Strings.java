
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Locale;

public final class Strings {
    public static final TruffleString EMPTY_STRING = TruffleString.Encoding.UTF_16.getEmpty();
    public static final TruffleString LINE_SEPARATOR = Strings.constant("\n");
    public static final String LINE_SEPARATOR_JLS = Strings.toJavaString(LINE_SEPARATOR);
    public static final TruffleString ZERO = Strings.constant("0");
    public static final TruffleString NEGATIVE_ZERO = Strings.constant("-0");
    public static final TruffleString INFINITY = Strings.constant("Infinity");
    public static final TruffleString NEGATIVE_INFINITY = Strings.constant("-Infinity");
    public static final TruffleString POSITIVE_INFINITY = Strings.constant("+Infinity");
    public static final TruffleString NAN = Strings.constant("NaN");
    public static final TruffleString CAPS_POSITIVE_INFINITY = Strings.constant("POSITIVE_INFINITY");
    public static final TruffleString CAPS_NEGATIVE_INFINITY = Strings.constant("NEGATIVE_INFINITY");
    public static final TruffleString CAPS_MAX_VALUE = Strings.constant("MAX_VALUE");
    public static final TruffleString CAPS_MIN_VALUE = Strings.constant("MIN_VALUE");
    public static final TruffleString CAPS_EPSILON = Strings.constant("EPSILON");
    public static final TruffleString CAPS_MAX_SAFE_INTEGER = Strings.constant("MAX_SAFE_INTEGER");
    public static final TruffleString CAPS_MIN_SAFE_INTEGER = Strings.constant("MIN_SAFE_INTEGER");
    public static final TruffleString E = Strings.constant("E");
    public static final TruffleString PI = Strings.constant("PI");
    public static final TruffleString LN_10 = Strings.constant("LN10");
    public static final TruffleString LN_2 = Strings.constant("LN2");
    public static final TruffleString LOG_2_E = Strings.constant("LOG2E");
    public static final TruffleString LOG_10_E = Strings.constant("LOG10E");
    public static final TruffleString SQRT_1_2 = Strings.constant("SQRT1_2");
    public static final TruffleString SQRT_2 = Strings.constant("SQRT2");
    public static final TruffleString ANGLE_BRACKET_OPEN = Strings.constant("<");
    public static final TruffleString ANGLE_BRACKET_OPEN_2 = Strings.constant("<<");
    public static final TruffleString ANGLE_BRACKET_CLOSE = Strings.constant(">");
    public static final TruffleString ANGLE_BRACKET_CLOSE_2 = Strings.constant(">>");
    public static final TruffleString ANGLE_BRACKET_CLOSE_3 = Strings.constant(">>>");
    public static final TruffleString ANGLE_BRACKET_OPEN_SLASH = Strings.constant("</");
    public static final TruffleString BACKSLASH = Strings.constant("\\");
    public static final TruffleString BIG_ARROW_SPACES = Strings.constant(" => ");
    public static final TruffleString BRACKET_OPEN = Strings.constant("[");
    public static final TruffleString BRACKET_OPEN_2 = Strings.constant("[[");
    public static final TruffleString BRACKET_CLOSE = Strings.constant("]");
    public static final TruffleString BRACKET_CLOSE_2_COLON = Strings.constant("]]: ");
    public static final TruffleString COMMA = Strings.constant(",");
    public static final TruffleString COMMA_SPC = Strings.constant(", ");
    public static final TruffleString COMMA_NEWLINE = Strings.constant(",\n");
    public static final TruffleString COLON = Strings.constant(":");
    public static final TruffleString COLON_SPACE = Strings.constant(": ");
    public static final TruffleString DASH = Strings.constant("-");
    public static final TruffleString DOUBLE_QUOTE = Strings.constant("\"");
    public static final TruffleString DOT = Strings.constant(".");
    public static final TruffleString DOT_SLASH = Strings.constant("./");
    public static final TruffleString DOT_DOT_SLASH = Strings.constant("../");
    public static final TruffleString DOT_DOT_DOT = Strings.constant("...");
    public static final TruffleString EMPTY_ARRAY = Strings.constant("[]");
    public static final TruffleString EMPTY_ARRAY_DOTS = Strings.constant("[...]");
    public static final TruffleString EMPTY_OBJECT = Strings.constant("{}");
    public static final TruffleString EMPTY_OBJECT_DOTS = Strings.constant("{...}");
    public static final TruffleString EQUALS_DOUBLE_QUOTE = Strings.constant("=\"");
    public static final TruffleString GET_DOLLAR_UNDERSCORE = Strings.constant("get $_");
    public static final TruffleString PAREN_OPEN = Strings.constant("(");
    public static final TruffleString PAREN_CLOSE = Strings.constant(")");
    public static final TruffleString UNDERSCORE = Strings.constant("_");
    public static final TruffleString UNDERSCORE_2 = Strings.constant("__");
    public static final TruffleString SLASH = Strings.constant("/");
    public static final TruffleString SPACE = Strings.constant(" ");
    public static final TruffleString SINGLE_QUOTE = Strings.constant("'");
    public static final TruffleString SYMBOL_PLUS = Strings.constant("+");
    public static final TruffleString SYMBOL_PLUS_PLUS = Strings.constant("++");
    public static final TruffleString SYMBOL_MINUS = Strings.constant("-");
    public static final TruffleString SYMBOL_MINUS_MINUS = Strings.constant("--");
    public static final TruffleString SYMBOL_AMPERSAND = Strings.constant("&");
    public static final TruffleString SYMBOL_PIPE = Strings.constant("|");
    public static final TruffleString SYMBOL_PERCENT = Strings.constant("%");
    public static final TruffleString SYMBOL_CARET = Strings.constant("^");
    public static final TruffleString SYMBOL_TILDE = Strings.constant("~");
    public static final TruffleString SYMBOL_EQUALS_EQUALS = Strings.constant("==");
    public static final TruffleString SYMBOL_STAR = Strings.constant("*");
    public static final TruffleString SYMBOL_STAR_STAR = Strings.constant("**");
    public static final TruffleString EMPTY_REGEX = Strings.constant("(?:)");
    public static final TruffleString INPUT = Strings.constant("input");
    public static final TruffleString MULTILINE = Strings.constant("multiline");
    public static final TruffleString LAST_MATCH = Strings.constant("lastMatch");
    public static final TruffleString LAST_PAREN = Strings.constant("lastParen");
    public static final TruffleString LEFT_CONTEXT = Strings.constant("leftContext");
    public static final TruffleString RIGHT_CONTEXT = Strings.constant("rightContext");
    public static final TruffleString $_ = Strings.constant("$_");
    public static final TruffleString $_AMPERSAND = Strings.constant("$&");
    public static final TruffleString $_PLUS = Strings.constant("$+");
    public static final TruffleString $_BACKTICK = Strings.constant("$`");
    public static final TruffleString $_SQUOT = Strings.constant("$'");
    public static final TruffleString $_1 = Strings.constant("$1");
    public static final TruffleString $_2 = Strings.constant("$2");
    public static final TruffleString $_3 = Strings.constant("$3");
    public static final TruffleString $_4 = Strings.constant("$4");
    public static final TruffleString $_5 = Strings.constant("$5");
    public static final TruffleString $_6 = Strings.constant("$6");
    public static final TruffleString $_7 = Strings.constant("$7");
    public static final TruffleString $_8 = Strings.constant("$8");
    public static final TruffleString $_9 = Strings.constant("$9");
    public static final TruffleString SET_INPUT = Strings.constant("setInput");
    public static final TruffleString BRACKET_SYMBOL_DOT = Strings.constant("[Symbol.");
    public static final TruffleString UND_DASH = Strings.constant("und-");
    public static final TruffleString DASH_PER_DASH = Strings.constant("-per-");
    public static final TruffleString X_DASH = Strings.constant("x-");
    public static final TruffleString DASH_X_DASH = Strings.constant("-x-");
    public static final TruffleString ACCESSOR = Strings.constant("accessor");
    public static final TruffleString ADD = Strings.constant("add");
    public static final TruffleString ALL = Strings.constant("all");
    public static final TruffleString ANY = Strings.constant("any");
    public static final TruffleString APPLY = Strings.constant("apply");
    public static final TruffleString ARGUMENTS = Strings.constant("arguments");
    public static final TruffleString BOUND = Strings.constant("bound");
    public static final TruffleString CALL = Strings.constant("call");
    public static final TruffleString CAUSE = Strings.constant("cause");
    public static final TruffleString COMPARE = Strings.constant("compare");
    public static final TruffleString CONSTRUCT = Strings.constant("construct");
    public static final TruffleString DEFAULT = Strings.constant("default");
    public static final TruffleString DELETE = Strings.constant("delete");
    public static final TruffleString DONE = Strings.constant("done");
    public static final TruffleString EMPTY = Strings.constant("empty");
    public static final TruffleString EMPTY_X = Strings.constant("empty \u00d7 ");
    public static final TruffleString UC_ERROR = Strings.constant("Error");
    public static final TruffleString FILE = Strings.constant("file");
    public static final TruffleString FLAGS = Strings.constant("flags");
    public static final TruffleString FORMAT = Strings.constant("format");
    public static final TruffleString FUNCTION = Strings.constant("function");
    public static final TruffleString GLOBAL = Strings.constant("global");
    public static final TruffleString HAS = Strings.constant("has");
    public static final TruffleString INSTANCE = Strings.constant("instance");
    public static final TruffleString JOIN = Strings.constant("join");
    public static final String JOIN_JLS = Strings.toJavaString(JOIN);
    public static final TruffleString JSON = Strings.constant("json");
    public static final TruffleString KEY = Strings.constant("key");
    public static final TruffleString KEYS = Strings.constant("keys");
    public static final TruffleString LENGTH = Strings.constant("length");
    public static final TruffleString LITERAL = Strings.constant("literal");
    public static final TruffleString LOWER = Strings.constant("lower");
    public static final TruffleString MESSAGE = Strings.constant("message");
    public static final TruffleString MODULE = Strings.constant("module");
    public static final TruffleString NAME = Strings.constant("name");
    public static final TruffleString NATIVE = Strings.constant("native");
    public static final TruffleString NEG = Strings.constant("neg");
    public static final TruffleString NEXT = Strings.constant("next");
    public static final TruffleString NOW = Strings.constant("now");
    public static final TruffleString NULL = Strings.constant("null");
    public static final TruffleString NULL_UNDEFINED = Strings.constant("null|undefined");
    public static final TruffleString UC_NUMBER = Strings.constant("Number");
    public static final TruffleString OBJECT = Strings.constant("object");
    public static final TruffleString PARSE = Strings.constant("parse");
    public static final TruffleString POS = Strings.constant("pos");
    public static final TruffleString PRIMITIVE_VALUE = Strings.constant("PrimitiveValue");
    public static final TruffleString RAW = Strings.constant("raw");
    public static final TruffleString RETURN = Strings.constant("return");
    public static final TruffleString SOURCE = Strings.constant("source");
    public static final TruffleString STRING = Strings.constant("string");
    public static final TruffleString UC_STRING = Strings.constant("String");
    public static final TruffleString SUPER = Strings.constant("super");
    public static final TruffleString SWITCH = Strings.constant("switch");
    public static final TruffleString SYMBOL = Strings.constant("symbol");
    public static final TruffleString UC_SYMBOL = Strings.constant("Symbol");
    public static final TruffleString THEN = Strings.constant("then");
    public static final TruffleString THIS = Strings.constant("this");
    public static final TruffleString THROW = Strings.constant("throw");
    public static final TruffleString UNDEFINED = Strings.constant("undefined");
    public static final TruffleString UNKNOWN = Strings.constant("unknown");
    public static final TruffleString UPPER = Strings.constant("upper");
    public static final TruffleString URL = Strings.constant("url");
    public static final TruffleString VALUE = Strings.constant("value");
    public static final String VALUE_JLS = Strings.toJavaString(VALUE);
    public static final TruffleString VALUES = Strings.constant("values");
    public static final TruffleString WITH = Strings.constant("with");
    public static final TruffleString ANYFUNC = Strings.constant("anyfunc");
    public static final TruffleString AT = Strings.constant("at");
    public static final TruffleString COPY_WITHIN = Strings.constant("copyWithin");
    public static final TruffleString EVAL_FILE = Strings.constant("evalFile");
    public static final TruffleString FILL = Strings.constant("fill");
    public static final TruffleString FIND = Strings.constant("find");
    public static final TruffleString FIND_INDEX = Strings.constant("findIndex");
    public static final TruffleString FIND_LAST = Strings.constant("findLast");
    public static final TruffleString FIND_LAST_INDEX = Strings.constant("findLastIndex");
    public static final TruffleString FLAT = Strings.constant("flat");
    public static final TruffleString FLAT_MAP = Strings.constant("flatMap");
    public static final TruffleString INCLUDES = Strings.constant("includes");
    public static final TruffleString IS_VIEW = Strings.constant("isView");
    public static final TruffleString PARSE_INT = Strings.constant("parseInt");
    public static final TruffleString PARSE_FLOAT = Strings.constant("parseFloat");
    public static final TruffleString SLICE = Strings.constant("slice");
    public static final TruffleString STRING_MAX_LENGTH = Strings.constant("stringMaxLength");
    public static final TruffleString TO_JSON = Strings.constant("toJSON");
    public static final TruffleString TO_ISO_STRING = Strings.constant("toISOString");
    public static final TruffleString TO_LOCALE_STRING = Strings.constant("toLocaleString");
    public static final TruffleString TO_STRING = Strings.constant("toString");
    public static final String TO_STRING_JLS = Strings.toJavaString(TO_STRING);
    public static final TruffleString TO_UTC_STRING = Strings.constant("toUTCString");
    public static final TruffleString TO_GMT_STRING = Strings.constant("toGMTString");
    public static final TruffleString VALUE_OF = Strings.constant("valueOf");
    public static final String VALUE_OF_JLS = Strings.toJavaString(VALUE_OF);
    public static final TruffleString IMPORT_SCRIPT_ENGINE_GLOBAL_BINDINGS = Strings.constant("importScriptEngineGlobalBindings");
    public static final TruffleString HAS_INSTANCE = Strings.constant("hasInstance");
    public static final TruffleString IS_CONCAT_SPREADABLE = Strings.constant("isConcatSpreadable");
    public static final TruffleString ITERATOR = Strings.constant("iterator");
    public static final TruffleString ASYNC_ITERATOR = Strings.constant("asyncIterator");
    public static final TruffleString MATCH = Strings.constant("match");
    public static final TruffleString MATCH_ALL = Strings.constant("matchAll");
    public static final TruffleString REPLACE = Strings.constant("replace");
    public static final TruffleString SEARCH = Strings.constant("search");
    public static final TruffleString SPECIES = Strings.constant("species");
    public static final TruffleString SPLIT = Strings.constant("split");
    public static final TruffleString TO_STRING_TAG = Strings.constant("toStringTag");
    public static final TruffleString TO_PRIMITIVE = Strings.constant("toPrimitive");
    public static final TruffleString UNSCOPABLES = Strings.constant("unscopables");
    public static final TruffleString UC_ARRAY = Strings.constant("Array");
    public static final TruffleString UC_OBJECT = Strings.constant("Object");
    public static final TruffleString CAPS_ID = Strings.constant("ID");
    public static final TruffleString CAPS_JSON = Strings.constant("JSON");
    public static final TruffleString CAPS_PWD = Strings.constant("PWD");
    public static final TruffleString DOLLAR_ENV = Strings.constant("$ENV");
    public static final TruffleString HINT_STRING = Strings.constant("string");
    public static final TruffleString HINT_NUMBER = Strings.constant("number");
    public static final TruffleString HINT_DEFAULT = Strings.constant("default");
    public static final TruffleString HTML_QUOT = Strings.constant("&quot;");
    public static final TruffleString UC_0X = Strings.constant("0X");
    public static final TruffleString LC_0X = Strings.constant("0x");
    public static final TruffleString PARENS_THIS = Strings.constant("(this)");
    public static final TruffleString SYMBOL_PAREN_OPEN = Strings.constant("Symbol(");
    public static final TruffleString LC_BOOLEAN = Strings.constant("boolean");
    public static final TruffleString UC_BOOLEAN = Strings.constant("Boolean");
    public static final TruffleString BOOLEAN_PROTOTYPE = Strings.constant("Boolean.prototype");
    public static final TruffleString TRUE = Strings.constant("true");
    public static final TruffleString FALSE = Strings.constant("false");
    public static final TruffleString GET = Strings.constant("get");
    public static final TruffleString SET = Strings.constant("set");
    public static final TruffleString GET_SPC = Strings.constant("get ");
    public static final TruffleString SET_SPC = Strings.constant("set ");
    public static final TruffleString IS = Strings.constant("is");
    public static final TruffleString G = Strings.constant("g");
    public static final TruffleString N = Strings.constant("n");
    public static final TruffleString NFC = Strings.constant("NFC");
    public static final TruffleString NFD = Strings.constant("NFD");
    public static final TruffleString NFKC = Strings.constant("NFKC");
    public static final TruffleString NFKD = Strings.constant("NFKD");
    public static final TruffleString MS = Strings.constant("ms");
    public static final TruffleString Y = Strings.constant("y");
    public static final TruffleString SECOND = Strings.constant("second");
    public static final TruffleString SECONDS = Strings.constant("seconds");
    public static final TruffleString MINUTE = Strings.constant("minute");
    public static final TruffleString MINUTES = Strings.constant("minutes");
    public static final TruffleString HOUR = Strings.constant("hour");
    public static final TruffleString HOURS = Strings.constant("hours");
    public static final TruffleString DAYS = Strings.constant("days");
    public static final TruffleString DAY = Strings.constant("day");
    public static final TruffleString WEEKDAY = Strings.constant("weekday");
    public static final TruffleString WEEKS = Strings.constant("weeks");
    public static final TruffleString WEEK = Strings.constant("week");
    public static final TruffleString MONTHS = Strings.constant("months");
    public static final TruffleString MONTH = Strings.constant("month");
    public static final TruffleString QUARTERS = Strings.constant("quarters");
    public static final TruffleString QUARTER = Strings.constant("quarter");
    public static final TruffleString YEAR = Strings.constant("year");
    public static final TruffleString YEARS = Strings.constant("years");
    public static final TruffleString DATE = Strings.constant("date");
    public static final TruffleString TIME = Strings.constant("time");
    public static final TruffleString DATE_STYLE = Strings.constant("dateStyle");
    public static final TruffleString TIME_STYLE = Strings.constant("timeStyle");
    public static final TruffleString NUMERIC = Strings.constant("numeric");
    public static final TruffleString OK = Strings.constant("ok");
    public static final TruffleString NOT_EQUAL = Strings.constant("not-equal");
    public static final TruffleString TIMED_OUT = Strings.constant("timed-out");
    public static final TruffleString ARRAY_PAREN_OPEN = Strings.constant("Array(");
    public static final TruffleString BOUND_SPC = Strings.constant("bound ");
    public static final TruffleString BRACKET_OBJECT_SPC = Strings.constant("[object ");
    public static final TruffleString BRACKET_BOOLEAN_SPC = Strings.constant("[Boolean ");
    public static final TruffleString BRACKET_DATE_SPC = Strings.constant("[Date ");
    public static final TruffleString COMMA_ANONYMOUS_BRACKETS = Strings.constant(", <anonymous>");
    public static final TruffleString FUNCTION_NATIVE_CODE_BODY = Strings.constant("() { [native code] }");
    public static final TruffleString FUNCTION_BODY_DOTS = Strings.constant("() {...}");
    public static final TruffleString FUNCTION_BODY_OMITTED = Strings.constant("...<omitted>...\n}");
    public static final TruffleString FUNCTION_SPC = Strings.constant("function ");
    public static final TruffleString FUNCTION_NATIVE_CODE = Strings.constant("function () { [native code] }");
    public static final TruffleString PROXY_PAREN = Strings.constant("Proxy(");
    public static final TruffleString ASYNC_SPC = Strings.constant("async ");
    public static final TruffleString ASYNC_PROMISE_ALL_BEGIN = Strings.constant("async Promise.all (index ");
    public static final TruffleString SPACE_PAREN_OPEN = Strings.constant(" (");
    public static final TruffleString NEW_SPACE = Strings.constant("new ");
    public static final TruffleString BACKSLASH_U = Strings.constant("\\u");
    public static final TruffleString BACKSLASH_U00 = Strings.constant("\\u00");
    public static final TruffleString BACKSLASH_UD = Strings.constant("\\ud");
    public static final TruffleString BACKSLASH_B = Strings.constant("\\b");
    public static final TruffleString BACKSLASH_F = Strings.constant("\\f");
    public static final TruffleString BACKSLASH_N = Strings.constant("\\n");
    public static final TruffleString BACKSLASH_R = Strings.constant("\\r");
    public static final TruffleString BACKSLASH_T = Strings.constant("\\t");
    public static final TruffleString BACKSLASH_BACKSLASH = Strings.constant("\\\\");
    public static final TruffleString BACKSLASH_DOUBLE_QUOTE = Strings.constant("\\\"");
    public static final TruffleString ESCAPE_B = Strings.constant("\\b");
    public static final TruffleString ESCAPE_F = Strings.constant("\\f");
    public static final TruffleString ESCAPE_N = Strings.constant("\\n");
    public static final TruffleString ESCAPE_R = Strings.constant("\\r");
    public static final TruffleString ESCAPE_T = Strings.constant("\\t");
    public static final TruffleString ESCAPE_U_00 = Strings.constant("\\u00");
    public static final TruffleString ESCAPE_BACKSLASH = Strings.constant("\\\\");
    public static final TruffleString ESCAPE_QUOTE = Strings.constant("\\\"");
    public static final TruffleString PROPERTY_DESCRIPTION_MUST_BE_AN_OBJECT = Strings.constant("Property description must be an object: ");
    public static final TruffleString METHOD_ERROR_PROTOTYPE_TO_STRING_CALLED_ON_INCOMPATIBLE_RECEIVER = Strings.constant("Method Error.prototype.toString called on incompatible receiver ");
    public static final TruffleString I_64 = Strings.constant("i64");
    public static final TruffleString I_32 = Strings.constant("i32");
    public static final TruffleString F_32 = Strings.constant("f32");
    public static final TruffleString F_64 = Strings.constant("f64");
    public static final TruffleString PROXY = Strings.constant("proxy");
    public static final TruffleString REVOKE = Strings.constant("revoke");
    public static final TruffleString REVOCABLE = Strings.constant("revocable");
    public static final TruffleString EVAL_OBJ_FILE_NAME = Strings.constant("name");
    public static final TruffleString EVAL_OBJ_SOURCE = Strings.constant("script");
    public static final TruffleString FILENAME_VAR_NAME = Strings.constant("__filename");
    public static final TruffleString DIRNAME_VAR_NAME = Strings.constant("__dirname");
    public static final TruffleString MODULE_PROPERTY_NAME = Strings.constant("module");
    public static final TruffleString EXPORTS_PROPERTY_NAME = Strings.constant("exports");
    public static final TruffleString REQUIRE_PROPERTY_NAME = Strings.constant("require");
    public static final TruffleString RESOLVE_PROPERTY_NAME = Strings.constant("resolve");
    public static final TruffleString LOADED_PROPERTY_NAME = Strings.constant("loaded");
    public static final TruffleString FILENAME_PROPERTY_NAME = Strings.constant("filename");
    public static final TruffleString ID_PROPERTY_NAME = Strings.constant("id");
    public static final TruffleString ENV_PROPERTY_NAME = Strings.constant("env");
    public static final TruffleString JS_EXT = Strings.constant(".js");
    public static final TruffleString JSON_EXT = Strings.constant(".json");
    public static final TruffleString NODE_EXT = Strings.constant(".node");
    public static final TruffleString PACKAGE_JSON_MAIN_PROPERTY_NAME = Strings.constant("main");
    public static final TruffleString PACKAGE_JSON_TYPE_PROPERTY_NAME = Strings.constant("type");
    public static final TruffleString PACKAGE_JSON_MODULE_VALUE = Strings.constant("module");
    public static final TruffleString CREATE_REALM = Strings.constant("createRealm");
    public static final TruffleString DETACH_ARRAY_BUFFER = Strings.constant("detachArrayBuffer");
    public static final TruffleString EVAL_SCRIPT = Strings.constant("evalScript");
    public static final TruffleString GC = Strings.constant("gc");
    public static final TruffleString AGENT = Strings.constant("agent");
    public static final TruffleString START = Strings.constant("start");
    public static final TruffleString BROADCAST = Strings.constant("broadcast");
    public static final TruffleString GET_REPORT = Strings.constant("getReport");
    public static final TruffleString SLEEP = Strings.constant("sleep");
    public static final TruffleString MONOTONIC_NOW = Strings.constant("monotonicNow");
    public static final TruffleString RECEIVE_BROADCAST = Strings.constant("receiveBroadcast");
    public static final TruffleString REPORT = Strings.constant("report");
    public static final TruffleString LEAVING = Strings.constant("leaving");
    public static final TruffleString AGENT_START = Strings.constant("agentStart");
    public static final TruffleString AGENT_BROADCAST = Strings.constant("agentBroadcast");
    public static final TruffleString AGENT_GET_REPORT = Strings.constant("agentGetReport");
    public static final TruffleString AGENT_SLEEP = Strings.constant("agentSleep");
    public static final TruffleString AGENT_RECEIVE_BROADCAST = Strings.constant("agentReceiveBroadcast");
    public static final TruffleString AGENT_REPORT = Strings.constant("agentReport");
    public static final TruffleString AGENT_LEAVING = Strings.constant("agentLeaving");
    public static final TruffleString LOAD = Strings.constant("load");
    public static final TruffleString LOAD_WITH_NEW_GLOBAL = Strings.constant("loadWithNewGlobal");
    public static final TruffleString GLOBAL_THIS = Strings.constant("globalThis");
    public static final TruffleString EXIT = Strings.constant("exit");
    public static final TruffleString QUIT = Strings.constant("quit");
    public static final TruffleString PARSE_TO_JSON = Strings.constant("parseToJSON");
    public static final TruffleString PRINT = Strings.constant("print");
    public static final TruffleString PRINT_ERR = Strings.constant("printErr");
    public static final TruffleString GRAAL = Strings.constant("Graal");
    public static final TruffleString LANGUAGE = Strings.constant("language");
    public static final TruffleString VERSION_GRAAL_VM = Strings.constant("versionGraalVM");
    public static final TruffleString VERSION_ECMA_SCRIPT = Strings.constant("versionECMAScript");
    public static final TruffleString IS_GRAAL_RUNTIME = Strings.constant("isGraalRuntime");
    public static final TruffleString SET_UNHANDLED_PROMISE_REJECTION_HANDLER = Strings.constant("setUnhandledPromiseRejectionHandler");
    public static final TruffleString UC_PACKAGES = Strings.constant("Packages");
    public static final TruffleString JAVA = Strings.constant("java");
    public static final TruffleString JAVAFX = Strings.constant("javafx");
    public static final TruffleString JAVAX = Strings.constant("javax");
    public static final TruffleString COM = Strings.constant("com");
    public static final TruffleString ORG = Strings.constant("org");
    public static final TruffleString EDU = Strings.constant("edu");
    public static final TruffleString CONSOLE = Strings.constant("console");
    public static final TruffleString EXEC = Strings.constant("exec");
    public static final TruffleString READ_FULLY = Strings.constant("readFully");
    public static final TruffleString READ_LINE = Strings.constant("readLine");
    public static final TruffleString $_EXEC = Strings.constant("$EXEC");
    public static final TruffleString $_EXIT = Strings.constant("$EXIT");
    public static final TruffleString $_OUT = Strings.constant("$OUT");
    public static final TruffleString $_ERR = Strings.constant("$ERR");
    public static final TruffleString UC_REALM = Strings.constant("Realm");
    public static final TruffleString GLOBAL__LINE__ = Strings.constant("__LINE__");
    public static final TruffleString GLOBAL__FILE__ = Strings.constant("__FILE__");
    public static final TruffleString GLOBAL__DIR__ = Strings.constant("__DIR__");
    public static final TruffleString _TIMEZONE = Strings.constant("_timezone");
    public static final TruffleString _SCRIPTING = Strings.constant("_scripting");
    public static final TruffleString _COMPILE_ONLY = Strings.constant("_compile_only");
    public static final TruffleString $_OPTIONS = Strings.constant("$OPTIONS");
    public static final TruffleString $_ARG = Strings.constant("$ARG");
    public static final TruffleString ACRE = Strings.constant("acre");
    public static final TruffleString BIT = Strings.constant("bit");
    public static final TruffleString BYTE = Strings.constant("byte");
    public static final TruffleString CELSIUS = Strings.constant("celsius");
    public static final TruffleString CENTIMETER = Strings.constant("centimeter");
    public static final TruffleString DEGREE = Strings.constant("degree");
    public static final TruffleString FAHRENHEIT = Strings.constant("fahrenheit");
    public static final TruffleString FLUID_OUNCE = Strings.constant("fluid-ounce");
    public static final TruffleString FOOT = Strings.constant("foot");
    public static final TruffleString GALLON = Strings.constant("gallon");
    public static final TruffleString GIGABIT = Strings.constant("gigabit");
    public static final TruffleString GIGABYTE = Strings.constant("gigabyte");
    public static final TruffleString GRAM = Strings.constant("gram");
    public static final TruffleString HECTARE = Strings.constant("hectare");
    public static final TruffleString INCH = Strings.constant("inch");
    public static final TruffleString KILOBIT = Strings.constant("kilobit");
    public static final TruffleString KILOBYTE = Strings.constant("kilobyte");
    public static final TruffleString KILOGRAM = Strings.constant("kilogram");
    public static final TruffleString KILOMETER = Strings.constant("kilometer");
    public static final TruffleString LITER = Strings.constant("liter");
    public static final TruffleString MEGABIT = Strings.constant("megabit");
    public static final TruffleString MEGABYTE = Strings.constant("megabyte");
    public static final TruffleString METER = Strings.constant("meter");
    public static final TruffleString MILE = Strings.constant("mile");
    public static final TruffleString MILE_SCANDINAVIAN = Strings.constant("mile-scandinavian");
    public static final TruffleString MILLILITER = Strings.constant("milliliter");
    public static final TruffleString MILLIMETER = Strings.constant("millimeter");
    public static final TruffleString MILLISECOND = Strings.constant("millisecond");
    public static final TruffleString OUNCE = Strings.constant("ounce");
    public static final TruffleString PERCENT = Strings.constant("percent");
    public static final TruffleString PETABYTE = Strings.constant("petabyte");
    public static final TruffleString POUND = Strings.constant("pound");
    public static final TruffleString STONE = Strings.constant("stone");
    public static final TruffleString TERABIT = Strings.constant("terabit");
    public static final TruffleString TERABYTE = Strings.constant("terabyte");
    public static final TruffleString YARD = Strings.constant("yard");
    public static final TruffleString GREGORY = Strings.constant("gregory");
    public static final TruffleString MEMORY = Strings.constant("memory");
    public static final TruffleString TABLE = Strings.constant("table");
    public static final TruffleString STATUS = Strings.constant("status");
    public static final TruffleString REASON = Strings.constant("reason");
    public static final TruffleString OPERATOR = Strings.constant("operator");
    public static final TruffleString TYPE = Strings.constant("type");
    public static final TruffleString DOT_PROTOTYPE = Strings.constant(".prototype");
    public static final TruffleString EVAL = Strings.constant("eval");
    public static final TruffleString A = Strings.constant("a");
    public static final TruffleString BIG = Strings.constant("big");
    public static final TruffleString BLINK = Strings.constant("blink");
    public static final TruffleString B = Strings.constant("b");
    public static final TruffleString TT = Strings.constant("tt");
    public static final TruffleString FONT = Strings.constant("font");
    public static final TruffleString COLOR = Strings.constant("color");
    public static final TruffleString I = Strings.constant("i");
    public static final TruffleString HREF = Strings.constant("href");
    public static final TruffleString SIZE = Strings.constant("size");
    public static final TruffleString SMALL = Strings.constant("small");
    public static final TruffleString STRIKE = Strings.constant("strike");
    public static final TruffleString SUB = Strings.constant("sub");
    public static final TruffleString SUP = Strings.constant("sup");
    public static final TruffleString TO_STRING_VALUE_NULL = Strings.constant("[object Null]");
    public static final TruffleString TO_STRING_VALUE_UNDEFINED = Strings.constant("[object Undefined]");
    public static final TruffleString TO_STRING_VALUE_ARRAY = Strings.constant("[object Array]");
    public static final TruffleString TO_STRING_VALUE_FUNCTION = Strings.constant("[object Function]");
    public static final TruffleString TO_STRING_VALUE_DATE = Strings.constant("[object Date]");
    public static final TruffleString TO_STRING_VALUE_OBJECT = Strings.constant("[object Object]");
    public static final TruffleString FULFILLED = Strings.constant("fulfilled");
    public static final TruffleString REJECTED = Strings.constant("rejected");
    public static final TruffleString CAPTURE_STACK_TRACE = Strings.constant("captureStackTrace");
    public static final TruffleString JAVA_CLASS_BRACKET = Strings.constant("JavaClass[");
    public static final TruffleString JAVA_OBJECT_BRACKET = Strings.constant("JavaObject[");
    public static final TruffleString RESOLVED = Strings.constant("resolved");
    public static final TruffleString PENDING = Strings.constant("pending");
    public static final TruffleString PROMISE_STATUS = Strings.constant("PromiseStatus");
    public static final TruffleString PROMISE_VALUE = Strings.constant("PromiseValue");
    public static final TruffleString TEST = Strings.constant("test");
    public static final TruffleString UNKNOWN_FILENAME = Strings.constant("<unknown>");
    public static final TruffleString DYNAMIC_FUNCTION_NAME = Strings.constant("anonymous");
    public static final TruffleString ASYNC = Strings.constant("async");
    public static final TruffleString GROUP = Strings.constant("group");
    public static final TruffleString GROUP_TO_MAP = Strings.constant("groupToMap");
    public static final TruffleString UC_M = Strings.constant("M");
    public static final TruffleString UC_M0 = Strings.constant("M0");
    public static final TruffleString UC_Z = Strings.constant("Z");
    public static final TruffleString Z = Strings.constant("z");
    public static final TruffleString UC_ETC = Strings.constant("Etc");
    public static final TruffleString UNICODE_MINUS_SIGN = Strings.constant("\u2212");
    public static final TruffleString MUTABLE = Strings.constant("mutable");
    public static final TruffleString ELEMENT = Strings.constant("element");
    public static final TruffleString INITIAL = Strings.constant("initial");
    public static final TruffleString MAXIMUM = Strings.constant("maximum");
    public static final TruffleString STATIC = Strings.constant("static");
    public static final TruffleString PRIVATE = Strings.constant("private");
    public static final TruffleString INIT = Strings.constant("init");

    public static boolean isTString(Object string) {
        return string instanceof TruffleString;
    }

    public static TruffleString constant(String s) {
        TruffleString ret = Strings.fromJavaString(s);
        ret.hashCodeUncached(TruffleString.Encoding.UTF_16);
        return ret;
    }

    public static TruffleString fromJavaString(String str) {
        return Strings.fromJavaString(TruffleString.FromJavaStringNode.getUncached(), str);
    }

    public static TruffleString fromJavaString(TruffleString.FromJavaStringNode node, String str) {
        if (str == null) {
            return null;
        }
        return node.execute(str, TruffleString.Encoding.UTF_16);
    }

    public static TruffleString fromCharSequence(CharSequence str) {
        if (str == null) {
            return null;
        }
        return Strings.fromJavaString(str.toString());
    }

    public static TruffleString fromLong(long longValue) {
        return Strings.fromLong(TruffleString.FromLongNode.getUncached(), longValue);
    }

    public static TruffleString fromLong(TruffleString.FromLongNode node, long longValue) {
        return node.execute(longValue, TruffleString.Encoding.UTF_16, true);
    }

    public static TruffleString[] constantArray(String ... strings) {
        TruffleString[] ret = new TruffleString[strings.length];
        for (int i = 0; i < strings.length; ++i) {
            ret[i] = Strings.constant(strings[i]);
        }
        return ret;
    }

    public static int length(TruffleString s) {
        return s.byteLength(TruffleString.Encoding.UTF_16) >> 1;
    }

    public static boolean isEmpty(TruffleString s) {
        return Strings.length(s) == 0;
    }

    public static char charAt(TruffleString s, int i) {
        return Strings.charAt(TruffleString.ReadCharUTF16Node.getUncached(), s, i);
    }

    public static char charAt(TruffleString.ReadCharUTF16Node readRawNode, TruffleString s, int i) {
        return readRawNode.execute(s, i);
    }

    public static int codePointAt(TruffleString.CodePointAtByteIndexNode node, TruffleString s, int i) {
        return node.execute(s, i << 1, TruffleString.Encoding.UTF_16);
    }

    public static TruffleString concat(TruffleString s1, TruffleString s2) {
        return Strings.concat(TruffleString.ConcatNode.getUncached(), s1, s2);
    }

    public static TruffleString concat(TruffleString.ConcatNode node, TruffleString s1, TruffleString s2) {
        return node.execute(s1, s2, TruffleString.Encoding.UTF_16, true);
    }

    public static TruffleString concatAll(TruffleString s, TruffleString ... concat) {
        int len = Strings.length(s);
        for (TruffleString c : concat) {
            len += Strings.length(c);
        }
        TruffleStringBuilder sb = TruffleStringBuilder.create(TruffleString.Encoding.UTF_16, len);
        TruffleStringBuilder.AppendStringNode.getUncached().execute(sb, s);
        for (TruffleString c : concat) {
            TruffleStringBuilder.AppendStringNode.getUncached().execute(sb, c);
        }
        return TruffleStringBuilder.ToStringNode.getUncached().execute(sb);
    }

    public static TruffleString lazySubstring(TruffleString s, int fromIndex) {
        return Strings.lazySubstring(s, fromIndex, Strings.length(s) - fromIndex);
    }

    public static TruffleString lazySubstring(TruffleString s, int fromIndex, int length) {
        return Strings.lazySubstring(TruffleString.SubstringByteIndexNode.getUncached(), s, fromIndex, length);
    }

    public static TruffleString lazySubstring(TruffleString.SubstringByteIndexNode node, TruffleString s, int fromIndex, int length) {
        return Strings.substring(true, node, s, fromIndex, length);
    }

    public static TruffleString substring(JSContext context, TruffleString s, int fromIndex) {
        return Strings.substring(context, s, fromIndex, Strings.length(s) - fromIndex);
    }

    public static TruffleString substring(JSContext context, TruffleString.SubstringByteIndexNode node, TruffleString s, int fromIndex) {
        return Strings.substring(context, node, s, fromIndex, Strings.length(s) - fromIndex);
    }

    public static TruffleString substring(JSContext context, TruffleString s, int fromIndex, int length) {
        return Strings.substring(context, TruffleString.SubstringByteIndexNode.getUncached(), s, fromIndex, length);
    }

    public static TruffleString substring(JSContext context, TruffleString.SubstringByteIndexNode node, TruffleString s, int fromIndex, int length) {
        return Strings.substring(context.getContextOptions().isStringLazySubstrings(), node, s, fromIndex, length);
    }

    public static TruffleString substring(boolean lazy, TruffleString.SubstringByteIndexNode node, TruffleString s, int fromIndex, int length) {
        return length == 0 ? EMPTY_STRING : node.execute(s, fromIndex << 1, length << 1, TruffleString.Encoding.UTF_16, lazy);
    }

    public static boolean startsWith(TruffleString s1, TruffleString s2) {
        return Strings.startsWith(s1, s2, 0);
    }

    public static boolean startsWith(TruffleString s1, TruffleString s2, int startPos) {
        return Strings.startsWith(TruffleString.RegionEqualByteIndexNode.getUncached(), s1, s2, startPos);
    }

    public static boolean startsWith(TruffleString.RegionEqualByteIndexNode regionEqualsNode, TruffleString s1, TruffleString s2) {
        return Strings.startsWith(regionEqualsNode, s1, s2, 0);
    }

    public static boolean startsWith(TruffleString.RegionEqualByteIndexNode regionEqualsNode, TruffleString s1, TruffleString s2, int startPos) {
        return Strings.length(s1) - startPos >= Strings.length(s2) && Strings.regionEquals(regionEqualsNode, s1, startPos, s2, 0, Strings.length(s2));
    }

    public static boolean regionEquals(TruffleString s1, int offset1, TruffleString s2, int offset2, int length) {
        return Strings.regionEquals(TruffleString.RegionEqualByteIndexNode.getUncached(), s1, offset1, s2, offset2, length);
    }

    public static boolean regionEquals(TruffleString.RegionEqualByteIndexNode regionEqualsNode, TruffleString s1, int offset1, TruffleString s2, int offset2, int length) {
        return regionEqualsNode.execute((AbstractTruffleString)s1, offset1 << 1, s2, offset2 << 1, length << 1, TruffleString.Encoding.UTF_16);
    }

    public static boolean endsWith(TruffleString s1, TruffleString s2) {
        return Strings.endsWith(TruffleString.RegionEqualByteIndexNode.getUncached(), s1, s2);
    }

    public static boolean endsWith(TruffleString.RegionEqualByteIndexNode regionEqualsNode, TruffleString s1, TruffleString s2) {
        return Strings.length(s1) >= Strings.length(s2) && Strings.regionEquals(regionEqualsNode, s1, Strings.length(s1) - Strings.length(s2), s2, 0, Strings.length(s2));
    }

    public static boolean contains(TruffleString s, char c) {
        return Strings.indexOf(s, c) >= 0;
    }

    public static int indexOf(TruffleString string, char c) {
        return string.charIndexOfAnyCharUTF16Uncached(0, Strings.length(string), new char[]{c});
    }

    public static int indexOfAny(TruffleString s, char ... chars) {
        return s.charIndexOfAnyCharUTF16Uncached(0, Strings.length(s), chars);
    }

    public static int indexOf(TruffleString.ByteIndexOfCodePointNode node, TruffleString s, int codepoint) {
        return Strings.indexOf(node, s, codepoint, 0);
    }

    public static int indexOf(TruffleString s, int codepoint, int fromIndex) {
        return Strings.indexOf(TruffleString.ByteIndexOfCodePointNode.getUncached(), s, codepoint, fromIndex);
    }

    public static int indexOf(TruffleString.ByteIndexOfCodePointNode node, TruffleString s, int codepoint, int fromIndex) {
        if (fromIndex >= Strings.length(s)) {
            return -1;
        }
        return node.execute(s, codepoint, fromIndex << 1, Strings.length(s) << 1, TruffleString.Encoding.UTF_16) >> 1;
    }

    public static int indexOf(TruffleString s1, TruffleString s2) {
        return Strings.indexOf(s1, s2, 0);
    }

    public static int indexOf(TruffleString.ByteIndexOfStringNode node, TruffleString s1, TruffleString s2) {
        return Strings.indexOf(node, s1, s2, 0);
    }

    public static int indexOf(TruffleString s1, TruffleString s2, int fromIndex) {
        return Strings.indexOf(TruffleString.ByteIndexOfStringNode.getUncached(), s1, s2, fromIndex);
    }

    public static int indexOf(TruffleString s1, TruffleString s2, int fromIndex, int toIndex) {
        return Strings.indexOf(TruffleString.ByteIndexOfStringNode.getUncached(), s1, s2, fromIndex, toIndex);
    }

    public static int indexOf(TruffleString.ByteIndexOfStringNode node, TruffleString s1, TruffleString s2, int fromIndex) {
        return Strings.indexOf(node, s1, s2, fromIndex, Strings.length(s1));
    }

    public static int indexOf(TruffleString.ByteIndexOfStringNode node, TruffleString s1, TruffleString s2, int fromIndex, int toIndex) {
        int fromIndexPos = Math.max(fromIndex, 0);
        if (Strings.length(s2) == 0) {
            return fromIndexPos;
        }
        return Strings.length(s1) - fromIndexPos >= Strings.length(s2) ? node.execute((AbstractTruffleString)s1, s2, fromIndexPos << 1, toIndex << 1, TruffleString.Encoding.UTF_16) >> 1 : -1;
    }

    public static int lastIndexOf(TruffleString.LastByteIndexOfStringNode lastIndexOfNode, TruffleString s1, TruffleString s2, int fromIndex) {
        return lastIndexOfNode.execute((AbstractTruffleString)s1, s2, Math.min(fromIndex + Strings.length(s2), Strings.length(s1)) << 1, 0, TruffleString.Encoding.UTF_16) >> 1;
    }

    public static int lastIndexOf(TruffleString s, int codePoint) {
        return Strings.lastIndexOf(TruffleString.LastByteIndexOfCodePointNode.getUncached(), s, codePoint);
    }

    public static int lastIndexOf(TruffleString.LastByteIndexOfCodePointNode node, TruffleString s, int codePoint) {
        return node.execute(s, codePoint, Strings.length(s) << 1, 0, TruffleString.Encoding.UTF_16) >> 1;
    }

    public static boolean equals(TruffleString s1, TruffleString s2) {
        return Strings.equals(TruffleString.EqualNode.getUncached(), s1, s2);
    }

    public static boolean equals(TruffleString.EqualNode node, TruffleString s1, TruffleString s2) {
        if (s1 == null) {
            return s2 == null;
        }
        if (!Strings.isTString(s2)) {
            return false;
        }
        return node.execute(s1, s2, TruffleString.Encoding.UTF_16);
    }

    public static TruffleString replace(TruffleString s, TruffleString search, TruffleString replace) {
        int pos = Strings.indexOf(s, search);
        if (pos < 0) {
            return s;
        }
        if (Strings.length(s) == Strings.length(search)) {
            return replace;
        }
        TruffleStringBuilder sb = Strings.builderCreate(Strings.length(s));
        int lastEndPos = 0;
        do {
            Strings.builderAppend(sb, s, lastEndPos, pos);
            Strings.builderAppend(sb, replace);
        } while ((pos = Strings.indexOf(s, search, lastEndPos = pos + Strings.length(search))) >= 0);
        Strings.builderAppend(sb, s, lastEndPos, Strings.length(s));
        return Strings.builderToString(sb);
    }

    public static int compareTo(TruffleString a, TruffleString b) {
        return TruffleString.CompareCharsUTF16Node.getUncached().execute(a, b);
    }

    public static int compareTo(TruffleString.CompareCharsUTF16Node node, TruffleString a, TruffleString b) {
        return node.execute(a, b);
    }

    public static String toJavaString(TruffleString s) {
        return Strings.toJavaString(TruffleString.ToJavaStringNode.getUncached(), s);
    }

    public static String toJavaString(TruffleString.ToJavaStringNode node, TruffleString s) {
        return s == null ? null : node.execute(s);
    }

    public static TruffleString toLowerCase(TruffleString s, Locale locale) {
        return Strings.fromJavaString(Strings.javaStringToLowerCase(Strings.toJavaString(s), locale));
    }

    public static TruffleString toUpperCase(TruffleString s, Locale locale) {
        return Strings.fromJavaString(Strings.javaStringToUpperCase(Strings.toJavaString(s), locale));
    }

    @CompilerDirectives.TruffleBoundary
    private static String javaStringToLowerCase(String s, Locale locale) {
        return s.toLowerCase(locale);
    }

    @CompilerDirectives.TruffleBoundary
    private static String javaStringToUpperCase(String s, Locale locale) {
        return s.toUpperCase(locale);
    }

    public static TruffleString lazyTrim(TruffleString s) {
        int start2;
        int end2 = Strings.length(s);
        for (start2 = 0; start2 < end2 && Strings.charAt(s, start2) <= ' '; ++start2) {
        }
        while (start2 < end2 && Strings.charAt(s, end2 - 1) <= ' ') {
            --end2;
        }
        return start2 > 0 || end2 < Strings.length(s) ? Strings.lazySubstring(s, start2, end2 - start2) : s;
    }

    public static long parseLong(TruffleString s) throws TruffleString.NumberFormatException {
        return Strings.parseLong(s, 10);
    }

    public static long parseLong(TruffleString s, int radix) throws TruffleString.NumberFormatException {
        return Strings.parseLong(TruffleString.ParseLongNode.getUncached(), s, radix);
    }

    public static long parseLong(TruffleString.ParseLongNode node, TruffleString s, int radix) throws TruffleString.NumberFormatException {
        return node.execute(s, radix);
    }

    public static double parseDouble(TruffleString s) throws TruffleString.NumberFormatException {
        return Strings.parseDouble(TruffleString.ParseDoubleNode.getUncached(), s);
    }

    public static double parseDouble(TruffleString.ParseDoubleNode node, TruffleString s) throws TruffleString.NumberFormatException {
        return node.execute(s);
    }

    public static TruffleString fromCodePoint(int c) {
        return Strings.fromCodePoint(TruffleString.FromCodePointNode.getUncached(), c);
    }

    public static TruffleString fromCodePoint(TruffleString.FromCodePointNode node, int c) {
        assert (c >= 0);
        return node.execute(c, TruffleString.Encoding.UTF_16, true);
    }

    public static TruffleString fromBoolean(boolean b) {
        return b ? TRUE : FALSE;
    }

    public static TruffleString fromInt(int intValue) {
        return Strings.fromLong(intValue);
    }

    public static TruffleString fromDouble(double d) {
        return TruffleString.FromJavaStringNode.getUncached().execute(Strings.doubleToJavaString(d), TruffleString.Encoding.UTF_16);
    }

    @CompilerDirectives.TruffleBoundary
    private static String doubleToJavaString(double d) {
        return String.valueOf(d);
    }

    public static TruffleString fromNumber(Number number) {
        if (number instanceof Integer) {
            return Strings.fromInt(number.intValue());
        }
        if (number instanceof Long) {
            return Strings.fromLong(number.longValue());
        }
        if (number instanceof Double) {
            return Strings.fromDouble(number.doubleValue());
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    public static TruffleString fromBigInt(BigInt bi) {
        return Strings.fromJavaString(bi.toString());
    }

    public static TruffleString fromBigInt(BigInt bi, int radix) {
        return Strings.fromJavaString(bi.toString(radix));
    }

    public static TruffleString fromObject(Object o) {
        return Strings.fromJavaString(Strings.objectToJavaString(o));
    }

    @CompilerDirectives.TruffleBoundary
    private static String objectToJavaString(Object o) {
        return String.valueOf(o);
    }

    public static TruffleString fromCharArray(char[] chars) {
        return Strings.fromCharArray(chars, 0, chars.length);
    }

    public static TruffleString fromCharArray(TruffleString.FromCharArrayUTF16Node node, char[] chars) {
        return Strings.fromCharArray(node, chars, 0, chars.length);
    }

    public static TruffleString fromCharArray(char[] chars, int fromIndex, int length) {
        return Strings.fromCharArray(TruffleString.FromCharArrayUTF16Node.getUncached(), chars, fromIndex, length);
    }

    public static TruffleString fromCharArray(TruffleString.FromCharArrayUTF16Node node, char[] chars, int fromIndex, int length) {
        return node.execute(chars, fromIndex, length);
    }

    public static TruffleString intToHexString(char i) {
        return Strings.fromJavaString(Integer.toHexString(i));
    }

    public static TruffleString flatten(TruffleString.MaterializeNode materializeNode, TruffleString value2) {
        materializeNode.execute(value2, TruffleString.Encoding.UTF_16);
        return value2;
    }

    public static String interopAsString(Object key) throws UnsupportedMessageException {
        return Strings.interopAsString(InteropLibrary.getUncached(), key);
    }

    public static TruffleString interopAsTruffleString(Object key) throws UnsupportedMessageException {
        return Strings.interopAsTruffleString(InteropLibrary.getUncached(), key);
    }

    public static String interopAsString(InteropLibrary stringInterop, Object key) throws UnsupportedMessageException {
        return key instanceof String ? (String)key : stringInterop.asString(key);
    }

    public static TruffleString interopAsTruffleString(InteropLibrary stringInterop, Object key) throws UnsupportedMessageException {
        return key instanceof TruffleString ? (TruffleString)key : stringInterop.asTruffleString(key);
    }

    public static BigInt parseBigInt(TruffleString s) {
        return BigInt.valueOf(Strings.toJavaString(s));
    }

    public static BigInteger parseBigInteger(TruffleString s, int radix) {
        return new BigInteger(Strings.toJavaString(s), radix);
    }

    public static TruffleStringBuilder builderCreate() {
        return TruffleStringBuilder.create(TruffleString.Encoding.UTF_16);
    }

    public static TruffleStringBuilder builderCreate(int capacity) {
        return TruffleStringBuilder.create(TruffleString.Encoding.UTF_16, capacity << 1);
    }

    public static void builderAppend(TruffleStringBuilder sb, char chr) {
        TruffleStringBuilder.AppendCharUTF16Node.getUncached().execute(sb, chr);
    }

    public static void builderAppend(TruffleStringBuilder.AppendCharUTF16Node node, TruffleStringBuilder sb, char chr) {
        node.execute(sb, chr);
    }

    public static void builderAppend(TruffleStringBuilder sb, int i) {
        TruffleStringBuilder.AppendIntNumberNode.getUncached().execute(sb, i);
    }

    public static void builderAppend(TruffleStringBuilder.AppendIntNumberNode node, TruffleStringBuilder sb, int i) {
        node.execute(sb, i);
    }

    public static void builderAppend(TruffleStringBuilder sb, long i) {
        TruffleStringBuilder.AppendLongNumberNode.getUncached().execute(sb, i);
    }

    public static void builderAppend(TruffleStringBuilder.AppendLongNumberNode node, TruffleStringBuilder sb, long i) {
        node.execute(sb, i);
    }

    public static void builderAppend(TruffleStringBuilder sb, String str) {
        TruffleStringBuilder.AppendJavaStringUTF16Node.getUncached().execute(sb, str, 0, str.length());
    }

    public static void builderAppend(TruffleStringBuilder sb, TruffleString str) {
        Strings.builderAppendLen(sb, str, 0, Strings.length(str));
    }

    public static void builderAppend(TruffleStringBuilder.AppendStringNode node, TruffleStringBuilder sb, TruffleString str) {
        node.execute(sb, str);
    }

    public static void builderAppend(TruffleStringBuilder sb, TruffleString str, int start2, int end2) {
        Strings.builderAppendLen(sb, str, start2, end2 - start2);
    }

    public static void builderAppend(TruffleStringBuilder.AppendSubstringByteIndexNode node, TruffleStringBuilder sb, TruffleString str, int start2, int end2) {
        Strings.builderAppendLen(node, sb, str, start2, end2 - start2);
    }

    public static void builderAppendLen(TruffleStringBuilder sb, TruffleString str, int start2, int len) {
        Strings.builderAppendLen(TruffleStringBuilder.AppendSubstringByteIndexNode.getUncached(), sb, str, start2, len);
    }

    public static void builderAppendLen(TruffleStringBuilder.AppendSubstringByteIndexNode node, TruffleStringBuilder sb, TruffleString str, int start2, int len) {
        node.execute(sb, str, start2 << 1, len << 1);
    }

    public static TruffleString builderToString(TruffleStringBuilder sb) {
        return Strings.builderToString(TruffleStringBuilder.ToStringNode.getUncached(), sb);
    }

    public static TruffleString builderToString(TruffleStringBuilder.ToStringNode node, TruffleStringBuilder sb) {
        return node.execute(sb);
    }

    public static String builderToJavaString(TruffleStringBuilder sb) {
        return Strings.toJavaString(Strings.builderToString(sb));
    }

    public static int builderLength(TruffleStringBuilder sb) {
        return sb.byteLength() >> 1;
    }

    public static TruffleString[] convertJavaStringArray(String[] array) {
        TruffleString[] ret = new TruffleString[array.length];
        for (int i = 0; i < array.length; ++i) {
            ret[i] = Strings.fromJavaString(array[i]);
        }
        return ret;
    }

    public static TruffleString addBrackets(TruffleString str) {
        return Strings.concatAll(BRACKET_OPEN, str, BRACKET_CLOSE);
    }

    public static TruffleString format(String formatString, Object ... args) {
        return Strings.fromJavaString(String.format(formatString, args));
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString[] split(JSContext context, TruffleString str, TruffleString delimiter) {
        if (Strings.isEmpty(str)) {
            return new TruffleString[0];
        }
        int pos = Strings.indexOf(str, delimiter);
        if (pos < 0) {
            return new TruffleString[]{str};
        }
        ArrayList<TruffleString> ret = new ArrayList<TruffleString>();
        int lastEnd = 0;
        do {
            ret.add(Strings.substring(context, str, lastEnd, pos - lastEnd));
        } while ((pos = Strings.indexOf(str, delimiter, lastEnd = pos + Strings.length(delimiter))) >= 0);
        ret.add(Strings.substring(context, str, lastEnd, Strings.length(str) - lastEnd));
        return ret.toArray(new TruffleString[0]);
    }
}

