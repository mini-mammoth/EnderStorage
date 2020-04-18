package codechicken.enderstorage.api;

import codechicken.enderstorage.manager.EnderStorageManager;
import net.minecraft.nbt.CompoundNBT;

public abstract class AbstractEnderStorage {

    public final EnderStorageManager manager;
    public final Frequency freq;
    private boolean dirty;
    private int changeCount;

    public AbstractEnderStorage(EnderStorageManager manager, Frequency freq) {
        this.manager = manager;
        this.freq = freq;
    }

    public void setDirty() {
        if (manager.client) {
            return;
        }

        if (!dirty) {
            dirty = true;
            manager.requestSave(this);
        }
        changeCount++;
    }

    public void setClean() {
        dirty = false;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public abstract void clearStorage();

    public abstract String type();

    public abstract CompoundNBT saveToTag();

    public abstract void loadFromTag(CompoundNBT tag);
}
