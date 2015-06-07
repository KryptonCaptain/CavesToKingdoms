package talonos.blightbuster.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.ForgeDirection;
import talonos.blightbuster.BBStrings;
import talonos.blightbuster.BlightBuster;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;

public class BlockDawnMachineInput extends Block {

    private IIcon backgroundTop;
    private IIcon backgroundSide;
    private IIcon[] topLeftIcons = new IIcon[6];
    private IIcon[] topRightIcons = new IIcon[6];
    private IIcon[] bottomLeftIcons = new IIcon[6];
    private IIcon[] bottomRightIcons = new IIcon[6];

    private Aspect[] spoutAspects = {
            Aspect.HEAL,
            Aspect.FIRE,
            Aspect.AIR,
            Aspect.MIND,
            Aspect.MECHANISM,
            Aspect.AURA,
            Aspect.VOID,
            Aspect.ORDER,
            Aspect.TREE,
            Aspect.PLANT
    };

    protected BlockDawnMachineInput() {
        super(Material.iron);

        this.setBlockName(BlightBuster.MODID+"_"+ BBStrings.dawnMachineInputName);
        this.setStepSound(soundTypeWood);
        this.setLightLevel(.875f);
        this.setBlockTextureName("dawnMachineBuffer");
        GameRegistry.registerBlock(this, this.getUnlocalizedName());
    }

    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        if (par5 == 1) {
            if (par1World.isRemote) {
                Thaumcraft.proxy.blockSparkle(par1World, par2, par3, par4, 16736256, 5);
            }
            return true;
        }
        return super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registry) {
        backgroundTop = registry.registerIcon("thaumcraft:silverwoodtop");
        backgroundSide = registry.registerIcon("thaumcraft:silverwoodside");
        IIcon bufferLayer = registry.registerIcon("blightbuster:dawnMachineBuffer");
        IIcon spoutLayer = registry.registerIcon("blightbuster:dawnMachineSpout");

        topLeftIcons[0] = bufferLayer;
        topLeftIcons[1] = bufferLayer;
        topLeftIcons[2] = spoutLayer;
        topLeftIcons[3] = bufferLayer;
        topLeftIcons[4] = spoutLayer;
        topLeftIcons[5] = spoutLayer;

        topRightIcons[0] = bufferLayer;
        topRightIcons[1] = bufferLayer;
        topRightIcons[2] = bufferLayer;
        topRightIcons[3] = spoutLayer;
        topRightIcons[4] = spoutLayer;
        topRightIcons[5] = spoutLayer;

        bottomLeftIcons[0] = bufferLayer;
        bottomLeftIcons[1] = bufferLayer;
        bottomLeftIcons[2] = bufferLayer;
        bottomLeftIcons[3] = bufferLayer;
        bottomLeftIcons[4] = spoutLayer;
        bottomLeftIcons[5] = spoutLayer;

        bottomRightIcons[0] = bufferLayer;
        bottomRightIcons[1] = bufferLayer;
        bottomRightIcons[2] = bufferLayer;
        bottomRightIcons[3] = bufferLayer;
        bottomRightIcons[4] = spoutLayer;
        bottomRightIcons[5] = spoutLayer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return (pass == 0 || pass == 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (ForgeHooksClient.getWorldRenderPass() == 1) {
            ForgeDirection sideDir = getInputSide(side, meta);
            return getInputIcon(sideDir.ordinal(), meta);
        }

        if (side == 0 || side == 1)
            return backgroundTop;
        return backgroundSide;
    }

    private IIcon getInputIcon(int side, int meta) {
        int block = meta/4;

        switch(block) {
            case 1:
                return bottomRightIcons[side];
            case 2:
                return topLeftIcons[side];
            case 3:
                return topRightIcons[side];
            default:
                return bottomLeftIcons[side];
        }
    }

    public ForgeDirection getInputSide(int side, int meta) {
        int orientation = meta & 0x3;
        ForgeDirection sideDir = ForgeDirection.VALID_DIRECTIONS[side];
        ForgeDirection orientationDir = ForgeDirection.VALID_DIRECTIONS[orientation+2];

        while (orientationDir != ForgeDirection.WEST) {
            orientationDir = orientationDir.getRotation(ForgeDirection.UP);
            sideDir = sideDir.getRotation(ForgeDirection.UP);
        }

        return sideDir;
    }

    public Aspect getSpoutAspect(int side, int meta) {
        ForgeDirection sideDir = getInputSide(side, meta);
        int inputBlockIndex = meta/4;

        switch (inputBlockIndex) {
            case 0:
                //North lower block, sano is west, machina is east
                if (sideDir == ForgeDirection.WEST)
                    return Aspect.HEAL;
                else if (sideDir == ForgeDirection.EAST)
                    return Aspect.MECHANISM;
                else
                    return null;
            case 1:
                //South lower block, auram is east, herba is west
                if (sideDir == ForgeDirection.WEST)
                    return Aspect.PLANT;
                else if (sideDir == ForgeDirection.EAST)
                    return Aspect.AURA;
                else
                    return null;
            case 2:
                //North upper block, ignis is west, aer is north, cognitio is east
                if (sideDir == ForgeDirection.WEST)
                    return Aspect.FIRE;
                else if (sideDir == ForgeDirection.NORTH)
                    return Aspect.AIR;
                else if (sideDir == ForgeDirection.EAST)
                    return Aspect.MIND;
                else
                    return null;
            default:
                //South upper block, vacuos is east, ordo is south, arbor is west
                if (sideDir == ForgeDirection.WEST)
                    return Aspect.TREE;
                else if (sideDir == ForgeDirection.SOUTH)
                    return Aspect.ORDER;
                else if (sideDir == ForgeDirection.EAST)
                    return Aspect.VOID;
                else
                    return null;
        }
    }
}