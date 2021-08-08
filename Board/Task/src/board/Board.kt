package board

import java.lang.IllegalArgumentException

data class Cell(val i: Int, val j: Int) {
    override fun toString()= "($i, $j)"
}

enum class Direction {
    UP, DOWN, RIGHT, LEFT;

    fun reversed() = when (this) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }
}

interface SquareBoard {
    val width: Int

    fun getCellOrNull(i: Int, j: Int): Cell?
    fun getCell(i: Int, j: Int): Cell

    fun getAllCells(): Collection<Cell>

    fun getRow(i: Int, jRange: IntProgression): List<Cell>
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>

    fun Cell.getNeighbour(direction: Direction): Cell?
}

interface GameBoard<T> : SquareBoard {

    operator fun get(cell: Cell): T?
    operator fun set(cell: Cell, value: T?)

    fun filter(predicate: (T?) -> Boolean): Collection<Cell>
    fun find(predicate: (T?) -> Boolean): Cell?
    fun any(predicate: (T?) -> Boolean): Boolean
    fun all(predicate: (T?) -> Boolean): Boolean
}

open class SquareBoardClass(override val width: Int) :SquareBoard {
    val cells: ArrayList<Cell> = ArrayList(width)
    init {
        for (x in 1..width+1) {
            for (y in 1..width+1) {
                cells.add(Cell(x,y))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i * j > cells.size  ) throw IllegalArgumentException("Your cell is out of board")
        return cells?.get( width*(i-1) + j - 1)
    }

    override fun getCell(i: Int, j: Int): Cell = cells.get( width*(i-1) + j - 1)

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        var cellslist : MutableList<Cell> = mutableListOf<Cell>()
        loop@ for ( j in  jRange) {
            if ( j in  1..width) {
                cellslist.add(cells.get(width*(i-1) + j ))
            } else break@loop
        }
        return cellslist
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        var cellslist : MutableList<Cell> = mutableListOf<Cell>()
        loop@ for ( i in  iRange) {
            if ( i in  1..width) {
                cellslist.add(cells.get(width*(i-1) + j ))
            } else break@loop
        }
        return cellslist
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when {
            direction == Direction.LEFT -> if (getCell(this.i, this.j).j == 1) return null else return getCell(
                this.i,
                this.j - 1
            )
            direction == Direction.RIGHT -> if (getCell(
                    this.i,
                    this.j
                ).j == width
            ) return null else return getCell(this.i, this.j + 1)
            direction == Direction.DOWN -> if (getCell(
                    this.i,
                    this.j
                ).i == width
            ) return null else return getCell(this.i + 1, this.j)
            direction == Direction.UP -> if (getCell(this.i, this.j).i == 1) return null else return getCell(
                this.i - 1,
                this.j
            )
            else -> return  null
        }

    }

}