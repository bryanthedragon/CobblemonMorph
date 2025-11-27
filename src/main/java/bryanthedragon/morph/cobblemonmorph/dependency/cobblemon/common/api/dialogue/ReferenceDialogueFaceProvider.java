/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

public final class ReferenceDialogueFaceProvider implements DialogueFaceProvider {
    private final int entityId;

    public ReferenceDialogueFaceProvider(int entityId) {
        this.entityId = entityId;
    }

    public final int getEntityId() {
        return this.entityId;
    }
}

