package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";

    public ClassicAndSimpleStatusBars() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void clientInit(final FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("appleskin")) { // Perhaps I don't need this because I can't achieve it
            MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
        }
    }
}
