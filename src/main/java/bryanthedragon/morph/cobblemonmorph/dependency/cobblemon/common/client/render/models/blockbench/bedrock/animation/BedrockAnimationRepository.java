package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.BedrockAnimationReferenceFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStream
import java.io.InputStreamReader
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nBedrockAnimationRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,70:1\n215#2:71\n216#2:73\n17#3:72\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository\n*L\n44#1:71\n44#1:73\n46#1:72\n*E\n"])
public object BedrockAnimationRepository {
   private final val animationGroups: MutableMap<String, BedrockAnimationGroup> = (new LinkedHashMap()) as java.util.Map
   private final val gson: Gson =
      new GsonBuilder()
         .disableHtmlEscaping()
         .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
         .registerTypeAdapter(BedrockAnimation::class.java, BedrockAnimationAdapter.INSTANCE)
         .create()

   public fun loadAnimations(resourceManager: ResourceManager, directories: List<String>) {
      JsonPokemonPoseableModel.Companion.registerFactory("bedrock", BedrockAnimationReferenceFactory.INSTANCE);
      Cobblemon.INSTANCE.getLOGGER().info("Loading animations...");
      var animationCount: Int = 0;
      animationGroups.clear();

      for (java.lang.String directory : directories) {
         var var10000: java.util.Map = resourceManager.m_214159_(directory, BedrockAnimationRepository::loadAnimations$lambda$0);

         for (Entry element$iv : var10000.entrySet()) {
            val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
            val resource: Resource = `element$iv`.getValue() as Resource;

            try {
               val var22: Gson = gson;
               val var23: InputStream = resource.m_215507_();
               val e: BedrockAnimationGroup = var22.fromJson(new InputStreamReader(var23, Charsets.UTF_8), BedrockAnimationGroup.class) as BedrockAnimationGroup;
               val var24: java.lang.String = identifier.m_135815_();
               val var19: java.lang.String = StringsKt.replace$default(
                  StringsKt.substringAfterLast$default(var24, "/", null, 2, null), ".animation.json", "", false, 4, null
               );
               var10000 = animationGroups;
               var10000.put(var19, e);
               animationCount += e.getAnimations().size();
            } catch (var18: Exception) {
               Cobblemon.INSTANCE.getLOGGER().error("Failed to load animation group $identifier", var18);
            }
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded $animationCount animations from ${animationGroups.size()} animation groups");
   }

   public fun tryGetAnimation(fileName: String, animationName: String): BedrockAnimation? {
      val var10000: BedrockAnimationGroup = animationGroups.get(fileName);
      return if (var10000 == null) null else var10000.getAnimations().get(animationName);
   }

   public fun getAnimation(fileName: String, animationName: String): BedrockAnimation {
      val var10000: BedrockAnimationGroup = animationGroups.get(fileName);
      if (var10000 == null) {
         throw new IllegalArgumentException("Unknown animation group: $fileName");
      } else {
         val var4: BedrockAnimation = var10000.getAnimations().get(animationName);
         if (var4 == null) {
            throw new IllegalArgumentException("Animation $animationName not found in animation group $fileName");
         } else {
            return var4;
         }
      }
   }

   @JvmStatic
   fun `loadAnimations$lambda$0`(it: ResourceLocation): Boolean {
      val var10000: java.lang.String = it.m_135815_();
      return StringsKt.endsWith$default(var10000, ".animation.json", false, 2, null);
   }
}
