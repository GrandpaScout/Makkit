package io.ejekta.makkit.client.mixin;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface MixinKeybindDefaultChanger  {
    @Accessor void setDefaultKeyCode(InputUtil.KeyCode code);
}

