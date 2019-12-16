package de.guntram.mcmod.worldtime.mixin;

import de.guntram.mcmod.worldtime.GuiWorldTime;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class PotionEffectsMixin {
    
    private static GuiWorldTime guiWorldTime;
    
    @Inject(method="renderStatusEffectOverlay", at=@At("RETURN"))
    
    private void onRenderPotionEffects(CallbackInfo ci) {
        if (guiWorldTime==null)
            guiWorldTime=new GuiWorldTime();
        guiWorldTime.onRenderGameOverlayPost(0);
    }
}
