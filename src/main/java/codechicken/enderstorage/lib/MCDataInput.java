package codechicken.enderstorage.lib;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public interface MCDataInput {

    long readLong();

    int readInt();

    short readShort();

    int readUShort();

    byte readByte();

    short readUByte();

    double readDouble();

    float readFloat();

    boolean readBoolean();

    char readChar();

    default int readVarShort() {
        return MCDataUtils.readVarShort(this);
    }

    default int readVarInt() {
        return MCDataUtils.readVarInt(this);
    }

    default long readVarLong() {
        return MCDataUtils.readVarLong(this);
    }

    byte[] readArray(int length);

    default String readString() {
        return MCDataUtils.readString(this);
    }

    default UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    default <T extends Enum<T>> T readEnum(Class<T> enumClass) {
        return enumClass.getEnumConstants()[readVarInt()];
    }

    default ResourceLocation readResourceLocation() {
        return new ResourceLocation(readString());
    }

    default BlockPos readPos() {
        return new BlockPos(readInt(), readInt(), readInt());
    }

    default CompoundNBT readNBTTagCompound() {
        return MCDataUtils.readNBTTagCompound(this);
    }

}
