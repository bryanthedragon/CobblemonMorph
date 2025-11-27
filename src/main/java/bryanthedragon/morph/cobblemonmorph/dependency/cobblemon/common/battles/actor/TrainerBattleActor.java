/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\u00020\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/battles/actor/TrainerBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/AIBattleActor;", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "name", "nameOwned", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "trainerName", "Ljava/lang/String;", "getTrainerName", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "Ljava/util/UUID;", "uuid", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemonList", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "artificialDecider", "<init>", "(Ljava/lang/String;Ljava/util/UUID;Ljava/util/List;Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;)V", "common"})
public final class TrainerBattleActor
extends AIBattleActor {
    @NotNull
    private final String trainerName;
    @NotNull
    private final ActorType type;

    public TrainerBattleActor(@NotNull String trainerName, @NotNull UUID uuid2, @NotNull List<? extends BattlePokemon> pokemonList, @NotNull BattleAI artificialDecider) {
        Intrinsics.checkNotNullParameter((Object)trainerName, (String)"trainerName");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        Intrinsics.checkNotNullParameter((Object)artificialDecider, (String)"artificialDecider");
        super(uuid2, pokemonList, artificialDecider);
        this.trainerName = trainerName;
        this.type = ActorType.NPC;
    }

    @NotNull
    public final String getTrainerName() {
        return this.trainerName;
    }

    @Override
    public MutableComponent getName() {
        return MiscUtils.asTranslated(this.trainerName);
    }

    @Override
    @NotNull
    public MutableComponent nameOwned(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Object[] objectArray = new Object[2];
        Intrinsics.checkNotNullExpressionValue((Object)this.getName(), (String)"this.getName()");
        objectArray[1] = name;
        MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("owned_pokemon", objectArray);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"owned_pokemon\", this.getName(), name)");
        return mutableComponent;
    }

    @Override
    @NotNull
    public ActorType getType() {
        return this.type;
    }
}

