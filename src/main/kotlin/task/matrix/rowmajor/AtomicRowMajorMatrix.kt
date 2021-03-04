package task.matrix.rowmajor

import task.matrix.Matrix
import java.util.concurrent.atomic.AtomicReference

/*
 NOTE: This matrix implementation is NOT thread safe. It only introduces deliberate synchronization on its elements.

 Certain operations on its elements eg. += may (and will) still lead to a race condition and yield erroneous results in some circumstances.
 */
class AtomicRowMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<Array<AtomicReference<Double>>> = Array(rows) {
        Array(columns) {
            AtomicReference(0.0)
        }
    }

    override operator fun get(row: Int, column: Int): Double = elements[row][column].get()

    override operator fun set(row: Int, column: Int, value: Double) {
        elements[row][column].getAndSet(value)
    }
}