/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001b\u0010\u0007\u001a\u00020\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/battles/model/actor/AIBattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "", "onChoiceRequested", "()V", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "sendUpdate", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "battleAI", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "getBattleAI", "()Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "Ljava/util/UUID;", "gameId", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemonList", "<init>", "(Ljava/util/UUID;Ljava/util/List;Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;)V", "common"})
public abstract class AIBattleActor
extends BattleActor {
    @NotNull
    private final BattleAI battleAI;

    public AIBattleActor(@NotNull UUID gameId, @NotNull List<? extends BattlePokemon> pokemonList, @NotNull BattleAI battleAI) {
        Intrinsics.checkNotNullParameter((Object)gameId, (String)"gameId");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        Intrinsics.checkNotNullParameter((Object)battleAI, (String)"battleAI");
        super(gameId, CollectionsKt.toMutableList((Collection)pokemonList));
        this.battleAI = battleAI;
    }

    @NotNull
    public final BattleAI getBattleAI() {
        return this.battleAI;
    }

    @Override
    public void sendUpdate(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        super.sendUpdate(packet);
        if (packet instanceof BattleMakeChoicePacket) {
            this.onChoiceRequested();
        }
    }

    public void onChoiceRequested() {
        try {
            ShowdownActionRequest showdownActionRequest = this.getRequest();
            Intrinsics.checkNotNull((Object)showdownActionRequest);
            this.setActionResponses(showdownActionRequest.iterate(this.getActivePokemon(), (Function3)new Function3<ActiveBattlePokemon, ShowdownMoveset, Boolean, ShowdownActionResponse>((Object)this.battleAI){

                @NotNull
                public final ShowdownActionResponse invoke(@NotNull ActiveBattlePokemon p0, @Nullable ShowdownMoveset p1, boolean p2) {
                    Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                    return ((BattleAI)this.receiver).choose(p0, p1, p2);
                }
            }));
        }
        catch (IllegalActionChoiceException exception) {
            Cobblemon.INSTANCE.getLOGGER().error("AI was unable to choose a move, we're going to need to pass!");
            exception.printStackTrace();
            ShowdownActionRequest showdownActionRequest = this.getRequest();
            Intrinsics.checkNotNull((Object)showdownActionRequest);
            this.setActionResponses(showdownActionRequest.iterate(this.getActivePokemon(), onChoiceRequested.2.INSTANCE));
        }
    }
}

