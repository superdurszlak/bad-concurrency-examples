package task.matrix.columnmajor

import task.matrix.Matrix

class UnsafeColumnMajorMatrix(
    rows: Int,
    columns: Int
) : Matrix(rows, columns) {
    private val elements: Array<DoubleArray> = Array(columns) { DoubleArray(rows) }

    override operator fun get(row: Int, column: Int): Double = elements[column][row]
    override operator fun set(row: Int, column: Int, value: Double) {
        elements[column][row] = value
    }
}