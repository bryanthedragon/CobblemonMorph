package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value;

public class StringValue
implements MoValue {
    public String value;

    public StringValue(String value2) {
        this.value = value2;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String asString() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) || obj instanceof StringValue && ((StringValue)obj).asString().equals(this.value) || obj instanceof DoubleValue && ((DoubleValue)obj).asString().equals(this.value);
    }
}

