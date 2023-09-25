package cn.mcxkly.classicandsimplestatusbars.events;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VanillaGuiHandler {

    private static final Minecraft mc = Minecraft.getInstance();

    private static final ResourceLocation vanillaHealthBar = new ResourceLocation("minecraft", "player_health");
    private static final ResourceLocation vanillaFoodBar = new ResourceLocation("minecraft", "food_level");
    private static final ResourceLocation vanillaAir = new ResourceLocation("minecraft", "air_level");
    private static final ResourceLocation vanillaMount_health = new ResourceLocation("minecraft", "mount_health");
    private static final ResourceLocation vanillAarmor_level = new ResourceLocation("minecraft", "armor_level");
    //private static final ResourceLocation ThirstLevel = new ResourceLocation("thirst", "thirst_level");
    private static final ResourceLocation toughasnailsl = new ResourceLocation("toughasnails", "thirst_level");

    @SubscribeEvent
    public static void disableVanillaAarmor(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!event.isCanceled() && event.getOverlay().id().equals(vanillAarmor_level) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void disableVanillaMount(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!event.isCanceled() && event.getOverlay().id().equals(vanillaMount_health) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void disableVanillaHealth(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!event.isCanceled() && event.getOverlay().id().equals(vanillaHealthBar) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void disableVanillaFood(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!event.isCanceled() && event.getOverlay().id().equals(vanillaFoodBar) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void disableVanillaAir(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!event.isCanceled() && event.getOverlay().id().equals(vanillaAir) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
            event.setCanceled(true);

        }
    }

    @SubscribeEvent
    public static void StopRenderingtoughasnailsl(RenderGuiOverlayEvent.Pre event) {
        final ForgeGui gui = ((ForgeGui) mc.gui);
        if (!(toughasnailsl == null)) { // 意志坚定
            if (!event.isCanceled() && event.getOverlay().id().equals(toughasnailsl) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
                event.setCanceled(true);
            }
        }
    }

//    @SubscribeEvent
//    public static void StopRenderingThirstLevel(RenderGuiOverlayEvent.Pre event) {
//        final ForgeGui gui = ((ForgeGui) mc.gui);
//        if (!event.isCanceled() && event.getOverlay().id().equals(ThirstLevelBar) && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player) {
//            //gui.leftHeight += 50;
//            event.setCanceled(true);
//
//        }
//    }
}
