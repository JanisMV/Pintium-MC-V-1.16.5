package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.Feature;

public class BasaltDeltasStructure extends Feature<BasaltDeltasFeature> {
   private static final ImmutableList<Block> field_236274_a_ = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
   private static final Direction[] field_236275_ac_ = Direction.values();

   public BasaltDeltasStructure(Codec<BasaltDeltasFeature> p_i231946_1_) {
      super(p_i231946_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BasaltDeltasFeature config) {
      boolean flag = false;
      boolean flag1 = rand.nextDouble() < 0.9D;
      int i = flag1 ? config.func_242808_e().getSpread(rand) : 0;
      int j = flag1 ? config.func_242808_e().getSpread(rand) : 0;
      boolean flag2 = flag1 && i != 0 && j != 0;
      int k = config.func_242807_d().getSpread(rand);
      int l = config.func_242807_d().getSpread(rand);
      int i1 = Math.max(k, l);

      for(BlockPos blockpos : BlockPos.getProximitySortedBoxPositionsIterator(pos, k, 0, l)) {
         if (blockpos.manhattanDistance(pos) > i1) {
            break;
         }

         if (func_236277_a_(reader, blockpos, config)) {
            if (flag2) {
               flag = true;
               this.setBlockState(reader, blockpos, config.func_242806_c());
            }

            BlockPos blockpos1 = blockpos.add(i, 0, j);
            if (func_236277_a_(reader, blockpos1, config)) {
               flag = true;
               this.setBlockState(reader, blockpos1, config.func_242804_b());
            }
         }
      }

      return flag;
   }

   private static boolean func_236277_a_(IWorld p_236277_0_, BlockPos p_236277_1_, BasaltDeltasFeature p_236277_2_) {
      BlockState blockstate = p_236277_0_.getBlockState(p_236277_1_);
      if (blockstate.matchesBlock(p_236277_2_.func_242804_b().getBlock())) {
         return false;
      } else if (field_236274_a_.contains(blockstate.getBlock())) {
         return false;
      } else {
         for(Direction direction : field_236275_ac_) {
            boolean flag = p_236277_0_.getBlockState(p_236277_1_.offset(direction)).isAir();
            if (flag && direction != Direction.UP || !flag && direction == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }
}
