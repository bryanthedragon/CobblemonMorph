package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.effects.PotionBaseEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.NoSuchElementException
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.ApiStatus.Internal

@SourceDebugExtension(["SMAP\nShoulderEffectRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShoulderEffectRegistry.kt\ncom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffectRegistry\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,67:1\n1#2:68\n766#3:69\n857#3,2:70\n1855#3:72\n1855#3,2:73\n1856#3:75\n*S KotlinDebug\n*F\n+ 1 ShoulderEffectRegistry.kt\ncom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffectRegistry\n*L\n56#1:69\n56#1:70,2\n56#1:72\n57#1:73,2\n56#1:75\n*E\n"])
public object ShoulderEffectRegistry {
   public final val POTION_EFFECT: Class<out ShoulderEffect> = INSTANCE.register("potion_effect", PotionBaseEffect.class)
   private final val effects: MutableMap<String, Class<out ShoulderEffect>> = (new LinkedHashMap()) as java.util.Map

   internal fun register() {
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGIN, null, (new Function1<ServerPlayerEvent.Login, Unit>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         public final void invoke(@NotNull ServerPlayerEvent.Login it) {
            ShoulderEffectRegistry.access$refreshEffects(this.this$0, it.getPlayer());
         }
      }) as Function1, 1, null);
   }

   public fun register(name: String, effect: Class<out ShoulderEffect>): Class<out ShoulderEffect> {
      effects.put(name, effect);
      return effect;
   }

   public fun unregister(name: String): Class<out ShoulderEffect>? {
      return effects.remove(name);
   }

   public fun getName(clazz: Class<out ShoulderEffect>): String {
      val var2: java.util.Iterator = effects.entrySet().iterator();

      var var10000: java.lang.String;
      do {
         if (!var2.hasNext()) {
            var10000 = null;
            break;
         }

         val it: Entry = var2.next() as Entry;
         var10000 = if (it.getValue() == clazz) it.getKey() as java.lang.String else null;
      } while (var10000 == null);

      if (var10000 == null) {
         throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
      } else {
         return var10000;
      }
   }

   public fun get(name: String): Class<out ShoulderEffect>? {
      return effects.get(name);
   }

   @Internal
   public fun onEffectEnd(player: ServerPlayer) {
      ServerTaskTracker.INSTANCE.momentarily((new Function0<Unit>(this, player) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$player = `$player`;
         }

         public final void invoke() {
            ShoulderEffectRegistry.access$refreshEffects(this.this$0, this.$player);
         }
      }) as () -> Unit);
   }

   private fun refreshEffects(player: ServerPlayer) {
      val `$this$forEach$iv`: java.lang.Iterable = PlayerExtensionsKt.party(player);
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((`$this$forEach$iv` as Pokemon).getState() is ShoulderedState) {
            `element$iv`.add(`$this$forEach$iv`);
         }
      }

      for (Object element$ivx : $this$filter$iv) {
         val var17: Pokemon = `element$ivx` as Pokemon;

         val var19: java.lang.Iterable;
         for (Object element$ivxx : var19) {
            val it: ShoulderEffect = `element$ivxx` as ShoulderEffect;
            val var10003: PokemonState = var17.getState();
            it.applyEffect(var17, player, (var10003 as ShoulderedState).isLeftShoulder());
         }
      }
   }
}
