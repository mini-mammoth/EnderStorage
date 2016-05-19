package codechicken.enderstorage.tile;

import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.Frequency;
import codechicken.enderstorage.network.EnderStorageSPH;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.IIndexedCuboidProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class TileFrequencyOwner extends TileEntity implements ITickable, IIndexedCuboidProvider {
    public static Cuboid6 selection_button = new Cuboid6(-1 / 16D, 0, -2 / 16D, 1 / 16D, 1 / 16D, 2 / 16D);

    public Frequency frequency = new Frequency();
    private Frequency cache = new Frequency();
    private int changeCount;

    @Override
    public void validate() {
        if (worldObj == null) {
            return;
        }
        super.validate();
        if (!(worldObj instanceof WorldServer) == worldObj.isRemote) {
            reloadStorage();
        }
    }

    public void setFreq(Frequency frequency) {
        this.frequency = frequency;
        reloadStorage();
        markDirty();
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void update() {
        if (worldObj == null) {
            return;
        }
        if (getStorage().getChangeCount() > changeCount) {
            worldObj.updateComparatorOutputLevel(pos, getBlockType());
            changeCount = getStorage().getChangeCount();
        }
        if (!cache.equals(frequency)) {
            reloadStorage();
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            cache = frequency.copy();
        }
    }

    @Override//TODO Remove when forge fixes their shit.
    public void setWorldObj(World worldIn) {
        if (!hasWorldObj()) {
            worldObj = worldIn;
        }
    }

    public abstract void reloadStorage();

    public abstract AbstractEnderStorage getStorage();

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        frequency.readNBT(tag.getCompoundTag("Frequency"));
        //owner = tag.getString("owner");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagCompound frequencyTag = new NBTTagCompound();
        frequency.writeNBT(frequencyTag);
        tag.setTag("Frequency", frequencyTag);
        //tag.setString("owner", owner);
    }

    public boolean activate(EntityPlayer player, int subHit) {
        return false;
    }

    public void onPlaced(EntityLivingBase entity) {
    }

    //public boolean invincible() {
    //    return false;
    //}

    public RayTraceResult rayTrace(World world, Vec3d vec3d, Vec3d vec3d1, RayTraceResult fullBlock) {
        return fullBlock;
    }

    @Override
    public IndexedCuboid6 getBlockBounds() {
        return new IndexedCuboid6(0, new Cuboid6(0, 0, 0, 1, 1, 1));
    }

    @Override
    public final Packet getDescriptionPacket() {
        PacketCustom packet = new PacketCustom(EnderStorageSPH.channel, 1);
        packet.writeCoord(pos.getX(), pos.getY(), pos.getZ());
        packet.writeNBTTagCompound(frequency.toNBT());
        //packet.writeString(owner);
        writeToPacket(packet);
        return packet.toPacket();
    }

    public void writeToPacket(PacketCustom packet) {
    }

    public void handleDescriptionPacket(PacketCustom desc) {
        frequency.readNBT(desc.readNBTTagCompound());
        //owner = desc.readString();
    }

    public int getLightValue() {
        return 0;
    }

    public boolean redstoneInteraction() {
        return false;
    }

    public int comparatorInput() {
        return 0;
    }

    public boolean rotate() {
        return false;
    }
}
