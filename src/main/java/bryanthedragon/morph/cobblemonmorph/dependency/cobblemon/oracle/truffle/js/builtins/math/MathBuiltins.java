
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.math.AbsNodeGen;
import com.oracle.truffle.js.builtins.math.AcosNodeGen;
import com.oracle.truffle.js.builtins.math.AcoshNodeGen;
import com.oracle.truffle.js.builtins.math.AsinNodeGen;
import com.oracle.truffle.js.builtins.math.AsinhNodeGen;
import com.oracle.truffle.js.builtins.math.Atan2NodeGen;
import com.oracle.truffle.js.builtins.math.AtanNodeGen;
import com.oracle.truffle.js.builtins.math.AtanhNodeGen;
import com.oracle.truffle.js.builtins.math.CbrtNodeGen;
import com.oracle.truffle.js.builtins.math.CeilNodeGen;
import com.oracle.truffle.js.builtins.math.Clz32NodeGen;
import com.oracle.truffle.js.builtins.math.CosNodeGen;
import com.oracle.truffle.js.builtins.math.CoshNodeGen;
import com.oracle.truffle.js.builtins.math.ExpNodeGen;
import com.oracle.truffle.js.builtins.math.Expm1NodeGen;
import com.oracle.truffle.js.builtins.math.FloorNodeGen;
import com.oracle.truffle.js.builtins.math.FroundNodeGen;
import com.oracle.truffle.js.builtins.math.HypotNodeGen;
import com.oracle.truffle.js.builtins.math.ImulNode;
import com.oracle.truffle.js.builtins.math.Log10NodeGen;
import com.oracle.truffle.js.builtins.math.Log1pNodeGen;
import com.oracle.truffle.js.builtins.math.Log2NodeGen;
import com.oracle.truffle.js.builtins.math.LogNodeGen;
import com.oracle.truffle.js.builtins.math.MaxNodeGen;
import com.oracle.truffle.js.builtins.math.MinNodeGen;
import com.oracle.truffle.js.builtins.math.PowNodeGen;
import com.oracle.truffle.js.builtins.math.RandomNodeGen;
import com.oracle.truffle.js.builtins.math.RoundNode;
import com.oracle.truffle.js.builtins.math.SignNodeGen;
import com.oracle.truffle.js.builtins.math.SinNodeGen;
import com.oracle.truffle.js.builtins.math.SinhNodeGen;
import com.oracle.truffle.js.builtins.math.SqrtNodeGen;
import com.oracle.truffle.js.builtins.math.TanNodeGen;
import com.oracle.truffle.js.builtins.math.TanhNodeGen;
import com.oracle.truffle.js.builtins.math.TruncNodeGen;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSMath;
import java.util.EnumSet;

public class MathBuiltins
extends JSBuiltinsContainer.SwitchEnum<Math> {
    public static final JSBuiltinsContainer BUILTINS = new MathBuiltins();

    protected MathBuiltins() {
        super(JSMath.CLASS_NAME, Math.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Math builtinEnum) {
        switch (builtinEnum) {
            case abs: {
                return AbsNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case acos: {
                return AcosNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case asin: {
                return AsinNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case atan2: {
                return Atan2NodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case atan: {
                return AtanNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case ceil: {
                return CeilNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case cos: {
                return CosNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case exp: {
                return ExpNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case floor: {
                return FloorNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case log: {
                return LogNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case max: {
                return MaxNodeGen.create(context, builtin, MathBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case min: {
                return MinNodeGen.create(context, builtin, MathBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case pow: {
                return PowNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case random: {
                return RandomNodeGen.create(context, builtin, MathBuiltins.args().createArgumentNodes(context));
            }
            case round: {
                return RoundNode.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case sin: {
                return SinNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case sqrt: {
                return SqrtNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case tan: {
                return TanNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case imul: {
                return ImulNode.create(context, builtin, MathBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case sign: {
                return SignNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case trunc: {
                return TruncNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case cbrt: {
                return CbrtNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case expm1: {
                return Expm1NodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case hypot: {
                return HypotNodeGen.create(context, builtin, MathBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case log2: {
                return Log2NodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case log10: {
                return Log10NodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case log1p: {
                return Log1pNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case clz32: {
                return Clz32NodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case cosh: {
                return CoshNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case sinh: {
                return SinhNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case tanh: {
                return TanhNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case acosh: {
                return AcoshNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case asinh: {
                return AsinhNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case atanh: {
                return AtanhNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fround: {
                return FroundNodeGen.create(context, builtin, MathBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static enum Math implements BuiltinEnum<Math>
    {
        abs(1),
        acos(1),
        asin(1),
        atan2(2),
        atan(1),
        ceil(1),
        cos(1),
        exp(1),
        floor(1),
        log(1),
        max(2),
        min(2),
        pow(2),
        random(0),
        round(1),
        sin(1),
        sqrt(1),
        tan(1),
        imul(2),
        sign(1),
        trunc(1),
        cbrt(1),
        expm1(1),
        hypot(2),
        log2(1),
        log10(1),
        log1p(1),
        clz32(1),
        cosh(1),
        sinh(1),
        tanh(1),
        acosh(1),
        asinh(1),
        atanh(1),
        fround(1);

        private final int length;

        private Math(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (EnumSet.range(imul, fround).contains(this)) {
                return 6;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

