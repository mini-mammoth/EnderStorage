package codechicken.enderstorage.lib;

import com.google.common.base.Charsets;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MCDataUtils {

    /**
     * PacketBuffer.readVarIntFromBuffer
     */
    public static int readVarInt(MCDataInput in) {

        int i = 0;
        int j = 0;
        byte b0;

        do {
            b0 = in.readByte();
            i |= (b0 & 127) << j++ * 7;

            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        }
        while ((b0 & 128) == 128);

        return i;
    }

    public static int readVarShort(MCDataInput in) {

        int low = in.readUShort();
        int high = 0;
        if ((low & 0x8000) != 0) {
            low = low & 0x7FFF;
            high = in.readUByte();
        }
        return ((high & 0xFF) << 15) | low;
    }

    public static long readVarLong(MCDataInput in) {

        long i = 0L;
        int j = 0;

        while (true) {
            byte b0 = in.readByte();
            i |= (long) (b0 & 127) << j++ * 7;

            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }

            if ((b0 & 128) != 128) {
                break;
            }
        }

        return i;
    }

    public static String readString(MCDataInput in) {

        return new String(in.readArray(in.readVarInt()), Charsets.UTF_8);
    }

    @Nullable
    public static CompoundNBT readNBTTagCompound(MCDataInput input) {

        byte flag = input.readByte();
        if (flag == 0) {
            return null;
        } else if (flag == 1) {
            try {
                return CompressedStreamTools.read(new DataInputStream(new MCDataInputStream(input)), new NBTSizeTracker(2097152L));
            } catch (IOException e) {
                throw new EncoderException(e);
            }
        } else {
            throw new EncoderException("Invalid flag for readNBTTagCompound. Expected 0 || 1 Got: " + flag + " Possible incorrect read order?");
        }
    }

    public static void writeNBTTagCompount(@Nonnull MCDataOutput out, @Nullable CompoundNBT tag) {

        if (tag == null) {
            out.writeByte(0);
            return;
        }
        try {
            out.writeByte(1);
            CompressedStreamTools.write(tag, new DataOutputStream(new MCDataOutputStream(out)));
        } catch (IOException e) {
            throw new EncoderException(e);
        }
    }

}
