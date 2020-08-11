package flaxbeard.immersivepetroleum.common.blocks.metal;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumIPMetalDevice implements IStringSerializable{
	GAS_GENERATOR,
	AUTOMATIC_LUBRICATOR;
	
	@Override
	public String getName(){
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
