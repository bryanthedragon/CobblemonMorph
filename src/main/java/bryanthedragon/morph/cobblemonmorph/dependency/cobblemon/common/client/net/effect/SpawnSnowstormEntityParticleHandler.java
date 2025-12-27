package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import kotlin.jvm.functions.Function0
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public object SpawnSnowstormEntityParticleHandler : ClientNetworkPacketHandler<SpawnSnowstormEntityParticlePacket> {
   public open fun handle(packet: SpawnSnowstormEntityParticlePacket, client: Minecraft) {
      val var10000: ClientLevel = Minecraft.m_91087_().f_91073_;
      if (var10000 != null) {
         val var11: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(packet.getEffectId());
         if (var11 != null) {
            val matrixWrapper: Entity = var10000.m_6815_(packet.getEntityId());
            val var12: Poseable = matrixWrapper as? Poseable;
            if ((matrixWrapper as? Poseable) != null) {
               val var13: EntitySideDelegate = var12.getDelegate();
               val state: PoseableEntityState = var13 as PoseableEntityState;
               var var14: MatrixWrapper = (var13 as PoseableEntityState).getLocatorStates().get(packet.getLocator());
               if (var14 == null) {
                  val var15: Any = state.getLocatorStates().get("root");
                  var14 = var15 as MatrixWrapper;
               }

               val particleRuntime: MoLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
               val var16: MoLangFunctions = MoLangFunctions.INSTANCE;
               val var10001: MoLangEnvironment = particleRuntime.getEnvironment();
               MoLangFunctions.getQueryStruct$default(var16, var10001, null, 1, null)
                  .addFunction("entity", SpawnSnowstormEntityParticleHandler::handle$lambda$0);
               new ParticleStorm(var11, var14, var10000, (new Function0<Vec3>(var12) {
                  {
                     super(0);
                     this.$entity = `$entity`;
                  }

                  @NotNull
                  public final Vec3 invoke() {
                     val var10000: Vec3 = (this.$entity as Entity).m_20184_();
                     return var10000;
                  }
               }) as Function0, (new Function0<java.lang.Boolean>(var12) {
                  {
                     super(0);
                     this.$entity = `$entity`;
                  }

                  @NotNull
                  public final java.lang.Boolean invoke() {
                     return !(this.$entity as Entity).m_213877_();
                  }
               }) as Function0, (new Function0<java.lang.Boolean>(var12) {
                  {
                     super(0);
                     this.$entity = `$entity`;
                  }

                  @NotNull
                  public final java.lang.Boolean invoke() {
                     return !(this.$entity as Entity).m_20145_();
                  }
               }) as Function0, null, particleRuntime, var12 as Entity, 64, null).spawn();
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SpawnSnowstormEntityParticlePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$lambda$0`(`$state`: PoseableEntityState, it: MoParams): Any {
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangEnvironment = `$state`.getRuntime().getEnvironment();
      return MoLangFunctions.getQueryStruct$default(var10000, var10001, null, 1, null);
   }
}
