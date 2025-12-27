package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.effects.PotionBaseEffect
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect

public object ShoulderEffectAdapter : JsonDeserializer<ShoulderEffect> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ShoulderEffect {
      val var4: Pair = if (json.isJsonPrimitive())
         TuplesKt.to(json.getAsString(), new JsonObject())
         else
         TuplesKt.to(json.getAsJsonObject().get("type").getAsString(), json.getAsJsonObject());
      val typeId: java.lang.String = var4.component1() as java.lang.String;
      val obj: JsonObject = var4.component2() as JsonObject;
      val var10000: ShoulderEffectRegistry = ShoulderEffectRegistry.INSTANCE;
      val var14: Class = var10000.get(typeId);
      if (var14 == null) {
         val `$this$deserialize_u24lambda_u240`: ShoulderEffectAdapter = this;

         try {
            val effect: MobEffect = BuiltInRegistries.f_256974_
               .m_7745_(
                  new ResourceLocation(
                     StringsKt.replace$default(StringsKt.replace$default(typeId, "-", "_", false, 4, null), "slow_fall", "slow_falling", false, 4, null)
                  )
               ) as MobEffect;
            if (effect != null) {
               return new PotionBaseEffect(effect, 0, true, false, false);
            }
         } catch (var13: Exception) {
         }

         throw new IllegalArgumentException("Cannot find shoulder effect with type '$typeId'");
      } else {
         val var15: Any = context.deserialize(obj as JsonElement, var14);
         return var15 as ShoulderEffect;
      }
   }
}
