package board

import java.security.Key

class GameBoardClass<T>(width1: Int) :GameBoard<T>, SquareBoardClass(width1) {
    //val sqb:SquareBoardClass = SquareBoardClass(width1)
    val cellsMap: MutableMap<Cell,T?> = mutableMapOf<Cell,T?>()

    init{
        for ( cell1 in cells ) {
            cellsMap.put(cell1,null)
        }
    }

    override fun get(cell: Cell): T? = cellsMap.get(cell)


    override fun set(cell: Cell, v1: T?) {
         cellsMap.put(cell,v1)
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        //this.cellsMap.filter { it -> predicate(it.value) }.keys
        var cc: Collection<Cell> = mutableListOf()
        for ((K,V) in cellsMap){
            if (predicate(V)) {
                cc += K
            }
        }
        return cc
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        //this.cellsMap.filter { it-> predicate(it.value)}.keys.single()
        for ((K,V) in  cellsMap.entries){
            if (predicate(V)) {
                return K
            }
        }
        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean = cellsMap
            .filter { it-> predicate(it.value) }.asSequence().count().equals(0).not()

    override fun all(predicate: (T?) -> Boolean): Boolean {
        var a1 = cellsMap
            .filter{it-> !predicate(it.value) }.count()
        return a1 == 0
    }

}

class T {}