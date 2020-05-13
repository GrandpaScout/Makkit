package io.ejekta.makkit.common.network.pakkit

import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

interface PakkitHandler {

    fun getId(): Identifier

    fun run(context: PacketContext, buffer: PacketByteBuf)

}