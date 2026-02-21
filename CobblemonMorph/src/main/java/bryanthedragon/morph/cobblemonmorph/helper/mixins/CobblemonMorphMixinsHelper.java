package bryanthedragon.morph.cobblemonmorph.helper.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphMixinsHelper extends CobblemonMorph
{
    public CobblemonMorphMixinsHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) 
    {
        System.out.println("[CobblemonMorph] Mixin system is working.");
    }
}
