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

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public abstract class AIBattleActor extends BattleActor {
    @NotNull
    private final BattleAI battleAI;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AIBattleActor(@NotNull UUID gameId, @NotNull List<? extends BattlePokemon> pokemonList, @NotNull BattleAI battleAI) {
        super(gameId, CollectionsKt.toMutableList((Collection)pokemonList));
        Intrinsics.checkNotNullParameter((Object)gameId, (String)"gameId");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        Intrinsics.checkNotNullParameter((Object)battleAI, (String)"battleAI");
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
            this.setActionResponses(
                showdownActionRequest.iterate(
                    this.getActivePokemon(),
                    new Function3<ActiveBattlePokemon, ShowdownMoveset, Boolean, ShowdownActionResponse>() {
                        @Override
                        public ShowdownActionResponse invoke(ActiveBattlePokemon p0, ShowdownMoveset p1, Boolean p2) {
                            // Delegate to battleAI's choose method
                            return battleAI.choose(p0, p1, p2 != null ? p2 : false);
                        }
                    }
                )
            );
        }
        catch (IllegalActionChoiceException exception) {
            Cobblemon.INSTANCE.getLOGGER().error("AI was unable to choose a move, we're going to need to pass!");
            exception.printStackTrace();
            ShowdownActionRequest showdownActionRequest = this.getRequest();
            Intrinsics.checkNotNull((Object)showdownActionRequest);
            this.setActionResponses(showdownActionRequest.iterate(this.getActivePokemon(), new Function3<ActiveBattlePokemon, ShowdownMoveset, Boolean, ShowdownActionResponse>() {
                @Override
                public ShowdownActionResponse invoke(ActiveBattlePokemon p0, ShowdownMoveset p1, Boolean p2) {
                    // Provide a default fallback action, e.g., pass or struggle
                    return battleAI.choose(p0, p1, p2 != null ? p2 : false);
                }
            }));
        }
    }
}

