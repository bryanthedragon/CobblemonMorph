/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 ,2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0004-./,B\u001b\b\u0016\u0012\u0006\u0010&\u001a\u00020%\u0012\b\u0010(\u001a\u0004\u0018\u00010'\u00a2\u0006\u0004\b)\u0010*B\u0007\u00a2\u0006\u0004\b)\u0010+J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0010\u001a\u00020\u000f8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0017\u001a\u00020\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\"\u001a\u00020\u001b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\"\u0010\u001d\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "battleFormat", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getBattleFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "setBattleFormat", "(Lcom/cobblemon/mod/common/battles/BattleFormat;)V", "Ljava/util/UUID;", "battleId", "Ljava/util/UUID;", "getBattleId", "()Ljava/util/UUID;", "setBattleId", "(Ljava/util/UUID;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;", "side1", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;", "getSide1", "()Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;", "setSide1", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;)V", "side2", "getSide2", "setSide2", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/BattleSide;", "allySide", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/BattleSide;)V", "()V", "Companion", "ActiveBattlePokemonDTO", "BattleActorDTO", "BattleSideDTO", "common"})
@SourceDebugExtension(value={"SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,245:1\n11335#2:246\n11670#2,2:247\n11335#2:249\n11670#2,2:250\n11672#2:256\n11672#2:257\n1549#3:252\n1620#3,3:253\n*S KotlinDebug\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket\n*L\n59#1:246\n59#1:247,2\n61#1:249\n61#1:250,2\n61#1:256\n59#1:257\n66#1:252\n66#1:253,3\n*E\n"})
public final class BattleInitializePacket
implements NetworkPacket<BattleInitializePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    public UUID battleId;
    public BattleFormat battleFormat;
    public BattleSideDTO side1;
    public BattleSideDTO side2;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("battle_initialize");

    public BattleInitializePacket() {
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @NotNull
    public final UUID getBattleId() {
        UUID uUID = this.battleId;
        if (uUID != null) {
            return uUID;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battleId");
        return null;
    }

    public final void setBattleId(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.battleId = uUID;
    }

    @NotNull
    public final BattleFormat getBattleFormat() {
        BattleFormat battleFormat = this.battleFormat;
        if (battleFormat != null) {
            return battleFormat;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battleFormat");
        return null;
    }

    public final void setBattleFormat(@NotNull BattleFormat battleFormat) {
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"<set-?>");
        this.battleFormat = battleFormat;
    }

    @NotNull
    public final BattleSideDTO getSide1() {
        BattleSideDTO battleSideDTO = this.side1;
        if (battleSideDTO != null) {
            return battleSideDTO;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"side1");
        return null;
    }

    public final void setSide1(@NotNull BattleSideDTO battleSideDTO) {
        Intrinsics.checkNotNullParameter((Object)battleSideDTO, (String)"<set-?>");
        this.side1 = battleSideDTO;
    }

    @NotNull
    public final BattleSideDTO getSide2() {
        BattleSideDTO battleSideDTO = this.side2;
        if (battleSideDTO != null) {
            return battleSideDTO;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"side2");
        return null;
    }

    public final void setSide2(@NotNull BattleSideDTO battleSideDTO) {
        Intrinsics.checkNotNullParameter((Object)battleSideDTO, (String)"<set-?>");
        this.side2 = battleSideDTO;
    }

    /*
     * WARNING - void declaration
     */
    public BattleInitializePacket(@NotNull PokemonBattle battle2, @Nullable BattleSide allySide) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        this();
        UUID uUID = battle2.getBattleId();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battle.battleId");
        this.setBattleId(uUID);
        this.setBattleFormat(battle2.getFormat());
        BattleSide[] battleSideArray = new BattleSide[]{battle2.getSide1(), battle2.getSide2()};
        BattleSide[] $this$map$iv = battleSideArray;
        boolean $i$f$map = false;
        BattleSide[] battleSideArray2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        int n = ((void)$this$mapTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void $this$mapTo$iv$iv2;
            void side;
            void item$iv$iv;
            void var12_11 = item$iv$iv = $this$mapTo$iv$iv[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            BattleActor[] $this$map$iv2 = side.getActors();
            boolean $i$f$map2 = false;
            BattleActor[] battleActorArray = $this$map$iv2;
            Collection destination$iv$iv2 = new ArrayList($this$map$iv2.length);
            boolean $i$f$mapTo2 = false;
            int n2 = ((void)$this$mapTo$iv$iv2).length;
            for (int j = 0; j < n2; ++j) {
                void $this$mapTo$iv$iv3;
                void actor;
                void item$iv$iv2;
                void var22_21 = item$iv$iv2 = $this$mapTo$iv$iv2[j];
                Collection collection2 = destination$iv$iv2;
                boolean bl2 = false;
                UUID uUID2 = actor.getUuid();
                String string = actor.getShowdownId();
                MutableComponent mutableComponent = actor.getName();
                Iterable $this$map$iv3 = actor.getActivePokemon();
                boolean $i$f$map3 = false;
                Iterable iterable = $this$map$iv3;
                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv3, (int)10));
                boolean $i$f$mapTo3 = false;
                for (Object item$iv$iv3 : $this$mapTo$iv$iv3) {
                    ActiveBattlePokemonDTO activeBattlePokemonDTO;
                    void it;
                    ActiveBattlePokemon activeBattlePokemon = (ActiveBattlePokemon)item$iv$iv3;
                    Collection collection3 = destination$iv$iv3;
                    boolean bl3 = false;
                    if (it.getBattlePokemon() != null) {
                        BattlePokemon pkm;
                        boolean bl4 = false;
                        activeBattlePokemonDTO = ActiveBattlePokemonDTO.Companion.fromPokemon(pkm, Intrinsics.areEqual((Object)allySide, (Object)side), it.getIllusion());
                    } else {
                        activeBattlePokemonDTO = null;
                    }
                    collection3.add(activeBattlePokemonDTO);
                }
                List list = (List)destination$iv$iv3;
                ActorType actorType = actor.getType();
                collection2.add(new BattleActorDTO(uUID2, mutableComponent, string, list, actorType));
            }
            List list = (List)destination$iv$iv2;
            collection.add(new BattleSideDTO(list));
        }
        List sides = (List)destination$iv$iv;
        this.setSide1((BattleSideDTO)sides.get(0));
        this.setSide2((BattleSideDTO)sides.get(1));
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.getBattleId());
        this.getBattleFormat().saveToBuffer(buffer);
        BattleSideDTO[] battleSideDTOArray = new BattleSideDTO[]{this.getSide1(), this.getSide2()};
        for (BattleSideDTO side : battleSideDTOArray) {
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, side.getActors().size());
            for (BattleActorDTO actor : side.getActors()) {
                buffer.m_130077_(actor.getUuid());
                buffer.m_130083_((Component)actor.getDisplayName());
                buffer.m_130070_(actor.getShowdownId());
                NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, actor.getActivePokemon().size());
                for (ActiveBattlePokemonDTO activePokemon : actor.getActivePokemon()) {
                    buffer.writeBoolean(activePokemon != null);
                    ActiveBattlePokemonDTO activeBattlePokemonDTO = activePokemon;
                    if (activeBattlePokemonDTO == null) continue;
                    activeBattlePokemonDTO.saveToBuffer(buffer);
                }
                NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, actor.getType().ordinal());
            }
        }
    }

    private final void decode(FriendlyByteBuf buffer) {
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.setBattleId(uUID);
        this.setBattleFormat(BattleFormat.Companion.loadFromBuffer(buffer));
        List sides = new ArrayList();
        int n = 2;
        for (int i = 0; i < n; ++i) {
            int it = i;
            boolean bl = false;
            List actors = new ArrayList();
            int n2 = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            for (int j = 0; j < n2; ++j) {
                int it2 = j;
                boolean bl2 = false;
                UUID uuid2 = buffer.m_130259_();
                MutableComponent displayName = buffer.m_130238_().m_6881_();
                String showdownId = buffer.m_130277_();
                List activePokemon = new ArrayList();
                int n3 = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
                for (int k = 0; k < n3; ++k) {
                    int it3 = k;
                    boolean bl3 = false;
                    if (buffer.readBoolean()) {
                        activePokemon.add(ActiveBattlePokemonDTO.Companion.loadFromBuffer(buffer));
                        continue;
                    }
                    activePokemon.add(null);
                }
                ActorType type = ActorType.values()[NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE)];
                Intrinsics.checkNotNullExpressionValue((Object)uuid2, (String)"uuid");
                Intrinsics.checkNotNullExpressionValue((Object)displayName, (String)"displayName");
                Intrinsics.checkNotNullExpressionValue((Object)showdownId, (String)"showdownId");
                actors.add(new BattleActorDTO(uuid2, displayName, showdownId, activePokemon, type));
            }
            sides.add(new BattleSideDTO(actors));
        }
        this.setSide1((BattleSideDTO)sides.get(0));
        this.setSide2((BattleSideDTO)sides.get(1));
    }

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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0086\b\u0018\u0000 F2\u00020\u0001:\u0001FBc\u0012\u0006\u0010\u001e\u001a\u00020\u0002\u0012\u0006\u0010\u001f\u001a\u00020\u0005\u0012\u0006\u0010 \u001a\u00020\b\u0012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\u0010\"\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010#\u001a\u00020\u0012\u0012\u0006\u0010$\u001a\u00020\u0012\u0012\u0006\u0010%\u001a\u00020\u0016\u0012\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019\u00a2\u0006\u0004\bD\u0010EJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012H\u00c6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0012H\u00c6\u0003\u00a2\u0006\u0004\b\u0015\u0010\u0014J\u0010\u0010\u0017\u001a\u00020\u0016H\u00c6\u0003\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019H\u00c6\u0003\u00a2\u0006\u0004\b\u001c\u0010\u001dJ~\u0010'\u001a\u00020\u00002\b\b\u0002\u0010\u001e\u001a\u00020\u00022\b\b\u0002\u0010\u001f\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\b2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010#\u001a\u00020\u00122\b\b\u0002\u0010$\u001a\u00020\u00122\b\b\u0002\u0010%\u001a\u00020\u00162\u0014\b\u0002\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019H\u00c6\u0001\u00a2\u0006\u0004\b'\u0010(J\u001a\u0010*\u001a\u00020\u00162\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b*\u0010+J\u0010\u0010,\u001a\u00020\u001bH\u00d6\u0001\u00a2\u0006\u0004\b,\u0010-J\u0015\u00100\u001a\u00020\u00002\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\b0\u00101J\u0010\u00102\u001a\u00020\fH\u00d6\u0001\u00a2\u0006\u0004\b2\u00103R\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b!\u00104\u001a\u0004\b5\u0010\u000eR\u0017\u0010\u001f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u001f\u00106\u001a\u0004\b7\u0010\u0007R\u0017\u0010#\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b#\u00108\u001a\u0004\b9\u0010\u0014R\u0017\u0010%\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b%\u0010:\u001a\u0004\b%\u0010\u0018R\u0017\u0010$\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b$\u00108\u001a\u0004\b;\u0010\u0014R\u0017\u0010 \u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b \u0010<\u001a\u0004\b=\u0010\nR#\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00198\u0006\u00a2\u0006\f\n\u0004\b&\u0010>\u001a\u0004\b?\u0010\u001dR\u0019\u0010\"\u001a\u0004\u0018\u00010\u000f8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010@\u001a\u0004\bA\u0010\u0011R\u0017\u0010\u001e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010B\u001a\u0004\bC\u0010\u0004\u00a8\u0006G"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "Lnet/minecraft/network/chat/MutableComponent;", "component2", "()Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "component3", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "", "component4", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "component5", "()Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "", "component6", "()F", "component7", "", "component8", "()Z", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "component9", "()Ljava/util/Map;", "uuid", "displayName", "properties", "aspects", "status", "hpValue", "maxHp", "isFlatHp", "statChanges", "copy", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/Set;Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;FFZLjava/util/Map;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "other", "equals", "(Ljava/lang/Object;)Z", "hashCode", "()I", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "toString", "()Ljava/lang/String;", "Ljava/util/Set;", "getAspects", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "F", "getHpValue", "Z", "getMaxHp", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProperties", "Ljava/util/Map;", "getStatChanges", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "getStatus", "Ljava/util/UUID;", "getUuid", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/Set;Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;FFZLjava/util/Map;)V", "Companion", "common"})
    @SourceDebugExtension(value={"SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"})
    public static final class ActiveBattlePokemonDTO {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final UUID uuid;
        @NotNull
        private final MutableComponent displayName;
        @NotNull
        private final PokemonProperties properties;
        @NotNull
        private final Set<String> aspects;
        @Nullable
        private final PersistentStatus status;
        private final float hpValue;
        private final float maxHp;
        private final boolean isFlatHp;
        @NotNull
        private final Map<Stat, Integer> statChanges;

        public ActiveBattlePokemonDTO(@NotNull UUID uuid2, @NotNull MutableComponent displayName, @NotNull PokemonProperties properties2, @NotNull Set<String> aspects, @Nullable PersistentStatus status, float hpValue, float maxHp, boolean isFlatHp, @NotNull Map<Stat, Integer> statChanges) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            Intrinsics.checkNotNullParameter(statChanges, (String)"statChanges");
            this.uuid = uuid2;
            this.displayName = displayName;
            this.properties = properties2;
            this.aspects = aspects;
            this.status = status;
            this.hpValue = hpValue;
            this.maxHp = maxHp;
            this.isFlatHp = isFlatHp;
            this.statChanges = statChanges;
        }

        @NotNull
        public final UUID getUuid() {
            return this.uuid;
        }

        @NotNull
        public final MutableComponent getDisplayName() {
            return this.displayName;
        }

        @NotNull
        public final PokemonProperties getProperties() {
            return this.properties;
        }

        @NotNull
        public final Set<String> getAspects() {
            return this.aspects;
        }

        @Nullable
        public final PersistentStatus getStatus() {
            return this.status;
        }

        public final float getHpValue() {
            return this.hpValue;
        }

        public final float getMaxHp() {
            return this.maxHp;
        }

        public final boolean isFlatHp() {
            return this.isFlatHp;
        }

        @NotNull
        public final Map<Stat, Integer> getStatChanges() {
            return this.statChanges;
        }

        @NotNull
        public final ActiveBattlePokemonDTO saveToBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130077_(this.uuid);
            buffer.m_130083_((Component)this.displayName);
            buffer.m_130070_(PokemonProperties.asString$default(this.properties, null, 1, null));
            buffer.m_236828_((Collection)this.aspects, ActiveBattlePokemonDTO::saveToBuffer$lambda$0);
            buffer.writeBoolean(this.status != null);
            PersistentStatus persistentStatus = this.status;
            if (persistentStatus != null) {
                PersistentStatus it = persistentStatus;
                boolean bl = false;
                buffer.m_130070_(it.getName().toString());
            }
            buffer.writeFloat(this.hpValue);
            buffer.writeFloat(this.maxHp);
            buffer.writeBoolean(this.isFlatHp);
            NetExtensionsKt.writeMapK((ByteBuf)buffer, IntSize.U_BYTE, this.statChanges, (Function1)new Function1<Map.Entry<? extends Stat, ? extends Integer>, Unit>(buffer){
                final /* synthetic */ FriendlyByteBuf $buffer;
                {
                    this.$buffer = $buffer;
                    super(1);
                }

                public final void invoke(@NotNull Map.Entry<? extends Stat, Integer> entry) {
                    Intrinsics.checkNotNullParameter(entry, (String)"<name for destructuring parameter 0>");
                    Stat stat = entry.getKey();
                    int stages = ((Number)entry.getValue()).intValue();
                    Cobblemon.INSTANCE.getStatProvider().encode(this.$buffer, stat);
                    NetExtensionsKt.writeSizedInt((ByteBuf)this.$buffer, IntSize.BYTE, stages);
                }
            });
            return this;
        }

        @NotNull
        public final UUID component1() {
            return this.uuid;
        }

        @NotNull
        public final MutableComponent component2() {
            return this.displayName;
        }

        @NotNull
        public final PokemonProperties component3() {
            return this.properties;
        }

        @NotNull
        public final Set<String> component4() {
            return this.aspects;
        }

        @Nullable
        public final PersistentStatus component5() {
            return this.status;
        }

        public final float component6() {
            return this.hpValue;
        }

        public final float component7() {
            return this.maxHp;
        }

        public final boolean component8() {
            return this.isFlatHp;
        }

        @NotNull
        public final Map<Stat, Integer> component9() {
            return this.statChanges;
        }

        @NotNull
        public final ActiveBattlePokemonDTO copy(@NotNull UUID uuid2, @NotNull MutableComponent displayName, @NotNull PokemonProperties properties2, @NotNull Set<String> aspects, @Nullable PersistentStatus status, float hpValue, float maxHp, boolean isFlatHp, @NotNull Map<Stat, Integer> statChanges) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            Intrinsics.checkNotNullParameter(statChanges, (String)"statChanges");
            return new ActiveBattlePokemonDTO(uuid2, displayName, properties2, aspects, status, hpValue, maxHp, isFlatHp, statChanges);
        }

        public static /* synthetic */ ActiveBattlePokemonDTO copy$default(ActiveBattlePokemonDTO activeBattlePokemonDTO, UUID uUID, MutableComponent mutableComponent, PokemonProperties pokemonProperties, Set set2, PersistentStatus persistentStatus, float f, float f2, boolean bl, Map map, int n, Object object) {
            if ((n & 1) != 0) {
                uUID = activeBattlePokemonDTO.uuid;
            }
            if ((n & 2) != 0) {
                mutableComponent = activeBattlePokemonDTO.displayName;
            }
            if ((n & 4) != 0) {
                pokemonProperties = activeBattlePokemonDTO.properties;
            }
            if ((n & 8) != 0) {
                set2 = activeBattlePokemonDTO.aspects;
            }
            if ((n & 0x10) != 0) {
                persistentStatus = activeBattlePokemonDTO.status;
            }
            if ((n & 0x20) != 0) {
                f = activeBattlePokemonDTO.hpValue;
            }
            if ((n & 0x40) != 0) {
                f2 = activeBattlePokemonDTO.maxHp;
            }
            if ((n & 0x80) != 0) {
                bl = activeBattlePokemonDTO.isFlatHp;
            }
            if ((n & 0x100) != 0) {
                map = activeBattlePokemonDTO.statChanges;
            }
            return activeBattlePokemonDTO.copy(uUID, mutableComponent, pokemonProperties, set2, persistentStatus, f, f2, bl, map);
        }

        @NotNull
        public String toString() {
            return "ActiveBattlePokemonDTO(uuid=" + this.uuid + ", displayName=" + this.displayName + ", properties=" + this.properties + ", aspects=" + this.aspects + ", status=" + this.status + ", hpValue=" + this.hpValue + ", maxHp=" + this.maxHp + ", isFlatHp=" + this.isFlatHp + ", statChanges=" + this.statChanges + ")";
        }

        public int hashCode() {
            int result = this.uuid.hashCode();
            result = result * 31 + this.displayName.hashCode();
            result = result * 31 + this.properties.hashCode();
            result = result * 31 + ((Object)this.aspects).hashCode();
            result = result * 31 + (this.status == null ? 0 : this.status.hashCode());
            result = result * 31 + Float.hashCode(this.hpValue);
            result = result * 31 + Float.hashCode(this.maxHp);
            int n = this.isFlatHp ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result = result * 31 + n;
            result = result * 31 + ((Object)this.statChanges).hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ActiveBattlePokemonDTO)) {
                return false;
            }
            ActiveBattlePokemonDTO activeBattlePokemonDTO = (ActiveBattlePokemonDTO)other;
            if (!Intrinsics.areEqual((Object)this.uuid, (Object)activeBattlePokemonDTO.uuid)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.displayName, (Object)activeBattlePokemonDTO.displayName)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.properties, (Object)activeBattlePokemonDTO.properties)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.aspects, activeBattlePokemonDTO.aspects)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.status, (Object)activeBattlePokemonDTO.status)) {
                return false;
            }
            if (Float.compare(this.hpValue, activeBattlePokemonDTO.hpValue) != 0) {
                return false;
            }
            if (Float.compare(this.maxHp, activeBattlePokemonDTO.maxHp) != 0) {
                return false;
            }
            if (this.isFlatHp != activeBattlePokemonDTO.isFlatHp) {
                return false;
            }
            return Intrinsics.areEqual(this.statChanges, activeBattlePokemonDTO.statChanges);
        }

        private static final void saveToBuffer$lambda$0(FriendlyByteBuf buf, String it) {
            buf.m_130070_(it);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J%\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ)\u0010\f\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO$Companion;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "isAlly", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "mock", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "fromMock", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;ZLcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "illusion", "fromPokemon", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;ZLcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "<init>", "()V", "common"})
        @SourceDebugExtension(value={"SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"})
        public static final class Companion {
            private Companion() {
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ActiveBattlePokemonDTO fromPokemon(@NotNull BattlePokemon battlePokemon, boolean isAlly, @Nullable BattlePokemon illusion) {
                void $this$fromPokemon_u24lambda_u240;
                Object object;
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                Pokemon pokemon = battlePokemon.getEffectedPokemon();
                if (isAlly) {
                    object = pokemon;
                } else {
                    object = illusion;
                    if (object == null || (object = ((BattlePokemon)object).getEffectedPokemon()) == null) {
                        object = pokemon;
                    }
                }
                Object exposed = object;
                float hpValue = isAlly ? (float)pokemon.getCurrentHealth() : (float)pokemon.getCurrentHealth() / (float)pokemon.getHp();
                UUID uUID = ((Pokemon)exposed).getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"exposed.uuid");
                Object object2 = new PokemonPropertyExtractor[]{PokemonPropertyExtractor.SPECIES, PokemonPropertyExtractor.GENDER};
                Object object3 = object2 = ((Pokemon)exposed).createPokemonProperties((PokemonPropertyExtractor)object2);
                MutableComponent mutableComponent = ((Pokemon)exposed).getDisplayName();
                UUID uUID2 = uUID;
                boolean bl = false;
                $this$fromPokemon_u24lambda_u240.setLevel(pokemon.getLevel());
                Unit unit = Unit.INSTANCE;
                PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
                Map<Stat, Integer> map = battlePokemon.getStatChanges();
                boolean bl2 = isAlly;
                float f = pokemon.getHp();
                float f2 = hpValue;
                PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
                Set<String> set2 = ((Pokemon)exposed).getAspects();
                Object object4 = object2;
                MutableComponent mutableComponent2 = mutableComponent;
                UUID uUID3 = uUID2;
                return new ActiveBattlePokemonDTO(uUID3, mutableComponent2, (PokemonProperties)object4, set2, persistentStatus, f2, f, bl2, map);
            }

            public static /* synthetic */ ActiveBattlePokemonDTO fromPokemon$default(Companion companion, BattlePokemon battlePokemon, boolean bl, BattlePokemon battlePokemon2, int n, Object object) {
                if ((n & 4) != 0) {
                    battlePokemon2 = null;
                }
                return companion.fromPokemon(battlePokemon, bl, battlePokemon2);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final ActiveBattlePokemonDTO fromMock(@NotNull BattlePokemon battlePokemon, boolean isAlly, @NotNull PokemonProperties mock) {
                void $this$fromMock_u24lambda_u241;
                PokemonProperties pokemonProperties;
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                Intrinsics.checkNotNullParameter((Object)mock, (String)"mock");
                Pokemon pokemon = battlePokemon.getEffectedPokemon();
                float hpValue = isAlly ? (float)pokemon.getCurrentHealth() : (float)pokemon.getCurrentHealth() / (float)pokemon.getHp();
                PokemonProperties pokemonProperties2 = pokemonProperties = mock;
                MutableComponent mutableComponent = pokemon.getDisplayName();
                UUID uUID = battlePokemon.getUuid();
                boolean bl = false;
                $this$fromMock_u24lambda_u241.setLevel(pokemon.getLevel());
                Unit unit = Unit.INSTANCE;
                PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
                Map<Stat, Integer> map = battlePokemon.getStatChanges();
                boolean bl2 = isAlly;
                float f = pokemon.getHp();
                float f2 = hpValue;
                PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
                Set<String> set2 = mock.getAspects();
                PokemonProperties pokemonProperties3 = pokemonProperties;
                MutableComponent mutableComponent2 = mutableComponent;
                UUID uUID2 = uUID;
                return new ActiveBattlePokemonDTO(uUID2, mutableComponent2, pokemonProperties3, set2, persistentStatus, f2, f, bl2, map);
            }

            @NotNull
            public final ActiveBattlePokemonDTO loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
                PersistentStatus persistentStatus;
                Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
                UUID uuid2 = buffer.m_130259_();
                MutableComponent pokemonDisplayName = buffer.m_130238_().m_6881_();
                String string = buffer.m_130277_();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
                PokemonProperties properties2 = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, string, " ", null, 4, null);
                List list = buffer.m_236845_(arg_0 -> Companion.loadFromBuffer$lambda$2(buffer, arg_0));
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
                Set aspects = CollectionsKt.toSet((Iterable)list);
                if (buffer.readBoolean()) {
                    ResourceLocation resourceLocation = buffer.m_130281_();
                    Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
                    Status status = Statuses.INSTANCE.getStatus(resourceLocation);
                    persistentStatus = status instanceof PersistentStatus ? (PersistentStatus)status : null;
                } else {
                    persistentStatus = null;
                }
                PersistentStatus status = persistentStatus;
                float hpRatio = buffer.readFloat();
                float maxHp = buffer.readFloat();
                boolean isFlatHp = buffer.readBoolean();
                Map statChanges = new LinkedHashMap();
                NetExtensionsKt.readMapK((ByteBuf)buffer, IntSize.U_BYTE, statChanges, (Function0)new Function0<Pair<? extends Stat, ? extends Integer>>(buffer){
                    final /* synthetic */ FriendlyByteBuf $buffer;
                    {
                        this.$buffer = $buffer;
                        super(0);
                    }

                    @NotNull
                    public final Pair<Stat, Integer> invoke() {
                        Stat stat = Cobblemon.INSTANCE.getStatProvider().decode(this.$buffer);
                        int stages = NetExtensionsKt.readSizedInt((ByteBuf)this.$buffer, IntSize.BYTE);
                        return TuplesKt.to((Object)stat, (Object)stages);
                    }
                });
                Intrinsics.checkNotNullExpressionValue((Object)uuid2, (String)"uuid");
                Intrinsics.checkNotNullExpressionValue((Object)pokemonDisplayName, (String)"pokemonDisplayName");
                return new ActiveBattlePokemonDTO(uuid2, pokemonDisplayName, properties2, aspects, status, hpRatio, maxHp, isFlatHp, statChanges);
            }

            private static final String loadFromBuffer$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
                Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
                return $buffer.m_130277_();
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0012\u001a\u00020\u0002\u0012\u0006\u0010\u0013\u001a\u00020\u0005\u0012\u0006\u0010\u0014\u001a\u00020\b\u0012\u000e\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u0012\u0006\u0010\u0016\u001a\u00020\u000f\u00a2\u0006\u0004\b+\u0010,J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fH\u00c6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011JJ\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0012\u001a\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\b2\u0010\b\u0002\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\b\b\u0002\u0010\u0016\u001a\u00020\u000fH\u00c6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001a\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\bH\u00d6\u0001\u00a2\u0006\u0004\b \u0010\nR\u001f\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010!\u001a\u0004\b\"\u0010\u000eR\u0017\u0010\u0013\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010#\u001a\u0004\b$\u0010\u0007R\u0017\u0010\u0014\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010%\u001a\u0004\b&\u0010\nR\u0017\u0010\u0016\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010'\u001a\u0004\b(\u0010\u0011R\u0017\u0010\u0012\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010)\u001a\u0004\b*\u0010\u0004\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleActorDTO;", "", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "Lnet/minecraft/network/chat/MutableComponent;", "component2", "()Lnet/minecraft/network/chat/MutableComponent;", "", "component3", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "component4", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "component5", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "uuid", "displayName", "showdownId", "activePokemon", "type", "copy", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/String;Ljava/util/List;Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleActorDTO;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/util/List;", "getActivePokemon", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "Ljava/lang/String;", "getShowdownId", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "Ljava/util/UUID;", "getUuid", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/String;Ljava/util/List;Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;)V", "common"})
    public static final class BattleActorDTO {
        @NotNull
        private final UUID uuid;
        @NotNull
        private final MutableComponent displayName;
        @NotNull
        private final String showdownId;
        @NotNull
        private final List<ActiveBattlePokemonDTO> activePokemon;
        @NotNull
        private final ActorType type;

        public BattleActorDTO(@NotNull UUID uuid2, @NotNull MutableComponent displayName, @NotNull String showdownId, @NotNull List<ActiveBattlePokemonDTO> activePokemon, @NotNull ActorType type) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
            Intrinsics.checkNotNullParameter(activePokemon, (String)"activePokemon");
            Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
            this.uuid = uuid2;
            this.displayName = displayName;
            this.showdownId = showdownId;
            this.activePokemon = activePokemon;
            this.type = type;
        }

        @NotNull
        public final UUID getUuid() {
            return this.uuid;
        }

        @NotNull
        public final MutableComponent getDisplayName() {
            return this.displayName;
        }

        @NotNull
        public final String getShowdownId() {
            return this.showdownId;
        }

        @NotNull
        public final List<ActiveBattlePokemonDTO> getActivePokemon() {
            return this.activePokemon;
        }

        @NotNull
        public final ActorType getType() {
            return this.type;
        }

        @NotNull
        public final UUID component1() {
            return this.uuid;
        }

        @NotNull
        public final MutableComponent component2() {
            return this.displayName;
        }

        @NotNull
        public final String component3() {
            return this.showdownId;
        }

        @NotNull
        public final List<ActiveBattlePokemonDTO> component4() {
            return this.activePokemon;
        }

        @NotNull
        public final ActorType component5() {
            return this.type;
        }

        @NotNull
        public final BattleActorDTO copy(@NotNull UUID uuid2, @NotNull MutableComponent displayName, @NotNull String showdownId, @NotNull List<ActiveBattlePokemonDTO> activePokemon, @NotNull ActorType type) {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
            Intrinsics.checkNotNullParameter(activePokemon, (String)"activePokemon");
            Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
            return new BattleActorDTO(uuid2, displayName, showdownId, activePokemon, type);
        }

        public static /* synthetic */ BattleActorDTO copy$default(BattleActorDTO battleActorDTO, UUID uUID, MutableComponent mutableComponent, String string, List list, ActorType actorType, int n, Object object) {
            if ((n & 1) != 0) {
                uUID = battleActorDTO.uuid;
            }
            if ((n & 2) != 0) {
                mutableComponent = battleActorDTO.displayName;
            }
            if ((n & 4) != 0) {
                string = battleActorDTO.showdownId;
            }
            if ((n & 8) != 0) {
                list = battleActorDTO.activePokemon;
            }
            if ((n & 0x10) != 0) {
                actorType = battleActorDTO.type;
            }
            return battleActorDTO.copy(uUID, mutableComponent, string, list, actorType);
        }

        @NotNull
        public String toString() {
            return "BattleActorDTO(uuid=" + this.uuid + ", displayName=" + this.displayName + ", showdownId=" + this.showdownId + ", activePokemon=" + this.activePokemon + ", type=" + this.type + ")";
        }

        public int hashCode() {
            int result = this.uuid.hashCode();
            result = result * 31 + this.displayName.hashCode();
            result = result * 31 + this.showdownId.hashCode();
            result = result * 31 + ((Object)this.activePokemon).hashCode();
            result = result * 31 + this.type.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BattleActorDTO)) {
                return false;
            }
            BattleActorDTO battleActorDTO = (BattleActorDTO)other;
            if (!Intrinsics.areEqual((Object)this.uuid, (Object)battleActorDTO.uuid)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.displayName, (Object)battleActorDTO.displayName)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.showdownId, (Object)battleActorDTO.showdownId)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.activePokemon, battleActorDTO.activePokemon)) {
                return false;
            }
            return this.type == battleActorDTO.type;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J \u0010\u0007\u001a\u00020\u00002\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0013\u001a\u0004\b\u0014\u0010\u0005\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;", "", "", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleActorDTO;", "component1", "()Ljava/util/List;", "actors", "copy", "(Ljava/util/List;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleSideDTO;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Ljava/util/List;", "getActors", "<init>", "(Ljava/util/List;)V", "common"})
    public static final class BattleSideDTO {
        @NotNull
        private final List<BattleActorDTO> actors;

        public BattleSideDTO(@NotNull List<BattleActorDTO> actors) {
            Intrinsics.checkNotNullParameter(actors, (String)"actors");
            this.actors = actors;
        }

        @NotNull
        public final List<BattleActorDTO> getActors() {
            return this.actors;
        }

        @NotNull
        public final List<BattleActorDTO> component1() {
            return this.actors;
        }

        @NotNull
        public final BattleSideDTO copy(@NotNull List<BattleActorDTO> actors) {
            Intrinsics.checkNotNullParameter(actors, (String)"actors");
            return new BattleSideDTO(actors);
        }

        public static /* synthetic */ BattleSideDTO copy$default(BattleSideDTO battleSideDTO, List list, int n, Object object) {
            if ((n & 1) != 0) {
                list = battleSideDTO.actors;
            }
            return battleSideDTO.copy(list);
        }

        @NotNull
        public String toString() {
            return "BattleSideDTO(actors=" + this.actors + ")";
        }

        public int hashCode() {
            return ((Object)this.actors).hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BattleSideDTO)) {
                return false;
            }
            BattleSideDTO battleSideDTO = (BattleSideDTO)other;
            return Intrinsics.areEqual(this.actors, battleSideDTO.actors);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final BattleInitializePacket decode(@NotNull FriendlyByteBuf buffer) {
            BattleInitializePacket battleInitializePacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            BattleInitializePacket $this$decode_u24lambda_u240 = battleInitializePacket = new BattleInitializePacket();
            boolean bl = false;
            $this$decode_u24lambda_u240.decode(buffer);
            return battleInitializePacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

