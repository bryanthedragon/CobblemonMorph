package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe
import java.util.ArrayList;
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nActionEffectTimeline.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,97:1\n800#2,11:98\n*S KotlinDebug\n*F\n+ 1 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n*L\n73#1:98,11\n*E\n"])
public class ActionEffectContext(actionEffect: ActionEffectTimeline,
   holds: MutableSet<String> = (new LinkedHashSet()) as java.util.Set,
   providers: MutableList<Any> = (new ArrayList()) as java.util.List,
   runtime: MoLangRuntime,
   canBeInterrupted: Boolean = false,
   interrupted: Boolean = false,
   currentKeyframes: MutableList<ActionEffectKeyframe> = (new ArrayList()) as java.util.List
) {
   public final val actionEffect: ActionEffectTimeline
   public final var canBeInterrupted: Boolean
   public final var currentKeyframes: MutableList<ActionEffectKeyframe>
   public final val holds: MutableSet<String>
   public final var interrupted: Boolean
   public final val providers: MutableList<Any>
   public final val runtime: MoLangRuntime

   init {
      this.actionEffect = actionEffect;
      this.holds = holds;
      this.providers = providers;
      this.runtime = runtime;
      this.canBeInterrupted = canBeInterrupted;
      this.interrupted = interrupted;
      this.currentKeyframes = currentKeyframes;
   }
}
