package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value;

public class DoubleValue
implements MoValue {
    public static final DoubleValue ZERO = new DoubleValue(0.0);
    public static final DoubleValue ONE = new DoubleValue(1.0);
    public double value;

    public DoubleValue(Object value2) {
        this.value = value2 instanceof Boolean ? ((Boolean)value2 != false ? 1.0 : 0.0) : (value2 instanceof Number ? ((Number)value2).doubleValue() : 1.0);
    }

    @Override
    public Double value() {
        return this.value;
    }

    @Override
    public String asString() {
        return Double.toString(this.value);
    }

    @Override
    public double asDouble() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) || obj instanceof DoubleValue && ((DoubleValue)obj).asDouble() == this.value || obj instanceof StringValue && ((StringValue)obj).asString().equals(this.asString());
    }
}

