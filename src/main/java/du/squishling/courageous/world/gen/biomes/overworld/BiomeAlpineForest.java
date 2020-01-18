package du.squishling.courageous.world.gen.biomes.overworld;

import du.squishling.courageous.world.gen.biomes.BiomeRegistry;
import du.squishling.courageous.world.gen.features.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.TaigaBiome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.HashMap;

public class BiomeAlpineForest extends Biome {

    public BiomeAlpineForest() {
        super(new Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .category(Category.TAIGA)
                .precipitation(RainType.RAIN)
                .downfall(0.3f)

                .temperature(0.3f)

                .depth(1.5f)
                .scale(0.2f)

                .waterColor(0x02367e)
                .waterFogColor(0x2158a5)

                .parent((String) null));

        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addSparseBerryBushes(this);
        DefaultBiomeFeatures.addStructures(this);

        ModFeatures.addUndergroundFeatures(this);
        ModFeatures.addVegetation(this);
        ModFeatures.addPillagerOutpost(this);
        ModFeatures.addVilage(this, "taiga");

        ModFeatures.addAlpineTrees(this);
        ModFeatures.addSparseTrees(this);

        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.WOLF, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.FOX, 8, 2, 4));
        this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0x8f9c48;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return super.getFoliageColor(p_180625_1_);
    }
}
