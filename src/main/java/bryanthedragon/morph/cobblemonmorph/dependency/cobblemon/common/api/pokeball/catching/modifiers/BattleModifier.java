/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001BZ\u0012Q\u0010\u001d\u001aM\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0013\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u000e0\u001a\u00a2\u0006\u0004\b\u001f\u0010 J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J5\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0017J\u001f\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R_\u0010\u001d\u001aM\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0013\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u000e0\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isGuaranteed", "()Z", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "playerPokemon", "(FLnet/minecraft/server/level/ServerPlayer;Ljava/lang/Iterable;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "calculator", "Lkotlin/jvm/functions/Function3;", "<init>", "(Lkotlin/jvm/functions/Function3;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleModifier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleModifier.kt\ncom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,51:1\n288#2,2:52\n*S KotlinDebug\n*F\n+ 1 BattleModifier.kt\ncom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier\n*L\n37#1:52,2\n*E\n"})
public class BattleModifier
implements CatchRateModifier {
    @NotNull
    private final Function3<ServerPlayer, Iterable<ActiveBattlePokemon>, Pokemon, Float> calculator;

    public BattleModifier(@NotNull Function3<? super ServerPlayer, ? super Iterable<ActiveBattlePokemon>, ? super Pokemon, Float> calculator) {
        Intrinsics.checkNotNullParameter(calculator, (String)"calculator");
        this.calculator = calculator;
    }

    @Override
    public boolean isGuaranteed() {
        return false;
    }

    @Override
    public float value(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        List<ActiveBattlePokemon> list;
        ServerPlayer player;
        block7: {
            block6: {
                BattleActor battleActor;
                block5: {
                    Iterable<BattleActor> iterable;
                    Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    ServerPlayer serverPlayer = thrower instanceof ServerPlayer ? (ServerPlayer)thrower : null;
                    if (serverPlayer == null) {
                        return 1.0f;
                    }
                    player = serverPlayer;
                    PokemonBattle pokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);
                    if (pokemonBattle == null || (iterable = pokemonBattle.getActors()) == null) break block6;
                    Iterable<BattleActor> $this$firstOrNull$iv = iterable;
                    boolean $i$f$firstOrNull = false;
                    Iterator<BattleActor> iterator = $this$firstOrNull$iv.iterator();
                    while (iterator.hasNext()) {
                        BattleActor element$iv;
                        BattleActor actor = element$iv = iterator.next();
                        boolean bl = false;
                        if (!(actor instanceof PlayerBattleActor && Intrinsics.areEqual((Object)actor.getUuid(), (Object)player.m_20148_()))) continue;
                        battleActor = element$iv;
                        break block5;
                    }
                    battleActor = null;
                }
                BattleActor battleActor2 = battleActor;
                if (battleActor2 != null && (list = battleActor2.getActivePokemon()) != null) break block7;
            }
            return 1.0f;
        }
        List<ActiveBattlePokemon> team = list;
        return ((Number)this.calculator.invoke((Object)player, team, (Object)pokemon)).floatValue();
    }

    @Override
    @NotNull
    public CatchRateModifier.Behavior behavior(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return CatchRateModifier.Behavior.MULTIPLY;
    }

    @Override
    public boolean isValid(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return true;
    }

    @Override
    public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.behavior(thrower, pokemon).getMutator().invoke((Object)Float.valueOf(currentCatchRate), (Object)Float.valueOf(this.value(thrower, pokemon)))).floatValue();
    }

    public float modifyCatchRate(float currentCatchRate, @NotNull ServerPlayer player, @NotNull Iterable<ActiveBattlePokemon> playerPokemon, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(playerPokemon, (String)"playerPokemon");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return ((Number)this.calculator.invoke((Object)player, playerPokemon, (Object)pokemon)).floatValue();
    }
}

