package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.silentchaos512.scalinghealth.client.gui.health.HeartDisplayHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HeartDisplayHandler.class, remap = false)
public class ScalingHealthMixin {
    @Inject(method = "onHealthBar", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfo ci) {
        // 效果等于 this.info.heartStyle.get() = HeartIconStyle.VANILLA ，无视掉配置文件的修改.
        if ( Config.All_On ) {
            ci.cancel();
        }
    }

    @Inject(method = "onHealthDraw", at = @At("HEAD"), cancellable = true)
    private void injected2(CallbackInfo ci) {
        // 效果等于 this.info.heartStyle.get() = HeartIconStyle.VANILLA ，无视掉配置文件的修改.
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}
