/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei;

import kotlin.Metadata;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/integration/jei/CobblemonJeiProvider;", "", "Lmezz/jei/api/registration/IRecipeCategoryRegistration;", "registration", "", "registerCategory", "(Lmezz/jei/api/registration/IRecipeCategoryRegistration;)V", "Lmezz/jei/api/registration/IRecipeRegistration;", "registerRecipes", "(Lmezz/jei/api/registration/IRecipeRegistration;)V", "common"})
public interface CobblemonJeiProvider {
    public void registerCategory(@NotNull IRecipeCategoryRegistration var1);

    public void registerRecipes(@NotNull IRecipeRegistration var1);
}

