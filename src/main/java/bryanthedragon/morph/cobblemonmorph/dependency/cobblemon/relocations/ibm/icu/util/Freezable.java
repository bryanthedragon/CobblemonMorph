
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.util;

public interface Freezable<T>
extends Cloneable {
    public boolean isFrozen();

    public T freeze();

    public T cloneAsThawed();
}

