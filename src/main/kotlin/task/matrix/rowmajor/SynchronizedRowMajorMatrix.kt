package task.matrix.rowmajor

import task.matrix.Matrix

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