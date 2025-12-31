package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trainer;

import net.minecraft.util.StringRepresentable;

public enum OriginalTrainerType implements StringRepresentable {
    NONE, PLAYER, NPC;

    public String getSerializedName() {
        this.name;
    }
    final class Companion  {
        val Codec<OriginalTrainerType> CODEC = StringRepresentable.fromEnum(OriginalTrainerType::values);
    }
}
