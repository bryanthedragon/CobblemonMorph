/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;


public final class ActorType
extends Enum<ActorType> {
    public static final /* enum */ ActorType WILD = new ActorType();
    public static final /* enum */ ActorType PLAYER = new ActorType();
    public static final /* enum */ ActorType NPC = new ActorType();
    private static final /* synthetic */ ActorType[] $VALUES;

    public static ActorType[] values() {
        return (ActorType[])$VALUES.clone();
    }

    public static ActorType valueOf(String value2) {
        return Enum.valueOf(ActorType.class, value2);
    }

    static {
        $VALUES = actorTypeArray = new ActorType[]{ActorType.WILD, ActorType.PLAYER, ActorType.NPC};
    }
}

