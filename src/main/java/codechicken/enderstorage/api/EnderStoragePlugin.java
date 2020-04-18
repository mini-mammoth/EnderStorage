package codechicken.enderstorage.api;

import codechicken.enderstorage.manager.EnderStorageManager;
import codechicken.lib.config.ConfigTag;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public interface EnderStoragePlugin {

    AbstractEnderStorage createEnderStorage(EnderStorageManager manager, Frequency freq);

    String identifier();

    void sendClientInfo(PlayerEntity player, List<AbstractEnderStorage> list);

    void loadConfig(ConfigTag tag);
}
