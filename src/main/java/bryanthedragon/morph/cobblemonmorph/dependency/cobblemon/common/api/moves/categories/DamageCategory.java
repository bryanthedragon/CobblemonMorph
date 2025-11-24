/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "resourceLocation", "Lnet/minecraft/resources/ResourceLocation;", "getResourceLocation", "()Lnet/minecraft/resources/ResourceLocation;", "", "textureXMultiplier", "I", "getTextureXMultiplier", "()I", "<init>", "(Ljava/lang/String;Lnet/minecraft/network/chat/Component;ILnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class DamageCategory {
    @NotNull
    private final String name;
    @NotNull
    private final Component displayName;
    private final int textureXMultiplier;
    @NotNull
    private final ResourceLocation resourceLocation;

    public DamageCategory(@NotNull String name, @NotNull Component displayName, int textureXMultiplier, @NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"resourceLocation");
        this.name = name;
        this.displayName = displayName;
        this.textureXMultiplier = textureXMultiplier;
        this.resourceLocation = resourceLocation;
    }

    public /* synthetic */ DamageCategory(String string, Component component, int n, ResourceLocation resourceLocation, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            resourceLocation = MiscUtilsKt.cobblemonResource("textures/gui/categories.png");
        }
        this(string, component, n, resourceLocation);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Component getDisplayName() {
        return this.displayName;
    }

    public final int getTextureXMultiplier() {
        return this.textureXMultiplier;
    }

    @NotNull
    public final ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}

