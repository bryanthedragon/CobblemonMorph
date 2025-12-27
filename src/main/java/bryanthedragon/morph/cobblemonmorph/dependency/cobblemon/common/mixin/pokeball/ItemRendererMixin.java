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

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
   @Shadow
   @Final
   private ItemModelShaper f_115095_;

   @Shadow
   public abstract void m_115143_(
      ItemStack stack,
      ItemDisplayContext renderMode,
      boolean leftHanded,
      PoseStack matrices,
      MultiBufferSource vertexConsumers,
      int light,
      int overlay,
      BakedModel model
   );

   @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
   private void cobblemon$bakePokeballModel(ItemStack stack, Level world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
      if (stack.m_41720_() instanceof PokeBallItem pokeBallItem) {
         BakedModel model = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation(pokeBallItem.getPokeBall().getModel3d(), "inventory"));
         ClientLevel clientWorld = world instanceof ClientLevel ? (ClientLevel)world : null;
         BakedModel overriddenModel = model.m_7343_().m_173464_(model, stack, clientWorld, entity, seed);
         cir.setReturnValue(overriddenModel == null ? this.f_115095_.m_109393_().m_119409_() : overriddenModel);
      }
   }

   @Inject(
      method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void cobblemon$determinePokeballModel(
      ItemStack stack,
      ItemDisplayContext renderMode,
      boolean leftHanded,
      PoseStack matrices,
      MultiBufferSource vertexConsumers,
      int light,
      int overlay,
      BakedModel model,
      CallbackInfo ci
   ) {
      boolean shouldBe2d = renderMode == ItemDisplayContext.GUI || renderMode == ItemDisplayContext.FIXED;
      if (shouldBe2d && stack.m_41720_() instanceof PokeBallItem pokeBallItem) {
         BakedModel replacementModel = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation(pokeBallItem.getPokeBall().getModel2d(), "inventory"));
         if (!model.equals(replacementModel)) {
            ci.cancel();
            this.m_115143_(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, replacementModel);
         }
      }
   }
}
