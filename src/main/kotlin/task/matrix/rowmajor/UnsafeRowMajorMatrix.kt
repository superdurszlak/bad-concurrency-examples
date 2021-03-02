package task.matrix.rowmajor

import task.matrix.Matrix

class UnsafeRowMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<DoubleArray> = Array(rows) { DoubleArray(columns) }

    override operator fun get(row: Int, column: Int): Double = elements[row][column]
    override operator fun set(row: Int, column: Int, value: Double) {
        elements[row][column] = value
    }
}

