package io.ejekta.makkit.common.network.pakkit

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.player.PlayerEntity

interface ClientBoundPakkit : Pakkit {
    fun sendToClient(player: PlayerEntity) {
        println("Sending pakkit to player")
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, getId(), write())
    }
}