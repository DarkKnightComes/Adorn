package juuxel.adorn.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WLabel
import juuxel.adorn.client.resources.ColorManager
import juuxel.adorn.util.color
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class WColorableLabel(text: Text, private val paletteId: Identifier, private val colorKey: Identifier) : WLabel(text) {
    @get:Environment(EnvType.CLIENT)
    private val _color by lazy {
        color(ColorManager.getColors(paletteId)[colorKey].fg)
    }

    @Environment(EnvType.CLIENT)
    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.drawString(matrices, text, x, y, _color)
    }
}
