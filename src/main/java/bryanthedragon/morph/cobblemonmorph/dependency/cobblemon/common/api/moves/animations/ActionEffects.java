package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.AddHoldsActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.AnimationActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.CanInterruptActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.CannotInterruptActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityMoLangActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityParticlesActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntitySoundActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ForkActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.MoLangActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.MoveToTargetActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ParallelActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.PauseActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.RemoveHoldsActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ReturnToPositionActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.RunActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.SavePositionActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.SequenceActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ActionEffectKeyframeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxCollectionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FloatNumberRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LiteralHexColorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SingleToPluralAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseIntRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseVec3dAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.awt.Color
import java.lang.reflect.Type
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nActionEffects.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActionEffects.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffects\n+ 2 ActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe$Companion\n*L\n1#1,95:1\n27#2,2:96\n27#2,2:98\n27#2,2:100\n27#2,2:102\n27#2,2:104\n27#2,2:106\n27#2,2:108\n27#2,2:110\n27#2,2:112\n27#2,2:114\n27#2,2:116\n27#2,2:118\n27#2,2:120\n27#2,2:122\n27#2,2:124\n27#2,2:126\n27#2,2:128\n*S KotlinDebug\n*F\n+ 1 ActionEffects.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffects\n*L\n42#1:96,2\n43#1:98,2\n44#1:100,2\n45#1:102,2\n46#1:104,2\n47#1:106,2\n48#1:108,2\n49#1:110,2\n50#1:112,2\n51#1:114,2\n52#1:116,2\n53#1:118,2\n54#1:120,2\n55#1:122,2\n56#1:124,2\n57#1:126,2\n58#1:128,2\n*E\n"])
public object ActionEffects : JsonDataRegistry<ActionEffectTimeline> {
   public final val actionEffects: MutableMap<ResourceLocation, ActionEffectTimeline> = (new LinkedHashMap()) as java.util.Map
   public open val gson: Gson =
      new GsonBuilder()
         .disableHtmlEscaping()
         .setPrettyPrinting()
         .registerTypeAdapter(ActionEffectKeyframe::class.java, ActionEffectKeyframeAdapter.INSTANCE)
         .registerTypeAdapter(Doubles::class.java, FloatNumberRangeAdapter.INSTANCE)
         .registerTypeAdapter(TypeToken.getParameterized(java.util.Collection::class.java, new Type[]{AABB.class}).getType(), BoxCollectionAdapter.INSTANCE)
         .registerTypeAdapter(AABB::class.java, BoxAdapter.INSTANCE)
         .registerTypeAdapter(Vec3::class.java, VerboseVec3dAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(IntRange::class.java, VerboseIntRangeAdapter.INSTANCE)
         .registerTypeAdapter(Color::class.java, LiteralHexColorAdapter.INSTANCE)
         .registerTypeAdapter(Stat::class.java, CobblemonStatTypeAdapter.INSTANCE)
         .registerTypeAdapter(Expression::class.java, ExpressionAdapter.INSTANCE)
         .registerTypeAdapter(ExpressionLike::class.java, ExpressionLikeAdapter.INSTANCE)
         .registerTypeAdapter(
            TypeToken.getParameterized(TypeToken.get(java.util.List.class).getType(), new Type[]{TypeToken.get(ActionEffectKeyframe.class).getType()})
               .getType(),
            new SingleToPluralAdapter<>(ActionEffectKeyframe.class, <unrepresentable>.INSTANCE)
         )
         .create()
         public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("action_effects")
   public open val observable: SimpleObservable<ActionEffects> = new SimpleObservable()
   public open val resourcePath: String = "action_effects"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<ActionEffectTimeline>

   public override fun reload(data: Map<ResourceLocation, ActionEffectTimeline>) {
      actionEffects.clear();
      actionEffects.putAll(data);
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      ActionEffectKeyframe.Companion.getTypes().put("animation", AnimationActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("entity_molang", EntityMoLangActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("molang", MoLangActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("parallel", ParallelActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("can_interrupt", CanInterruptActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("cannot_interrupt", CannotInterruptActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("remove_holds", RemoveHoldsActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("add_holds", AddHoldsActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("move_to_target", MoveToTargetActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("return_to_position", ReturnToPositionActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("pause", PauseActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("save_position", SavePositionActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("fork", ForkActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("sequence", SequenceActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("run_action_effect", RunActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("entity_particles", EntityParticlesActionEffectKeyframe::class.java);
      ActionEffectKeyframe.Companion.getTypes().put("entity_sound", EntitySoundActionEffectKeyframe::class.java);
      val var54: TypeToken = TypeToken.get(ActionEffectTimeline.class);
      typeToken = var54;
   }
}
