package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean vampirism = false;
    public static boolean origins = false;
    public static boolean supersaturation = false;

    public ClassicAndSimpleStatusBars(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.

        //NeoForge.EVENT_BUS.register(this);

        // 注册客户端配置文件
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("CSSB: " + "Now 'CSSB' Has Loaded.");
        /*
        if ( Config.All_On ) {
            if ( Config.Food_On && ModList.get().isLoaded("appleskin") ) {
                LOGGER.info("CSSB: " + "Enable the Modifications to the appleskin");
                NeoForge.EVENT_BUS.register(new AppleSkinEventHandler());
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
                vampirism = true;
            }
            if ( Config.Origins_On && ModList.get().isLoaded("origins") ) {
                LOGGER.info("CSSB: " + "Enable the origins power value");
                origins = true;
            }
            if ( Config.supersaturation_On && ModList.get().isLoaded("supersaturation") ) {
                LOGGER.info("CSSB: " + "Enable the SuperSaturation Added Value");
                supersaturation = true;
            }
        } */
    }
}
