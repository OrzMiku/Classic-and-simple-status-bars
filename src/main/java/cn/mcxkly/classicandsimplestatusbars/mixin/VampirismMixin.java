package cn.mcxkly.classicandsimplestatusbars.mixin;

import de.teamlapen.vampirism.client.gui.overlay.BloodBarOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BloodBarOverlay.class, remap = false)
public class VampirismMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderExhaustion(CallbackInfo ci) {
        // 让我替你渲染吧。
        ci.cancel();
    }
}
