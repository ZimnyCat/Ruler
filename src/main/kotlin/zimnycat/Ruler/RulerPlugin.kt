package zimnycat.Ruler

import com.lambda.client.plugin.api.Plugin
import zimnycat.Ruler.modules.Ruler

internal object RulerPlugin : Plugin() {

    override fun onLoad() {
        modules.add(Ruler)
    }

}