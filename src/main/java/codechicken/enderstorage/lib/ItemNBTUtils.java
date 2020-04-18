package codechicken.enderstorage.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class ItemNBTUtils {

    public static boolean hasTag(@Nonnull ItemStack stack) {
        return stack.hasTag();
    }

    public static void setTag(@Nonnull ItemStack stack, CompoundNBT tagCompound) {
        stack.setTag(tagCompound);
    }

    public static CompoundNBT getTag(@Nonnull ItemStack stack) {
        return stack.getTag();
    }

    public static CompoundNBT validateTagExists(@Nonnull ItemStack stack) {
        if (!hasTag(stack)) {
            setTag(stack, new CompoundNBT());
        }
        return getTag(stack);
    }
}
