package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item.LeftoversCreatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.NoPokemonStoreException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
   @Shadow
   public abstract CompoundTag m_36331_();

   @Shadow
   public abstract CompoundTag m_36332_();

   @Shadow
   public abstract void m_36370_(CompoundTag entityNbt);

   @Shadow
   public abstract void m_36364_(CompoundTag entityNbt);

   @Shadow
   public abstract void m_36362_(CompoundTag entityNbt);

   @Shadow
   public abstract boolean m_5833_();

   @Shadow
   public abstract boolean m_36356_(ItemStack stack);

   @Shadow
   public abstract void m_5661_(Component message, boolean overlay);

   protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
      super(p_20966_, p_20967_);
   }

   @Inject(
      method = "dropShoulderEntity",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/EntityType;getEntityFromNbt(Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/world/World;)Ljava/util/Optional;"
      ),
      cancellable = true
   )
   private void cobblemon$removePokemon(CompoundTag nbt, CallbackInfo ci) {
      if (CompoundTagExtensionsKt.isPokemonEntity(nbt)) {
         UUID uuid = this.getPokemonID(nbt);
         if (this.isShoulderPokemon(this.m_36332_())) {
            UUID uuidRight = this.getPokemonID(this.m_36332_());
            if (uuid.equals(uuidRight)) {
               this.recallPokemon(uuidRight);
               this.m_36364_(new CompoundTag());
            }
         }

         if (this.isShoulderPokemon(this.m_36331_())) {
            UUID uuidLeft = this.getPokemonID(this.m_36331_());
            if (uuid.equals(uuidLeft)) {
               this.recallPokemon(uuidLeft);
               this.m_36362_(new CompoundTag());
            }
         }

         ci.cancel();
      }
   }

   @Inject(method = "dropShoulderEntities", at = @At(value = "JUMP", opcode = 156, ordinal = 0, shift = Shift.AFTER), cancellable = true)
   private void cobblemon$preventPokemonDropping(CallbackInfo ci) {
      if (!this.m_5833_() && !this.m_21224_()) {
         if (!this.isShoulderPokemon(this.m_36331_())) {
            this.m_36370_(this.m_36331_());
            this.m_36362_(new CompoundTag());
         }

         if (!this.isShoulderPokemon(this.m_36332_())) {
            this.m_36370_(this.m_36332_());
            this.m_36364_(new CompoundTag());
         }

         ci.cancel();
      }
   }

   private UUID getPokemonID(CompoundTag nbt) {
      return nbt.m_128469_("Pokemon").m_128342_("UUID");
   }

   private void recallPokemon(UUID uuid) {
      try {
         for (Pokemon pokemon : Cobblemon.INSTANCE.getStorage().getParty(this.f_19820_)) {
            if (pokemon.getUuid().equals(uuid)) {
               pokemon.recall();
            }
         }
      } catch (NoPokemonStoreException var5) {
      }
   }

   private boolean isShoulderPokemon(CompoundTag nbt) {
      return CompoundTagExtensionsKt.isPokemonEntity(nbt);
   }

   @Inject(
      method = "eatFood",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;getHungerManager()Lnet/minecraft/entity/player/HungerManager;",
         shift = Shift.AFTER
      )
   )
   public void onEatFood(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
      if (!this.m_9236_().f_46443_
         && stack.m_204117_(CobblemonItemTags.LEAVES_LEFTOVERS)
         && this.m_9236_().f_46441_.m_188500_() < Cobblemon.config.getAppleLeftoversChance()) {
         ItemStack leftovers = new ItemStack(CobblemonItems.LEFTOVERS);
         ServerPlayer player = Objects.requireNonNull(this.m_20194_()).m_6846_().m_11259_(this.f_19820_);

         assert player != null;

         CobblemonEvents.LEFTOVERS_CREATED
            .postThen(
               new LeftoversCreatedEvent(player, leftovers),
               leftoversCreatedEvent -> null,
               leftoversCreatedEvent -> {
                  if (!player.m_36356_(leftoversCreatedEvent.getLeftovers())) {
                     Vec3 itemPos = player.m_20154_().m_82490_(0.5).m_82549_(this.m_20182_());
                     this.m_9236_()
                        .m_7967_(new ItemEntity(this.m_9236_(), itemPos.m_7096_(), itemPos.m_7098_(), itemPos.m_7094_(), leftoversCreatedEvent.getLeftovers()));
                  }

                  return null;
               }
            );
      }
   }
}
