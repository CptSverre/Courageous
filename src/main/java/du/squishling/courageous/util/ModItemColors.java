package du.squishling.courageous.util;

import du.squishling.courageous.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModItemColors implements IItemColor {

    public static final IItemColor INSTANCE = new ModItemColors();

    private static HashMap<Item, Integer> ITEMS = new HashMap<Item, Integer>();
    private static HashMap<Block, Integer> BLOCKS = new HashMap<Block, Integer>();

    @Override
    public int getColor(ItemStack itemStack, int i) {
        for (Item item : ITEMS.keySet()) if (itemStack.getItem().equals(item)) return ITEMS.get(item);
        for (Block item : BLOCKS.keySet()) if (itemStack.getItem() instanceof BlockItem && ((BlockItem) itemStack.getItem()).getBlock().equals(item)) return BLOCKS.get(item);

        return 0;
    }

    public static void registerItem(Item item, int color) {
        ITEMS.put(item, color);
    }

    public static void registerItem(Block item, int color) {
        BLOCKS.put(item, color);
    }

    public static void registerItemColors() {
        registerItem(ModBlocks.ALPINE_LEAVES, 0x446852);
        registerItem(ModBlocks.PALM_LEAVES, 0xe1ff43);
        registerItem(ModBlocks.PEAR_LEAVES, 0x59ae30);
        for (Item item : ITEMS.keySet()) Minecraft.getInstance().getItemColors().register(INSTANCE, item);
        for (Block item : BLOCKS.keySet()) Minecraft.getInstance().getItemColors().register(INSTANCE, item);
    }

}
