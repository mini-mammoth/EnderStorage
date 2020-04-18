package codechicken.enderstorage.lib;

import net.minecraft.nbt.CompoundNBT;

public interface MCDataOutput {
    MCDataOutput writeByte(int b);

    default MCDataOutput writeNBTTagCompound(CompoundNBT tag) {
        MCDataUtils.writeNBTTagCompount(this, tag);
        return this;
    }
}
