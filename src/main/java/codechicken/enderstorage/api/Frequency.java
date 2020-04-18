package codechicken.enderstorage.api;

import codechicken.enderstorage.lib.ItemNBTUtils;
import codechicken.enderstorage.lib.MCDataInput;
import codechicken.enderstorage.lib.MCDataOutput;
import codechicken.lib.colour.EnumColour;
import codechicken.lib.util.Copyable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

/**
 * Created by covers1624 on 4/26/2016.
 */
public final class Frequency implements Copyable<Frequency> {

    public EnumColour left;
    public EnumColour middle;
    public EnumColour right;
    public String owner;

    public Frequency() {
        this(EnumColour.WHITE, EnumColour.WHITE, EnumColour.WHITE);
    }

    public Frequency(EnumColour left, EnumColour middle, EnumColour right) {
        this(left, middle, right, null);
    }

    public Frequency(EnumColour left, EnumColour middle, EnumColour right, String owner) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.owner = owner;
    }

    public Frequency(CompoundNBT tagCompound) {
        read_internal(tagCompound);
    }

    public static Frequency fromString(String left, String middle, String right) {
        return fromString(left, middle, right, null);
    }

    public static Frequency fromString(String left, String middle, String right, String owner) {
        EnumColour c1 = EnumColour.fromName(left);
        EnumColour c2 = EnumColour.fromName(middle);
        EnumColour c3 = EnumColour.fromName(right);
        if (c1 == null) {
            throw new RuntimeException(left + " is an invalid colour!");
        }
        if (c2 == null) {
            throw new RuntimeException(middle + " is an invalid colour!");
        }
        if (c3 == null) {
            throw new RuntimeException(right + " is an invalid colour!");
        }
        return new Frequency(c1, c2, c3, owner);
    }

    public Frequency setLeft(EnumColour left) {
        if (left != null) {
            this.left = left;
        }
        return this;
    }

    public Frequency setMiddle(EnumColour middle) {
        if (middle != null) {
            this.middle = middle;
        }
        return this;
    }

    public Frequency setRight(EnumColour right) {
        if (right != null) {
            this.right = right;
        }
        return this;
    }

    public Frequency setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public Frequency set(EnumColour[] colours) {
        setLeft(colours[0]);
        setMiddle(colours[1]);
        setRight(colours[2]);
        return this;
    }

    public Frequency set(Frequency frequency) {
        setLeft(frequency.left);
        setMiddle(frequency.middle);
        setRight(frequency.right);
        setOwner(frequency.owner);
        return this;
    }

    public EnumColour getLeft() {
        return left;
    }

    public EnumColour getMiddle() {
        return middle;
    }

    public EnumColour getRight() {
        return right;
    }

    public EnumColour[] toArray() {
        return new EnumColour[] { left, middle, right };
    }

    protected Frequency read_internal(CompoundNBT tagCompound) {
        left = EnumColour.fromWoolMeta(tagCompound.getInt("left"));
        middle = EnumColour.fromWoolMeta(tagCompound.getInt("middle"));
        right = EnumColour.fromWoolMeta(tagCompound.getInt("right"));
        if (tagCompound.contains("owner")) {
            owner = tagCompound.getString("owner");
        }
        return this;
    }

    protected CompoundNBT write_internal(CompoundNBT tagCompound) {
        tagCompound.putInt("left", left.getWoolMeta());
        tagCompound.putInt("middle", middle.getWoolMeta());
        tagCompound.putInt("right", right.getWoolMeta());
        if (owner != null) {
            tagCompound.putString("owner", owner);
        }
        return tagCompound;
    }

    public CompoundNBT writeToNBT(CompoundNBT tagCompound) {
        write_internal(tagCompound);
        return tagCompound;
    }

    public void writeToPacket(MCDataOutput packet) {
        packet.writeNBTTagCompound(write_internal(new CompoundNBT()));
    }

    public static Frequency readFromPacket(MCDataInput packet) {
        return new Frequency(packet.readNBTTagCompound());
    }

    public static Frequency readFromStack(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT stackTag = stack.getTag();
            if (stackTag.contains("Frequency")) {
                return new Frequency(stackTag.getCompound("Frequency"));
            }
        }
        return new Frequency();
    }

    public ItemStack writeToStack(ItemStack stack) {
        CompoundNBT tagCompound = ItemNBTUtils.validateTagExists(stack);
        tagCompound.put("Frequency", write_internal(new CompoundNBT()));
        return stack;
    }

    public String toModelLoc() {
        return "left=" + getLeft().getName() + ",middle=" + getMiddle().getName() + ",right=" + getRight().getName() + ",owned=" + hasOwner();
    }

    @Override
    public String toString() {
        String owner = "";
        if (hasOwner()) {
            owner = ",owner=" + this.owner;
        }
        return "left=" + getLeft().getName() + ",middle=" + getMiddle().getName() + ",right=" + getRight().getName() + owner;
    }

    public String getTooltip() {
        return String.format("%s/%s/%s", getLeft().getLocalizedName(), getMiddle().getLocalizedName(), getRight().getLocalizedName());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public Frequency copy()
    {
        return new Frequency(this.left, this.middle, this.right, this.owner);
    }
}
