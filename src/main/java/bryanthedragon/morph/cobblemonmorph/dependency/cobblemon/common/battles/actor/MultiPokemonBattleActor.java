/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai.RandomBattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\n\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/battles/actor/MultiPokemonBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/AIBattleActor;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "name", "nameOwned", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemonList", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "artificialDecider", "Ljava/util/UUID;", "uuid", "<init>", "(Ljava/util/List;Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;Ljava/util/UUID;)V", "common"})
public final class MultiPokemonBattleActor
extends AIBattleActor {
    @NotNull
    private final ActorType type;

    public MultiPokemonBattleActor(@NotNull List<? extends BattlePokemon> pokemonList, @NotNull BattleAI artificialDecider, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        Intrinsics.checkNotNullParameter((Object)artificialDecider, (String)"artificialDecider");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        super(uuid2, pokemonList, artificialDecider);
        this.type = ActorType.WILD;
    }

    public /* synthetic */ MultiPokemonBattleActor(List list, BattleAI battleAI, UUID uUID, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            battleAI = new RandomBattleAI();
        }
        if ((n & 4) != 0) {
            UUID uUID2 = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"randomUUID()");
            uUID = uUID2;
        }
        this(list, battleAI, uUID);
    }

    @Override
    @NotNull
    public MutableComponent getName() {
        return TextKt.text("Wild Pok\u00e9mon");
    }

    @Override
    @NotNull
    public MutableComponent nameOwned(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        MutableComponent mutableComponent = Component.m_237113_((String)name);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(name)");
        return mutableComponent;
    }

    @Override
    @NotNull
    public ActorType getType() {
        return this.type;
    }
}

