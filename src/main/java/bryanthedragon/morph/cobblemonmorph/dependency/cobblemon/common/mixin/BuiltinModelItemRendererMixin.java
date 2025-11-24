/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRendererRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BlockEntityWithoutLevelRenderer.class})
public class BuiltinModelItemRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void cobblemon$useDynamicItemRenderer(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay2, CallbackInfo ci) {
        CobblemonBuiltinItemRenderer renderer = CobblemonBuiltinItemRendererRegistry.INSTANCE.rendererOf(stack.m_41720_());
        if (renderer != null) {
            renderer.render(stack, mode, matrices, vertexConsumers, light, overlay2);
            ci.cancel();
        }
    }
}

