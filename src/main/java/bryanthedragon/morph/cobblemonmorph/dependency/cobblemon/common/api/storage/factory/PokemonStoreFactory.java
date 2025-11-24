/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001JA\u0010\n\u001a\u0004\u0018\u00018\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0002\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u00062\u0006\u0010\t\u001a\u00020\bH&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\bH&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ!\u0010\u0014\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\f\u001a\u00020\bH&\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001a\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\bH&\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0019H&\u00a2\u0006\u0004\b\u001c\u0010\u001d\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/api/storage/factory/PokemonStoreFactory;", "", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "getCustomStore", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "playerID", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPC", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "pcBlockEntity", "getPCForPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "getPlayerParty", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "", "onPlayerDisconnect", "(Ljava/util/UUID;)V", "shutdown", "()V", "common"})
public interface PokemonStoreFactory {
    @Nullable
    public PlayerPartyStore getPlayerParty(@NotNull UUID var1);

    @Nullable
    public PCStore getPC(@NotNull UUID var1);

    @Nullable
    public PCStore getPCForPlayer(@NotNull ServerPlayer var1, @NotNull PCBlockEntity var2);

    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T getCustomStore(@NotNull Class<T> var1, @NotNull UUID var2);

    public void shutdown();

    public void onPlayerDisconnect(@NotNull UUID var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @Nullable
        public static PCStore getPCForPlayer(@NotNull PokemonStoreFactory $this, @NotNull ServerPlayer player, @NotNull PCBlockEntity pcBlockEntity) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)((Object)pcBlockEntity), (String)"pcBlockEntity");
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            return $this.getPC(uUID);
        }
    }
}

