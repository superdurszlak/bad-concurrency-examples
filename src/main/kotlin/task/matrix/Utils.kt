package task.matrix

import kotlin.random.Random

fun initializeRandomly(matrix: Matrix, randomGenerator: Random): Matrix {
    (0 until matrix.rows).forEach { row ->
        (0 until matrix.columns).forEach { column ->
            matrix[row, column] = randomGenerator.nextDouble()
        }
    }
    return matrix
}

fun dotProduct(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    leftRow: Int,
    rightColumn: Int
): Double = partialDotProduct(
    leftMatrix,
    rightMatrix,
    leftRow,
    rightColumn,
    (0 until leftMatrix.columns)
)

fun partialDotProduct(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    leftRow: Int,
    rightColumn: Int,
    indexes: Iterable<Int>
): Double {
    require(leftMatrix.columns == rightMatrix.rows) { "Cannot compute dot product - incompatible matrices dimensions" }
    return indexes.fold(0.0) { product, index ->
        val partialProduct = leftMatrix[leftRow, index] * rightMatrix[index, rightColumn]
        product + partialProduct
    }
}

fun Matrix.copy(matrixFactory: MatrixFactory): Matrix {
    val newMatrix = matrixFactory.create(rows, columns)
    (0 until rows).forEach { row ->
        (0 until columns).forEach { column ->
            newMatrix[row, column] = this[row, column]
        }
    }
    return newMatrix
}