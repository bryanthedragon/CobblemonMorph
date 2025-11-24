/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00162\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0016B\u001d\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\u000b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket;", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SingleUpdatePacket;", "Lcom/cobblemon/mod/common/pokemon/EVs;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encodeValue", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "value", "set", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/EVs;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lkotlin/Function0;", "eVs", "<init>", "(Lkotlin/jvm/functions/Function0;Lcom/cobblemon/mod/common/pokemon/EVs;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStatsUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n1855#2,2:60\n*S KotlinDebug\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket\n*L\n29#1:60,2\n*E\n"})
public final class EVsUpdatePacket
extends SingleUpdatePacket<EVs, EVsUpdatePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("ev_update");

    public EVsUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon, @NotNull EVs eVs) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)eVs, (String)"eVs");
        super(pokemon, eVs);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeValue(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ((EVs)this.getValue()).saveToBuffer(buffer);
    }

    @Override
    public void set(@NotNull Pokemon pokemon, @NotNull EVs value2) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        Iterable $this$forEach$iv = value2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Map.Entry entry = (Map.Entry)element$iv;
            boolean bl = false;
            Stat stat = (Stat)entry.getKey();
            int value3 = ((Number)entry.getValue()).intValue();
            pokemon.getEvs().set(stat, value3);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nStatsUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,59:1\n1#2:60\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final EVsUpdatePacket decode(@NotNull FriendlyByteBuf buffer) {
            void $this$decode_u24lambda_u240;
            EVs eVs;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            EVs eVs2 = eVs = new EVs();
            Function0<Pokemon> function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
            boolean bl = false;
            $this$decode_u24lambda_u240.loadFromBuffer(buffer);
            EVs eVs3 = eVs;
            Function0<Pokemon> function02 = function0;
            return new EVsUpdatePacket(function02, eVs3);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

