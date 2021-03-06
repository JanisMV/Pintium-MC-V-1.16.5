package net.minecraft.world.spawner;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class PhantomSpawner implements ISpecialSpawner {
   private int ticksUntilSpawn;

   public int onUpdate(ServerWorld world, boolean spawnHostiles, boolean spawnPassives) {
      if (!spawnHostiles) {
         return 0;
      } else if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
         return 0;
      } else {
         Random random = world.rand;
         --this.ticksUntilSpawn;
         if (this.ticksUntilSpawn > 0) {
            return 0;
         } else {
            this.ticksUntilSpawn += (60 + random.nextInt(60)) * 20;
            if (world.getSkylightSubtracted() < 5 && world.getDimensionType().hasSkyLight()) {
               return 0;
            } else {
               int i = 0;

               for(PlayerEntity playerentity : world.getPlayers()) {
                  if (!playerentity.isSpectator()) {
                     BlockPos blockpos = playerentity.getPosition();
                     if (!world.getDimensionType().hasSkyLight() || blockpos.getY() >= world.getSeaLevel() && world.canSeeSky(blockpos)) {
                        DifficultyInstance difficultyinstance = world.getDifficultyForLocation(blockpos);
                        if (difficultyinstance.isHarderThan(random.nextFloat() * 3.0F)) {
                           ServerStatisticsManager serverstatisticsmanager = ((ServerPlayerEntity)playerentity).getStats();
                           int j = MathHelper.clamp(serverstatisticsmanager.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                           int k = 24000;
                           if (random.nextInt(j) >= 72000) {
                              BlockPos blockpos1 = blockpos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
                              BlockState blockstate = world.getBlockState(blockpos1);
                              FluidState fluidstate = world.getFluidState(blockpos1);
                              if (WorldEntitySpawner.func_234968_a_(world, blockpos1, blockstate, fluidstate, EntityType.PHANTOM)) {
                                 ILivingEntityData ilivingentitydata = null;
                                 int l = 1 + random.nextInt(difficultyinstance.getDifficulty().getId() + 1);

                                 for(int i1 = 0; i1 < l; ++i1) {
                                    PhantomEntity phantomentity = EntityType.PHANTOM.create(world);
                                    phantomentity.moveToBlockPosAndAngles(blockpos1, 0.0F, 0.0F);
                                    if(net.minecraftforge.common.ForgeHooks.canEntitySpawn(phantomentity, world, blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), null, SpawnReason.NATURAL) == -1) return 0;
                                    ilivingentitydata = phantomentity.onInitialSpawn(world, difficultyinstance, SpawnReason.NATURAL, ilivingentitydata, (CompoundNBT)null);
                                    world.func_242417_l(phantomentity);
                                 }

                                 i += l;
                              }
                           }
                        }
                     }
                  }
               }

               return i;
            }
         }
      }
   }
}
