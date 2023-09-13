package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTaken;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";

    public ClassicAndSimpleStatusBars() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        if (ModList.get().isLoaded("thirst")) {
            //MinecraftForge.EVENT_BUS.register(new ThirstWasTaken());
            ThirstWasTakenUse.StopConflictRenderingIDEA(false);
            FoodLevel.StopConflictRenderingIDEA(false);
        }
    }
}
