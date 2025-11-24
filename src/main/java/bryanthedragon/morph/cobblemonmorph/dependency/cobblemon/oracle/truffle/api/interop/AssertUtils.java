
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;

final class AssertUtils {
    private AssertUtils() {
    }

    private static String formatArgs(Object[] args) {
        if (args == null) {
            return "null";
        }
        StringBuilder b = new StringBuilder("[");
        String sep = "";
        for (Object arg : args) {
            b.append(sep);
            b.append(AssertUtils.formatValue(arg));
            sep = ", ";
        }
        b.append("]");
        return b.toString();
    }

    private static String formatValue(Object arg) {
        if (arg == null) {
            return "null";
        }
        if (arg instanceof TruffleObject) {
            return arg.toString() + "(" + arg.getClass().getName() + ")";
        }
        if (arg instanceof String) {
            return "\"" + arg.toString() + "\"";
        }
        if (arg instanceof Character) {
            return "'" + arg.toString() + "'";
        }
        if (arg instanceof Boolean) {
            return arg.toString();
        }
        return arg.toString() + "(" + arg.getClass().getSimpleName() + ")";
    }

    private static String violationPre(Object receiver) {
        return String.format("Pre-condition contract violation for receiver %s.", AssertUtils.formatValue(receiver));
    }

    static String violationPost(Object receiver, Object returnValue) {
        return String.format("Post-condition contract violation for receiver %s and return value %s.", AssertUtils.formatValue(receiver), AssertUtils.formatValue(returnValue));
    }

    static String violationPost(Object receiver, Throwable t) {
        return String.format("Post-condition contract violation for receiver %s. Thrown unexpected error %s.", AssertUtils.formatValue(receiver), t.getClass().getName());
    }

    static String violationInvariant(Object receiver) {
        return String.format("Invariant contract violation for receiver %s.", AssertUtils.formatValue(receiver));
    }

    static String violationInvariant(Object receiver, Object[] args) {
        return String.format("Invariant contract violation for receiver %s and arguments %s.", AssertUtils.formatValue(receiver), AssertUtils.formatArgs(args));
    }

    static String violationInvariant(Object receiver, String arg) {
        return String.format("Invariant contract violation for receiver %s and identifier %s.", AssertUtils.formatValue(receiver), arg);
    }

    static String violationInvariant(Object receiver, long arg) {
        return String.format("Invariant contract violation for receiver %s and index %s.", AssertUtils.formatValue(receiver), arg);
    }

    static String violationInvariant(Object receiver, Object arg) {
        return String.format("Invariant contract violation for receiver %s and key %s.", AssertUtils.formatValue(receiver), AssertUtils.formatValue(arg));
    }

    private static String violationReturn(Object receiver, Object returnValue) {
        return String.format("Post-condition contract violation for receiver %s and return value %s.", AssertUtils.formatValue(receiver), AssertUtils.formatValue(returnValue));
    }

    private static String violationArgument(Object receiver, Object arg) {
        return String.format("Pre-condition contract violation for receiver %s and argument %s. Valid arguments must be of type Boolean, Byte, Short, Integer, Long, Float, Double, Character, String or implement TruffleObject.", AssertUtils.formatValue(receiver), AssertUtils.formatValue(arg));
    }

    static boolean validInteropReturn(Object receiver, Object arg) {
        assert (InteropLibrary.isValidValue(arg)) : AssertUtils.violationReturn(receiver, arg);
        return true;
    }

    static boolean validProtocolReturn(Object receiver, Object arg) {
        assert (InteropLibrary.isValidProtocolValue(arg)) : AssertUtils.violationReturn(receiver, arg);
        return true;
    }

    static boolean validInteropArgument(Object receiver, Object arg) {
        if (arg == null) {
            throw new NullPointerException(AssertUtils.violationArgument(receiver, arg));
        }
        if (!InteropLibrary.isValidValue(arg)) {
            throw new ClassCastException(AssertUtils.violationArgument(receiver, arg));
        }
        return true;
    }

    static boolean validProtocolArgument(Object receiver, Object arg) {
        if (arg == null) {
            throw new NullPointerException(AssertUtils.violationArgument(receiver, arg));
        }
        if (!InteropLibrary.isValidProtocolValue(arg)) {
            throw new ClassCastException(AssertUtils.violationArgument(receiver, arg));
        }
        return true;
    }

    static boolean assertString(Object receiver, Object string) {
        block4: {
            InteropLibrary uncached = InteropLibrary.getUncached(string);
            assert (uncached.isString(string)) : AssertUtils.violationPost(receiver, string);
            try {
                assert (uncached.asString(string) != null) : AssertUtils.violationPost(receiver, string);
            }
            catch (UnsupportedMessageException e) {
                if ($assertionsDisabled) break block4;
                throw new AssertionError();
            }
        }
        return true;
    }

    static boolean validNonInteropArgument(Object receiver, Object arg) {
        if (arg == null) {
            throw new NullPointerException(AssertUtils.violationNonInteropArgument(receiver, arg));
        }
        return true;
    }

    private static String violationNonInteropArgument(Object receiver, Object arg) {
        return String.format("Pre-condition contract violation for receiver %s and argument %s. Argument must not be null.", AssertUtils.formatValue(receiver), AssertUtils.formatValue(arg));
    }

    static boolean validArguments(Object receiver, Object[] args) {
        assert (args != null) : AssertUtils.violationPre(receiver);
        for (Object arg : args) {
            assert (AssertUtils.validInteropArgument(receiver, arg));
        }
        return true;
    }

    static boolean validScope(Object o) {
        if (!(o instanceof TruffleObject)) {
            return false;
        }
        InteropLibrary uncached = InteropLibrary.getUncached(o);
        assert (uncached.isScope(o)) : String.format("Invariant contract violation for receiver %s: is not a scope.", AssertUtils.formatValue(o));
        assert (uncached.hasMembers(o)) : String.format("Invariant contract violation for receiver %s: does not have members.", AssertUtils.formatValue(o));
        return true;
    }

    static String violationScopeMemberLengths(Object allMembers, Object parentMembers) {
        return String.format("Scope members of %s do not contain all scope parent members of %s", allMembers, parentMembers);
    }

    static boolean validScopeMemberLengths(long allSize, long parentSize, Object allMembers, Object parentMembers) {
        assert (allSize >= parentSize) : String.format("Scope members of %s (count = %d) do not contain all scope parent members of %s (count = %d)", allMembers, allSize, parentMembers, parentSize);
        return allSize >= parentSize;
    }

    static boolean validScopeMemberNames(String allElementName, String parentElementName, Object allMembers, Object parentMembers, long allIndex, long parentIndex) {
        assert (allElementName.equals(parentElementName)) : String.format("Member %s of scope %s at [%d] does not equal to member %s of parent scope %s at [%d]. Scope must contain all members from parent scopes.", allElementName, allMembers, allIndex, parentElementName, parentMembers, parentIndex);
        return allElementName.equals(parentElementName);
    }

    static boolean preCondition(Object receiver) {
        if (receiver == null) {
            throw new NullPointerException(AssertUtils.violationPre(receiver));
        }
        return true;
    }
}

