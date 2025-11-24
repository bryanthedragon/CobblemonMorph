/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\n\u0010\tJ\u0015\u0010\f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\f\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0015\u001a\u0004\b\u0019\u0010\u0017R\u0017\u0010\u001a\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0015\u001a\u0004\b\u001b\u0010\u0017R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/moves/categories/DamageCategories;", "", "", "count", "()I", "", "name", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "get", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "getOrException", "damageCategory", "register", "(Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;)Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/resources/ResourceLocation;", "resourceLocation", "textureXMultiplier", "(Ljava/lang/String;Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceLocation;I)Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "PHYSICAL", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "getPHYSICAL", "()Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "SPECIAL", "getSPECIAL", "STATUS", "getSTATUS", "", "allCategories", "Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDamageCategories.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageCategories.kt\ncom/cobblemon/mod/common/api/moves/categories/DamageCategories\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n288#2,2:65\n223#2,2:67\n*S KotlinDebug\n*F\n+ 1 DamageCategories.kt\ncom/cobblemon/mod/common/api/moves/categories/DamageCategories\n*L\n56#1:65,2\n60#1:67,2\n*E\n"})
public final class DamageCategories {
    @NotNull
    public static final DamageCategories INSTANCE = new DamageCategories();
    @NotNull
    private static final List<DamageCategory> allCategories = new ArrayList();
    @NotNull
    private static final DamageCategory PHYSICAL;
    @NotNull
    private static final DamageCategory SPECIAL;
    @NotNull
    private static final DamageCategory STATUS;

    private DamageCategories() {
    }

    @NotNull
    public final DamageCategory getPHYSICAL() {
        return PHYSICAL;
    }

    @NotNull
    public final DamageCategory getSPECIAL() {
        return SPECIAL;
    }

    @NotNull
    public final DamageCategory getSTATUS() {
        return STATUS;
    }

    @NotNull
    public final DamageCategory register(@NotNull String name, @NotNull Component displayName, @NotNull ResourceLocation resourceLocation, int textureXMultiplier) {
        DamageCategory damageCategory;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"resourceLocation");
        DamageCategory it = damageCategory = new DamageCategory(name, displayName, textureXMultiplier, resourceLocation);
        boolean bl = false;
        allCategories.add(it);
        return damageCategory;
    }

    public static /* synthetic */ DamageCategory register$default(DamageCategories damageCategories, String string, Component component, ResourceLocation resourceLocation, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            resourceLocation = MiscUtilsKt.cobblemonResource("textures/gui/categories.png");
        }
        return damageCategories.register(string, component, resourceLocation, n);
    }

    @NotNull
    public final DamageCategory register(@NotNull DamageCategory damageCategory) {
        Intrinsics.checkNotNullParameter((Object)damageCategory, (String)"damageCategory");
        allCategories.add(damageCategory);
        return damageCategory;
    }

    @Nullable
    public final DamageCategory get(@NotNull String name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable $this$firstOrNull$iv = allCategories;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                DamageCategory cat = (DamageCategory)element$iv;
                boolean bl = false;
                if (!StringsKt.equals((String)cat.getName(), (String)name, (boolean)true)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @NotNull
    public final DamageCategory getOrException(@NotNull String name) {
        Object element$iv2;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable $this$first$iv = allCategories;
            boolean $i$f$first = false;
            for (Object element$iv2 : $this$first$iv) {
                DamageCategory cat = (DamageCategory)element$iv2;
                boolean bl = false;
                if (!StringsKt.equals((String)cat.getName(), (String)name, (boolean)true)) continue;
                break block1;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (DamageCategory)element$iv2;
    }

    public final int count() {
        return allCategories.size();
    }

    static {
        MutableComponent mutableComponent = Component.m_237115_((String)"cobblemon.move.category.physical");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"cobblemon.move.category.physical\")");
        PHYSICAL = DamageCategories.register$default(INSTANCE, "physical", (Component)mutableComponent, null, 0, 4, null);
        MutableComponent mutableComponent2 = Component.m_237115_((String)"cobblemon.move.category.special");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"translatable(\"cobblemon.move.category.special\")");
        SPECIAL = DamageCategories.register$default(INSTANCE, "special", (Component)mutableComponent2, null, 1, 4, null);
        MutableComponent mutableComponent3 = Component.m_237115_((String)"cobblemon.move.category.status");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"translatable(\"cobblemon.move.category.status\")");
        STATUS = DamageCategories.register$default(INSTANCE, "status", (Component)mutableComponent3, null, 2, 4, null);
    }
}

