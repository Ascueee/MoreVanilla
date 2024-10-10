package net.celebee.morevanilla;

import net.celebee.morevanilla.Items.DodgeCharm;
import net.celebee.morevanilla.Items.TotemOfDestruction;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static Item register(Item item, String id){
        Identifier itemId = Identifier.of(MoreVanilla.MOD_ID, id);
        Item registeredItem = Registry.register(Registries.ITEM, itemId, item);

        return registeredItem;
    }

    public static void Initialize(){
        //registers a combat item
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(ModItems.DODGE_CHARM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(ModItems.TOTEM_OF_DESTRUCTION));


    }

    //***********************More Vanilla: Adventure Update Items****************************************
    public static final Item DODGE_CHARM = register(new DodgeCharm(new Item.Settings().rarity(Rarity.RARE).maxDamage(4)), "dodge_charm");
    public static final Item TOTEM_OF_DESTRUCTION = register(new TotemOfDestruction(new Item.Settings().maxCount(1).rarity(Rarity.RARE)),"totem_of_destruction");



    //***********************More Vanilla: Adventure Update Items******************************************
}
