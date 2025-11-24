/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.ItemModelShaper
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ItemRenderer.class})
public abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper f_115095_;

    @Shadow
    public abstract void m_115143_(ItemStack var1, ItemDisplayContext var2, boolean var3, PoseStack var4, MultiBufferSource var5, int var6, int var7, BakedModel var8);

    @Inject(method={"getModel"}, at={@At(value="HEAD")}, cancellable=true)
    private void cobblemon$bakePokeballModel(ItemStack stack, Level world, LivingEntity entity2, int seed, CallbackInfoReturnable<BakedModel> cir) {
        Item item = stack.m_41720_();
        if (item instanceof PokeBallItem) {
            PokeBallItem pokeBallItem = (PokeBallItem)item;
            BakedModel model = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation(pokeBallItem.getPokeBall().getModel3d(), "inventory"));
            ClientLevel clientWorld = world instanceof ClientLevel ? (ClientLevel)world : null;
            BakedModel overriddenModel = model.m_7343_().m_173464_(model, stack, clientWorld, entity2, seed);
            cir.setReturnValue((Object)(overriddenModel == null ? this.f_115095_.m_109393_().m_119409_() : overriddenModel));
        }
    }

    @Inject(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void cobblemon$determinePokeballModel(ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay2, BakedModel model, CallbackInfo ci) {
        Item item;
        boolean shouldBe2d;
        boolean bl = shouldBe2d = renderMode == ItemDisplayContext.GUI || renderMode == ItemDisplayContext.FIXED;
        if (shouldBe2d && (item = stack.m_41720_()) instanceof PokeBallItem) {
            PokeBallItem pokeBallItem = (PokeBallItem)item;
            BakedModel replacementModel = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation(pokeBallItem.getPokeBall().getModel2d(), "inventory"));
            if (!model.equals(replacementModel)) {
                ci.cancel();
                this.m_115143_(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay2, replacementModel);
            }
        }
    }
}

