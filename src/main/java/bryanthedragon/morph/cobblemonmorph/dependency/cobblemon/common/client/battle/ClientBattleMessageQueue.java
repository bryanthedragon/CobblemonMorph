package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.util.FormattedCharSequence

@SourceDebugExtension(["SMAP\nClientBattleMessageQueue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattleMessageQueue.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,34:1\n1855#2,2:35\n1855#2,2:37\n*S KotlinDebug\n*F\n+ 1 ClientBattleMessageQueue.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleMessageQueue\n*L\n27#1:35,2\n32#1:37,2\n*E\n"])
public class ClientBattleMessageQueue {
   public final var listeners: MutableList<(FormattedCharSequence) -> Unit> = (new ArrayList()) as java.util.List
   private final val messages: MutableList<FormattedCharSequence> = (new ArrayList()) as java.util.List

   public fun add(messages: Iterable<FormattedCharSequence>) {
      CollectionsKt.addAll(this.messages, messages);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val listener: Function1 = `element$iv` as Function1;

         for (Object element$ivx : messages) {
            listener.invoke(`element$ivx`);
         }
      }
   }

   public fun subscribe(listener: (FormattedCharSequence) -> Unit) {
      this.listeners.add(listener);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         listener.invoke(`element$iv`);
      }
   }
}
