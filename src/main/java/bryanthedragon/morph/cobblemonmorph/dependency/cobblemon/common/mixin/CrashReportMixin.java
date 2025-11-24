/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.CrashReport
 *  net.minecraft.CrashReportCategory
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBuildDetails;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CrashReport.class})
public final class CrashReportMixin {
    @Inject(method={"addStackTrace"}, at={@At(value="INVOKE", target="Lnet/minecraft/util/SystemDetails;writeTo(Ljava/lang/StringBuilder;)V")})
    public void cobblemon$printCobblemonDetails(StringBuilder builder, CallbackInfo callback) {
        CrashReportCategory cobblemon = new CrashReportCategory("Cobblemon");
        cobblemon.m_128159_("Version", (Object)"1.5.2");
        cobblemon.m_128159_("Is Snapshot", (Object)false);
        cobblemon.m_128159_("Git Commit", (Object)(CobblemonBuildDetails.INSTANCE.smallCommitHash() + " (https://gitlab.com/cable-mc/cobblemon/-/commit/df8f078d13702ab9a000438910b822ceffbb2248)"));
        cobblemon.m_128159_("Branch", (Object)"HEAD");
        cobblemon.m_128168_(builder);
        builder.append("\n\n");
    }
}

