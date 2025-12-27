package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueActionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueFaceProviderAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueInputAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialoguePredicateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueTextAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TextAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nDialogues.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Dialogues.kt\ncom/cobblemon/mod/common/api/dialogue/Dialogues\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,72:1\n1#2:73\n*E\n"])
public object Dialogues : JsonDataRegistry<Dialogue> {
   public final val dialogues: MutableMap<ResourceLocation, Dialogue> = (new LinkedHashMap()) as java.util.Map
   public open val gson: Gson
   public final val gsonObservable: SimpleObservable<GsonBuilder> = new SimpleObservable()
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("dialogues")
   public open val observable: SimpleObservable<Dialogues> = new SimpleObservable()
   public open val resourcePath: String = "dialogues"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<Dialogue> = TypeToken.get(Dialogue.class)

   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(data: Map<ResourceLocation, Dialogue>) {
      dialogues.putAll(data);
      this.getObservable().emit(this);
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var0: GsonBuilder = new GsonBuilder()
         .registerTypeAdapter(DialogueAction::class.java, DialogueActionAdapter.INSTANCE)
         .registerTypeAdapter(DialoguePredicate::class.java, DialoguePredicateAdapter.INSTANCE)
         .registerTypeAdapter(DialogueInput::class.java, DialogueInputAdapter.INSTANCE)
         .registerTypeAdapter(DialogueFaceProvider::class.java, DialogueFaceProviderAdapter.INSTANCE)
         .registerTypeAdapter(DialogueText::class.java, DialogueTextAdapter.INSTANCE)
         .registerTypeAdapter(Expression::class.java, ExpressionAdapter.INSTANCE)
         .registerTypeAdapter(ExpressionLike::class.java, ExpressionLikeAdapter.INSTANCE)
         .registerTypeAdapter(MutableComponent::class.java, TextAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE);
      val var10000: SimpleObservable = gsonObservable;
      val var3: Array<GsonBuilder> = new GsonBuilder[1];
      var3[0] = var0;
      var10000.emit(var3);
      gson = var0.create();
   }
}
