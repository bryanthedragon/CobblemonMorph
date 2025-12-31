package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

/**
 * A specific exception that allows canceled transformations to occur in pipes.
 *
 * @author Hiroku
 * @since November 26th, 2021
 */
public class NoTransformThrowable extends RuntimeException {

    final boolean terminate;

    public NoTransformThrowable(boolean terminate) {
        super();
        this.terminate = terminate;
    }

    public boolean shouldTerminate() {
        return terminate;
    }
}
