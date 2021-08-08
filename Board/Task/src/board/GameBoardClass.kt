package board

import java.security.Key

class GameBoardClass<T>(width: Int) :GameBoard<T>, SquareBoardClass(width) {

    open val sqb:SquareBoardClass
    open var cellsMap: MutableMap<Cell,T?> = mutableMapOf<Cell,T?>()


    init {
        sqb = SquareBoardClass(width)
        for ( cell1 in this.sqb.cells ) {
            this.cellsMap.put(cell1,null)
        }
    }

    override fun get(cell: Cell): T? = this.cellsMap.get(cell)


    override fun set(cell: Cell, v1: T?) {
         this.cellsMap.put(cell,v1)
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        //this.cellsMap.filter { it -> predicate(it.value) }.keys
        var cc: Collection<Cell> = mutableListOf()
        for ((K,V) in this.cellsMap){
            if (predicate(V)) {
                cc += K
            }
        }
        return cc
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        //this.cellsMap.filter { it-> predicate(it.value)}.keys.single()
        for ((K,V) in  this.cellsMap.entries){
            if (predicate(V)) {
                return K
            }
        }
        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean = this.cellsMap
            .filter { it-> predicate(it.value) }.asSequence().count().equals(0).not()

    override fun all(predicate: (T?) -> Boolean): Boolean {
        var a1 = this.cellsMap
            .filter{it-> !predicate(it.value) }.count()
        return a1 == 0
    }

}

class T {}