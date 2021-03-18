package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class MultipleWithChanceRandomFeature extends Feature<MultipleRandomFeatureConfig> {
   public MultipleWithChanceRandomFeature(Codec<MultipleRandomFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, MultipleRandomFeatureConfig config) {
      for(ConfiguredRandomFeatureList configuredrandomfeaturelist : config.features) {
         if (rand.nextFloat() < configuredrandomfeaturelist.chance) {
            return configuredrandomfeaturelist.generate(reader, generator, rand, pos);
         }
      }

      return config.defaultFeature.get().generate(reader, generator, rand, pos);
   }
}
