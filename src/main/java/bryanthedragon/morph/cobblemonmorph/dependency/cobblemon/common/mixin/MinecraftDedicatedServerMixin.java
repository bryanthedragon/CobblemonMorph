/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.ServerInterface
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ServerLevel.class})
public abstract class MinecraftDedicatedServerMixin
implements ServerInterface {
    @Inject(method={"shouldCancelSpawn"}, at={@At(value="HEAD")}, cancellable=true)
    public void cobblemon$allowPokemonSpawns(Entity entity2, CallbackInfoReturnable<Boolean> callback) {
        if (entity2 instanceof PokemonEntity) {
            callback.setReturnValue((Object)false);
        }
    }
}

