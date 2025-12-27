package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.NaturalMaterialRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.TypeIntrinsics
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nNaturalMaterials.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NaturalMaterials.kt\ncom/cobblemon/mod/common/api/fossil/NaturalMaterials\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,91:1\n215#2:92\n216#2:95\n1855#3,2:93\n1747#3,3:96\n288#3,2:99\n288#3,2:101\n*S KotlinDebug\n*F\n+ 1 NaturalMaterials.kt\ncom/cobblemon/mod/common/api/fossil/NaturalMaterials\n*L\n47#1:92\n47#1:95\n48#1:93,2\n62#1:96,3\n72#1:99,2\n84#1:101,2\n*E\n"])
public object NaturalMaterials : JsonDataRegistry<java.util.List<? extends NaturalMaterial>> {
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("natural_materials")
   private final val itemMap: MutableMap<ResourceLocation, NaturalMaterial> = (new LinkedHashMap()) as java.util.Map
   public open val observable: SimpleObservable<NaturalMaterials> = new SimpleObservable()
   public open val resourcePath: String = "natural_materials"
   private final val tagMap: MutableMap<ItemTagCondition, NaturalMaterial> = (new LinkedHashMap()) as java.util.Map
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<List<NaturalMaterial>>

   public override fun sync(player: ServerPlayer) {
      new NaturalMaterialRegistrySyncPacket(CollectionsKt.plus(CollectionsKt.toList(itemMap.values()), CollectionsKt.toList(tagMap.values())))
         .sendToPlayer(player);
   }

   public override fun reload(data: Map<ResourceLocation, List<NaturalMaterial>>) {
      for (Entry element$iv : data.entrySet()) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$iv) {
            val it: NaturalMaterial = `element$ivx` as NaturalMaterial;
            TypeIntrinsics.asMutableMap(itemMap).remove((`element$ivx` as NaturalMaterial).getItem());
            if (it.getItem() != null) {
               itemMap.put(it.getItem(), it);
            }

            if (it.getTag() != null) {
               tagMap.put(it.getTag(), it);
            }
         }
      }
   }

   public fun isNaturalMaterial(item: ItemStack): Boolean {
      val var10000: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(item.m_41720_());
      if (!itemMap.keySet().contains(var10000)) {
         val `$this$any$iv`: java.lang.Iterable = tagMap.keySet();
         var var9: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var9 = false;
         } else {
            val var5: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var5.hasNext()) {
                  var9 = false;
                  break;
               }

               val it: ItemTagCondition = var5.next() as ItemTagCondition;
               val var10001: Item = item.m_41720_();
               val var10002: DefaultedRegistry = BuiltInRegistries.f_257033_;
               if (it.fits(var10001, var10002 as Registry<Item>)) {
                  var9 = true;
                  break;
               }
            }
         }

         if (!var9) {
            return false;
         }
      }

      return true;
   }

   public fun getContent(item: ItemStack): Int? {
      var var10000: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(item.m_41720_());
      if (itemMap.keySet().contains(var10000)) {
         val var12: NaturalMaterial = itemMap.get(var10000);
         return if (var12 != null) var12.getContent() else null;
      } else {
         val var6: java.util.Iterator = tagMap.keySet().iterator();

         while (true) {
            if (var6.hasNext()) {
               val `element$iv`: Any = var6.next();
               val it: ItemTagCondition = `element$iv` as ItemTagCondition;
               val var10001: Item = item.m_41720_();
               val var10002: DefaultedRegistry = BuiltInRegistries.f_257033_;
               if (!it.fits(var10001, var10002 as Registry<Item>)) {
                  continue;
               }

               var10000 = (ResourceLocation)`element$iv`;
               break;
            }

            var10000 = null;
            break;
         }

         val tag: ItemTagCondition = var10000 as ItemTagCondition;
         if (var10000 as ItemTagCondition != null) {
            val var11: NaturalMaterial = tagMap.get(tag);
            return if (var11 != null) var11.getContent() else null;
         } else {
            return null;
         }
      }
   }

   public fun getReturnItem(item: ItemStack): ResourceLocation? {
      var var10000: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(item.m_41720_());
      if (itemMap.keySet().contains(var10000)) {
         val var12: NaturalMaterial = itemMap.get(var10000);
         return if (var12 != null) var12.getReturnItem() else null;
      } else {
         val var6: java.util.Iterator = tagMap.keySet().iterator();

         while (true) {
            if (var6.hasNext()) {
               val `element$iv`: Any = var6.next();
               val it: ItemTagCondition = `element$iv` as ItemTagCondition;
               val var10001: Item = item.m_41720_();
               val var10002: DefaultedRegistry = BuiltInRegistries.f_257033_;
               if (!it.fits(var10001, var10002 as Registry<Item>)) {
                  continue;
               }

               var10000 = (ResourceLocation)`element$iv`;
               break;
            }

            var10000 = null;
            break;
         }

         val tag: ItemTagCondition = var10000 as ItemTagCondition;
         if (var10000 as ItemTagCondition != null) {
            val var11: NaturalMaterial = tagMap.get(tag);
            return if (var11 != null) var11.getReturnItem() else null;
         } else {
            return null;
         }
      }
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var1: TypeToken = TypeToken.getParameterized(java.util.List::class.java, new Type[]{NaturalMaterial.class});
      typeToken = var1;
      val var2: Gson = new GsonBuilder()
         .setPrettyPrinting()
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(ItemTagCondition::class.java, ItemLikeConditionAdapter.INSTANCE)
         .create();
      gson = var2;
   }
}
