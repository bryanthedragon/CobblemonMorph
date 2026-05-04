package bryanthedragon.cobblemon.morph.helper.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bryanthedragon.cobblemon.morph.CobblemonMorph;

public class CobblemonMorphMixinsHelper extends CobblemonMorph
{
    public CobblemonMorphMixinsHelper()    
    {
        
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) 
    {
        System.out.println("[CobblemonMorph] Mixin system is working.");
    }
}
