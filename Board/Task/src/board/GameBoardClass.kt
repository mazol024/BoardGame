package board

class GameBoardClass<T>(width: Int) :GameBoard<T>, SquareBoardClass(width) {

    val sqb = SquareBoardClass(width)
    var cellsMap: Map<Cell,T> = mutableMapOf()
    var cellValue : T? = null



    override fun get(cell: Cell): T? {
        return cellsMap.get(cell)
    }

    override fun set(cell: Cell, value: T?) {
        cellsMap.plus(Pair(cell,cellValue))
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return filter(predicate)
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return find(predicate)
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
       return any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return all(predicate)
    }
}

class T {}