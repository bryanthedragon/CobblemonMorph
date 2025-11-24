/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.DefaultActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.PassActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.SwitchActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ)\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/battles/ai/RandomBattleAI;", "Lcom/cobblemon/mod/common/api/battles/model/ai/BattleAI;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "moveset", "", "forceSwitch", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "choose", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nRandomBattleAI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RandomBattleAI.kt\ncom/cobblemon/mod/common/battles/ai/RandomBattleAI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,57:1\n766#2:58\n857#2,2:59\n766#2:61\n857#2,2:62\n766#2:64\n857#2,2:65\n766#2:67\n857#2,2:68\n*S KotlinDebug\n*F\n+ 1 RandomBattleAI.kt\ncom/cobblemon/mod/common/battles/ai/RandomBattleAI\n*L\n33#1:58\n33#1:59,2\n43#1:61\n43#1:62,2\n44#1:64\n44#1:65,2\n53#1:67\n53#1:68,2\n*E\n"})
public final class RandomBattleAI
implements BattleAI {
    /*
     * Unable to fully structure code
     */
    @Override
    @NotNull
    public ShowdownActionResponse choose(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset moveset, boolean forceSwitch) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        if (forceSwitch || activeBattlePokemon.isGone()) {
            $this$filter$iv = activeBattlePokemon.getActor().getPokemonList();
            $i$f$filter = false;
            var8_8 = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                it = (BattlePokemon)element$iv$iv;
                $i$a$-filter-RandomBattleAI$choose$switchTo$1 = false;
                if (!it.canBeSentOut()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            v0 = (BattlePokemon)CollectionsKt.randomOrNull((Collection)((List)destination$iv$iv), (Random)((Random)Random.Default));
            if (v0 == null) {
                return new DefaultActionResponse();
            }
            switchTo = v0;
            switchTo.setWillBeSwitchedIn(true);
            return new SwitchActionResponse(switchTo.getUuid());
        }
        if (moveset == null) {
            return PassActionResponse.INSTANCE;
        }
        $this$filter$iv = moveset.getMoves();
        $i$f$filter = false;
        $this$filterTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$filterTo = false;
        for (T element$iv$iv : $this$filterTo$iv$iv) {
            it = (InBattleMove)element$iv$iv;
            $i$a$-filter-RandomBattleAI$choose$move$1 = false;
            if (!it.canBeUsed()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        $i$f$filter = false;
        $this$filterTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$filterTo = false;
        for (T element$iv$iv : $this$filterTo$iv$iv) {
            it = (InBattleMove)element$iv$iv;
            $i$a$-filter-RandomBattleAI$choose$move$2 = false;
            if (it.mustBeUsed()) ** GOTO lbl-1000
            v1 = (List)it.getTarget().getTargetList().invoke((Object)activeBattlePokemon);
            if (!(v1 != null ? v1.isEmpty() : false)) lbl-1000:
            // 2 sources

            {
                v2 = true;
            } else {
                v2 = false;
            }
            if (!v2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        v3 = (InBattleMove)CollectionsKt.randomOrNull((Collection)((List)destination$iv$iv), (Random)((Random)Random.Default));
        if (v3 == null) {
            return new MoveActionResponse("struggle", null, null, 6, null);
        }
        move = v3;
        v4 = target = move.mustBeUsed() != false ? null : (List)move.getTarget().getTargetList().invoke((Object)activeBattlePokemon);
        if (target == null) {
            v5 = new MoveActionResponse(move.getId(), null, null, 6, null);
        } else {
            $this$filter$iv = target;
            $i$f$filter = false;
            $i$f$filterTo = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                it = (Targetable)element$iv$iv;
                $i$a$-filter-RandomBattleAI$choose$chosenTarget$1 = false;
                if (!(it.isAllied(activeBattlePokemon) == false)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            v6 = (Targetable)CollectionsKt.randomOrNull((Collection)((List)destination$iv$iv), (Random)((Random)Random.Default));
            if (v6 == null) {
                v6 = (Targetable)CollectionsKt.random((Collection)target, (Random)((Random)Random.Default));
            }
            chosenTarget = v6;
            v7 = move.getId();
            Intrinsics.checkNotNull((Object)chosenTarget, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon");
            v5 = new MoveActionResponse(v7, ((ActiveBattlePokemon)chosenTarget).getPNX(), null, 4, null);
        }
        return v5;
    }
}

