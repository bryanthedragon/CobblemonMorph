/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.BattleExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.AddExperienceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u001d\u0012\u0006\u0010*\u001a\u00020\u000f\u0012\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040+\u00a2\u0006\u0004\b-\u0010.J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u0018\u001a\u00020\b2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R.\u0010\u001c\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0016\u0010$\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u001a\u0010&\u001a\u00020%8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/EntityBackedBattleActor;", "Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "experience", "", "awardExperience", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;I)V", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "Ljava/util/UUID;", "getPlayerUUIDs", "()Ljava/util/Set;", "", "name", "nameOwned", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "sendUpdate", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "Lnet/minecraft/sounds/SoundEvent;", "value", "battleTheme", "Lnet/minecraft/sounds/SoundEvent;", "getBattleTheme", "()Lnet/minecraft/sounds/SoundEvent;", "setBattleTheme", "(Lnet/minecraft/sounds/SoundEvent;)V", "getEntity", "()Lnet/minecraft/server/level/ServerPlayer;", "entity", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "uuid", "", "pokemonList", "<init>", "(Ljava/util/UUID;Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerBattleActor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerBattleActor.kt\ncom/cobblemon/mod/common/battles/actor/PlayerBattleActor\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,65:1\n1#2:66\n1#2:77\n1603#3,9:67\n1855#3:76\n1856#3:78\n1612#3:79\n*S KotlinDebug\n*F\n+ 1 PlayerBattleActor.kt\ncom/cobblemon/mod/common/battles/actor/PlayerBattleActor\n*L\n62#1:77\n62#1:67,9\n62#1:76\n62#1:78\n62#1:79\n*E\n"})
public final class PlayerBattleActor
extends BattleActor
implements EntityBackedBattleActor<ServerPlayer> {
    @Nullable
    private SoundEvent battleTheme;
    @NotNull
    private final ActorType type;

    public PlayerBattleActor(@NotNull UUID uuid2, @NotNull List<? extends BattlePokemon> pokemonList) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        super(uuid2, CollectionsKt.toMutableList((Collection)pokemonList));
        this.type = ActorType.PLAYER;
    }

    @Override
    @Nullable
    public ServerPlayer getEntity() {
        return PlayerExtensionsKt.getPlayer(this.getUuid());
    }

    @Nullable
    public final SoundEvent getBattleTheme() {
        return this.battleTheme;
    }

    public final void setBattleTheme(@Nullable SoundEvent value2) {
        if (!Intrinsics.areEqual((Object)this.battleTheme, (Object)value2) && this.getBattle().getStarted()) {
            this.sendUpdate(new BattleMusicPacket(value2, 0.0f, 0.0f, 6, null));
        }
        this.battleTheme = value2;
    }

    @Override
    @NotNull
    public MutableComponent getName() {
        MutableComponent mutableComponent;
        ServerPlayer serverPlayer = this.getEntity();
        if ((serverPlayer != null && (serverPlayer = serverPlayer.m_7755_()) != null ? serverPlayer.m_6881_() : (mutableComponent = null)) == null) {
            mutableComponent = TextKt.red("Offline Player");
        }
        return mutableComponent;
    }

    @Override
    @NotNull
    public MutableComponent nameOwned(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Object[] objectArray = new Object[]{this.getName(), name};
        MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("owned_pokemon", objectArray);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"owned_pokemon\", this.getName(), name)");
        return mutableComponent;
    }

    @Override
    @NotNull
    public ActorType getType() {
        return this.type;
    }

    @NotNull
    public Set<UUID> getPlayerUUIDs() {
        return SetsKt.setOf((Object)this.getUuid());
    }

    @Override
    public void awardExperience(@NotNull BattlePokemon battlePokemon, int experience) {
        block4: {
            BattleExperienceSource source;
            block5: {
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                if (this.getBattle().isPvP() && !Cobblemon.INSTANCE.getConfig().getAllowExperienceFromPvP()) {
                    return;
                }
                source = new BattleExperienceSource(this.getBattle(), CollectionsKt.toList((Iterable)battlePokemon.getFacedOpponents()));
                if (!Intrinsics.areEqual((Object)battlePokemon.getEffectedPokemon(), (Object)battlePokemon.getOriginalPokemon()) || experience <= 0) break block4;
                ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(this.getUuid());
                if (serverPlayer == null) break block5;
                ServerPlayer it = serverPlayer;
                boolean bl = false;
                AddExperienceResult addExperienceResult = battlePokemon.getEffectedPokemon().addExperienceWithPlayer(it, source, experience);
                if (addExperienceResult != null) break block4;
            }
            PlayerBattleActor $this$awardExperience_u24lambda_u241 = this;
            boolean bl = false;
            battlePokemon.getEffectedPokemon().addExperience(source, experience);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendUpdate(@NotNull NetworkPacket<?> packet) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable iterable = this.getPlayerUUIDs();
        CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
        boolean $i$f$mapNotNull = false;
        void var4_5 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            UUID it = (UUID)element$iv$iv;
            boolean bl2 = false;
            if (PlayerExtensionsKt.getPlayer(it) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        cobblemonNetwork.sendPacketToPlayers((List)destination$iv$iv, packet);
    }
}

