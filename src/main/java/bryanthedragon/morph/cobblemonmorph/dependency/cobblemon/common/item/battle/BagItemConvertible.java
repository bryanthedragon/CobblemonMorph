/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/item/battle/BagItemConvertible;", "", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/item/battle/BagItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "handleInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lnet/minecraft/world/item/ItemStack;)Z", "common"})
public interface BagItemConvertible {
    @Nullable
    public BagItem getBagItem(@NotNull ItemStack var1);

    public boolean handleInteraction(@NotNull ServerPlayer var1, @NotNull BattlePokemon var2, @NotNull ItemStack var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean handleInteraction(@NotNull BagItemConvertible $this, @NotNull ServerPlayer player, @NotNull BattlePokemon battlePokemon, @NotNull ItemStack stack) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            PokemonBattle battle2 = battlePokemon.getActor().getBattle();
            BagItem bagItem2 = $this.getBagItem(stack);
            if (bagItem2 == null) {
                return false;
            }
            BagItem bagItem3 = bagItem2;
            if (!battlePokemon.getActor().canFitForcedAction()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            if (!bagItem3.canUse(battle2, battlePokemon)) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.invalid\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            battlePokemon.getActor().forceChoose(new BagItemActionResponse(bagItem3, battlePokemon, null, 4, null));
            stack.m_41774_(1);
            SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
            PokemonEntity pokemonEntity = battlePokemon.getEntity();
            Intrinsics.checkNotNull((Object)pokemonEntity);
            ResourceLocation resourceLocation = pokemonEntity.getPokemon().getSpecies().getResourceIdentifier();
            ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)stack.m_41720_());
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
            simpleCriterionTrigger.trigger(player, new PokemonInteractContext(resourceLocation, resourceLocation2));
            return true;
        }
    }
}

