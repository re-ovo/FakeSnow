package me.rerere.fakesnow.nms.versions.v1_18_r1

import com.comphenix.protocol.events.PacketContainer
import me.rerere.fakesnow.nms.BiomeBridge
import me.rerere.fakesnow.nms.NMSVisitor
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData
import org.bukkit.entity.Player
import java.util.function.Consumer

class NMS_V1_18_R1 : NMSVisitor {
    private val clazz = ClientboundLevelChunkPacketData::class.java
    private val cField = clazz.getDeclaredField("c").apply {
        isAccessible = true
    }

    override fun modifyChunkPacket(
        player: Player,
        packet: PacketContainer,
        biomeBridgeConsumer: Consumer<BiomeBridge>
    ) {
        val chunkX = packet.integers.readSafely(0)
        val chunkZ = packet.integers.readSafely(1)
        val data: ClientboundLevelChunkPacketData = packet.getSpecificModifier(clazz).readSafely(0)
        val sectionsData = cField.get(data) as ByteArray

        // TODO: How to find the correct biomes index and modify it?

        biomeBridgeConsumer.accept(object : BiomeBridge {
            override fun getChunkX(): Int {
                return chunkX
            }

            override fun getChunkZ(): Int {
                return chunkZ
            }

            override fun getPlayer(): Player {
                return player
            }

            override fun setBiome(x: Int, z: Int) {
                throw UnsupportedOperationException("Not support 2D Biome Operations in this version!")
            }

            override fun setBiome(x: Int, y: Int, z: Int) {
                // TODO: Modify the sectionData
            }
        })
    }
}