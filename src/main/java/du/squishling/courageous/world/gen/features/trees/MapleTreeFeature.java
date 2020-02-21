package du.squishling.courageous.world.gen.features.trees;

import com.mojang.datafixers.Dynamic;
import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.blocks.vegetation.MapleLog;
import du.squishling.courageous.util.Reference;
import du.squishling.courageous.world.gen.ModFeatures;
import net.minecraft.block.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class MapleTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
    private static final BlockState DEFAULT_TRUNK = ModBlocks.MAPLE_LOG.getDefaultState();
    private static final BlockState DEFAULT_LEAF = ModBlocks.MAPLE_LEAVES.getDefaultState();
    protected final int minTreeHeight;
    private final boolean vinesGrow;
    private final BlockState trunk = ModBlocks.MAPLE_LOG_SYRUP.getDefaultState();
    private final BlockState leaf;

    public MapleTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        this(configFactoryIn, false, 4, DEFAULT_TRUNK, DEFAULT_LEAF, false);
    }

    public MapleTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, int minTreeHeightIn, BlockState trunkState, BlockState leafState, boolean vinesGrowIn) {
        super(configFactoryIn, doBlockNotifyOnPlace);
        this.minTreeHeight = minTreeHeightIn;
        this.leaf = leafState;
        this.vinesGrow = vinesGrowIn;

        this.setRegistryName(Reference.MOD_ID, "maple_tree");
        ModFeatures.FEATURES.add(this);
    }

    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        int i = this.getHeight(rand);
        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
            for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
                int k = 1;
                if (j == position.getY()) {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2) {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < worldIn.getMaxHeight()) {
                            if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
                this.setDirtAt(worldIn, position.down(), position);
                int j2 = 3;
                int k2 = 0;

                for(int l2 = position.getY() - 3 + i; l2 <= position.getY() + i; ++l2) {
                    int l3 = l2 - (position.getY() + i);
                    int j4 = 1 - l3 / 2;

                    for(int j1 = position.getX() - j4; j1 <= position.getX() + j4; ++j1) {
                        int k1 = j1 - position.getX();

                        for(int l1 = position.getZ() - j4; l1 <= position.getZ() + j4; ++l1) {
                            int i2 = l1 - position.getZ();
                            if (Math.abs(k1) != j4 || Math.abs(i2) != j4 || rand.nextInt(2) != 0 && l3 != 0) {
                                BlockPos blockpos = new BlockPos(j1, l2, l1);
                                if (isAirOrLeaves(worldIn, blockpos) || isTallPlants(worldIn, blockpos)) {
                                    setLeaves(changedBlocks, worldIn, rand, blockpos, boundsIn);
                                }
                            }
                        }
                    }
                }

                int index = rand.nextInt(i);
                for(int i3 = 0; i3 < i; ++i3) {
                    if (isAirOrLeaves(worldIn, position.up(i3)) || isTallPlants(worldIn, position.up(i3))) {
                        if (i3 == index) this.setLogState(changedBlocks, worldIn, position.up(i3), this.trunk.with(MapleLog.GROWN, rand.nextInt(2) == 0), boundsIn);
                        else this.setLogState(changedBlocks, worldIn, position.up(i3), DEFAULT_TRUNK, boundsIn);

                        if (this.vinesGrow && i3 > 0) {
                            if (rand.nextInt(3) > 0 && isAir(worldIn, position.add(-1, i3, 0))) {
                                this.addVine(worldIn, position.add(-1, i3, 0), VineBlock.EAST);
                            }

                            if (rand.nextInt(3) > 0 && isAir(worldIn, position.add(1, i3, 0))) {
                                this.addVine(worldIn, position.add(1, i3, 0), VineBlock.WEST);
                            }

                            if (rand.nextInt(3) > 0 && isAir(worldIn, position.add(0, i3, -1))) {
                                this.addVine(worldIn, position.add(0, i3, -1), VineBlock.SOUTH);
                            }

                            if (rand.nextInt(3) > 0 && isAir(worldIn, position.add(0, i3, 1))) {
                                this.addVine(worldIn, position.add(0, i3, 1), VineBlock.NORTH);
                            }
                        }
                    }
                }

                if (this.vinesGrow) {
                    for(int j3 = position.getY() - 3 + i; j3 <= position.getY() + i; ++j3) {
                        int i4 = j3 - (position.getY() + i);
                        int k4 = 2 - i4 / 2;
                        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                        for(int l4 = position.getX() - k4; l4 <= position.getX() + k4; ++l4) {
                            for(int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; ++i5) {
                                blockpos$mutableblockpos1.setPos(l4, j3, i5);
                                if (isLeaves(worldIn, blockpos$mutableblockpos1)) {
                                    BlockPos blockpos3 = blockpos$mutableblockpos1.west();
                                    BlockPos blockpos4 = blockpos$mutableblockpos1.east();
                                    BlockPos blockpos1 = blockpos$mutableblockpos1.north();
                                    BlockPos blockpos2 = blockpos$mutableblockpos1.south();
                                    if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos3)) {
                                        this.addHangingVine(worldIn, blockpos3, VineBlock.EAST);
                                    }

                                    if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos4)) {
                                        this.addHangingVine(worldIn, blockpos4, VineBlock.WEST);
                                    }

                                    if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos1)) {
                                        this.addHangingVine(worldIn, blockpos1, VineBlock.SOUTH);
                                    }

                                    if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos2)) {
                                        this.addHangingVine(worldIn, blockpos2, VineBlock.NORTH);
                                    }
                                }
                            }
                        }
                    }

                    if (rand.nextInt(5) == 0 && i > 5) {
                        for(int k3 = 0; k3 < 2; ++k3) {
                            for(Direction direction : Direction.Plane.HORIZONTAL) {
                                if (rand.nextInt(4 - k3) == 0) {
                                    Direction direction1 = direction.getOpposite();
                                    this.placeCocoa(worldIn, rand.nextInt(3), position.add(direction1.getXOffset(), i - 5 + k3, direction1.getZOffset()), direction);
                                }
                            }
                        }
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected int getHeight(Random random) {
        return this.minTreeHeight + random.nextInt(3);
    }

    private void placeCocoa(IWorldWriter worldIn, int age, BlockPos pos, Direction side) {
        this.setBlockState(worldIn, pos, Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, Integer.valueOf(age)).with(CocoaBlock.HORIZONTAL_FACING, side));
    }

    private void addVine(IWorldWriter worldIn, BlockPos pos, BooleanProperty prop) {
        this.setBlockState(worldIn, pos, Blocks.VINE.getDefaultState().with(prop, Boolean.valueOf(true)));
    }

    private void addHangingVine(IWorldGenerationReader worldIn, BlockPos pos, BooleanProperty prop) {
        this.addVine(worldIn, pos, prop);
        int i = 4;

        for(BlockPos blockpos = pos.down(); isAir(worldIn, blockpos) && i > 0; --i) {
            this.addVine(worldIn, blockpos, prop);
            blockpos = blockpos.down();
        }

    }

    private void setLeaves(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        this.setLogState(changedBlocks, worldIn, position, this.leaf, boundsIn);
    }

}
