package flaxbeard.immersivepetroleum.api.crafting;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.common.crafting.Serializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;


/**
 * @author BluSunrize - 02.03.2016
 * <p>
 * The recipe for the Refinery
 */
public class DistillationRecipe extends MultiblockRecipe{
	public static IRecipeType<DistillationRecipe> TYPE=IRecipeType.register(ImmersivePetroleum.MODID+":distillationtower");
	/** Initialized in {@link Serializers} */
	public static RegistryObject<IERecipeSerializer<DistillationRecipe>> SERIALIZER;
	
	public static double energyModifier = 1;
	public static double timeModifier = 1;
	
	public final double[] chances;
	
	public final FluidStack[] fluidOutput;
	public final ItemStack[] itemOutput;
	public final FluidStack input;
	
	public static Map<ResourceLocation, DistillationRecipe> recipes=new HashMap<>();
	
	public DistillationRecipe(ResourceLocation id, FluidStack[] fluidOutput, ItemStack[] itemOutput, FluidStack input, int energy, int time, double[] chances){
		super(ItemStack.EMPTY, TYPE, id);
		this.fluidOutput = fluidOutput;
		this.itemOutput = itemOutput;
		this.input = input;
		this.totalProcessEnergy = (int) Math.floor(energy * energyModifier);
		this.totalProcessTime = (int) Math.floor(time * timeModifier);
		
		this.chances = chances;
		
		this.fluidInputList = Collections.singletonList(this.input);
		this.fluidOutputList = Arrays.asList(this.fluidOutput);
		this.outputList = NonNullList.from(ItemStack.EMPTY, itemOutput);
	}
	
	/** @deprecated <pre>in favour of JSON Recipes. See {@link DistillationRecipeBuilder}</pre> */
	@Deprecated
	public DistillationRecipe(FluidStack[] fluidOutput, ItemStack[] itemOutput, FluidStack input, int energy, int time, double[] chances){
		super(ItemStack.EMPTY, TYPE, null);
		this.fluidOutput = fluidOutput;
		this.itemOutput = itemOutput;
		this.input = input;
		this.totalProcessEnergy = (int) Math.floor(energy * energyModifier);
		this.totalProcessTime = (int) Math.floor(time * timeModifier);
		
		this.chances = chances;
		
		this.fluidInputList = Collections.singletonList(this.input);
		this.fluidOutputList = Arrays.asList(this.fluidOutput);
		this.outputList = NonNullList.from(ItemStack.EMPTY, itemOutput);
	}
	
	/**
	 * @deprecated <pre>in favour of JSON Recipes. See {@link DistillationRecipeBuilder}</pre>
	 * 
	 * Add a Distillation Tower recipe
	 *
	 * @param fluidOutputs A list of FluidStacks, each an output from the recipe
	 * @param itemOutputs  A list of ItemStacks, each an output as well
	 * @param input        The fluid input fo this recipe
	 * @param energy       The amount of energy one operation takes
	 * @param time         How long this operation takes
	 * @param chances      A list of chances of obtaining each solid byproduct
	 * @return The created/registered recipe matching the input
	 */
	@Deprecated
	public static DistillationRecipe addRecipe(FluidStack[] fluidOutputs, ItemStack[] itemOutputs, FluidStack input, int energy, int time, double[] chances){
		return null;
	}
	
	public static DistillationRecipe findRecipe(FluidStack input){
		if(!recipes.isEmpty()){
			for(DistillationRecipe r:recipes.values()){
				if(r.input!=null && r.input.containsFluid(input)){
					return r;
				}
			}
		}
		return null;
	}
	
	@Override
	protected IERecipeSerializer<DistillationRecipe> getIESerializer(){
		return SERIALIZER.get();
	}
	
	@Override
	public int getMultipleProcessTicks(){
		return 0;
	}
	
	public CompoundNBT writeToNBT(CompoundNBT nbt){
		nbt.put("input", input.writeToNBT(new CompoundNBT()));
		return nbt;
	}
	
	public static DistillationRecipe loadFromNBT(CompoundNBT nbt){
		FluidStack input = FluidStack.loadFluidStackFromNBT(nbt.getCompound("input"));
		return findRecipe(input);
	}
	
	int totalProcessTime;
	
	@Override
	public int getTotalProcessTime(){
		return this.totalProcessTime;
	}
	
	int totalProcessEnergy;
	
	@Override
	public int getTotalProcessEnergy(){
		return this.totalProcessEnergy;
	}
	
	@Override
	public NonNullList<ItemStack> getActualItemOutputs(TileEntity tile){
		NonNullList<ItemStack> output = NonNullList.create();
		for(int i = 0;i < itemOutput.length;i++){
			if(tile.getWorld().rand.nextFloat() <= chances[i]){
				output.add(itemOutput[i]);
			}
		}
		return output;
	}
}