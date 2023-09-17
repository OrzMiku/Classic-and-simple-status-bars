package cn.mcxkly.classicandsimplestatusbars.overlays;

import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.api.event.HUDOverlayEvent;

public class AppleSkinEventHandler implements AppleSkinApi {
    @Override
    public void registerEvents() {
        // Disable unnecessary calculations and rendering
        HUDOverlayEvent.HungerRestored.EVENT.register(HungerRestored -> HungerRestored.isCanceled = true);
        HUDOverlayEvent.Saturation.EVENT.register(Saturation -> Saturation.isCanceled = true);
        HUDOverlayEvent.Exhaustion.EVENT.register(Exhaustion -> Exhaustion.isCanceled = true);
        HUDOverlayEvent.HealthRestored.EVENT.register(healthRestored -> healthRestored.isCanceled = true);
    }
}