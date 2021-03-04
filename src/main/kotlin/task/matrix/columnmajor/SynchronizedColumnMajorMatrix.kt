package task.matrix.columnmajor

import task.matrix.Matrix

/*
 NOTE: This matrix implementation is NOT thread safe. It only introduces deliberate synchronization on its methods.

 Certain operations on its elements eg. += may (and will) still lead to a race condition and yield erroneous results in some circumstances.
 */
class SynchronizedColumnMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<DoubleArray> = Array(columns) { DoubleArray(rows) }

    @Synchronized
    override operator fun get(row: Int, column: Int): Double = elements[column][row]

    @Synchronized
    override operator fun set(row: Int, column: Int, value: Double) {
        elements[column][row] = value
    }
}