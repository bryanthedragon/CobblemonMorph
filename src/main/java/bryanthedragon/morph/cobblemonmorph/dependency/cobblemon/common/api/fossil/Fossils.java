package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.FossilRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.NbtItemPredicateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nFossils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Fossils.kt\ncom/cobblemon/mod/common/api/fossil/Fossils\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,107:1\n215#2,2:108\n288#3,2:110\n288#3,2:112\n1747#3,3:114\n*S KotlinDebug\n*F\n+ 1 Fossils.kt\ncom/cobblemon/mod/common/api/fossil/Fossils\n*L\n53#1:108,2\n87#1:110,2\n96#1:112,2\n104#1:114,3\n*E\n"])
public object Fossils : JsonDataRegistry<Fossil> {
   private final val fossils: HashMap<ResourceLocation, Fossil> = new HashMap()
   public open val gson: Gson =
      new GsonBuilder()
         .disableHtmlEscaping()
         .setPrettyPrinting()
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(PokemonProperties::class.java, PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter())
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Item.class}).getType(), ItemLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(NbtItemPredicate::class.java, NbtItemPredicateAdapter.INSTANCE)
         .create()
         public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("fossils")
   public open val observable: SimpleObservable<Fossils> = new SimpleObservable()
   public open val resourcePath: String = "fossils"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<Fossil>

   public override fun reload(data: Map<ResourceLocation, Fossil>) {
      fossils.clear();

      for (Entry element$iv : data.entrySet()) {
         val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
         val fossil: Fossil = `element$iv`.getValue() as Fossil;

         try {
            fossil.setIdentifier$common(identifier);
            fossils.put(identifier, fossil);
         } catch (var11: Exception) {
            Cobblemon.INSTANCE.getLOGGER().error("Skipped loading the {} fossil", identifier, var11);
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} fossils", fossils.size());
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
      new FossilRegistrySyncPacket(this.all()).sendToPlayer(player);
   }

   public fun all(): List<Fossil> {
      val var10000: java.util.Collection = fossils.values();
      return CollectionsKt.toList(var10000);
   }

   public fun getByIdentifier(identifier: ResourceLocation): Fossil? {
      return fossils.get(identifier);
   }

   public fun getFossilByItemStacks(fossilStacks: List<ItemStack>): Fossil? {
      val var4: java.util.Iterator = this.all().iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!(`element$iv` as Fossil).matchesIngredients(fossilStacks)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Fossil;
   }

   public fun getSubFossilByItemStacks(fossilStacks: List<ItemStack>): Fossil? {
      val var4: java.util.Iterator = this.all().iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!(`element$iv` as Fossil).matchesIngredientsSubSet(fossilStacks)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Fossil;
   }

   public fun isFossilIngredient(itemStack: ItemStack): Boolean {
      val `$this$any$iv`: java.lang.Iterable = this.all();
      var var10000: Boolean;
      if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val var4: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            if ((var4.next() as Fossil).isIngredient(itemStack)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var1: TypeToken = TypeToken.get(Fossil.class);
      typeToken = var1;
   }
}
