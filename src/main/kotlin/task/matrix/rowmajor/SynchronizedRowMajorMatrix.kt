package task.matrix.rowmajor

import task.matrix.Matrix

/*
 NOTE: This matrix implementation is NOT thread safe. It only introduces deliberate synchronization on its methods.

 Certain operations on its elements eg. += may (and will) still lead to a race condition and yield erroneous results in some circumstances.
 */
class SynchronizedRowMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<DoubleArray> = Array(rows) { DoubleArray(columns) }

    @Synchronized
    override operator fun get(row: Int, column: Int): Double = elements[row][column]

    @Synchronized
    override operator fun set(row: Int, column: Int, value: Double) {
        elements[row][column] = value
    }
}