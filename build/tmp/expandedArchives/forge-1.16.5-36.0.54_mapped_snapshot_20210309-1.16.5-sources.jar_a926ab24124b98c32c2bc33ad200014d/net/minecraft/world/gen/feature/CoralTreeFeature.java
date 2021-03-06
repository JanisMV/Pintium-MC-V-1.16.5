package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CoralTreeFeature extends CoralFeature {
   public CoralTreeFeature(Codec<NoFeatureConfig> codec) {
      super(codec);
   }

   protected boolean func_204623_a(IWorld world, Random rand, BlockPos pos, BlockState state) {
      BlockPos.Mutable blockpos$mutable = pos.toMutable();
      int i = rand.nextInt(3) + 1;

      for(int j = 0; j < i; ++j) {
         if (!this.func_204624_b(world, rand, blockpos$mutable, state)) {
            return true;
         }

         blockpos$mutable.move(Direction.UP);
      }

      BlockPos blockpos = blockpos$mutable.toImmutable();
      int k = rand.nextInt(3) + 2;
      List<Direction> list = Lists.newArrayList(Direction.Plane.HORIZONTAL);
      Collections.shuffle(list, rand);

      for(Direction direction : list.subList(0, k)) {
         blockpos$mutable.setPos(blockpos);
         blockpos$mutable.move(direction);
         int l = rand.nextInt(5) + 2;
         int i1 = 0;

         for(int j1 = 0; j1 < l && this.func_204624_b(world, rand, blockpos$mutable, state); ++j1) {
            ++i1;
            blockpos$mutable.move(Direction.UP);
            if (j1 == 0 || i1 >= 2 && rand.nextFloat() < 0.25F) {
               blockpos$mutable.move(direction);
               i1 = 0;
            }
         }
      }

      return true;
   }
}
