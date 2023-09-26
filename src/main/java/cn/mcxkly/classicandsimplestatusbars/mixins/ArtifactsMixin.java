package cn.mcxkly.classicandsimplestatusbars.mixins;

import artifacts.client.HeliumFlamingoOverlay;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodBar;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = HeliumFlamingoOverlay.class, remap = false)
abstract class ArtifactsMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"))
    private static void registerThirstOverlay(int height, DrawContext guiGraphics, int screenWidth, int screenHeight, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
    }
}