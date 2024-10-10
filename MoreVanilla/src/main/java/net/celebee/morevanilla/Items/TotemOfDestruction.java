package net.celebee.morevanilla.Items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TotemOfDestruction extends Item {
    public TotemOfDestruction(Settings settings) {
        super(settings);
    }

    int lightningDistence = 3;
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
        if (world.isClient) {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage("The client is being called");
            SpawnParticles(world, player);
            //plays sound when clicked
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        MinecraftClient.getInstance().player.networkHandler.sendChatMessage("Using Totem Of Destruction");
        //LOGIC Create a ring of fire around the player
        BlockPos blockBelow = player.getBlockPos().down();

        //create fire charge entity
        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        LightningEntity lightningBoltTwo = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        LightningEntity lightningBoltThree = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        LightningEntity lightningBoltFour = new LightningEntity(EntityType.LIGHTNING_BOLT, world);

        lightningBolt.setPosition(blockBelow.add(lightningDistence, 0,0).toCenterPos());
        lightningBoltTwo.setPosition(blockBelow.add(-lightningDistence, 0,0).toCenterPos());
        lightningBoltThree.setPosition(blockBelow.add(0,0,lightningDistence).toCenterPos());
        lightningBoltFour.setPosition(blockBelow.add(0,0,-lightningDistence).toCenterPos());

        //spawns lightning entities
        world.spawnEntity(lightningBolt);
        world.spawnEntity(lightningBoltTwo);
        world.spawnEntity(lightningBoltThree);
        world.spawnEntity(lightningBoltFour);


        //decrements item stack by one
        ItemStack heldStack = player.getStackInHand(hand);
        heldStack.decrement(1);
        TypedActionResult.success(heldStack);

        //return itemstack
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    void SpawnParticles(World world, PlayerEntity player){

        for(int i = 0; i < 360 ; i++){
            if(i % 20 == 0){
                world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY() + 1, player.getZ(), Math.cos(i) * 0.25, 0.0, Math.sin(i) * 0.25);
            }
        }

    }
}
