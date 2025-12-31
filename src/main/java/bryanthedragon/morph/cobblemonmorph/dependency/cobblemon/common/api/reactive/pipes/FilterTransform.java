package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes;

import java.util.function.Predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;

/**
 * A transform that will only emit received values that match the given predicate.
 *
 * @author Hiroku
 * @since November 26th, 2021
 */
public class FilterTransform<I> implements Transform<I, I> {

    private final Predicate<I> predicate;

    public FilterTransform(Predicate<I> predicate) {
        this.predicate = predicate;
    }

    @Override
    public I invoke(I input) {
        if (predicate.test(input)) {
            return input;
        }

        noTransform(false);
        throw new AssertionError("Unreachable");
    }
}
