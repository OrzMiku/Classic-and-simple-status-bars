package cn.mcxkly.classicandsimplestatusbars.events;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.overlays.Overlays;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverlayRegisteringHandler {

    private static final ResourceLocation chatOverlayLocation = new ResourceLocation("minecraft", "chat_panel");

    @SubscribeEvent
    public static void registerHealthBar(RegisterGuiOverlaysEvent event) {
        event.registerBelow(chatOverlayLocation, "health_bar", Overlays.healthBar);
    }

}
