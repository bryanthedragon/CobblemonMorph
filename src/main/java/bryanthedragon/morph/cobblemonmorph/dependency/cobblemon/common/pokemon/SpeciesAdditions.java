package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.TypeIntrinsics
import kotlin.reflect.KCallable
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.TypesJVMKt
import kotlin.reflect.full.KClasses
import kotlin.reflect.jvm.KCallablesJvm
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nSpeciesAdditions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesAdditions.kt\ncom/cobblemon/mod/common/pokemon/SpeciesAdditions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n1855#2,2:114\n*S KotlinDebug\n*F\n+ 1 SpeciesAdditions.kt\ncom/cobblemon/mod/common/pokemon/SpeciesAdditions\n*L\n44#1:114,2\n*E\n"])
internal object SpeciesAdditions : JsonDataRegistry<SpeciesAdditions.AdditionParameter> {
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("species_additions")
   public open val observable: SimpleObservable<SpeciesAdditions> = new SimpleObservable()
   public open val resourcePath: String
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.AdditionParameter>

   public override fun reload(data: Map<ResourceLocation, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.AdditionParameter>) {
      for (Entry var3 : data.entrySet()) {
         val identifier: ResourceLocation = var3.getKey() as ResourceLocation;
         val parameter: SpeciesAdditions.AdditionParameter = var3.getValue() as SpeciesAdditions.AdditionParameter;
         val species: Species = PokemonSpecies.INSTANCE.getByIdentifier(parameter.getTargetIdentifier());
         if (species == null) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("Cannot find species {} for addition {}, skipping", parameter.getTargetIdentifier().toString(), identifier.toString());
         } else {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               val addition: SpeciesAdditions.Addition = `element$iv` as SpeciesAdditions.Addition;

               try {
                  var e: Any = addition.getValue();
                  if (TypeIntrinsics.isMutableCollection(e)) {
                     val var21: Any = addition.getProperty().getGetter().call(new Object[]{species});
                     val existing: java.util.Collection = TypeIntrinsics.asMutableCollection(var21);
                     existing.addAll(CollectionsKt.filterNotNull(e as java.lang.Iterable));
                     e = existing;
                  } else if (TypeIntrinsics.isMutableMap(e)) {
                     val var23: Any = addition.getProperty().getGetter().call(new Object[]{species});
                     val var19: java.util.Map = TypeIntrinsics.asMutableMap(var23);
                     var19.putAll(TypeIntrinsics.asMutableMap(e));
                     e = var19;
                  }

                  addition.getProperty().getSetter().call(new Object[]{species, e});
               } catch (var16: Exception) {
                  Cobblemon.INSTANCE
                     .getLOGGER()
                     .error("Caught exception applying addition {} to {}", identifier.toString(), parameter.getTargetIdentifier().toString(), var16);
               }
            }
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Finished additions");
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var10000: Gson = PokemonSpecies.INSTANCE
         .getGson()
         .newBuilder()
         .registerTypeAdapter(SpeciesAdditions.AdditionParameter::class.java, SpeciesAdditions.AdditionParameterAdapter.INSTANCE)
         .create();
      gson = var10000;
      val var0: TypeToken = TypeToken.get(SpeciesAdditions.AdditionParameter.class);
      typeToken = var0;
      val var1: java.lang.String = INSTANCE.getId().m_135815_();
      resourcePath = var1;
   }

   public data Addition(property: KMutableProperty<*>, value: Any) {
      public final val property: KMutableProperty<*>
      public final val value: Any

      init {
         this.property = property;
         this.value = value;
      }

      public operator fun component1(): KMutableProperty<*> {
         return this.property;
      }

      public operator fun component2(): Any {
         return this.value;
      }

      public fun copy(property: KMutableProperty<*> = this.property, value: Any = this.value): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.Addition {
         return new SpeciesAdditions.Addition(property, value);
      }

      public override fun toString(): String {
         return "Addition(property=${this.property}, value=${this.value})";
      }

      public override fun hashCode(): Int {
         return this.property.hashCode() * 31 + this.value.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is SpeciesAdditions.Addition) {
            return false;
         } else {
            val var2: SpeciesAdditions.Addition = other as SpeciesAdditions.Addition;
            if (!(this.property == (other as SpeciesAdditions.Addition).property)) {
               return false;
            } else {
               return this.value == var2.value;
            }
         }
      }
   }

   public data AdditionParameter(targetIdentifier: ResourceLocation, additions: Collection<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.Addition>) {
      public final val additions: Collection<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.Addition>
      public final val targetIdentifier: ResourceLocation

      init {
         this.targetIdentifier = targetIdentifier;
         this.additions = additions;
      }

      public operator fun component1(): ResourceLocation {
         return this.targetIdentifier;
      }

      public operator fun component2(): Collection<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.Addition> {
         return this.additions;
      }

      public fun copy(
         targetIdentifier: ResourceLocation = this.targetIdentifier,
         additions: Collection<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.Addition> = this.additions
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.AdditionParameter {
         return new SpeciesAdditions.AdditionParameter(targetIdentifier, additions);
      }

      public override fun toString(): String {
         return "AdditionParameter(targetIdentifier=${this.targetIdentifier}, additions=${this.additions})";
      }

      public override fun hashCode(): Int {
         return this.targetIdentifier.hashCode() * 31 + this.additions.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is SpeciesAdditions.AdditionParameter) {
            return false;
         } else {
            val var2: SpeciesAdditions.AdditionParameter = other as SpeciesAdditions.AdditionParameter;
            if (!(this.targetIdentifier == (other as SpeciesAdditions.AdditionParameter).targetIdentifier)) {
               return false;
            } else {
               return this.additions == var2.additions;
            }
         }
      }
   }

   public object AdditionParameterAdapter : JsonDeserializer<SpeciesAdditions.AdditionParameter> {
      private const val TARGET: String = "target"
      private final val properties: HashMap<String, KMutableProperty<*>> = new HashMap()

      public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions.AdditionParameter {
         val jObject: JsonObject = element.getAsJsonObject();
         var var10000: java.lang.String = jObject.get("target").getAsString();
         val target: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null);
         val additions: ArrayList = new ArrayList();

         for (Entry var8 : jObject.entrySet()) {
            val key: java.lang.String = var8.getKey() as java.lang.String;
            val jElement: JsonElement = var8.getValue() as JsonElement;
            if (!(key == "target") && properties.containsKey(key)) {
               var10000 = properties.get(key);
               val property: KMutableProperty = var10000 as KMutableProperty;
               val value: Any = context.deserialize(jElement, TypesJVMKt.getJavaType((var10000 as KMutableProperty).getReturnType()));
               val var14: java.util.Collection = additions;
               var14.add(new SpeciesAdditions.Addition(property, value));
            }
         }

         return new SpeciesAdditions.AdditionParameter(target, additions);
      }

      @JvmStatic
      fun {
         for (KProperty1 property : KClasses.getDeclaredMemberProperties(Species::class)) {
            if (!property.isLateinit() && property is KMutableProperty) {
               if (!KCallablesJvm.isAccessible(property as KCallable)) {
                  KCallablesJvm.setAccessible(property as KCallable, true);
               }

               properties.put(property.getName(), property);
            }
         }
      }
   }
}
