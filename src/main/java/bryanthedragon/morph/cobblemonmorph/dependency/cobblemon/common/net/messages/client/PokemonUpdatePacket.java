/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorageManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 \u0013*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u00012\b\u0012\u0004\u0012\u00028\u00000\u0001:\u0001\u0013B\u0015\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0004\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\n\u0010\tR\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "", "applyToPokemon", "()V", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encodeDetails", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lkotlin/jvm/functions/Function0;", "getPokemon", "()Lkotlin/jvm/functions/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "Companion", "common"})
public abstract class PokemonUpdatePacket<T extends NetworkPacket<T>>
implements NetworkPacket<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Function0<Pokemon> pokemon;

    public PokemonUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        this.pokemon = pokemon;
    }

    @NotNull
    public final Function0<Pokemon> getPokemon() {
        return this.pokemon;
    }

    @Override
    public final void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Pokemon pokemon = (Pokemon)this.pokemon.invoke();
        StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
        if (storeCoordinates == null || (storeCoordinates = storeCoordinates.getStore()) == null || (storeCoordinates = ((PokemonStore)((Object)storeCoordinates)).getUuid()) == null) {
            storeCoordinates = UUID.randomUUID();
        }
        buffer.m_130077_((UUID)((Object)storeCoordinates));
        buffer.m_130077_(pokemon.getUuid());
        this.encodeDetails(buffer);
    }

    public abstract void encodeDetails(@NotNull FriendlyByteBuf var1);

    public abstract void applyToPokemon();

    @Override
    public void sendToPlayer(@NotNull ServerPlayer player) {
        NetworkPacket.DefaultImpls.sendToPlayer(this, player);
    }

    @Override
    public void sendToPlayers(@NotNull Iterable<? extends ServerPlayer> players2) {
        NetworkPacket.DefaultImpls.sendToPlayers(this, players2);
    }

    @Override
    public void sendToAllPlayers() {
        NetworkPacket.DefaultImpls.sendToAllPlayers(this);
    }

    @Override
    public void sendToServer() {
        NetworkPacket.DefaultImpls.sendToServer(this);
    }

    @Override
    public void sendToPlayersAround(double x, double y, double z, double distance, @NotNull ResourceKey<Level> worldKey, @NotNull Function1<? super ServerPlayer, Boolean> exclusionCondition) {
        NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
    }

    @Override
    @NotNull
    public FriendlyByteBuf toBuffer() {
        return NetworkPacket.DefaultImpls.toBuffer(this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "decodePokemon", "(Lnet/minecraft/network/FriendlyByteBuf;)Lkotlin/jvm/functions/Function0;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Function0<Pokemon> decodePokemon(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID storeId = buffer.m_130259_();
            UUID pokemonId = buffer.m_130259_();
            return (Function0)new Function0<Pokemon>(storeId, pokemonId){
                final /* synthetic */ UUID $storeId;
                final /* synthetic */ UUID $pokemonId;
                {
                    this.$storeId = $storeId;
                    this.$pokemonId = $pokemonId;
                    super(0);
                }

                @NotNull
                public final Pokemon invoke() {
                    ClientStorageManager clientStorageManager = CobblemonClient.INSTANCE.getStorage();
                    UUID uUID = this.$storeId;
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"storeId");
                    UUID uUID2 = this.$pokemonId;
                    Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemonId");
                    Pokemon pokemon = clientStorageManager.locatePokemon(uUID, uUID2);
                    Intrinsics.checkNotNull((Object)pokemon);
                    return pokemon;
                }
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

