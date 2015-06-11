package talonos.blightbuster.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import talonos.blightbuster.multiblock.DawnMachineMultiblock;
import talonos.blightbuster.multiblock.Multiblock;

/**
 * Thanks to Martijn Woudstra for the code samples.
 * @Author Talonos
 */

public class BBBlock extends Block
{
    /**
     * Constructor for when no material is passed on.
     * Default material: rock
     */
    public BBBlock()
    {
        super(Material.rock);
    }

    /**
     * Constructor for defined material.
     * @param material
     */
    public BBBlock(Material material)
    {
        super(material);
    }
    
    public static Block dawnTotem;
    public static Block dawnMachineInput;
    public static Block dawnMachineBuffer;
    public static Block dawnMachine;
    public static Multiblock dawnMachineMultiblock;

	public static void init() 
	{
        dawnMachineMultiblock = new DawnMachineMultiblock();

	    dawnTotem = new BlockDawnTotem(Material.wood);
        dawnMachineInput = new BlockDawnMachineInput();
        dawnMachineBuffer = new BlockDawnMachineDummy();
        dawnMachine = new BlockDawnMachine();

        dawnMachineMultiblock.init();
	}

}