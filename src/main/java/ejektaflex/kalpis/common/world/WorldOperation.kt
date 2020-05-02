package ejektaflex.kalpis.common.world

import ejektaflex.kalpis.common.ext.getBlockArray

enum class WorldOperation(val execute: (it: EditAction) -> Unit) {

    FILL(::fillBlocks);

    private companion object {

        fun fillBlocks(action: EditAction) {
            for (pos in action.box.getBlockArray()) {

            }
        }

    }


}