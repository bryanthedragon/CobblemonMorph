/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.NoPokemonStoreException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.PokemonStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b1\u0010+JA\u0010\n\u001a\u0004\u0018\u00018\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0002\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ6\u0010\n\u001a\u0004\u0018\u00018\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0002\"\u0010\b\u0001\u0010\u0005\u0018\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\t\u001a\u00020\bH\u0086\b\u00a2\u0006\u0004\b\n\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J!\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00172\u0006\u0010\r\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001d\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00172\u0006\u0010\r\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0019J\u0017\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001fJ\u0017\u0010!\u001a\u00020 2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010#\u001a\u00020 2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b#\u0010\"J\u001f\u0010(\u001a\u00020 2\u0006\u0010%\u001a\u00020$2\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b(\u0010)J\u000f\u0010*\u001a\u00020 H\u0016\u00a2\u0006\u0004\b*\u0010+J\u0017\u0010,\u001a\u00020 2\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b,\u0010-R\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020&0.8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b/\u00100\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/api/storage/PokemonStoreManager;", "", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "getCustomStore", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "playerID", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPC", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "pcBlockEntity", "getPCForPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "", "getPCs", "(Ljava/util/UUID;)Ljava/lang/Iterable;", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "getParties", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "getParty", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "", "onPlayerDataSync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "onPlayerDisconnect", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lcom/cobblemon/mod/common/api/storage/factory/PokemonStoreFactory;", "factory", "registerFactory", "(Lcom/cobblemon/mod/common/api/Priority;Lcom/cobblemon/mod/common/api/storage/factory/PokemonStoreFactory;)V", "unregisterAll", "()V", "unregisterFactory", "(Lcom/cobblemon/mod/common/api/storage/factory/PokemonStoreFactory;)V", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "factories", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonStoreManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStoreManager.kt\ncom/cobblemon/mod/common/api/storage/PokemonStoreManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n1855#2,2:107\n1855#2,2:110\n1855#2,2:112\n1#3:109\n*S KotlinDebug\n*F\n+ 1 PokemonStoreManager.kt\ncom/cobblemon/mod/common/api/storage/PokemonStoreManager\n*L\n45#1:107,2\n96#1:110,2\n97#1:112,2\n*E\n"})
public class PokemonStoreManager {
    @NotNull
    private final PrioritizedList<PokemonStoreFactory> factories = new PrioritizedList();

    public void registerFactory(@NotNull Priority priority, @NotNull PokemonStoreFactory factory) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter((Object)factory, (String)"factory");
        this.factories.add(priority, factory);
    }

    public void unregisterFactory(@NotNull PokemonStoreFactory factory) {
        Intrinsics.checkNotNullParameter((Object)factory, (String)"factory");
        factory.shutdown();
        this.factories.remove(factory);
    }

    public void unregisterAll() {
        Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)this.factories);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PokemonStoreFactory p0 = (PokemonStoreFactory)element$iv;
            boolean bl = false;
            this.unregisterFactory(p0);
        }
    }

    @NotNull
    public PlayerPartyStore getParty(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        return this.getParty(uUID);
    }

    @NotNull
    public PlayerPartyStore getParty(@NotNull UUID playerID) throws NoPokemonStoreException {
        PlayerPartyStore playerPartyStore;
        block2: {
            Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
            for (PokemonStoreFactory it : (Iterable)this.factories) {
                boolean bl = false;
                PlayerPartyStore playerPartyStore2 = it.getPlayerParty(playerID);
                if (playerPartyStore2 == null) continue;
                playerPartyStore = playerPartyStore2;
                break block2;
            }
            playerPartyStore = null;
        }
        if (playerPartyStore == null) {
            throw new NoPokemonStoreException("No factory was able to provide a party for " + playerID + " - this should not be possible unless someone has removed the default provider!");
        }
        return playerPartyStore;
    }

    @NotNull
    public PCStore getPC(@NotNull UUID playerID) throws NoPokemonStoreException {
        PCStore pCStore;
        block2: {
            Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
            for (PokemonStoreFactory it : (Iterable)this.factories) {
                boolean bl = false;
                PCStore pCStore2 = it.getPC(playerID);
                if (pCStore2 == null) continue;
                pCStore = pCStore2;
                break block2;
            }
            pCStore = null;
        }
        if (pCStore == null) {
            throw new NoPokemonStoreException("No factory was able to provide a PC for " + playerID + " - this should not be possible unless someone has removed the default provider!");
        }
        return pCStore;
    }

    @Nullable
    public PCStore getPCForPlayer(@NotNull ServerPlayer player, @NotNull PCBlockEntity pcBlockEntity) {
        PCStore pCStore;
        block1: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)((Object)pcBlockEntity), (String)"pcBlockEntity");
            for (PokemonStoreFactory it : (Iterable)this.factories) {
                boolean bl = false;
                PCStore pCStore2 = it.getPCForPlayer(player, pcBlockEntity);
                if (pCStore2 == null) continue;
                pCStore = pCStore2;
                break block1;
            }
            pCStore = null;
        }
        return pCStore;
    }

    @NotNull
    public Iterable<PartyStore> getParties(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        List parties = new ArrayList();
        for (PokemonStoreFactory factory : this.factories) {
            PlayerPartyStore it;
            if (factory.getPlayerParty(playerID) == null) continue;
            boolean bl = false;
            parties.add(it);
        }
        return parties;
    }

    @NotNull
    public Iterable<PCStore> getPCs(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        List pcs = new ArrayList();
        for (PokemonStoreFactory factory : this.factories) {
            PCStore it;
            if (factory.getPC(playerID) == null) continue;
            boolean bl = false;
            pcs.add(it);
        }
        return pcs;
    }

    public final /* synthetic */ <E extends StorePosition, T extends PokemonStore<E>> T getCustomStore(UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        boolean $i$f$getCustomStore = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        return (T)this.getCustomStore(PokemonStore.class, uuid2);
    }

    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T getCustomStore(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        for (PokemonStoreFactory factory : this.factories) {
            T t = factory.getCustomStore(storeClass, uuid2);
            if (t == null) continue;
            T $this$getCustomStore_u24lambda_u245 = t;
            boolean bl = false;
            return $this$getCustomStore_u24lambda_u245;
        }
        return null;
    }

    public void onPlayerDataSync(@NotNull ServerPlayer player) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        Iterable<PartyStore> parties = this.getParties(uUID);
        Iterable<PokemonStore> $this$forEach$iv = parties;
        boolean $i$f$forEach = false;
        Iterator<PokemonStore> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            PartyStore partyStore;
            PartyStore party = partyStore = iterator.next();
            bl = false;
            party.sendTo(player);
        }
        UUID uUID2 = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
        $this$forEach$iv = this.getPCs(uUID2);
        $i$f$forEach = false;
        for (PokemonStore pokemonStore : $this$forEach$iv) {
            PCStore pc = (PCStore)pokemonStore;
            bl = false;
            pc.sendTo(player);
        }
        CobblemonNetwork.INSTANCE.sendPacket(player, new SetPartyReferencePacket(((PartyStore)CollectionsKt.first(parties)).getUuid()));
    }

    public void onPlayerDisconnect(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        for (PokemonStoreFactory factory : this.factories) {
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            factory.onPlayerDisconnect(uUID);
        }
    }
}

