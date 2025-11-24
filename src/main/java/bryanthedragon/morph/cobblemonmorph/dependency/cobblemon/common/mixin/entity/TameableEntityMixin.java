/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={TamableAnimal.class})
public class TameableEntityMixin {
    @Redirect(method={"onDeath"}, at=@At(value="FIELD", target="Lnet/minecraft/world/World;isClient:Z"))
    public boolean cobblemon$checkIfPokemonBeforeSendingMessage(Level world) {
        return world.f_46443_ || this.getClass().isAssignableFrom(PokemonEntity.class);
    }
}

