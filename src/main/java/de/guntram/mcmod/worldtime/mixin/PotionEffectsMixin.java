package de.guntram.mcmod.worldtime.mixin;

import de.guntram.mcmod.worldtime.GuiWorldTime;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class PotionEffectsMixin {
    
    private static GuiWorldTime guiWorldTime;
    
    @Inject(method="render", at=@At(
            value="FIELD", 
            target="Lnet/minecraft/client/options/GameOptions;debugEnabled:Z", 
            opcode = Opcodes.GETFIELD, args = {"log=false"}))
    
    private void onRenderPotionEffects(MatrixStack stack, float f, CallbackInfo ci) {
        if (guiWorldTime==null)
            guiWorldTime=new GuiWorldTime();
        guiWorldTime.onRenderGameOverlayPost(stack, 0);
    }
}
