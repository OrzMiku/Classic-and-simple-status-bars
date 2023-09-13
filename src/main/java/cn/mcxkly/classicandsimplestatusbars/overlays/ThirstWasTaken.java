package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;

public class ThirstWasTaken extends HUDOverlayHandler {
//        private static final ResourceLocation THIRST_LEVEL_ELEMENT = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "thirst_level");;
//
//        @Override
//        public void onRenderGuiOverlayPre(RenderGuiOverlayEvent.Pre event) {
//                if (event.getOverlay() == GuiOverlayManager.findOverlay(THIRST_LEVEL_ELEMENT)) {
//                        Minecraft mc = Minecraft.getInstance();
//                        ForgeGui gui = (ForgeGui)mc.gui;
//                        boolean isMounted = mc.player.getVehicle() instanceof LivingEntity;
//                        if (!isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
//                                //renderExhaustion(gui, event.getGuiGraphics());
//                        }
//                }
//        }

}
