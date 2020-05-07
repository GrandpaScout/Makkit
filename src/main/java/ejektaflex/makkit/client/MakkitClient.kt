package ejektaflex.makkit.client

import ejektaflex.makkit.client.config.MakkitConfigHandler
import ejektaflex.makkit.client.editor.EditRegion
import ejektaflex.makkit.client.editor.input.InputState
import ejektaflex.makkit.client.editor.input.MakkitKeys
import ejektaflex.makkit.client.event.Events
import ejektaflex.makkit.client.keys.KeyRemapper
import ejektaflex.makkit.common.world.WorldOperation
import ejektaflex.makkit.client.render.RenderHelper
import ejektaflex.makkit.common.enum.UndoRedoMode
import ejektaflex.makkit.common.network.pakkits.client.FocusRegionPacket
import ejektaflex.makkit.common.network.pakkits.server.EditHistoryPacket
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

class MakkitClient : ClientModInitializer {

    override fun onInitializeClient() {

        // Clientbound
        FocusRegionPacket.registerS2C()

        MakkitConfigHandler.load()

        MakkitKeys.setup()

        MakkitKeys.apply {
            fillBinding.setKeyDown {
                region?.doOperation(WorldOperation.SET)
            }

            wallsBinding.setKeyDown {
                region?.doOperation(WorldOperation.WALLS)
            }

            undoButton.setKeyDown {
                EditHistoryPacket(UndoRedoMode.UNDO).sendToServer()
            }

            redoButton.setKeyDown {
                EditHistoryPacket(UndoRedoMode.REDO).sendToServer()
            }
        }

        // Remap toolbar activators to '[' and ']'. These are rarely used and the player can view the controls
        // If they wish to see the new bindings.
        KeyRemapper.remap("key.saveToolbarActivator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_BRACKET)
        KeyRemapper.remap("key.loadToolbarActivator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_BRACKET)

        Events.DrawScreenEvent.Dispatcher.register(::onDrawScreen)
        Events.MouseScrollEvent.Dispatcher.register(::onScroll)
    }

    private fun onScroll(e: Events.MouseScrollEvent) {
        val reg = getOrCreateRegion()
        reg.tryScrollFace(e.amount)
    }

    private fun onDrawScreen(e: Events.DrawScreenEvent) {
        // RenderHelper state
        RenderHelper.setState(e.matrices, e.tickDelta, e.camera, e.buffers, e.matrix)

        for (handler in MakkitKeys.keyHandlers) {
            handler.update()
        }

        InputState.update()

        RenderHelper.drawInWorld {
            region?.update()
            region?.draw()
        }
    }

    companion object {

        var config = MakkitConfigHandler.load()

        fun getOrCreateRegion(): EditRegion {
            if (region == null) {
                region = EditRegion()
            }
            return region!!
        }

        var region: EditRegion? = EditRegion().apply {
            moveTo(0, 0, 0, 2, 3, 4)
        }

        const val ID = "makkit"
    }

}