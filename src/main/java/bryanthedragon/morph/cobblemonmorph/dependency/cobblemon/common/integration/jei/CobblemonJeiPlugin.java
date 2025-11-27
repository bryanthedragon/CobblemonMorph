/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  mezz.jei.api.IModPlugin
 *  mezz.jei.api.JeiPlugin
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.CobblemonJeiProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry.BerryMutationProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin;", "Lmezz/jei/api/IModPlugin;", "Lnet/minecraft/resources/ResourceLocation;", "getPluginUid", "()Lnet/minecraft/resources/ResourceLocation;", "Lmezz/jei/api/registration/IRecipeCategoryRegistration;", "registration", "", "registerCategories", "(Lmezz/jei/api/registration/IRecipeCategoryRegistration;)V", "Lmezz/jei/api/registration/IRecipeRegistration;", "registerRecipes", "(Lmezz/jei/api/registration/IRecipeRegistration;)V", "", "Lcom/cobblemon/mod/common/integration/jei/CobblemonJeiProvider;", "jeiProviders", "Ljava/util/Set;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonJeiPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonJeiPlugin.kt\ncom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1855#2,2:50\n1855#2,2:52\n*S KotlinDebug\n*F\n+ 1 CobblemonJeiPlugin.kt\ncom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin\n*L\n34#1:50,2\n40#1:52,2\n*E\n"})
public final class CobblemonJeiPlugin
implements IModPlugin {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Set<CobblemonJeiProvider> jeiProviders = SetsKt.setOf((Object)new BerryMutationProvider());
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("jei_plugin");

    @NotNull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        Intrinsics.checkNotNullParameter((Object)registration, (String)"registration");
        Iterable $this$forEach$iv = this.jeiProviders;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CobblemonJeiProvider it = (CobblemonJeiProvider)element$iv;
            boolean bl = false;
            it.registerCategory(registration);
        }
    }

    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        Intrinsics.checkNotNullParameter((Object)registration, (String)"registration");
        Iterable $this$forEach$iv = this.jeiProviders;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CobblemonJeiProvider it = (CobblemonJeiProvider)element$iv;
            boolean bl = false;
            it.registerRecipes(registration);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

