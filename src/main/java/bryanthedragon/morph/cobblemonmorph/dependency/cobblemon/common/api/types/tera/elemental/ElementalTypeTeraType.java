/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.elemental;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0006\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\n8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/types/tera/elemental/ElementalTypeTeraType;", "Lcom/cobblemon/mod/common/api/types/tera/TeraType;", "", "showdownId", "()Ljava/lang/String;", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "legalAsStatic", "Z", "getLegalAsStatic", "()Z", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "type", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "<init>", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)V", "common"})
public final class ElementalTypeTeraType
implements TeraType {
    @NotNull
    private final ElementalType type;
    private final boolean legalAsStatic;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final Component displayName;

    public ElementalTypeTeraType(@NotNull ElementalType type) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        this.type = type;
        this.legalAsStatic = true;
        this.id = MiscUtils.cobblemonResource(this.type.getName());
        this.displayName = (Component)this.type.getDisplayName();
    }

    @NotNull
    public final ElementalType getType() {
        return this.type;
    }

    @Override
    public boolean getLegalAsStatic() {
        return this.legalAsStatic;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    @NotNull
    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    @NotNull
    public String showdownId() {
        return this.type.getName();
    }
}

