package zimnycat.Ruler.modules

import com.lambda.client.module.Category
import com.lambda.client.plugin.api.PluginModule
import com.lambda.client.util.TickTimer
import com.lambda.client.util.TimeUnit
import com.lambda.client.util.text.MessageSendHelper
import com.lambda.client.util.threads.safeListener
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.gameevent.TickEvent
import zimnycat.Ruler.RulerPlugin
import kotlin.math.round
import kotlin.math.sqrt

internal object Ruler : PluginModule(
    name = "Ruler",
    category = Category.PLAYER,
    description = "Just a ruler",
    pluginMain = RulerPlugin
) {

    private val timer = TickTimer(TimeUnit.SECONDS)
    private var enablePos: BlockPos? = null

    private val delay by setting("Delay", 3, 1..10, 1)

    init {
        onEnable {
            timer.reset()
            enablePos = mc.player.position
            MessageSendHelper.sendChatMessage("enable position: ${pos2coords(enablePos)}")
        }

        safeListener<TickEvent.ClientTickEvent> {
            if (timer.tick(delay)) sendDist()
        }

        onDisable {
            sendDist()
            MessageSendHelper.sendChatMessage("disable position: ${pos2coords(mc.player.position)}")
        }
    }

    private fun sendDist() {
        val dist = round(sqrt(mc.player.getDistanceSq(enablePos)) * 10) / 10
        MessageSendHelper.sendChatMessage("distance: ${if (dist < 1) 0 else dist}")
    }

    private fun pos2coords(pos: BlockPos?) = "${pos?.x} ${pos?.y} ${pos?.z}"

}