package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class VoidStartPlatformFeature extends Feature<NoFeatureConfig> {
   private static final BlockPos VOID_SPAWN_POS = new BlockPos(8, 3, 8);
   private static final ChunkPos VOID_SPAWN_CHUNK_POS = new ChunkPos(VOID_SPAWN_POS);

   public VoidStartPlatformFeature(Codec<NoFeatureConfig> codec) {
      super(codec);
   }

   /**
    * Returns the Manhattan distance between the two points.
    */
   private static int distance(int firstX, int firstZ, int secondX, int secondZ) {
      return Math.max(Math.abs(firstX - secondX), Math.abs(firstZ - secondZ));
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      ChunkPos chunkpos = new ChunkPos(pos);
      if (distance(chunkpos.x, chunkpos.z, VOID_SPAWN_CHUNK_POS.x, VOID_SPAWN_CHUNK_POS.z) > 1) {
         return true;
      } else {
         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

         for(int i = chunkpos.getZStart(); i <= chunkpos.getZEnd(); ++i) {
            for(int j = chunkpos.getXStart(); j <= chunkpos.getXEnd(); ++j) {
               if (distance(VOID_SPAWN_POS.getX(), VOID_SPAWN_POS.getZ(), j, i) <= 16) {
                  blockpos$mutable.setPos(j, VOID_SPAWN_POS.getY(), i);
                  if (blockpos$mutable.equals(VOID_SPAWN_POS)) {
                     reader.setBlockState(blockpos$mutable, Blocks.COBBLESTONE.getDefaultState(), 2);
                  } else {
                     reader.setBlockState(blockpos$mutable, Blocks.STONE.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
