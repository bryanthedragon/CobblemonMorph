package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ranges.ints;

public record IntRange(int first, int last) {

    public static IntRange of(int first, int last) {
        return new IntRange(first, last);
    }

    public boolean isSingle() {
        return first == last;
    }
}
