/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.entity.player.Player
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
    @Shadow
    private Map<String, EntityRenderer<? extends Player>> f_114363_;

    @Inject(method={"reload"}, at={@At(value="TAIL")})
    public void resourceManagerReloadHook(ResourceManager resourceManager, CallbackInfo ci) {
        CobblemonClient.INSTANCE.onAddLayer(this.f_114363_);
    }
}

