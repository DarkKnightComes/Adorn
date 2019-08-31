package juuxel.adorn.compat.extrapieces

import com.shnupbups.extrapieces.api.EPInitializer
import com.shnupbups.extrapieces.core.PieceSets
import com.shnupbups.extrapieces.core.PieceType
import com.shnupbups.extrapieces.core.PieceTypes
import com.swordglowsblue.artifice.api.ArtificeResourcePack
import juuxel.adorn.Adorn
import juuxel.adorn.compat.extrapieces.piece.*
import juuxel.polyester.block.PolyesterBlock
import juuxel.polyester.block.PolyesterBlockEntityType
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback
import net.minecraft.block.Block
import net.minecraft.item.Items
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry

object AdornPieces : EPInitializer {
    val HORIZONTAL_DIRECTIONS = Direction.values().filter { it.axis.isHorizontal }

    val DRAWER = DrawerPiece
    val TABLE = TablePiece
    val SHELF = ShelfPiece
    val POST = PostPiece
    val PLATFORM = PlatformPiece
    val STEP = StepPiece
    val CHAIR = ChairPiece
    val SOFA = SofaPiece
    val KITCHEN_COUNTER = KitchenCounterPiece
    val KITCHEN_CUPBOARD = KitchenCupboardPiece

    override fun addData(data: ArtificeResourcePack.ServerResourcePackBuilder) {
        PieceSets.registry.values.forEach { set ->
            if (set.hasPiece(KITCHEN_COUNTER) && set.hasPiece(KITCHEN_CUPBOARD)) {
                val id = Registry.BLOCK.getId(set.getPiece(KITCHEN_CUPBOARD))
                val counter = Registry.ITEM.getId(set.getPiece(KITCHEN_COUNTER).asItem())
                data.addShapelessRecipe(id) {
                    it.result(id, 2)
                    it.group(Adorn.id(set.originalName))

                    it.ingredientItem(Registry.ITEM.getId(Items.CHEST))
                    it.ingredientItem(counter)
                    it.ingredientItem(counter)
                }
            }
        }
    }

    override fun onInitialize() {
        register(DRAWER, TABLE, SHELF, POST, PLATFORM, STEP, CHAIR, SOFA, KITCHEN_COUNTER, KITCHEN_CUPBOARD)

        RegistryEntryAddedCallback.event(Registry.BLOCK).register(RegistryEntryAddedCallback { _, _, block: Block ->
            if (block is PolyesterBlock) {
                (block.blockEntityType as? PolyesterBlockEntityType<*>)?.let { type ->
                    type.addBlock(block)
                }
            }
        })
    }

    private fun register(vararg types: PieceType) {
        for (type in types) {
            PieceTypes.register(type)
            //ModItemGroups.groups[type] = ItemGroup.DECORATIONS
        }
    }

    override fun toString() = "from Adorn is wondering why Extra Pieces is using toString()"
}