package net.celebee.morevanilla.Items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DodgeCharm extends Item {

    double dodgeForce = 1.5;
    int cooldownDuration = 60;

    public DodgeCharm(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){


        if (world.isClient) {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage("The client is being called");
            SpawnParticles(world, player);
            //plays sound when clicked
            world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_WITCH_THROW, SoundCategory.BLOCKS, 1f, 1f);

            return TypedActionResult.pass(player.getStackInHand(hand));
        }



        Vec3d playerLook = player.getRotationVector();
        player.addVelocity(playerLook.x * -dodgeForce, 0, playerLook.z * -dodgeForce);
        player.velocityModified = true;




        //adds cooldown after use to avoid spam
        player.getItemCooldownManager().set(this, cooldownDuration);

        //damages durability of an item
        ItemStack heldStack = player.getStackInHand(hand);
        heldStack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
        TypedActionResult.success(heldStack);


        //return itemstack
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    void SpawnParticles(World world, PlayerEntity player){

        for(int i = 0; i < 360 ; i++){
            if(i % 20 == 0){
                world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), Math.cos(i) * 0.25, 0.0, Math.sin(i) * 0.25);
            }
        }

    }
}
