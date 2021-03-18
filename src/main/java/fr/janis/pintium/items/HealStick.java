package fr.janis.pintium.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HealStick extends Item {
    public HealStick(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)){
            tooltip.add(new TranslationTextComponent("tooltip.heal_stick.hold_shift"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.heal_stick.not_hold_shift"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!playerIn.getCooldownTracker().hasCooldown(this)) {

            playerIn.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 4));

            playerIn.getCooldownTracker().setCooldown(this, 20*120);

            playerIn.getHeldItem(handIn).damageItem(8, playerIn, (player) -> player.sendBreakAnimation(EquipmentSlotType.MAINHAND));

            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }

        //ZombieEntity entity = new ZombieEntity(worldIn);
        //entity.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
        //worldIn.addEntity(entity);

        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

}
