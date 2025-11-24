/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J?\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH&\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRenderer;", "", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/item/ItemDisplayContext;", "mode", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "common"})
public interface CobblemonBuiltinItemRenderer {
    public void render(@NotNull ItemStack var1, @NotNull ItemDisplayContext var2, @NotNull PoseStack var3, @NotNull MultiBufferSource var4, int var5, int var6);
}

