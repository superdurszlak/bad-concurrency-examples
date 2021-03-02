package task.matrix

abstract class Matrix(
    val rows: Int,
    val columns: Int)
{
    init {
        require(rows > 0) { "Matrix row count must be greater than 0" }
        require(columns > 0) { "Matrix column count must be greater than 0" }
    }

    abstract operator fun get(row: Int, column: Int): Double
    abstract operator fun set(row: Int, column: Int, value: Double)
}