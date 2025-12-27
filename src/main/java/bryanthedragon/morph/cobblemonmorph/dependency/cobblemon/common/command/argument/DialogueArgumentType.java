package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.mojang.brigadier.ImmutableStringReader
import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nDialogueArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueArgumentType.kt\ncom/cobblemon/mod/common/command/argument/DialogueArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,54:1\n1549#2:55\n1620#2,3:56\n*S KotlinDebug\n*F\n+ 1 DialogueArgumentType.kt\ncom/cobblemon/mod/common/command/argument/DialogueArgumentType\n*L\n50#1:55\n50#1:56,3\n*E\n"])
public class DialogueArgumentType : ArgumentType<ResourceLocation> {
   public open fun parse(reader: StringReader): ResourceLocation {
      try {
         return ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(reader, null, 1, null);
      } catch (var3: Exception) {
         val var10000: CommandSyntaxException = new SimpleCommandExceptionType(INVALID_DIALOGUE as Message).createWithContext(reader as ImmutableStringReader);
         throw var10000 as java.lang.Throwable;
      }
   }

   public open fun <S : Any> listSuggestions(context: CommandContext<Any>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
      val `$this$map$iv`: java.lang.Iterable = Dialogues.INSTANCE.getDialogues().keySet();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            if ((`item$iv$iv` as ResourceLocation).m_135827_() == "cobblemon")
               (`item$iv$iv` as ResourceLocation).m_135815_()
               else
               (`item$iv$iv` as ResourceLocation).toString()
         );
      }

      val var10000: CompletableFuture = SharedSuggestionProvider.m_82970_(`destination$iv$iv` as java.util.List, builder);
      return var10000;
   }

   public open fun getExamples(): List<String> {
      return EXAMPLES;
   }

   public companion object {
      public final val EXAMPLES: List<String>
      public final val INVALID_DIALOGUE: MutableComponent

      public fun dialogue(): DialogueArgumentType {
         return new DialogueArgumentType();
      }

      public fun <S> getDialogue(context: CommandContext<Any>, name: String): ResourceLocation {
         val var10000: Any = context.getArgument(name, ResourceLocation.class);
         return var10000 as ResourceLocation;
      }
   }
}
