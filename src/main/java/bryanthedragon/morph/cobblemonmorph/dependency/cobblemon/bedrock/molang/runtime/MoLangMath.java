
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;

import java.util.HashMap;
import java.util.function.Function;

public final class MoLangMath {
    public static final QueryStruct LIBRARY = new QueryStruct(new HashMap<String, Function<MoParams, Object>>(){
        {
            this.put("abs", params -> Math.abs(params.getDouble(0)));
            this.put("acos", params -> Math.acos(params.getDouble(0)) * 180.0 / Math.PI);
            this.put("sin", params -> Math.sin(params.getDouble(0) * Math.PI / 180.0));
            this.put("asin", params -> Math.asin(params.getDouble(0)) * 180.0 / Math.PI);
            this.put("atan", params -> Math.atan(params.getDouble(0)) * 180.0 / Math.PI);
            this.put("atan2", params -> Math.atan2(params.getDouble(0), params.getDouble(1)) * 180.0 / Math.PI);
            this.put("ceil", params -> Math.ceil(params.getDouble(0)));
            this.put("clamp", params -> Math.min(params.getDouble(2), Math.max(params.getDouble(0), params.getDouble(1))));
            this.put("cos", params -> Math.cos(params.getDouble(0) * Math.PI / 180.0));
            this.put("die_roll", params -> MoLangMath.dieRoll(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            this.put("die_roll_integer", params -> MoLangMath.dieRollInt(params.getInt(0), params.getInt(1), params.getInt(2)));
            this.put("exp", params -> Math.exp(params.getDouble(0)));
            this.put("mod", params -> params.getDouble(0) % params.getDouble(1));
            this.put("floor", params -> Math.floor(params.getDouble(0)));
            this.put("hermite_blend", params -> MoLangMath.hermiteBlend(params.getInt(0)));
            this.put("lerp", params -> MoLangMath.lerp(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            this.put("lerp_rotate", params -> MoLangMath.lerpRotate(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            this.put("ln", params -> Math.log(params.getDouble(0)));
            this.put("max", params -> Math.max(params.getDouble(0), params.getDouble(1)));
            this.put("min", params -> Math.min(params.getDouble(0), params.getDouble(1)));
            this.put("pi", params -> Math.PI);
            this.put("pow", params -> Math.pow(params.getDouble(0), params.getDouble(1)));
            this.put("random", params -> MoLangMath.random(params.getDouble(0), params.getDouble(1)));
            this.put("random_integer", params -> MoLangMath.randomInt(params.getInt(0), params.getInt(1)));
            this.put("round", params -> Math.round(params.getDouble(0)));
            this.put("sqrt", params -> Math.sqrt(params.getDouble(0)));
            this.put("trunc", params -> Math.floor(params.getDouble(0)));
            this.put("d2r", params -> Math.toRadians(params.getDouble(0)));
            this.put("r2d", params -> Math.toDegrees(params.getDouble(0)));
        }
    });

    public static double random(double low, double high) {
        return low + Math.random() * (high - low);
    }

    public static int randomInt(int low, int high) {
        return (int)Math.round((double)low + Math.random() * (double)(high - low));
    }

    public static double dieRoll(double num, double low, double high) {
        int i = 0;
        int total = 0;
        while (true) {
            int n = i++;
            if (!((double)n < num)) break;
            total = (int)((double)total + MoLangMath.random(low, high));
        }
        return total;
    }

    public static int dieRollInt(int num, int low, int high) {
        int i = 0;
        int total = 0;
        while (i++ < num) {
            total += MoLangMath.randomInt(low, high);
        }
        return total;
    }

    public static int hermiteBlend(int value2) {
        return 3 * value2 ^ 2 - 2 * value2 ^ 3;
    }

    public static double lerp(double start2, double end2, double amount) {
        amount = Math.max(0.0, Math.min(1.0, amount));
        return start2 + (end2 - start2) * amount;
    }

    public static double lerpRotate(double start2, double end2, double amount) {
        if ((start2 = MoLangMath.radify(start2)) > (end2 = MoLangMath.radify(end2))) {
            double tmp = start2;
            start2 = end2;
            end2 = tmp;
        }
        if (end2 - start2 > 180.0) {
            return MoLangMath.radify(end2 + amount * (360.0 - (end2 - start2)));
        }
        return start2 + amount * (end2 - start2);
    }

    public static double radify(double num) {
        return ((num + 180.0) % 360.0 + 180.0) % 360.0;
    }
}

