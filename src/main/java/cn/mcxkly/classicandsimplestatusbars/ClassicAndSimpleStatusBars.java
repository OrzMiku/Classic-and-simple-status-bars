package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean vampirism = false;
    public static boolean origins = false;
    public ClassicAndSimpleStatusBars() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this :: commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        // 注册客户端配置文件
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if ( Config.All_On ) {
            LOGGER.info("CSSB: " + "Now 'CSSB' Has Loaded.");
            if ( Config.Food_On && ModList.get().isLoaded("appleskin") ) {
                LOGGER.info("CSSB: " + "Enable the Modifications to the appleskin");
                MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
            }
            if ( Config.Thirst_On && ModList.get().isLoaded("thirst") ) {
                LOGGER.info("CSSB: " + "Enable For thirst the Thirst value");
                ThirstWasTakenUse.StopConflictRenderingIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Thirst_On && ModList.get().isLoaded("toughasnails") ) {
                LOGGER.info("CSSB: " + "Enable For toughasnails, the Thirst value");
                ThirstWasTakenUse.toughasnailsIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Thirst_On && ModList.get().isLoaded("homeostatic") ) {
                LOGGER.info("CSSB: " + "Enable For homeostatic, the Thirst value");
                ThirstWasTakenUse.HomeostaticIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Artifacts_On && ModList.get().isLoaded("artifacts") ) {
                LOGGER.info("CSSB: " + "Enable the flamingo swimming ring");
                FoodLevel.ArtifactsIDEA(true);
            }
            if ( Config.Bloodsucker_On && ModList.get().isLoaded("vampirism") ) {
                LOGGER.info("CSSB: " + "Enable the vampirism blood value");
                vampirism=true;
            }
            if ( Config.Origins_On && ModList.get().isLoaded("origins") ) {
                LOGGER.info("CSSB: " + "Enable the origins power value");
                origins=true;
            }
        }
    }
}
