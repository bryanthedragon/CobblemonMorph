/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.drawable.IDrawableStatic
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry.BerryMutationRecipe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001d2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001dB\u000f\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0006\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0005J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/integration/jei/berry/BerryRecipeCategory;", "Lmezz/jei/api/recipe/category/IRecipeCategory;", "Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationRecipe;", "Lmezz/jei/api/gui/drawable/IDrawable;", "getBackground", "()Lmezz/jei/api/gui/drawable/IDrawable;", "getIcon", "Lmezz/jei/api/recipe/RecipeType;", "getRecipeType", "()Lmezz/jei/api/recipe/RecipeType;", "Lnet/minecraft/network/chat/Component;", "getTitle", "()Lnet/minecraft/network/chat/Component;", "Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;", "p0", "p1", "Lmezz/jei/api/recipe/IFocusGroup;", "p2", "", "setRecipe", "(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V", "background", "Lmezz/jei/api/gui/drawable/IDrawable;", "icon", "Lmezz/jei/api/registration/IRecipeCategoryRegistration;", "registration", "Lmezz/jei/api/registration/IRecipeCategoryRegistration;", "<init>", "(Lmezz/jei/api/registration/IRecipeCategoryRegistration;)V", "Companion", "common"})
public final class BerryRecipeCategory
implements IRecipeCategory<BerryMutationRecipe> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final IRecipeCategoryRegistration registration;
    @NotNull
    private IDrawable background;
    @NotNull
    private IDrawable icon;
    @NotNull
    private static final RecipeType<BerryMutationRecipe> RECIPE_TYPE;
    @NotNull
    private static final ResourceLocation GUI_TEXTURE_ID;
    public static final int WIDTH = 124;
    public static final int HEIGHT = 17;

    public BerryRecipeCategory(@NotNull IRecipeCategoryRegistration registration) {
        Intrinsics.checkNotNullParameter((Object)registration, (String)"registration");
        this.registration = registration;
        IGuiHelper guiHelper = this.registration.getJeiHelpers().getGuiHelper();
        IDrawableStatic iDrawableStatic = guiHelper.createDrawable(GUI_TEXTURE_ID, 0, 0, 124, 17);
        Intrinsics.checkNotNullExpressionValue((Object)iDrawableStatic, (String)"guiHelper.createDrawable\u2026_ID, 0, 0, WIDTH, HEIGHT)");
        this.background = (IDrawable)iDrawableStatic;
        IDrawable iDrawable = guiHelper.createDrawableItemStack(CobblemonItems.SURPRISE_MULCH.m_7968_());
        Intrinsics.checkNotNullExpressionValue((Object)iDrawable, (String)"guiHelper.createDrawable\u2026PRISE_MULCH.defaultStack)");
        this.icon = iDrawable;
    }

    @NotNull
    public RecipeType<BerryMutationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @NotNull
    public Component getTitle() {
        Component component = Component.m_130674_((String)"Berry Mutation");
        Intrinsics.checkNotNullExpressionValue((Object)component, (String)"of(\"Berry Mutation\")");
        return component;
    }

    @NotNull
    public IDrawable getBackground() {
        return this.background;
    }

    @NotNull
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(@NotNull IRecipeLayoutBuilder p0, @NotNull BerryMutationRecipe p1, @NotNull IFocusGroup p2) {
        Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
        Intrinsics.checkNotNullParameter((Object)p1, (String)"p1");
        Intrinsics.checkNotNullParameter((Object)p2, (String)"p2");
        ItemStack[] itemStackArray = new ItemStack[]{p1.getBerryOne().m_7968_()};
        p0.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.m_43927_((ItemStack[])itemStackArray));
        itemStackArray = new ItemStack[]{p1.getBerryTwo().m_7968_()};
        p0.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(Ingredient.m_43927_((ItemStack[])itemStackArray));
        itemStackArray = new ItemStack[]{p1.getBerryResult().m_7968_()};
        p0.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addIngredients(Ingredient.m_43927_((ItemStack[])itemStackArray));
    }

    static {
        RecipeType recipeType = RecipeType.create((String)"cobblemon", (String)"berry_recipe", BerryMutationRecipe.class);
        Intrinsics.checkNotNull((Object)recipeType);
        RECIPE_TYPE = recipeType;
        GUI_TEXTURE_ID = MiscUtilsKt.cobblemonResource("textures/gui/jei/berry_mutation.png");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR%\u0010\r\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\n8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\t\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/integration/jei/berry/BerryRecipeCategory$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "GUI_TEXTURE_ID", "Lnet/minecraft/resources/ResourceLocation;", "getGUI_TEXTURE_ID", "()Lnet/minecraft/resources/ResourceLocation;", "", "HEIGHT", "I", "Lmezz/jei/api/recipe/RecipeType;", "Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationRecipe;", "kotlin.jvm.PlatformType", "RECIPE_TYPE", "Lmezz/jei/api/recipe/RecipeType;", "getRECIPE_TYPE", "()Lmezz/jei/api/recipe/RecipeType;", "WIDTH", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final RecipeType<BerryMutationRecipe> getRECIPE_TYPE() {
            return RECIPE_TYPE;
        }

        @NotNull
        public final ResourceLocation getGUI_TEXTURE_ID() {
            return GUI_TEXTURE_ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

