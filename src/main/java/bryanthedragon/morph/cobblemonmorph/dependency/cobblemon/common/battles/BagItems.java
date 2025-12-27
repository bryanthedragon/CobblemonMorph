package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nBagItems.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BagItems.kt\ncom/cobblemon/mod/common/battles/BagItems\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,64:1\n288#2,2:65\n215#3,2:67\n*S KotlinDebug\n*F\n+ 1 BagItems.kt\ncom/cobblemon/mod/common/battles/BagItems\n*L\n49#1:65,2\n53#1:67,2\n*E\n"])
public object BagItems : DataRegistry {
   public final val bagItems: PrioritizedList<BagItemConvertible> = new PrioritizedList()
   internal final val bagItemsScripts: MutableMap<String, String> = (new LinkedHashMap()) as java.util.Map
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("bag_items")
   public open val observable: SimpleObservable<BagItems> = new SimpleObservable()
   public open val type: PackType = PackType.SERVER_DATA

   public override fun sync(player: ServerPlayer) {
   }

   public fun getConvertibleForStack(stack: ItemStack): BagItemConvertible? {
      val var4: java.util.Iterator = bagItems.iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if ((`element$iv` as BagItemConvertible).getBagItem(stack) == null) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as BagItemConvertible;
   }

   public override fun reload(manager: ResourceManager) {
      label46: {
         val var10000: java.util.Map = manager.m_214159_("bag_items", BagItems::reload$lambda$1);

         for (Entry element$iv : var10000.entrySet()) {
            val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
            val var10: Closeable = (`element$iv`.getValue() as Resource).m_215507_();
            var var11: java.lang.Throwable = null;

            try {
               try {
                  val stream: InputStream = var10 as InputStream;
                  val reader: Reader = new InputStreamReader(stream, Charsets.UTF_8);
                  val var14: Closeable = if (reader is BufferedReader) reader as BufferedReader else new BufferedReader(reader, 8192);
                  var var34: java.lang.Throwable = null;

                  try {
                     try {
                        val var35: BufferedReader = var14 as BufferedReader;
                        val resolvedIdentifier: ResourceLocation = new ResourceLocation(
                           identifier.m_135827_(), FilesKt.getNameWithoutExtension(new File(identifier.m_135815_()))
                        );
                        val js: java.lang.String = TextStreamsKt.readText(var35);
                        val var20: java.util.Map = bagItemsScripts;
                        val var38: java.lang.String = resolvedIdentifier.m_135815_();
                        var20.put(var38, js);
                     } catch (var21: java.lang.Throwable) {
                        var34 = var21;
                        throw var21;
                     }
                  } catch (var22: java.lang.Throwable) {
                     CloseableKt.closeFinally(var14, var34);
                  }

                  CloseableKt.closeFinally(var14, null);
               } catch (var23: java.lang.Throwable) {
                  var11 = var23;
                  throw var23;
               }
            } catch (var24: java.lang.Throwable) {
               CloseableKt.closeFinally(var10, var11);
            }

            CloseableKt.closeFinally(var10, null);
         }

         this.getObservable().emit(this);
      }
   }

   @JvmStatic
   fun `reload$lambda$1`(it: ResourceLocation): Boolean {
      val var10000: java.lang.String = it.m_135815_();
      return StringsKt.endsWith$default(var10000, ".js", false, 2, null);
   }

   @JvmStatic
   fun {
      Observable.DefaultImpls.subscribe$default(INSTANCE.getObservable(), null, <unrepresentable>.INSTANCE, 1, null);
   }
}
