package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.MoStruct
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.HashMap
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent

public open class MoveTemplate(name: String,
   num: Int,
   elementalType: ElementalType,
   damageCategory: DamageCategory,
   power: Double,
   target: MoveTarget,
   accuracy: Double,
   pp: Int,
   priority: Int,
   critRatio: Double,
   vararg effectChances: Any,
   actionEffect: ActionEffectTimeline?
) {
   public final val accuracy: Double
   public final val actionEffect: ActionEffectTimeline?
   public final val critRatio: Double
   public final val damageCategory: DamageCategory

   public final val description: MutableComponent
      public final get() {
         val var10000: MutableComponent = LocalizationUtilsKt.lang("move.${this.name}.desc");
         return var10000;
      }


   public final val displayName: MutableComponent
      public final get() {
         val var10000: MutableComponent = LocalizationUtilsKt.lang("move.${this.name}");
         return var10000;
      }


   public final val effectChances: Array<Double>
   public final val elementalType: ElementalType

   public final val maxPp: Int
      public final get() {
         return 8 * this.pp / 5;
      }


   public final val name: String
   public final val num: Int
   public final val power: Double
   public final val pp: Int
   public final val priority: Int

   public final val struct: MoStruct
      public final get() {
         val var10000: Any = this.struct$delegate.getValue();
         return var10000 as MoStruct;
      }


   public final val target: MoveTarget

   init {
      this.name = name;
      this.num = num;
      this.elementalType = elementalType;
      this.damageCategory = damageCategory;
      this.power = power;
      this.target = target;
      this.accuracy = accuracy;
      this.pp = pp;
      this.priority = priority;
      this.critRatio = critRatio;
      this.effectChances = effectChances;
      this.actionEffect = actionEffect;
      this.struct$delegate = LazyKt.lazy(
         (
            new Function0<QueryStruct>(this) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
               }

               public final QueryStruct invoke() {
                  return new QueryStruct(new HashMap<>())
                     .addFunction("name", <unrepresentable>::invoke$lambda$0)
                     .addFunction("type", <unrepresentable>::invoke$lambda$1)
                     .addFunction("damage_category", <unrepresentable>::invoke$lambda$2)
                     .addFunction("power", <unrepresentable>::invoke$lambda$3)
                     .addFunction("target", <unrepresentable>::invoke$lambda$4)
                     .addFunction("accuracy", <unrepresentable>::invoke$lambda$5)
                     .addFunction("pp", <unrepresentable>::invoke$lambda$6)
                     .addFunction("priority", <unrepresentable>::invoke$lambda$7)
                     .addFunction("crit_ratio", <unrepresentable>::invoke$lambda$8);
               }

               private static final Object invoke$lambda$0(MoveTemplate this$0, MoParams it) {
                  return new StringValue(`this$0`.getName());
               }

               private static final Object invoke$lambda$1(MoveTemplate this$0, MoParams it) {
                  return new StringValue(`this$0`.getElementalType().getName());
               }

               private static final Object invoke$lambda$2(MoveTemplate this$0, MoParams it) {
                  return new StringValue(`this$0`.getDamageCategory().getName());
               }

               private static final Object invoke$lambda$3(MoveTemplate this$0, MoParams it) {
                  return new DoubleValue(`this$0`.getPower());
               }

               private static final Object invoke$lambda$4(MoveTemplate this$0, MoParams it) {
                  return new StringValue(`this$0`.getTarget().name());
               }

               private static final Object invoke$lambda$5(MoveTemplate this$0, MoParams it) {
                  return new DoubleValue(`this$0`.getAccuracy());
               }

               private static final Object invoke$lambda$6(MoveTemplate this$0, MoParams it) {
                  return new DoubleValue(`this$0`.getPp());
               }

               private static final Object invoke$lambda$7(MoveTemplate this$0, MoParams it) {
                  return new DoubleValue(`this$0`.getPriority());
               }

               private static final Object invoke$lambda$8(MoveTemplate this$0, MoParams it) {
                  return new DoubleValue(`this$0`.getCritRatio());
               }
            }
         ) as Function0
      );
   }

   public fun create(): Move {
      return this.create(this.pp);
   }

   public fun create(currentPp: Int): Move {
      return this.create(currentPp, 0);
   }

   public fun create(currentPp: Int, raisedPpStages: Int): Move {
      return new Move(this, currentPp, raisedPpStages);
   }

   public companion object {
      public fun dummy(name: String): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate.Dummy {
         return new MoveTemplate.Dummy(name);
      }
   }

   @SourceDebugExtension(["SMAP\nMoveTemplate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTemplate.kt\ncom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,117:1\n26#2:118\n*S KotlinDebug\n*F\n+ 1 MoveTemplate.kt\ncom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy\n*L\n88#1:118\n*E\n"])
   public class Dummy(name: String) : MoveTemplate(
         name,
         -1,
         ElementalTypes.INSTANCE.getNORMAL(),
         DamageCategories.INSTANCE.getSTATUS(),
         0.0,
         MoveTarget.all,
         100.0,
         5,
         0,
         0.0,
         new java.lang.Double[0],
         null
      )
}
