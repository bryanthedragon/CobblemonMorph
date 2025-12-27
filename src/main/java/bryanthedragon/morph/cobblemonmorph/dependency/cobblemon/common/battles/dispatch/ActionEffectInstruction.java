package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import java.util.concurrent.CompletableFuture
import net.minecraft.resources.ResourceLocation

public interface ActionEffectInstruction : InterpreterInstruction {
   public var future: CompletableFuture<*>
   public var holds: MutableSet<String>
   public val id: ResourceLocation

   public override operator fun invoke(battle: PokemonBattle) {
   }

   public abstract fun preActionEffect(battle: PokemonBattle) {
   }

   public abstract fun runActionEffect(battle: PokemonBattle, runtime: MoLangRuntime) {
   }

   public abstract fun postActionEffect(battle: PokemonBattle) {
   }

   public open fun addMolangQueries(runtime: MoLangRuntime) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun invoke(`$this`: ActionEffectInstruction, battle: PokemonBattle) {
         `$this`.preActionEffect(battle);
         val runtime: MoLangRuntime = new MoLangRuntime();
         var var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
         var var10002: MoLangEnvironment = runtime.getEnvironment();
         battle.addQueryFunctions(MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null));
         val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
         var10001 = MoLangFunctions.INSTANCE;
         var10002 = runtime.getEnvironment();
         var10000.addStandardFunctions(MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null));
         `$this`.addMolangQueries(runtime);
         `$this`.runActionEffect(battle, runtime);
         `$this`.postActionEffect(battle);
      }

      @JvmStatic
      fun addMolangQueries(`$this`: ActionEffectInstruction, runtime: MoLangRuntime) {
         val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var10001: MoLangEnvironment = runtime.getEnvironment();
         MoLangFunctions.getQueryStruct$default(var10000, var10001, null, 1, null)
            .addFunction("instruction_id", ActionEffectInstruction.DefaultImpls::addMolangQueries$lambda$0);
      }

      @JvmStatic
      fun `addMolangQueries$lambda$0`(`this$0`: ActionEffectInstruction, it: MoParams): Any {
         return new StringValue(`this$0`.getId().toString());
      }
   }
}
