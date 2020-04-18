package codechicken.enderstorage;

import codechicken.enderstorage.proxy.ClientProxy;
import codechicken.enderstorage.proxy.CommonProxy;
import codechicken.enderstorage.proxy.ServerProxy;
import codechicken.lib.CodeChickenLib;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import static codechicken.enderstorage.EnderStorage.MOD_ID;
import static codechicken.lib.CodeChickenLib.MC_VERSION;

@Mod (MOD_ID)
public class EnderStorage {

    public static final String MOD_ID = "enderstorage";
    public static final String MOD_NAME = "EnderStorage";
    public static final String VERSION = "${mod_version}";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.4,);" + CodeChickenLib.MOD_VERSION_DEP;
    static final String UPDATE_URL = "http://chickenbones.net/Files/notification/version.php?query=forge&version=" + MC_VERSION + "&file=EnderStorage";

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);


    private static String modifyDesc(String desc) {
        desc += "\n";
        desc += "    Credits: Ecu - original idea, design, chest and pouch texture\n";
        desc += "    Rosethorns - tank model\n";
        desc += "    Soaryn - tank texture\n";
        return desc;
    }
}
