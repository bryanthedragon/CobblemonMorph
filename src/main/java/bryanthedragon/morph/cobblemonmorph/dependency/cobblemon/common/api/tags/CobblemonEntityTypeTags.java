/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.EntityType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJK\u0010\u0007\u001a6\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005 \u0006*\u001a\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bRH\u0010\t\u001a6\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005 \u0006*\u001a\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nRH\u0010\u000b\u001a6\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005 \u0006*\u001a\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\n\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/tags/CobblemonEntityTypeTags;", "", "", "path", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/entity/EntityType;", "kotlin.jvm.PlatformType", "create", "(Ljava/lang/String;)Lnet/minecraft/tags/TagKey;", "BOATS", "Lnet/minecraft/tags/TagKey;", "CANNOT_HAVE_NAME_TAG", "<init>", "()V", "common"})
public final class CobblemonEntityTypeTags {
    @NotNull
    public static final CobblemonEntityTypeTags INSTANCE = new CobblemonEntityTypeTags();
    @JvmField
    public static final TagKey<EntityType<?>> CANNOT_HAVE_NAME_TAG = INSTANCE.create("cannot_have_name_tag");
    @JvmField
    public static final TagKey<EntityType<?>> BOATS = INSTANCE.create("boats");

    private CobblemonEntityTypeTags() {
    }

    private final TagKey<EntityType<?>> create(String path) {
        return TagKey.m_203882_((ResourceKey)Registries.f_256939_, (ResourceLocation)MiscUtils.cobblemonResource(path));
    }
}

