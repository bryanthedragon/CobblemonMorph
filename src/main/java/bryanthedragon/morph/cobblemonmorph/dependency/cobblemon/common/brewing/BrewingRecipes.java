/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.jvm.functions.Function0
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.BrewingRecipes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonIngredient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonItemIngredient;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.functions.Function0;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J9\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\b\u0010\tR3\u0010\u000f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00060\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/brewing/BrewingRecipes;", "", "Lnet/minecraft/world/item/Item;", "input", "ingredient", "output", "Lkotlin/Triple;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;", "convert", "(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/Item;)Lkotlin/Triple;", "", "recipes$delegate", "Lkotlin/Lazy;", "getRecipes", "()Ljava/util/List;", "recipes", "<init>", "()V", "common"})
public final class BrewingRecipes {
    @NotNull
    public static final BrewingRecipes INSTANCE = new BrewingRecipes();
    @NotNull
    private static final Lazy recipes$delegate = LazyKt.lazy((Function0)recipes.2.INSTANCE);

    private BrewingRecipes() {
    }

    @NotNull
    public final List<Triple<CobblemonIngredient, CobblemonIngredient, Item>> getRecipes() {
        Lazy lazy = recipes$delegate;
        return (List)lazy.getValue();
    }

    private final Triple<CobblemonIngredient, CobblemonIngredient, Item> convert(Item input, Item ingredient, Item output) {
        return new Triple((Object)new CobblemonItemIngredient(input), (Object)new CobblemonItemIngredient(ingredient), (Object)output);
    }

    public static final /* synthetic */ Triple access$convert(BrewingRecipes $this, Item input, Item ingredient, Item output) {
        return $this.convert(input, ingredient, output);
    }
}

