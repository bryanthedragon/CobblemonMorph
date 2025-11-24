/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.BaseCobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b(\u0010\rJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ'\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nJ\u000f\u0010\u000e\u001a\u00020\bH\u0010\u00a2\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J#\u0010\u0018\u001a\u00020\b2\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0015\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u001dR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u001e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 R \u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00110!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R(\u0010%\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00150$8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00110\u001e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010 \u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/pokemon/helditem/CobblemonHeldItemManager;", "Lcom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "battleMessage", "", "handleEndInstruction", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "handleStartInstruction", "load$common", "()V", "load", "Lnet/minecraft/world/item/Item;", "item", "", "showdownId", "registerRemap", "(Lnet/minecraft/world/item/Item;Ljava/lang/String;)V", "Ljava/util/function/Function;", "Lnet/minecraft/world/item/ItemStack;", "remap", "registerStackRemap", "(Ljava/util/function/Function;)V", "", "shouldConsumeItem", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/lang/String;)Z", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/lang/String;", "", "giveItemEffect", "Ljava/util/Set;", "", "remaps", "Ljava/util/Map;", "", "stackRemaps", "Ljava/util/List;", "takeItemEffect", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonHeldItemManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/CobblemonHeldItemManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,178:1\n1#2:179\n*E\n"})
public final class CobblemonHeldItemManager
extends BaseCobblemonHeldItemManager {
    @NotNull
    public static final CobblemonHeldItemManager INSTANCE = new CobblemonHeldItemManager();
    @NotNull
    private static final Set<String> giveItemEffect;
    @NotNull
    private static final Set<String> takeItemEffect;
    @NotNull
    private static final Map<Item, String> remaps;
    @NotNull
    private static final List<Function<ItemStack, String>> stackRemaps;

    private CobblemonHeldItemManager() {
    }

    @Override
    public void load$common() {
        super.load$common();
        Cobblemon.INSTANCE.getLOGGER().info("Imported {} held item IDs from showdown", (Object)this.loadedItemCount());
    }

    @Override
    @Nullable
    public String showdownId(@NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        ItemStack itemStack = pokemon.getEffectedPokemon().heldItemNoCopy$common();
        if (remaps.containsKey(itemStack.m_41720_())) {
            return remaps.get(itemStack.m_41720_());
        }
        for (Function<ItemStack, String> remap : stackRemaps) {
            String id = remap.apply(itemStack);
            if (id == null) continue;
            return id;
        }
        String original = super.showdownId(pokemon);
        if (original == null && pokemon.getEffectedPokemon().heldItemNoCopy$common().m_41619_()) {
            return "";
        }
        return original;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void handleStartInstruction(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle, @NotNull BattleMessage battleMessage) {
        block25: {
            block24: {
                block23: {
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    Intrinsics.checkNotNullParameter((Object)battle, (String)"battle");
                    Intrinsics.checkNotNullParameter((Object)battleMessage, (String)"battleMessage");
                    v0 = battleMessage.effectAt(1);
                    if (v0 == null || (v0 = v0.getId()) == null) {
                        return;
                    }
                    itemID = v0;
                    consumeHeldItems = this.shouldConsumeItem(pokemon, battle, (String)itemID);
                    if (battleMessage.hasOptionalArgument("silent")) {
                        if (consumeHeldItems) {
                            this.take(pokemon, (String)itemID);
                        }
                        return;
                    }
                    effect = BattleMessage.effect$default(battleMessage, null, 1, null);
                    battlerName = pokemon.getName();
                    if (effect == null) {
                        var9_8 = new Object[]{battlerName};
                        v1 = LocalizationUtilsKt.battleLang("item." + (String)itemID, var9_8);
                        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"battleLang(\"item.$itemID\", battlerName)");
                        battle.broadcastChatMessage((Component)v1);
                        return;
                    }
                    v2 = BattleMessage.battlePokemonFromOptional$default(battleMessage, battle, null, 2, null);
                    sourceName = v2 != null && (v2 = v2.getName()) != null ? (Component)v2 : Component.m_130674_((String)"UNKNOWN");
                    itemName = this.nameOf((String)itemID);
                    var12_12 = effectId = effect.getId();
                    switch (var12_12.hashCode()) {
                        case 110330838: {
                            if (!var12_12.equals("thief")) {
                                ** break;
                            }
                            ** GOTO lbl54
                        }
                        case -346775423: {
                            if (!var12_12.equals("switcheroo")) {
                                ** break;
                            }
                            break block23;
                        }
                        case 1082880659: {
                            if (var12_12.equals("recycle")) break;
                            ** break;
                        }
                        case -988476804: {
                            if (var12_12.equals("pickup")) break;
                            ** break;
                        }
                        case 110628691: {
                            if (!var12_12.equals("trick")) {
                                ** break;
                            }
                            break block23;
                        }
                        case -69865079: {
                            if (!var12_12.equals("magician")) {
                                ** break;
                            }
                            ** GOTO lbl54
                        }
                        case -1108625161: {
                            if (!var12_12.equals("pickpocket")) {
                                ** break;
                            }
                            ** GOTO lbl54
                        }
                        case 94852025: {
                            if (!var12_12.equals("covet")) ** break;
lbl54:
                            // 4 sources

                            var13_13 = new Object[3];
                            var13_13[0] = battlerName;
                            var13_13[1] = itemName;
                            Intrinsics.checkNotNullExpressionValue((Object)sourceName, (String)"sourceName");
                            var13_13[2] = sourceName;
                            v3 = LocalizationUtilsKt.battleLang("item.thief", var13_13);
                            break block24;
                        }
                    }
                    var13_13 = new Object[]{battlerName, itemName};
                    v3 = LocalizationUtilsKt.battleLang("item.recycle", var13_13);
                    break block24;
                }
                var13_13 = new Object[]{battlerName, itemName};
                v3 = LocalizationUtilsKt.battleLang("item.trick", var13_13);
                break block24;
lbl68:
                // 9 sources

                v4 = "item." + effectId;
                var13_13 = new Object[3];
                var13_13[0] = battlerName;
                var13_13[1] = itemName;
                Intrinsics.checkNotNullExpressionValue((Object)sourceName, (String)"sourceName");
                var13_13[2] = sourceName;
                v3 = LocalizationUtilsKt.battleLang(v4, var13_13);
            }
            text = v3;
            Intrinsics.checkNotNullExpressionValue((Object)text, (String)"text");
            battle.broadcastChatMessage((Component)text);
            if (CobblemonHeldItemManager.takeItemEffect.contains(effectId) && CobblemonHeldItemManager.giveItemEffect.contains(effectId) && !consumeHeldItems) {
                return;
            }
            if (battle.isPvP() && !consumeHeldItems) {
                return;
            }
            if (CobblemonHeldItemManager.giveItemEffect.contains(effectId) && (pokemon.getActor() instanceof PlayerBattleActor || consumeHeldItems)) {
                this.give(pokemon, (String)itemID);
            }
            if (!CobblemonHeldItemManager.takeItemEffect.contains(effectId) || pokemon.getActor() instanceof PlayerBattleActor && !consumeHeldItems) break block25;
            v5 = BattleMessage.actorAndActivePokemonFromOptional$default(battleMessage, battle, null, 2, null);
            if (v5 != null && (v5 = (ActiveBattlePokemon)v5.getSecond()) != null && (v5 = v5.getBattlePokemon()) != null) {
                it = v5;
                $i$a$-let-CobblemonHeldItemManager$handleStartInstruction$1 = false;
                this.take((BattlePokemon)it, (String)itemID);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void handleEndInstruction(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle, @NotNull BattleMessage battleMessage) {
        block20: {
            block19: {
                Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                Intrinsics.checkNotNullParameter((Object)battle, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)battleMessage, (String)"battleMessage");
                v0 = battleMessage.effectAt(1);
                if (v0 == null || (v0 = v0.getId()) == null) {
                    return;
                }
                itemID = v0;
                consumeHeldItems = this.shouldConsumeItem(pokemon, battle, (String)itemID);
                if (battleMessage.hasOptionalArgument("silent")) {
                    if (consumeHeldItems) {
                        this.take(pokemon, (String)itemID);
                    }
                    return;
                }
                battlerName = pokemon.getName();
                itemName = this.nameOf((String)itemID);
                if (battleMessage.hasOptionalArgument("eat")) {
                    var9_8 = new Object[]{battlerName, itemName};
                    v1 = LocalizationUtilsKt.battleLang("item.eat", var9_8);
                    Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"battleLang(\"item.eat\", battlerName, itemName)");
                    battle.broadcastChatMessage((Component)v1);
                    if (consumeHeldItems) {
                        this.take(pokemon, (String)itemID);
                    }
                    return;
                }
                v2 = BattleMessage.battlePokemonFromOptional$default(battleMessage, battle, null, 2, null);
                sourceName = v2 != null && (v2 = v2.getName()) != null ? (Component)v2 : Component.m_130674_((String)"UNKNOWN");
                effect = BattleMessage.effect$default(battleMessage, null, 1, null);
                v3 = effect;
                if ((v3 != null ? v3.getId() : null) == null) break block19;
                v4 = "enditem." + effect.getId();
                var11_11 = new Object[3];
                var11_11[0] = battlerName;
                var11_11[1] = itemName;
                Intrinsics.checkNotNullExpressionValue((Object)sourceName, (String)"sourceName");
                var11_11[2] = sourceName;
                v5 = LocalizationUtilsKt.battleLang(v4, var11_11);
                break block20;
            }
            var11_11 = itemID;
            switch (var11_11.hashCode()) {
                case 1473685510: {
                    if (!var11_11.equals("psychicseed")) {
                        break;
                    }
                    ** GOTO lbl62
                }
                case 1287412621: {
                    if (!var11_11.equals("mistyseed")) {
                        break;
                    }
                    ** GOTO lbl62
                }
                case 987810420: {
                    if (!var11_11.equals("grassyseed")) {
                        break;
                    }
                    ** GOTO lbl62
                }
                case 1548556824: {
                    if (!var11_11.equals("boosterenergy")) {
                        break;
                    }
                    ** GOTO lbl62
                }
                case 880781690: {
                    if (!var11_11.equals("roomservice")) {
                        break;
                    }
                    ** GOTO lbl62
                }
                case -362369778: {
                    if (!var11_11.equals("electricseed")) break;
lbl62:
                    // 6 sources

                    var12_12 = new Object[]{battlerName, itemName};
                    v5 = LocalizationUtilsKt.battleLang("enditem.generic", var12_12);
                    break block20;
                }
            }
            var12_12 = new Object[]{battlerName};
            v5 = text = LocalizationUtilsKt.battleLang("enditem." + (String)itemID, var12_12);
        }
        if (consumeHeldItems) {
            this.take(pokemon, (String)itemID);
        }
        Intrinsics.checkNotNullExpressionValue((Object)text, (String)"text");
        battle.broadcastChatMessage((Component)text);
    }

    @Override
    public boolean shouldConsumeItem(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle2, @NotNull String showdownId) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        TagKey<Item> tag = battle2.isPvP() ? CobblemonItemTags.CONSUMED_IN_PVP_BATTLE : (battle2.isPvN() ? CobblemonItemTags.CONSUMED_IN_NPC_BATTLE : CobblemonItemTags.CONSUMED_IN_WILD_BATTLE);
        return pokemon.getEffectedPokemon().heldItem().m_204117_(tag);
    }

    public final void registerRemap(@NotNull Item item, @NotNull String showdownId) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        remaps.put(item, showdownId);
    }

    public final void registerStackRemap(@NotNull Function<ItemStack, String> remap) {
        Intrinsics.checkNotNullParameter(remap, (String)"remap");
        stackRemaps.add(remap);
    }

    static {
        Object[] objectArray = new String[]{"pickup", "recycle", "magician", "pickpocket", "thief", "covet", "harvest", "bestow", "switcheroo", "trick"};
        giveItemEffect = SetsKt.setOf((Object[])objectArray);
        objectArray = new String[]{"magician", "pickpocket", "covet", "bestow"};
        takeItemEffect = SetsKt.setOf((Object[])objectArray);
        remaps = new LinkedHashMap();
        stackRemaps = new ArrayList();
    }
}

