package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import java.util.ArrayList;
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSpeciesFeatureSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,68:1\n800#2,11:69\n766#2:80\n857#2,2:81\n1#3:83\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket\n*L\n25#1:69,11\n25#1:80\n25#1:81,2\n*E\n"])
public abstract class SpeciesFeatureSyncPacket<T extends SpeciesFeatureSyncPacket<T>>
   : DataRegistrySyncPacket<Entry<? extends java.lang.String, ? extends SynchronizedSpeciesFeatureProvider<?>>, T> {
   open fun SpeciesFeatureSyncPacket(speciesFeatureProviders: MutableMap<java.lang.String, SpeciesFeatureProvider<?>>) {
      var `$this$filter$iv`: java.lang.Iterable = speciesFeatureProviders.entrySet();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (`element$iv$iv` is Entry) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$filter$iv` = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         if (((`element$iv$ivx` as Entry).getValue() as SynchronizedSpeciesFeatureProvider).getVisible()) {
            `destination$iv$iv`.add(`element$iv$ivx`);
         }
      }

      super(`destination$iv$iv`);
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: kotlin.collections.Map.Entry<String, SynchronizedSpeciesFeatureProvider<*>>) {
      val var6: java.util.Iterator = SpeciesFeatures.INSTANCE.getTypes().entrySet().iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val var7: Any = var6.next();
            if (!((var7 as Entry).getValue() as Class).isInstance(entry.getValue())) {
               continue;
            }

            var10000 = var7;
            break;
         }

         var10000 = null;
         break;
      }

      val typeName: java.lang.String = if (var10000 as Entry != null) (var10000 as Entry).getKey() as java.lang.String else null;
      val value: SynchronizedSpeciesFeatureProvider = entry.getValue() as SynchronizedSpeciesFeatureProvider;
      if (typeName == null) {
         buffer.writeBoolean(false);
      } else {
         buffer.writeBoolean(true);
         buffer.m_130070_(entry.getKey() as java.lang.String);
         buffer.m_130070_(typeName);
         value.encode(buffer);
      }
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): kotlin.collections.Map.Entry<String, SynchronizedSpeciesFeatureProvider<*>>? {
      if (!buffer.readBoolean()) {
         return null;
      } else {
         val name: java.lang.String = buffer.m_130277_();
         val typeName: java.lang.String = buffer.m_130277_();
         val var10000: Class = SpeciesFeatures.INSTANCE.getTypes().get(typeName);
         if (var10000 == null) {
            throw new IllegalStateException(
               StringsKt.trimIndent(
                  "\n                    A custom species feature provider, $typeName with encoding implementations was registered on the server and \n                    not the client, and therefore cannot be synced. Remove the implementation or install it \n                    on the client.\n                "
               )
            );
         } else {
            val instance: SpeciesFeatureProvider = var10000.getConstructor().newInstance() as SpeciesFeatureProvider;
            if (instance !is SynchronizedSpeciesFeatureProvider) {
               throw new IllegalStateException("Somehow a non-SynchronizedSpeciesFeatureProvider was sent to the client. Version mismatch?");
            } else {
               (instance as SynchronizedSpeciesFeatureProvider).decode(buffer);
               return new Entry<java.lang.String, SynchronizedSpeciesFeatureProvider<?>>(name, instance) {
                  @NotNull
                  private final java.lang.String key;
                  @NotNull
                  private final SynchronizedSpeciesFeatureProvider<?> value;

                  {
                     this.key = `$name`;
                     this.value = `$instance` as SynchronizedSpeciesFeatureProvider<?>;
                  }

                  @NotNull
                  public java.lang.String getKey() {
                     return this.key;
                  }

                  @NotNull
                  public SynchronizedSpeciesFeatureProvider<?> getValue() {
                     return this.value;
                  }

                  public SynchronizedSpeciesFeatureProvider<?> setValue(SynchronizedSpeciesFeatureProvider<?> newValue) {
                     throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                  }
               };
            }
         }
      }
   }
}
