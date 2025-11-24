/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.GameRules$Category
 *  net.minecraft.world.level.GameRules$Key
 *  net.minecraft.world.level.GameRules$Type
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonImplementation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.BooleanRuleInvoker;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0005R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0005\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/gamerules/CobblemonGameRules;", "", "Lnet/minecraft/world/GameRules$Key;", "Lnet/minecraft/world/GameRules$BooleanRule;", "DO_POKEMON_LOOT", "Lnet/minecraft/world/level/GameRules$Key;", "DO_POKEMON_SPAWNING", "SHINY_STARTERS", "<init>", "()V", "common"})
public final class CobblemonGameRules {
    @NotNull
    public static final CobblemonGameRules INSTANCE = new CobblemonGameRules();
    @JvmField
    @NotNull
    public static final GameRules.Key<GameRules.BooleanValue> DO_POKEMON_SPAWNING;
    @JvmField
    @NotNull
    public static final GameRules.Key<GameRules.BooleanValue> DO_POKEMON_LOOT;
    @JvmField
    @NotNull
    public static final GameRules.Key<GameRules.BooleanValue> SHINY_STARTERS;

    private CobblemonGameRules() {
    }

    static {
        CobblemonImplementation cobblemonImplementation = Cobblemon.INSTANCE.getImplementation();
        GameRules.Type<GameRules.BooleanValue> type = BooleanRuleInvoker.cobblemon$create(true);
        Intrinsics.checkNotNullExpressionValue(type, (String)"`cobblemon$create`(true)");
        DO_POKEMON_SPAWNING = cobblemonImplementation.registerGameRule("doPokemonSpawning", GameRules.Category.SPAWNING, type);
        CobblemonImplementation cobblemonImplementation2 = Cobblemon.INSTANCE.getImplementation();
        GameRules.Type<GameRules.BooleanValue> type2 = BooleanRuleInvoker.cobblemon$create(true);
        Intrinsics.checkNotNullExpressionValue(type2, (String)"`cobblemon$create`(true)");
        DO_POKEMON_LOOT = cobblemonImplementation2.registerGameRule("doPokemonLoot", GameRules.Category.DROPS, type2);
        CobblemonImplementation cobblemonImplementation3 = Cobblemon.INSTANCE.getImplementation();
        GameRules.Type<GameRules.BooleanValue> type3 = BooleanRuleInvoker.cobblemon$create(false);
        Intrinsics.checkNotNullExpressionValue(type3, (String)"`cobblemon$create`(false)");
        SHINY_STARTERS = cobblemonImplementation3.registerGameRule("doShinyStarters", GameRules.Category.MISC, type3);
    }
}

