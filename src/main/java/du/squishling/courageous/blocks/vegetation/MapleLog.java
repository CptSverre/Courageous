package du.squishling.courageous.blocks.vegetation;

import du.squishling.courageous.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class MapleLog extends CustomLog implements IGrowable {

    public static final BooleanProperty GROWN = BooleanProperty.create("grown");

    public MapleLog(String name, MaterialColor color) {
        super(name, color);
        this.setDefaultState(this.stateContainer.getBaseState().with(GROWN, false).with(LogBlock.AXIS, Axis.Y));
    }

    public boolean ticksRandomly(BlockState state) {
        return !state.get(GROWN);
    }

    public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);

        if (!state.get(GROWN) && random.nextInt(5) == 0) {
            worldIn.setBlockState(pos, state.with(GROWN, true), 2);
        }
    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(GROWN) && player.getHeldItem(handIn).getItem().equals(Items.GLASS_BOTTLE)) {
            player.getHeldItem(handIn).setCount(player.getHeldItem(handIn).getCount() - 1);
            if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.MAPLE_SYRUP))) spawnAsEntity(worldIn, pos, new ItemStack(ModItems.MAPLE_SYRUP, 1));
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            worldIn.setBlockState(pos, state.with(GROWN, false), 2);
            return true;
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GROWN);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return !blockState.get(GROWN);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, blockState.with(GROWN, true), 2);
    }

}