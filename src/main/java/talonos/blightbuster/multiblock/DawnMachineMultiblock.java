package talonos.blightbuster.multiblock;

import talonos.blightbuster.blocks.BBBlock;
import talonos.blightbuster.multiblock.entries.*;
import thaumcraft.common.config.ConfigBlocks;

import java.util.ArrayList;
import java.util.List;

public class DawnMachineMultiblock extends Multiblock {

    private List<MultiblockEntry> entries = new ArrayList<MultiblockEntry>(9);

    public DawnMachineMultiblock() {

        IMultiblockEntryState silverwoodLog = new BasicMultiblockState(ConfigBlocks.blockMagicalLog, 1);
        IMultiblockEntryState dawnTotem = new BasicMultiblockState(BBBlock.dawnTotem);
        IMultiblockEntryState blankSpace = new NonSolidBlockState();

        IMultiblockEntryState nullState = new NullMultiblockState();
        IMultiblockEntryState dawnMachine = new OrientationMultiblockState(BBBlock.dawnMachine);
        IMultiblockEntryState buffer = new OrientationMultiblockState(BBBlock.dawnMachineBuffer);
        IMultiblockEntryState bottomLeftSpout = new OrientationMultiblockState(BBBlock.dawnMachineInput, 0, 0xC);
        IMultiblockEntryState bottomRightSpout = new OrientationMultiblockState(BBBlock.dawnMachineInput, 4, 0xC);
        IMultiblockEntryState topLeftSpout = new OrientationMultiblockState(BBBlock.dawnMachineInput, 8, 0xC);
        IMultiblockEntryState topRightSpout = new OrientationMultiblockState(BBBlock.dawnMachineInput, 12, 0xC);

        entries.add(new MultiblockEntry(-1, 1, 0, silverwoodLog, topLeftSpout));
        entries.add(new MultiblockEntry(0, 1, 0, blankSpace, nullState));
        entries.add(new MultiblockEntry(1, 1, 0, silverwoodLog, topRightSpout));
        entries.add(new MultiblockEntry(-1, 0, 0, silverwoodLog, bottomLeftSpout));
        entries.add(new MultiblockEntry(0, 0, 0, dawnTotem, dawnMachine));
        entries.add(new MultiblockEntry(1, 0, 0, silverwoodLog, bottomRightSpout));
        entries.add(new MultiblockEntry(-1, -1, 0, silverwoodLog, buffer));
        entries.add(new MultiblockEntry(1, -1, 0, silverwoodLog, buffer));
    }

    @Override
    protected Iterable<MultiblockEntry> getMultiblockSchema() {
        return entries;
    }
}