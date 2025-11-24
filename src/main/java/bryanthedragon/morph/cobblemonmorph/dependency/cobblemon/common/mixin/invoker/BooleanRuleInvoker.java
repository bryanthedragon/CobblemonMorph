/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.GameRules$Type
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={GameRules.BooleanValue.class})
public interface BooleanRuleInvoker {
    @Invoker(value="create")
    public static GameRules.Type<GameRules.BooleanValue> cobblemon$create(boolean initialValue) {
        throw new UnsupportedOperationException();
    }
}

