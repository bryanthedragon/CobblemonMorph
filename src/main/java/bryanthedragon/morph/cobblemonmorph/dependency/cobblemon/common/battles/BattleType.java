/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\bf\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0011\u001a\u00020\u000e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0013\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0014\u0010\u0015\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\b\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/battles/BattleType;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/FriendlyByteBuf;", "", "getActorsPerSide", "()I", "actorsPerSide", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "displayName", "", "getName", "()Ljava/lang/String;", "name", "getPokemonPerSide", "pokemonPerSide", "getSlotsPerActor", "slotsPerActor", "Companion", "common"})
public interface BattleType {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleType$Companion.$$INSTANCE;

    @NotNull
    public String getName();

    @NotNull
    public MutableComponent getDisplayName();

    public int getActorsPerSide();

    public int getSlotsPerActor();

    public int getPokemonPerSide();

    @NotNull
    public FriendlyByteBuf saveToBuffer(@NotNull FriendlyByteBuf var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/battles/BattleType$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/battles/BattleType;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/BattleType;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final BattleType loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String name = buffer.m_130277_();
            Component displayName = buffer.m_130238_();
            int actorsPerSide = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            int slotsPerActor = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            MutableComponent mutableComponent = displayName.m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"displayName.copy()");
            return BattleTypes.INSTANCE.makeBattleType(name, mutableComponent, actorsPerSide, slotsPerActor);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static int getPokemonPerSide(@NotNull BattleType $this) {
            return $this.getActorsPerSide() * $this.getSlotsPerActor();
        }

        @NotNull
        public static FriendlyByteBuf saveToBuffer(@NotNull BattleType $this, @NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130070_($this.getName());
            buffer.m_130083_((Component)$this.getDisplayName());
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, $this.getActorsPerSide());
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, $this.getSlotsPerActor());
            return buffer;
        }
    }
}

