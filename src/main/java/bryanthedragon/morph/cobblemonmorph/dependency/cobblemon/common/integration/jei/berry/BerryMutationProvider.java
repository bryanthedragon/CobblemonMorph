/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.CobblemonJeiProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry.BerryMutationRecipe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry.BerryRecipeCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationProvider;", "Lcom/cobblemon/mod/common/integration/jei/CobblemonJeiProvider;", "Lmezz/jei/api/registration/IRecipeCategoryRegistration;", "registration", "", "registerCategory", "(Lmezz/jei/api/registration/IRecipeCategoryRegistration;)V", "Lmezz/jei/api/registration/IRecipeRegistration;", "registerRecipes", "(Lmezz/jei/api/registration/IRecipeRegistration;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBerryMutationProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryMutationProvider.kt\ncom/cobblemon/mod/common/integration/jei/berry/BerryMutationProvider\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n125#2:44\n152#2,3:45\n1855#3,2:48\n1549#3:50\n1620#3,3:51\n*S KotlinDebug\n*F\n+ 1 BerryMutationProvider.kt\ncom/cobblemon/mod/common/integration/jei/berry/BerryMutationProvider\n*L\n24#1:44\n24#1:45,3\n26#1:48,2\n37#1:50\n37#1:51,3\n*E\n"})
public final class BerryMutationProvider
implements CobblemonJeiProvider {
    @Override
    public void registerCategory(@NotNull IRecipeCategoryRegistration registration) {
        Intrinsics.checkNotNullParameter((Object)registration, (String)"registration");
        IRecipeCategory[] iRecipeCategoryArray = new IRecipeCategory[]{new BerryRecipeCategory(registration)};
        registration.addRecipeCategories(iRecipeCategoryArray);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        void $this$mapTo$iv$iv;
        Object berryTwo;
        Collection collection;
        Iterator iterator;
        void $this$mapTo$iv$iv2;
        Intrinsics.checkNotNullParameter((Object)registration, (String)"registration");
        Map<ResourceLocation, BerryItem> $this$map$iv = CobblemonItems.INSTANCE.berries();
        boolean $i$f$map = false;
        Map<ResourceLocation, BerryItem> map = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.size());
        boolean $i$f$mapTo = false;
        for (Map.Entry item$iv$iv : $this$mapTo$iv$iv2.entrySet()) {
            void it;
            iterator = item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add((BerryItem)((Object)it.getValue()));
        }
        List berryList = (List)destination$iv$iv;
        Set berryMutations = new LinkedHashSet();
        Iterable $this$forEach$iv = berryList;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BerryItem berry = (BerryItem)((Object)element$iv);
            boolean bl = false;
            Object object = berry.berry();
            if (object == null || (object = ((Berry)object).getMutations()) == null || (object = object.entrySet()) == null) {
                object = SetsKt.emptySet();
            }
            iterator = object.iterator();
            while (iterator.hasNext()) {
                BerryItem berryThree;
                Map.Entry mut = (Map.Entry)iterator.next();
                if (CobblemonItems.INSTANCE.berries().get(mut.getKey()) == null || CobblemonItems.INSTANCE.berries().get(mut.getValue()) == null) continue;
                Triple mutation = new Triple((Object)berry, berryTwo, (Object)berryThree);
                Triple altMutation = new Triple(berryTwo, (Object)berry, (Object)berryThree);
                if (berryMutations.contains(mutation) || berryMutations.contains(altMutation)) continue;
                berryMutations.add(mutation);
            }
        }
        Iterable $this$map$iv2 = berryMutations;
        boolean $i$f$map2 = false;
        Iterable element$iv = $this$map$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            berryTwo = (Triple)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl = false;
            collection.add(new BerryMutationRecipe((BerryItem)((Object)it.getFirst()), (BerryItem)((Object)it.getSecond()), (BerryItem)((Object)it.getThird())));
        }
        List berryMutationRecipes = (List)destination$iv$iv2;
        registration.addRecipes(BerryRecipeCategory.Companion.getRECIPE_TYPE(), berryMutationRecipes);
    }
}

