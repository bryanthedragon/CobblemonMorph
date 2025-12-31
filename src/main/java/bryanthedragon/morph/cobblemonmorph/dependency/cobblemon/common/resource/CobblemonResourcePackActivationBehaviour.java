package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.resource;

public enum CobblemonResourcePackActivationBehaviour {
    /**
     * The resource pack will start disabled.
     */
    NORMAL,

    /**
     * The resource pack will start enabled.
     */
    DEFAULT_ENABLED,

    /**
     * The resource pack will always be enabled.
     * The user can reorder it but cannot remove it.
     */
    ALWAYS_ENABLED
}
