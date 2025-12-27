package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockParticleKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.QuirkData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.ArrayList;
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map.Entry
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPoseableEntityState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PoseableEntityState.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,300:1\n76#2:301\n96#2,5:302\n1#3:307\n12744#4,2:308\n18987#4,2:310\n4098#4,11:312\n3792#4:341\n4307#4,2:342\n1360#5:323\n1446#5,5:324\n766#5:329\n857#5:330\n2624#5,3:331\n858#5:334\n1855#5,2:335\n1855#5,2:337\n1855#5,2:339\n1855#5,2:344\n1549#5:346\n1620#5,3:347\n1549#5:350\n1620#5,3:351\n*S KotlinDebug\n*F\n+ 1 PoseableEntityState.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState\n*L\n153#1:301\n153#1:302,5\n207#1:308,2\n208#1:310,2\n236#1:312,11\n279#1:341\n279#1:342,2\n237#1:323\n237#1:324,5\n238#1:329\n238#1:330\n238#1:331,3\n238#1:334\n239#1:335,2\n250#1:337,2\n277#1:339,2\n279#1:344,2\n117#1:346\n117#1:347,3\n121#1:350\n121#1:351,3\n*E\n"])
public abstract class PoseableEntityState<T extends Entity> : Schedulable {
   protected final var age: Int

   public final val allStatefulAnimations: List<StatefulAnimation<Any, *>>
      public final get() {
         val var10000: java.util.Collection = this.statefulAnimations;
         val `$this$flatMap$iv`: java.util.Map = this.quirks;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Entry element$iv$iv : $this$flatMap$iv.entrySet()) {
            CollectionsKt.addAll(`destination$iv$iv`, (`element$iv$iv`.getValue() as QuirkData).getAnimations());
         }

         return CollectionsKt.plus(var10000, `destination$iv$iv` as java.util.List);
      }


   public final val animationSeconds: Float
      public final get() {
         return (this.age + this.getPartialTicks()) / 20.0F;
      }


   public final var currentModel: PoseableEntityModel<Any>?
      public final set(value) {
         var var3: QueryStruct;
         var var5: HashMap;
         var var10000: MoLangFunctions;
         label17: {
            this.currentModel = value;
            var10000 = MoLangFunctions.INSTANCE;
            val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
            val var10002: MoLangEnvironment = this.runtime.getEnvironment();
            var3 = MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null);
            if (value != null) {
               val var4: QueryStruct = value.getFunctions();
               if (var4 != null) {
                  var5 = var4.functions;
                  break label17;
               }
            }

            var5 = null;
         }

         var10000.addFunctions(var3, if (var5 == null) new HashMap<>() else var5);
      }


   protected final var currentPartialTicks: Float
   public final var currentPose: String?
   public final val locatorStates: MutableMap<String, MatrixWrapper>
   public final val poseParticles: MutableList<BedrockParticleKeyframe> = (new ArrayList()) as java.util.List
   public final var primaryAnimation: PrimaryAnimation<Any>?
   public final var primaryOverridePortion: Float
   public final val quirks: MutableMap<ModelQuirk<Any, *>, QuirkData<Any>> = (new LinkedHashMap()) as java.util.Map
   public final val renderQueue: ConcurrentLinkedQueue<() -> Unit>
   public final val runtime: MoLangRuntime
   public final val statefulAnimations: MutableList<StatefulAnimation<Any, *>> = (new ArrayList()) as java.util.List
   public final var timeEnteredPose: Float

   open fun PoseableEntityState() {
      val var1: MoLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
      val reusableAnimTime: DoubleValue = new DoubleValue(0.0);
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = var1.getEnvironment();
      var10000.addFunctions(
         MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null),
         MapsKt.mapOf(
            new Pair[]{
               TuplesKt.to("anim_time", PoseableEntityState::runtime$lambda$12$lambda$1),
               TuplesKt.to("pose_type", PoseableEntityState::runtime$lambda$12$lambda$2),
               TuplesKt.to("pose", PoseableEntityState::runtime$lambda$12$lambda$3),
               TuplesKt.to("has_entity", PoseableEntityState::runtime$lambda$12$lambda$4),
               TuplesKt.to("sound", PoseableEntityState::runtime$lambda$12$lambda$5),
               TuplesKt.to("play_animation", PoseableEntityState::runtime$lambda$12$lambda$6),
               TuplesKt.to("particle", PoseableEntityState::runtime$lambda$12$lambda$11)
            }
         )
      );
      this.runtime = var1;
      this.primaryOverridePortion = 1.0F;
      this.locatorStates = new LinkedHashMap<>();
      this.renderQueue = new ConcurrentLinkedQueue<>();
   }

   public abstract fun getEntity(): Any? {
   }

   public fun getPartialTicks(): Float {
      return this.currentPartialTicks;
   }

   public open fun updateAge(age: Int) {
      this.age = age;
   }

   public open fun incrementAge(entity: Any) {
      val previousAge: Int = this.age;
      this.updateAge(this.age + 1);
      this.runEffects((T)entity, previousAge, this.age);
      if (this.primaryAnimation != null) {
         val primaryAnimation: PrimaryAnimation = this.primaryAnimation;
         if (this.primaryAnimation.getStarted() + primaryAnimation.getDuration() <= this.getAnimationSeconds()) {
            this.primaryAnimation = null;
            primaryAnimation.getAfterAction().accept(Unit.INSTANCE);
         }
      }
   }

   public abstract fun updatePartialTicks(partialTicks: Float) {
   }

   public open fun reset() {
      this.updateAge(0);
   }

   public fun addFirstAnimation(animation: Set<String>) {
      if (this.currentModel != null) {
         val model: PoseableEntityModel = this.currentModel;
         val var4: java.util.Iterator = animation.iterator();

         var var10000: StatefulAnimation;
         while (true) {
            if (var4.hasNext()) {
               val var7: StatefulAnimation = model.getAnimation(this, var4.next() as java.lang.String, this.runtime);
               if (var7 == null) {
                  continue;
               }

               var10000 = var7;
               break;
            }

            var10000 = null;
            break;
         }

         if (var10000 != null) {
            if (var10000 is PrimaryAnimation) {
               this.addPrimaryAnimation(var10000 as PrimaryAnimation<T>);
            } else {
               addStatefulAnimation$default(this, var10000, null, 2, null);
            }
         }
      }
   }

   public fun isPosedIn(vararg poses: Pose<Any, in ModelFrame>): Boolean {
      val `$this$any$iv`: Array<Any> = poses;
      var var4: Int = 0;
      val var5: Int = poses.length;

      var var10000: Boolean;
      while (true) {
         if (var4 >= var5) {
            var10000 = false;
            break;
         }

         if (((Pose)`$this$any$iv`[var4]).getPoseName() == this.currentPose) {
            var10000 = true;
            break;
         }

         var4++;
      }

      return var10000;
   }

   public fun isNotPosedIn(vararg poses: Pose<Any, in ModelFrame>): Boolean {
      val `$this$none$iv`: Array<Any> = poses;
      var var4: Int = 0;
      val var5: Int = poses.length;

      var var10000: Boolean;
      while (true) {
         if (var4 >= var5) {
            var10000 = true;
            break;
         }

         if (((Pose)`$this$none$iv`[var4]).getPoseName() == this.currentPose) {
            var10000 = false;
            break;
         }

         var4++;
      }

      return var10000;
   }

   public fun preRender() {
      while (this.renderQueue.peek() != null) {
         this.renderQueue.poll().invoke();
      }
   }

   public fun doLater(action: () -> Unit) {
      this.renderQueue.offer(action);
   }

   public fun getPose(): String? {
      return this.currentPose;
   }

   public fun setPose(pose: String) {
      this.currentPose = pose;
      this.primaryOverridePortion = 1.0F;
      if (this.currentModel != null) {
         val var10000: Pose = this.currentModel.getPose(pose);
         if (var10000 == null) {
            return;
         }

         val poseImpl: Pose = var10000;
         this.poseParticles.removeIf(PoseableEntityState::setPose$lambda$17);
         poseImpl.getOnTransitionedInto().invoke(this);
         val entity: Entity = this.getEntity();
         if (entity != null) {
            val `$this$forEach$iv`: Array<StatelessAnimation> = poseImpl.getIdleAnimations();
            var `element$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$forEach$iv) {
               if (particle is BedrockStatelessAnimation) {
                  `element$iv`.add(particle);
               }
            }

            val var20: java.lang.Iterable = `element$iv` as java.util.List;
            `element$iv` = new ArrayList();

            for (Object element$iv$ivx : var20) {
               CollectionsKt.addAll(`element$iv`, (`element$iv$ivx` as BedrockStatelessAnimation).getParticleKeyFrames());
            }

            val var21: java.lang.Iterable = `element$iv` as java.util.List;
            `element$iv` = new ArrayList();

            for (Object element$iv$ivx : var21) {
               label67: {
                  val var40: BedrockParticleKeyframe = `element$iv$ivx` as BedrockParticleKeyframe;
                  if ((`element$iv$ivx` as BedrockParticleKeyframe).getSeconds() == 0.0F) {
                     val `$this$none$iv`: java.lang.Iterable = this.poseParticles;
                     var var42: Boolean;
                     if (this.poseParticles is java.util.Collection && this.poseParticles.isEmpty()) {
                        var42 = true;
                     } else {
                        val var16: java.util.Iterator = `$this$none$iv`.iterator();

                        while (true) {
                           if (!var16.hasNext()) {
                              var42 = true;
                              break;
                           }

                           if (var40.isSameAs(var16.next() as BedrockParticleKeyframe)) {
                              var42 = false;
                              break;
                           }
                        }
                     }

                     if (var42) {
                        var43 = true;
                        break label67;
                     }
                  }

                  var43 = false;
               }

               if (var43) {
                  `element$iv`.add(`element$iv$ivx`);
               }
            }

            for (Object element$ivx : var22) {
               (`element$ivx` as BedrockParticleKeyframe).run(entity, this);
            }
         }
      }
   }

   public fun setStatefulAnimations(vararg animations: StatefulAnimation<Any, out ModelFrame>) {
      this.statefulAnimations.clear();
      CollectionsKt.addAll(this.statefulAnimations, animations);
   }

   public fun updateLocatorPosition(position: Vec3) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as MatrixWrapper).updatePosition(position);
      }
   }

   public fun addStatefulAnimation(
      animation: StatefulAnimation<Any, *>,
      whenComplete: (PoseableEntityState<Any>) -> Unit = <unrepresentable>.INSTANCE as Function1
   ) {
      this.statefulAnimations.add(animation);
      val duration: Float = animation.getDuration();
      if (duration > 0.0F) {
         this.after((float)((int)(duration * 20.0F)) / 20.0F, (new Function0<Unit>(whenComplete, this) {
            {
               super(0);
               this.$whenComplete = `$whenComplete`;
               this.this$0 = `$receiver`;
            }

            public final void invoke() {
               this.$whenComplete.invoke(this.this$0);
            }
         }) as () -> Unit);
      }
   }

   public fun addPrimaryAnimation(primaryAnimation: PrimaryAnimation<Any>) {
      this.primaryAnimation = primaryAnimation;
      this.statefulAnimations.clear();
      this.quirks.clear();
      this.primaryOverridePortion = 1.0F;
      primaryAnimation.setStarted(this.getAnimationSeconds());
   }

   public fun runEffects(entity: Any, previousAge: Int, newAge: Int) {
      val previousSeconds: Float = previousAge / 20.0F;
      val currentSeconds: Float = newAge / 20.0F;
      if (this.currentModel != null) {
         val var10000: Pose = if (this.currentPose != null) this.currentModel.getPose(this.currentPose) else null;

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            (var24 as StatefulAnimation).applyEffects(entity, this, previousSeconds, currentSeconds);
         }

         if (this.primaryAnimation != null) {
            val var33: StatefulAnimation = this.primaryAnimation.getAnimation();
            if (var33 != null) {
               var33.applyEffects(entity, this, previousSeconds, currentSeconds);
            }
         }

         if (var10000 != null && var10000.getIdleAnimations() != null) {
            val var29: java.util.Collection = new ArrayList();

            val var22: Any;
            for (Object element$iv$iv : var22) {
               if (this.shouldIdleRun((StatelessAnimation<T, ?>)`element$iv$iv`, 0.5F)) {
                  var29.add(`element$iv$iv`);
               }
            }

            for (Object element$iv : var22) {
               (var30 as StatelessAnimation).applyEffects(entity, this, previousSeconds, currentSeconds);
            }
         }
      }
   }

   public fun shouldIdleRun(idleAnimation: StatelessAnimation<Any, *>, requiredIntensity: Float): Boolean {
      return this.primaryAnimation == null || !this.primaryAnimation.prevents(idleAnimation) || this.primaryOverridePortion > requiredIntensity;
   }

   public fun getIdleIntensity(idleAnimation: StatelessAnimation<Any, *>): Float {
      return if (this.primaryAnimation != null && this.primaryAnimation.prevents(idleAnimation)) this.primaryOverridePortion else 1.0F;
   }

   override fun momentarily(action: () -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.momentarily(this, action);
   }

   override fun after(seconds: Float, action: () -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.after(this, seconds, action);
   }

   override fun lerp(seconds: Float, action: (java.lang.Float?) -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.lerp(this, seconds, action);
   }

   override fun taskBuilder(): ScheduledTask.Builder {
      return Schedulable.DefaultImpls.taskBuilder(this);
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$1`(`$reusableAnimTime`: DoubleValue, `this$0`: PoseableEntityState, it: MoParams): Any {
      `$reusableAnimTime`.value = `this$0`.getAnimationSeconds();
      return `$reusableAnimTime`;
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$2`(`this$0`: PoseableEntityState, it: MoParams): Any {
      val var10002: Entity = `this$0`.getEntity();
      return new StringValue((var10002 as Poseable).getCurrentPoseType().name());
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$3`(`this$0`: PoseableEntityState, var1: MoParams): Any {
      val var10000: StringValue = new StringValue;
      var var10002: java.lang.String = `this$0`.currentPose;
      if (`this$0`.currentPose == null) {
         var10002 = "";
      }

      var10000./* $VF: Unable to resugar constructor */<init>(var10002);
      return var10000;
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$4`(`this$0`: PoseableEntityState, var1: MoParams): Any {
      return new DoubleValue(`this$0`.getEntity() != null);
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$5`(`this$0`: PoseableEntityState, params: MoParams): Unit {
      val var10000: Entity = `this$0`.getEntity();
      if (var10000 == null) {
         return Unit.INSTANCE;
      } else if (params.get(0) !is StringValue) {
         return Unit.INSTANCE;
      } else {
         val var6: java.lang.String = params.getString(0);
         val soundEvent: SoundEvent = SoundEvent.m_262824_(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var6, null, 1, null));
         if (soundEvent != null) {
            Minecraft.m_91087_()
               .m_91106_()
               .m_120367_(
                  (
                     new SimpleSoundInstance(
                        soundEvent,
                        SoundSource.NEUTRAL,
                        if (params.contains(1)) (float)params.getDouble(1) else 1.0F,
                        if (params.contains(2)) (float)params.getDouble(2) else 1.0F,
                        var10000.m_9236_().f_46441_,
                        var10000.m_20185_(),
                        var10000.m_20186_(),
                        var10000.m_20189_()
                     )
                  ) as SoundInstance
               );
         }

         return Unit.INSTANCE;
      }
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$6`(`this$0`: PoseableEntityState, `$runtime`: MoLangRuntime, params: MoParams): Unit {
      val animationParameter: MoValue = params.get(0);
      var var5: StatefulAnimation;
      if (animationParameter is ObjectValue) {
         var5 = (StatefulAnimation)(animationParameter as ObjectValue).getObj();
         var5 = var5 as BedrockStatefulAnimation;
      } else {
         val var6: PoseableEntityModel = `this$0`.currentModel;
         if (`this$0`.currentModel != null) {
            val var10002: java.lang.String = animationParameter.asString();
            var5 = var6.getAnimation(`this$0`, var10002, `$runtime`);
         } else {
            var5 = null;
         }
      }

      if (var5 != null) {
         if (var5 is PrimaryAnimation) {
            `this$0`.addPrimaryAnimation(var5 as PrimaryAnimation<T>);
         } else {
            addStatefulAnimation$default(`this$0`, var5, null, 2, null);
         }
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$11$lambda$10`(`$runtime`: MoLangRuntime, it: MoParams): Any {
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangEnvironment = `$runtime`.getEnvironment();
      return MoLangFunctions.getQueryStruct$default(var10000, var10001, null, 1, null);
   }

   @JvmStatic
   fun `runtime$lambda$12$lambda$11`(`this$0`: PoseableEntityState, `$runtime`: MoLangRuntime, params: MoParams): Unit {
      val particlesParam: MoValue = params.get(0);
      val particles: java.util.List = new ArrayList();
      if (particlesParam is StringValue) {
         val var10001: java.lang.String = (particlesParam as StringValue).value;
         particles.add(var10001);
      } else {
         if (particlesParam !is VariableStruct) {
            return Unit.INSTANCE;
         }

         val `$this$map$iv`: java.lang.Iterable = (particlesParam as VariableStruct).getMap().values();
         val effect: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

         for (Object item$iv$iv : $this$map$iv) {
            effect.add((matrixWrapper as MoValue).asString());
         }

         particles.addAll(effect as java.util.List);
      }

      val var17: java.lang.Iterable = particles;
      val var21: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(particles, 10));

      for (Object item$iv$iv : $this$map$iv) {
         var21.add(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var27 as java.lang.String, null, 1, null));
      }

      for (ResourceLocation effectId : (java.util.List)destination$iv$iv) {
         val locator: java.lang.String = if (params.getParams().size() > 1) params.getString(1) else "root";
         var var10000: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(var20);
         if (var10000 == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Unable to find a particle effect with id $var20");
            return Unit.INSTANCE;
         }

         val var34: Entity = `this$0`.getEntity();
         if (var34 == null) {
            return Unit.INSTANCE;
         }

         val var35: Level = var34.m_9236_();
         val var26: ClientLevel = var35 as ClientLevel;
         var var36: MatrixWrapper = `this$0`.locatorStates.get(locator);
         if (var36 == null) {
            var10000 = `this$0`.locatorStates.get("root");
            var36 = var10000 as MatrixWrapper;
         }

         val var30: MoLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
         val var38: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var39: MoLangEnvironment = var30.getEnvironment();
         MoLangFunctions.getQueryStruct$default(var38, var39, null, 1, null).addFunction("entity", PoseableEntityState::runtime$lambda$12$lambda$11$lambda$10);
         new ParticleStorm(var10000, var36, var26, (new Function0<Vec3>(var34) {
            {
               super(0);
               this.$entity = (T)`$entity`;
            }

            @NotNull
            public final Vec3 invoke() {
               val var10000: Vec3 = this.$entity.m_20184_();
               return var10000;
            }
         }) as Function0, (new Function0<java.lang.Boolean>(var34) {
            {
               super(0);
               this.$entity = (T)`$entity`;
            }

            @NotNull
            public final java.lang.Boolean invoke() {
               return !this.$entity.m_213877_();
            }
         }) as Function0, (new Function0<java.lang.Boolean>(var34) {
            {
               super(0);
               this.$entity = (T)`$entity`;
            }

            @NotNull
            public final java.lang.Boolean invoke() {
               return !this.$entity.m_20145_();
            }
         }) as Function0, null, var30, var34, 64, null).spawn();
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `setPose$lambda$17`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
