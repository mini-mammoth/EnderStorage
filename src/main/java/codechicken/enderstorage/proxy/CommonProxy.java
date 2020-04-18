package codechicken.enderstorage.proxy;

import codechicken.enderstorage.init.ModBlocks;
import codechicken.enderstorage.init.ModItems;
import codechicken.enderstorage.manager.EnderStorageManager;
import codechicken.enderstorage.network.EnderStorageSPH;
import codechicken.enderstorage.network.TankSynchroniser;
import codechicken.enderstorage.plugin.EnderItemStoragePlugin;
import codechicken.enderstorage.plugin.EnderLiquidStoragePlugin;
import codechicken.lib.packet.PacketCustom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

public class CommonProxy {

    public void preInit() {
        EnderStorageManager.registerPlugin(new EnderItemStoragePlugin());
        EnderStorageManager.registerPlugin(new EnderLiquidStoragePlugin());
        ModBlocks.init();
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new EnderStorageManager.EnderStorageSaveHandler());
        MinecraftForge.EVENT_BUS.register(new TankSynchroniser());
    }

    public void init() {
        PacketCustom.assignHandler(EnderStorageSPH.channel, new EnderStorageSPH());
    }

    @SubscribeEvent
    public void preServerStart(FMLServerStartedEvent event) {
        EnderStorageManager.reloadManager(false);
    }
}
