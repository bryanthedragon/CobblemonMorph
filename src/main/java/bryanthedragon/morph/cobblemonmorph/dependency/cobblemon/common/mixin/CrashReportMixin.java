package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBuildDetails;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public final class CrashReportMixin {
   @Inject(method = "addStackTrace", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SystemDetails;writeTo(Ljava/lang/StringBuilder;)V"))
   public void cobblemon$printCobblemonDetails(StringBuilder builder, CallbackInfo callback) {
      CrashReportCategory cobblemon = new CrashReportCategory("Cobblemon");
      cobblemon.m_128159_("Version", "1.5.2");
      cobblemon.m_128159_("Is Snapshot", false);
      cobblemon.m_128159_(
         "Git Commit",
         CobblemonBuildDetails.INSTANCE.smallCommitHash() + " (https://gitlab.com/cable-mc/cobblemon/-/commit/df8f078d13702ab9a000438910b822ceffbb2248)"
      );
      cobblemon.m_128159_("Branch", "HEAD");
      cobblemon.m_128168_(builder);
      builder.append("\n\n");
   }
}
