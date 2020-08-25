package flaxbeard.immersivepetroleum.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;

public class BlockDummy extends IPBlockBase{
	private static final Material Material = new Material(MaterialColor.IRON, false, false, false, false, false, false, false, PushReaction.NORMAL);
	
	public BlockDummy(String name){
		super(name, Block.Properties.create(Material));
	}
	
	@Override
	protected BlockItem createBlockItem(){
		return new BlockItem(this, new Item.Properties());
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items){
		
	}
	
	@Override
	public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer){
		return layer==BlockRenderLayer.TRANSLUCENT || layer==BlockRenderLayer.SOLID;
	}
}
