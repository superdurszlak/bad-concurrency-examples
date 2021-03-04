package task.matrix.columnmajor

import task.matrix.Matrix
import java.util.concurrent.atomic.AtomicReference

/*
 NOTE: This matrix implementation is NOT thread safe. It only introduces deliberate synchronization on its elements.

 Certain operations on its elements eg. += may (and will) still lead to a race condition and yield erroneous results in some circumstances.
 */
class AtomicColumnMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<Array<AtomicReference<Double>>> = Array(columns) {
        Array(rows) {
            AtomicReference(0.0)
        }
    }

    override operator fun get(row: Int, column: Int): Double = elements[column][row].get()

    override operator fun set(row: Int, column: Int, value: Double) {
        elements[column][row].getAndSet(value)
    }
}