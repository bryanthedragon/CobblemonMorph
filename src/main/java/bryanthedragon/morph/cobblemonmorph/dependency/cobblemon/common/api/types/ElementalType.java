/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0016\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\t\u001a\u0004\b\u0017\u0010\u000b\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/types/ElementalType;", "", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "hue", "I", "getHue", "()I", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "resourceLocation", "Lnet/minecraft/resources/ResourceLocation;", "getResourceLocation", "()Lnet/minecraft/resources/ResourceLocation;", "textureXMultiplier", "getTextureXMultiplier", "<init>", "(Ljava/lang/String;Lnet/minecraft/network/chat/MutableComponent;IILnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class ElementalType {
    @NotNull
    private final String name;
    @NotNull
    private final MutableComponent displayName;
    private final int hue;
    private final int textureXMultiplier;
    @NotNull
    private final ResourceLocation resourceLocation;

    public ElementalType(@NotNull String name, @NotNull MutableComponent displayName, int hue, int textureXMultiplier, @NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"resourceLocation");
        this.name = name;
        this.displayName = displayName;
        this.hue = hue;
        this.textureXMultiplier = textureXMultiplier;
        this.resourceLocation = resourceLocation;
    }

    public /* synthetic */ ElementalType(String string, MutableComponent mutableComponent, int n, int n2, ResourceLocation resourceLocation, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 0x10) != 0) {
            resourceLocation = new ResourceLocation("cobblemon", "ui/types.png");
        }
        this(string, mutableComponent, n, n2, resourceLocation);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        return this.displayName;
    }

    public final int getHue() {
        return this.hue;
    }

    public final int getTextureXMultiplier() {
        return this.textureXMultiplier;
    }

    @NotNull
    public final ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}

