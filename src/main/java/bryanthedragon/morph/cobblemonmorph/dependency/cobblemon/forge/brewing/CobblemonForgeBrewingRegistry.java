/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Triple
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.alchemy.Potion
 *  net.minecraft.world.item.alchemy.Potions
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.brewing.BrewingRecipe
 *  net.minecraftforge.common.brewing.BrewingRecipeRegistry
 *  net.minecraftforge.common.brewing.IBrewingRecipe
 *  net.minecraftforge.common.crafting.AbstractIngredient
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.common.crafting.IIngredientSerializer
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.brewing;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.BrewingRecipes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonIngredient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonItemIngredient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonPotionIngredient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c0\u0002\u0018\u00002\u00020\u0001:\u0002\r\u000eB\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry;", "", "", "register", "()V", "registerIngredientTypes", "registerRecipes", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;", "ingredient", "Lnet/minecraft/world/item/crafting/Ingredient;", "wrapIngredient", "(Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient;)Lnet/minecraft/world/item/crafting/Ingredient;", "<init>", "ForgePotionIngredient", "ForgePotionIngredientSerializer", "forge"})
@SourceDebugExtension(value={"SMAP\nCobblemonForgeBrewingRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForgeBrewingRegistry.kt\ncom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,90:1\n1855#2,2:91\n*S KotlinDebug\n*F\n+ 1 CobblemonForgeBrewingRegistry.kt\ncom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry\n*L\n42#1:91,2\n*E\n"})
public final class CobblemonForgeBrewingRegistry {
    @NotNull
    public static final CobblemonForgeBrewingRegistry INSTANCE = new CobblemonForgeBrewingRegistry();

    private CobblemonForgeBrewingRegistry() {
    }

    public final void register() {
        this.registerIngredientTypes();
        this.registerRecipes();
    }

    private final void registerIngredientTypes() {
        CraftingHelper.register((ResourceLocation)MiscUtilsKt.cobblemonResource("potion"), (IIngredientSerializer)ForgePotionIngredientSerializer.INSTANCE);
    }

    private final void registerRecipes() {
        Iterable $this$forEach$iv = BrewingRecipes.INSTANCE.getRecipes();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Triple triple = (Triple)element$iv;
            boolean bl = false;
            CobblemonIngredient input = (CobblemonIngredient)triple.component1();
            CobblemonIngredient ingredient = (CobblemonIngredient)triple.component2();
            Item output = (Item)triple.component3();
            BrewingRecipeRegistry.addRecipe((IBrewingRecipe)((IBrewingRecipe)new BrewingRecipe(this.wrapIngredient(input), this.wrapIngredient(ingredient), output.m_7968_())));
        }
    }

    private final Ingredient wrapIngredient(CobblemonIngredient ingredient) {
        Ingredient ingredient2;
        CobblemonIngredient cobblemonIngredient = ingredient;
        if (cobblemonIngredient instanceof CobblemonItemIngredient) {
            ItemLike[] itemLikeArray = new ItemLike[]{((CobblemonItemIngredient)ingredient).getItem()};
            Ingredient ingredient3 = Ingredient.m_43929_((ItemLike[])itemLikeArray);
            ingredient2 = ingredient3;
            Intrinsics.checkNotNullExpressionValue((Object)ingredient3, (String)"ofItems(ingredient.item)");
        } else if (cobblemonIngredient instanceof CobblemonPotionIngredient) {
            ingredient2 = (Ingredient)new ForgePotionIngredient((CobblemonPotionIngredient)ingredient);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return ingredient2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u000e\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient;", "Lnet/minecraftforge/common/crafting/AbstractIngredient;", "", "Lnet/minecraft/world/item/ItemStack;", "getMatchingStacks", "()[Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraftforge/common/crafting/IIngredientSerializer;", "Lnet/minecraft/world/item/crafting/Ingredient;", "getSerializer", "()Lnet/minecraftforge/common/crafting/IIngredientSerializer;", "", "isSimple", "()Z", "arg", "test", "(Lnet/minecraft/world/item/ItemStack;)Z", "Lcom/google/gson/JsonElement;", "toJson", "()Lcom/google/gson/JsonElement;", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;", "base", "Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;", "getBase", "()Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;", "<init>", "(Lcom/cobblemon/mod/common/brewing/ingredient/CobblemonPotionIngredient;)V", "forge"})
    @SourceDebugExtension(value={"SMAP\nCobblemonForgeBrewingRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForgeBrewingRegistry.kt\ncom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,90:1\n37#2,2:91\n*S KotlinDebug\n*F\n+ 1 CobblemonForgeBrewingRegistry.kt\ncom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient\n*L\n66#1:91,2\n*E\n"})
    private static final class ForgePotionIngredient
    extends AbstractIngredient {
        @NotNull
        private final CobblemonPotionIngredient base;

        public ForgePotionIngredient(@NotNull CobblemonPotionIngredient base) {
            Intrinsics.checkNotNullParameter((Object)base, (String)"base");
            this.base = base;
        }

        @NotNull
        public final CobblemonPotionIngredient getBase() {
            return this.base;
        }

        @NotNull
        public JsonElement m_43942_() {
            JsonObject json = new JsonObject();
            ResourceLocation resourceLocation = ForgeRegistries.POTIONS.getKey((Object)this.base.getPotion());
            Intrinsics.checkNotNull((Object)resourceLocation);
            json.addProperty("potion", resourceLocation.toString());
            return (JsonElement)json;
        }

        public boolean isSimple() {
            return false;
        }

        @NotNull
        public IIngredientSerializer<? extends Ingredient> getSerializer() {
            return ForgePotionIngredientSerializer.INSTANCE;
        }

        public boolean test(@Nullable ItemStack arg) {
            return arg != null && this.base.matches(arg);
        }

        @NotNull
        public ItemStack[] m_43908_() {
            Collection $this$toTypedArray$iv = this.base.matchingStacks();
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            return thisCollection$iv.toArray(new ItemStack[0]);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c2\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredientSerializer;", "Lnet/minecraftforge/common/crafting/IIngredientSerializer;", "Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient;", "Lcom/google/gson/JsonObject;", "jsonObject", "parse", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient;", "Lnet/minecraft/network/FriendlyByteBuf;", "buf", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient;", "ingredient", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/forge/brewing/CobblemonForgeBrewingRegistry$ForgePotionIngredient;)V", "<init>", "()V", "forge"})
    private static final class ForgePotionIngredientSerializer
    implements IIngredientSerializer<ForgePotionIngredient> {
        @NotNull
        public static final ForgePotionIngredientSerializer INSTANCE = new ForgePotionIngredientSerializer();

        private ForgePotionIngredientSerializer() {
        }

        @NotNull
        public ForgePotionIngredient parse(@NotNull FriendlyByteBuf buf) {
            Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
            ResourceLocation id = buf.m_130281_();
            Potion potion = (Potion)ForgeRegistries.POTIONS.getValue(id);
            if (potion == null) {
                potion = Potions.f_43598_;
            }
            Potion potion2 = potion;
            Intrinsics.checkNotNullExpressionValue((Object)potion2, (String)"potion");
            return new ForgePotionIngredient(new CobblemonPotionIngredient(potion2));
        }

        @NotNull
        public ForgePotionIngredient parse(@NotNull JsonObject jsonObject) {
            Intrinsics.checkNotNullParameter((Object)jsonObject, (String)"jsonObject");
            ResourceLocation id = new ResourceLocation(jsonObject.getAsString());
            Potion potion = (Potion)ForgeRegistries.POTIONS.getValue(id);
            if (potion == null) {
                potion = Potions.f_43598_;
            }
            Potion potion2 = potion;
            Intrinsics.checkNotNullExpressionValue((Object)potion2, (String)"potion");
            return new ForgePotionIngredient(new CobblemonPotionIngredient(potion2));
        }

        public void write(@NotNull FriendlyByteBuf buf, @NotNull ForgePotionIngredient ingredient) {
            Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
            Intrinsics.checkNotNullParameter((Object)((Object)ingredient), (String)"ingredient");
            ResourceLocation resourceLocation = ForgeRegistries.POTIONS.getKey((Object)ingredient.getBase().getPotion());
            Intrinsics.checkNotNull((Object)resourceLocation);
            buf.m_130085_(resourceLocation);
        }
    }
}

