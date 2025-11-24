/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats;

import kotlin.Metadata;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u0012R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0011\u001a\u00020\u000e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "identifier", "", "getShowdownId", "()Ljava/lang/String;", "showdownId", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "getType", "()Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "type", "Type", "common"})
public interface Stat {
    @NotNull
    public ResourceLocation getIdentifier();

    @NotNull
    public Component getDisplayName();

    @NotNull
    public Type getType();

    @NotNull
    public String getShowdownId();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "", "<init>", "(Ljava/lang/String;I)V", "PERMANENT", "BATTLE_ONLY", "common"})
    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type PERMANENT = new Type();
        public static final /* enum */ Type BATTLE_ONLY = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String value2) {
            return Enum.valueOf(Type.class, value2);
        }

        static {
            $VALUES = typeArray = new Type[]{Type.PERMANENT, Type.BATTLE_ONLY};
        }
    }
}

