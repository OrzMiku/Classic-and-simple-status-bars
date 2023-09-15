package cn.mcxkly.classicandsimplestatusbars.mixin;

import dev.ghen.thirst.foundation.gui.ThirstBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ThirstBarRenderer.class,remap = false)
abstract class ThirstBarRendererMixin {
    @Inject(method = "registerThirstOverlay", at = @At("HEAD"), cancellable = true)
    private static void registerThirstOverlay(CallbackInfo ci) {
        ci.cancel();
    }

}
