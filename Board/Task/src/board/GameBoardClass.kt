package board

import java.security.Key

class GameBoardClass<T>(width: Int) :GameBoard<T>, SquareBoardClass(width) {

    val sqb:SquareBoardClass
    //= SquareBoardClass(width)
    var cellsMap: Map<Cell,T?> = mutableMapOf<Cell,T?>()

    init {
        sqb = SquareBoardClass(width)
        for ( cell1 in this.sqb.cells ) {
            this.cellsMap = this.cellsMap + Pair(cell1,null)
        }

    }

    override fun get(cell: Cell): T? {
        return this.cellsMap.get(cell)
    }

    override fun set(cell: Cell, value: T?) {
        this.cellsMap = this.cellsMap.plus(Pair(cell,value))
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        this.cellsMap.filter { it -> predicate(it.value) }.keys


    override fun find(predicate: (T?) -> Boolean): Cell? =
        this.cellsMap.filter { it -> predicate(it.value) }.asSequence().first().key

    override fun any(predicate: (T?) -> Boolean): Boolean {
        for (t in this.cellsMap.asSequence()){
            println("[ $t ]")
        }
        return this.cellsMap
            .filter { it-> predicate(it.value) }.asSequence().count().equals(0).not()
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        for (t in this.cellsMap.asSequence()){
            println("[ $t ]")
        }
        var a1 = this.cellsMap
            .filter{it-> !predicate(it.value) }.count()
        println("perdicate: ${predicate.toString()} \n found cells: $a1 \n MAP size: ${this.cellsMap.size}")

        return a1 == 0
    }

}

class T {}