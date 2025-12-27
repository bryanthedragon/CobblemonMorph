package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon.PokemonRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MovingSoundInstance
import com.mojang.blaze3d.vertex.PoseStack
import java.util.Locale
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.ObjectRef
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.Nullable
import org.joml.Matrix4f

@SourceDebugExtension(["SMAP\nPokemonClientDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/PokemonClientDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,319:1\n1#2:320\n*E\n"])
public class PokemonClientDelegate : PoseableEntityState<PokemonEntity>, PokemonSideDelegate {
   public final var ballDone: Boolean
   public final var ballOffset: Float
   public final var ballRotOffset: Float
   public final var ballStartTime: Long = System.currentTimeMillis()
   public final var beamStartTime: Long = System.currentTimeMillis()
   private final var cryAnimation: StatefulAnimation<PokemonEntity, *>?
   public final lateinit var currentEntity: PokemonEntity
   public final var entityScaleModifier: Float = 1.0F
   public final var phaseTarget: Entity?
   public final var playedSendOutSound: Boolean
   public final var playedThrowingSound: Boolean

   public open val schedulingTracker: SchedulingTracker
      public open get() {
         return this.getCurrentEntity().getSchedulingTracker();
      }


   public final val secondsSinceBallThrown: Float
      public final get() {
         return (float)(System.currentTimeMillis() - this.ballStartTime) / 1000.0F;
      }


   public final val secondsSinceBeamEffectStarted: Float
      public final get() {
         return (float)(System.currentTimeMillis() - this.beamStartTime) / 1000.0F;
      }


   public final var sendOutOffset: Vec3?
   public final var sendOutPosition: Vec3?

   public open fun getEntity(): PokemonEntity {
      return this.getCurrentEntity();
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(partialTicks);
      this.getSchedulingTracker().update(0.0F);
   }

   public override fun onTrackedDataSet(data: EntityDataAccessor<*>) {
      PokemonSideDelegate.DefaultImpls.onTrackedDataSet(this, data);
      if (this.currentEntity != null) {
         if (data == PokemonEntity.Companion.getSPECIES()) {
            val phasingTargetId: ResourceLocation = new ResourceLocation(
               this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getSPECIES()) as java.lang.String
            );
            this.setCurrentPose(null);
            val var10000: Pokemon = this.getCurrentEntity().getPokemon();
            val var10001: Species = PokemonSpecies.INSTANCE.getByIdentifier(phasingTargetId);
            var10000.setSpecies(var10001);
            this.setCurrentModel(PokemonModelRepository.INSTANCE.getPoser(phasingTargetId, this.getCurrentEntity().getAspects()));
         } else if (data == PokemonEntity.Companion.getDYING_EFFECTS_STARTED()) {
            val var19: java.lang.Boolean = this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getDYING_EFFECTS_STARTED()) as java.lang.Boolean;
            if (var19) {
               val var34: PoseableEntityModel = this.getCurrentModel();
               if (var34 == null) {
                  return;
               }

               val model: PokemonPoseableModel = var34 as PokemonPoseableModel;

               var sound: StatefulAnimation;
               try {
                  sound = model.getAnimation(this as PoseableEntityState<?>, "faint", this.getRuntime());
               } catch (var18: Exception) {
                  var18.printStackTrace();
                  sound = null;
               }

               if (sound == null) {
                  return;
               }

               val var5: PrimaryAnimation = new PrimaryAnimation(sound, null, null, false, 14, null);
               this.after(3.0F, (new Function0<Unit>(this) {
                  {
                     super(0);
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke() {
                     this.this$0.setEntityScaleModifier(0.0F);
                  }
               }) as () -> Unit);
               this.addPrimaryAnimation(var5);
            }
         } else if (data == PokemonEntity.Companion.getBEAM_MODE()) {
            switch (this.getCurrentEntity().getBeamMode()) {
               case 0:
               default:
                  break;
               case 1:
                  this.playedSendOutSound = false;
                  this.entityScaleModifier = 0.0F;
                  this.beamStartTime = System.currentTimeMillis();
                  this.ballStartTime = System.currentTimeMillis();
                  this.getCurrentEntity().m_6842_(true);
                  this.ballDone = false;
                  val var25: ObjectRef = new ObjectRef();
                  var25.element = this.getCurrentEntity().m_20182_();
                  val var35: UUID = this.getCurrentEntity().getPokemon().getOwnerUUID();
                  if (var35 != null) {
                     val var36: Player = this.getCurrentEntity().m_9236_().m_46003_(var35);
                     if (var36 != null) {
                        val offset: Vec3 = var36.m_20182_()
                           .m_82546_(this.getCurrentEntity().m_20182_().m_82520_(0.0, 2.0 - (double)this.ballOffset / (double)10.0F, 0.0))
                           .m_82541_()
                           .m_82490_(-PokemonRenderer.Companion.ease((double)this.ballOffset));
                        val `$this$onTrackedDataSet_u24lambda_u242_u24lambda_u241_u24lambda_u240`: Vec3 = var36.m_20182_()
                           .m_82546_(this.getCurrentEntity().m_20182_());
                        var25.element = this.getCurrentEntity()
                           .m_20182_()
                           .m_82549_(offset.m_82490_(2.0).m_82490_(var36.m_20182_().m_82554_(this.getCurrentEntity().m_20182_()) / 10.0 * (double)5));
                        val var17: InteractionHand = var36.m_7655_();
                        val var37: InteractionHand;
                        if (var17 == null) {
                           var37 = InteractionHand.MAIN_HAND;
                        } else {
                           var37 = var17;
                        }

                        var36.m_6674_(var37);
                     }
                  }

                  val var29: Minecraft = Minecraft.m_91087_();
                  val var10002: SoundEvent = SoundEvent.m_262824_(CobblemonSounds.POKE_BALL_TRAIL.m_11660_());
                  val var32: MovingSoundInstance = new MovingSoundInstance(var10002, SoundSource.PLAYERS, (new Function0<Vec3>(this) {
                     {
                        super(0);
                        this.this$0 = `$receiver`;
                     }

                     @Nullable
                     public final Vec3 invoke() {
                        val var10000: Vec3 = this.this$0.getSendOutPosition();
                        return if (var10000 != null) var10000.m_82549_(this.this$0.getSendOutOffset()) else null;
                     }
                  }) as () -> Vec3, 0.1F, 1.0F, false, 20, 0);
                  if (!this.playedThrowingSound) {
                     var29.m_91106_().m_120367_(var32 as SoundInstance);
                     this.playedThrowingSound = true;
                  }

                  SchedulingFunctionsKt.lerpOnClient(0.5F, (new Function1<java.lang.Float, Unit>(this) {
                     {
                        super(1);
                        this.this$0 = `$receiver`;
                     }

                     public final void invoke(float it) {
                        this.this$0.setBallOffset(it);
                     }
                  }) as (java.lang.Float?) -> Unit);
                  this.ballRotOffset = (float)(Math.random() * this.getCurrentEntity().m_9236_().f_46441_.m_216332_(-15, 15));
                  this.getCurrentEntity()
                     .after(
                        0.5F,
                        (
                           new Function0<Unit>(this, var29, var25) {
                              {
                                 super(0);
                                 this.this$0 = `$receiver`;
                                 this.$client = `$client`;
                                 this.$soundPos = `$soundPos`;
                              }

                              public final void invoke() {
                                 this.this$0.setBeamStartTime(System.currentTimeMillis());
                                 this.this$0.setBallDone(true);
                                 if (this.$client.m_91106_().m_120384_(CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()) != null
                                    && !this.this$0.getPlayedSendOutSound()) {
                                    if (this.$client.f_91073_ != null) {
                                       this.$client
                                          .f_91073_
                                          .m_6263_(
                                             this.$client.f_91074_ as Player,
                                             (this.$soundPos.element as Vec3).f_82479_,
                                             (this.$soundPos.element as Vec3).f_82480_,
                                             (this.$soundPos.element as Vec3).f_82481_,
                                             SoundEvent.m_262824_(CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()),
                                             SoundSource.PLAYERS,
                                             0.6F,
                                             1.0F
                                          );
                                    }

                                    this.this$0.setPlayedSendOutSound(true);
                                 }

                                 val var10000: Vec3 = this.this$0.getSendOutPosition();
                                 if (var10000 != null) {
                                    val var2: PokemonClientDelegate = this.this$0;
                                    val newPos: Vec3 = var10000.m_82549_(this.this$0.getSendOutOffset());
                                    val var16: java.lang.String = var2.getCurrentEntity().getPokemon().getCaughtBall().getName().m_135815_();
                                    val var17: java.lang.String = var16.toLowerCase(Locale.ROOT);
                                    val ballType: java.lang.String = StringsKt.replace$default(var17, "_", "", false, 4, null);
                                    val mode: java.lang.String = if (var2.getCurrentEntity().isBattling()) "battle" else "casual";
                                    val sendflash: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE
                                       .getEffect(MiscUtilsKt.cobblemonResource("$ballType/$mode/sendflash"));
                                    if (sendflash != null) {
                                       val wrapper: MatrixWrapper = new MatrixWrapper();
                                       val matrix: PoseStack = new PoseStack();
                                       matrix.m_85837_(newPos.f_82479_, newPos.f_82480_, newPos.f_82481_);
                                       val var10001: Matrix4f = matrix.m_85850_().m_252922_();
                                       wrapper.updateMatrix(var10001);
                                       val var18: ClientLevel = Minecraft.m_91087_().f_91073_;
                                       if (var18 != null) {
                                          val world: ClientLevel = var18;
                                          new ParticleStorm(sendflash, wrapper, world, null, null, null, null, null, null, 504, null).spawn();
                                          val ballsparks: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE
                                             .getEffect(MiscUtilsKt.cobblemonResource("$ballType/$mode/ballsparks"));
                                          val ballsendsparkle: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE
                                             .getEffect(MiscUtilsKt.cobblemonResource("$ballType/$mode/ballsendsparkle"));
                                          SchedulingFunctionsKt.afterOnClient$default(
                                             0,
                                             0.01667F,
                                             (
                                                new Function0<Unit>(ballsparks, ballsendsparkle, var2, wrapper, world, ballType) {
                                                   {
                                                      super(0);
                                                      this.$ballsparks = `$ballsparks`;
                                                      this.$ballsendsparkle = `$ballsendsparkle`;
                                                      this.this$0 = `$receiver`;
                                                      this.$wrapper = `$wrapper`;
                                                      this.$world = `$world`;
                                                      this.$ballType = `$ballType`;
                                                   }

                                                   public final void invoke() {
                                                      if (this.$ballsparks != null) {
                                                         new ParticleStorm(
                                                               this.$ballsparks, this.$wrapper, this.$world, null, null, null, null, null, null, 504, null
                                                            )
                                                            .spawn();
                                                      }

                                                      if (this.$ballsendsparkle != null) {
                                                         new ParticleStorm(
                                                               this.$ballsendsparkle, this.$wrapper, this.$world, null, null, null, null, null, null, 504, null
                                                            )
                                                            .spawn();
                                                      }

                                                      this.this$0
                                                         .getCurrentEntity()
                                                         .after(
                                                            0.4F,
                                                            (
                                                               new Function0<Unit>(this.$ballType, this.$wrapper, this.$world) {
                                                                  {
                                                                     super(0);
                                                                     this.$ballType = `$ballType`;
                                                                     this.$wrapper = `$wrapper`;
                                                                     this.$world = `$world`;
                                                                  }

                                                                  public final void invoke() {
                                                                     val ballsparkle: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE
                                                                        .getEffect(MiscUtilsKt.cobblemonResource("${this.$ballType}/ballsparkle"));
                                                                     if (ballsparkle != null) {
                                                                        new ParticleStorm(
                                                                              ballsparkle,
                                                                              this.$wrapper,
                                                                              this.$world,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              504,
                                                                              null
                                                                           )
                                                                           .spawn();
                                                                     }
                                                                  }
                                                               }
                                                            ) as () -> Unit
                                                         );
                                                   }
                                                }
                                             ) as Function0,
                                             1,
                                             null
                                          );
                                       }
                                    }
                                 }

                                 this.this$0.getCurrentEntity().after(0.2F, (new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       SchedulingFunctionsKt.lerpOnClient(0.4F, (new Function1<java.lang.Float, Unit>(this.this$0) {
                                          {
                                             super(1);
                                             this.this$0 = `$receiver`;
                                          }

                                          public final void invoke(float it) {
                                             this.this$0.setEntityScaleModifier(it);
                                          }
                                       }) as (java.lang.Float?) -> Unit);
                                       this.this$0.getCurrentEntity().m_6842_(false);
                                       this.this$0.getCurrentEntity().after(1.0F, (new Function0<Unit>(this.this$0) {
                                          {
                                             super(0);
                                             this.this$0 = `$receiver`;
                                          }

                                          public final void invoke() {
                                             this.this$0.setBallOffset(0.0F);
                                             this.this$0.setBallRotOffset(0.0F);
                                             this.this$0.setSendOutPosition(null);
                                          }
                                       }) as () -> Unit);
                                    }
                                 }) as () -> Unit);
                              }
                           }
                        ) as () -> Unit
                     );
                  break;
               case 2:
                  this.playedSendOutSound = false;
                  this.entityScaleModifier = 0.0F;
                  this.getCurrentEntity().m_6842_(false);
                  val it: Vec3 = this.getCurrentEntity().m_20182_();
                  val var28: Minecraft = Minecraft.m_91087_();
                  if (var28.m_91106_().m_120384_(CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()) != null && !this.playedSendOutSound) {
                     if (var28.f_91073_ != null) {
                        var28.f_91073_
                           .m_6263_(
                              var28.f_91074_ as Player,
                              it.f_82479_,
                              it.f_82480_,
                              it.f_82481_,
                              CobblemonSounds.POKE_BALL_SEND_OUT,
                              SoundSource.PLAYERS,
                              0.6F,
                              1.0F
                           );
                     }

                     this.playedSendOutSound = true;
                  }

                  SchedulingFunctionsKt.lerpOnClient(0.4F, (new Function1<java.lang.Float, Unit>(this) {
                     {
                        super(1);
                        this.this$0 = `$receiver`;
                     }

                     public final void invoke(float it) {
                        this.this$0.setEntityScaleModifier(it);
                     }
                  }) as (java.lang.Float?) -> Unit);
                  this.getCurrentEntity().after(0.8F, (new Function0<Unit>(this) {
                     {
                        super(0);
                        this.this$0 = `$receiver`;
                     }

                     public final void invoke() {
                        this.this$0.setBallOffset(0.0F);
                        this.this$0.setBallRotOffset(0.0F);
                        this.this$0.setSendOutPosition(null);
                     }
                  }) as () -> Unit);
                  break;
               case 3:
                  this.entityScaleModifier = 1.0F;
                  this.beamStartTime = System.currentTimeMillis();
                  this.ballOffset = 0.0F;
                  this.ballRotOffset = 0.0F;
                  this.sendOutPosition = null;
                  SchedulingFunctionsKt.afterOnClient$default(0, 0.2F, (new Function0<Unit>(this) {
                     {
                        super(0);
                        this.this$0 = `$receiver`;
                     }

                     public final void invoke() {
                        SchedulingFunctionsKt.lerpOnClient(0.4F, (new Function1<java.lang.Float, Unit>(this.this$0) {
                           {
                              super(1);
                              this.this$0 = `$receiver`;
                           }

                           public final void invoke(float it) {
                              this.this$0.setEntityScaleModifier((float)1 - it);
                           }
                        }) as (java.lang.Float?) -> Unit);
                     }
                  }) as Function0, 1, null);
            }
         } else if (data == PokemonEntity.Companion.getLABEL_LEVEL()) {
            val var23: Any = this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getLABEL_LEVEL());
            val var26: Int = var23 as Int;
            val var21: Int = (if (var26 > 0) var23 else null) as Int;
            if (var21 != null) {
               this.getCurrentEntity().getPokemon().setLevel(var21.intValue());
            }
         } else if (data == PokemonEntity.Companion.getPHASING_TARGET_ID()) {
            val var22: Int = this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getPHASING_TARGET_ID()) as Int;
            if (var22 != null) {
               if (var22 == -1) {
                  this.phaseTarget = null;
                  return;
               }
            }

            this.setPhaseTarget(var22);
         }
      }
   }

   public override fun changePokemon(pokemon: Pokemon) {
      pokemon.setClient$common(true);
   }

   public open fun initialize(entity: PokemonEntity) {
      this.setCurrentEntity(entity);
      this.setAge(entity.f_19797_);
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = this.getRuntime().getEnvironment();
      var10000.addFunctions(
         MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null),
         MapsKt.mapOf(
            new Pair[]{
               TuplesKt.to("in_battle", PokemonClientDelegate::initialize$lambda$5),
               TuplesKt.to("shiny", PokemonClientDelegate::initialize$lambda$6),
               TuplesKt.to("form", PokemonClientDelegate::initialize$lambda$7),
               TuplesKt.to("width", PokemonClientDelegate::initialize$lambda$8),
               TuplesKt.to("height", PokemonClientDelegate::initialize$lambda$9),
               TuplesKt.to("weight", PokemonClientDelegate::initialize$lambda$10),
               TuplesKt.to("friendship", PokemonClientDelegate::initialize$lambda$11)
            }
         )
      );
   }

   public open fun tick(entity: PokemonEntity) {
      val var10001: Vec3 = entity.m_20182_();
      this.updateLocatorPosition(var10001);
      this.incrementAge(entity as Entity);
   }

   public fun setPhaseTarget(targetId: Int) {
      this.phaseTarget = this.getCurrentEntity().m_9236_().m_6815_(targetId);
   }

   public override fun handleStatus(status: Byte) {
      if (status == 10) {
         val var10000: PoseableEntityModel = this.getCurrentModel();
         if (var10000 == null) {
            return;
         }

         val var4: StatefulAnimation = (var10000 as PokemonPoseableModel).getEatAnimation(this.getCurrentEntity(), this as PoseableEntityState<PokemonEntity>);
         if (var4 == null) {
            return;
         }

         this.getStatefulAnimations().add(var4);
      }
   }

   public override fun updatePostDeath() {
      this.getCurrentEntity().f_20919_++;
   }

   public fun cry() {
      val var10000: PoseableEntityModel = this.getCurrentModel();
      if (var10000 != null) {
         if (var10000 is PokemonPoseableModel) {
            if (this.cryAnimation != null
               && (CollectionsKt.contains(this.getStatefulAnimations(), this.cryAnimation) || this.cryAnimation == this.getPrimaryAnimation())) {
               return;
            }

            val var3: StatefulAnimation = (var10000 as PokemonPoseableModel)
               .getCryAnimation()
               .invoke(this.getCurrentEntity(), this as PoseableEntityState<PokemonEntity>);
            if (var3 == null) {
               return;
            }

            if (var3 is PrimaryAnimation) {
               this.addPrimaryAnimation(var3 as PrimaryAnimation<PokemonEntity>);
            } else {
               this.getStatefulAnimations().add(var3);
            }

            this.cryAnimation = var3;
         }
      }
   }

   override fun drop(source: DamageSource?) {
      PokemonSideDelegate.DefaultImpls.drop(this, source);
   }

   @JvmStatic
   fun `initialize$lambda$5`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentEntity().isBattling());
   }

   @JvmStatic
   fun `initialize$lambda$6`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentEntity().getPokemon().getShiny());
   }

   @JvmStatic
   fun `initialize$lambda$7`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new StringValue(`this$0`.getCurrentEntity().getPokemon().getForm().getName());
   }

   @JvmStatic
   fun `initialize$lambda$8`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentEntity().m_20191_().m_82362_());
   }

   @JvmStatic
   fun `initialize$lambda$9`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentEntity().m_20191_().m_82376_());
   }

   @JvmStatic
   fun `initialize$lambda$10`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue((double)`this$0`.getCurrentEntity().getPokemon().getSpecies().getWeight());
   }

   @JvmStatic
   fun `initialize$lambda$11`(`this$0`: PokemonClientDelegate, it: MoParams): Any {
      return new DoubleValue((double)`this$0`.getCurrentEntity().getPokemon().getFriendship());
   }

   public companion object {
      public const val BEAM_EXTEND_TIME: Float
      public const val BEAM_SHRINK_TIME: Float
      public const val POKEBALL_AIR_TIME: Float
   }
}
