package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.BerriesMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.PotionsMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.RemediesMechanic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public object CobblemonMechanics : DataRegistry {
   public final var berries: BerriesMechanic = new BerriesMechanic();
   public final val gson: Gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Expression::class.java, ExpressionAdapter.INSTANCE).create();
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("mechanics");
   public open val observable: SimpleObservable<CobblemonMechanics> = new SimpleObservable();
   public final var potions: PotionsMechanic = new PotionsMechanic();
   public final var remedies: RemediesMechanic = new RemediesMechanic();
   public open val type: PackType = PackType.SERVER_DATA;
   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(manager: ResourceManager) {
      remedies = this.loadMechanic(manager, "remedies", RemediesMechanic.class);
      berries = this.loadMechanic(manager, "berries", BerriesMechanic.class);
      potions = this.loadMechanic(manager, "potions", PotionsMechanic.class);
   }

   private fun <T> loadMechanic(manager: ResourceManager, name: String, clazz: Class<Any>): Any {
      label18: {
         val var4: Closeable = manager.m_215593_(MiscUtilsKt.cobblemonResource("mechanics/$name.json")).m_215507_();
         var var5: java.lang.Throwable = null;

         try {
            try {
               val it: InputStream = var4 as InputStream;
               val var10000: Gson = gson;
               val var10: Any = var10000.fromJson(new InputStreamReader(it, Charsets.UTF_8), clazz);
            } 
            catch (var11: java.lang.Throwable) {
               var5 = var11;
               throw var11;
            }
         } catch (var12: java.lang.Throwable) {
            CloseableKt.closeFinally(var4, var5);
         }

         CloseableKt.closeFinally(var4, null);
      }
   }
}
