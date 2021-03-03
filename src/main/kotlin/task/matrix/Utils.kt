package task.matrix

import task.executor.TaskExecutor
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

    partialCopy(newMatrix, (0 until rows), (0 until columns))

    return newMatrix
}

fun Matrix.copyConcurrently(
    matrixFactory: MatrixFactory,
    rowBlockSize: Int,
    columnBlockSize: Int,
    taskExecutor: TaskExecutor
): Matrix {
    val newMatrix = matrixFactory.create(rows, columns)

    val columnChunks = (0 until columns).chunked(columnBlockSize)
    val rowChunks = (0 until rows).chunked(rowBlockSize)

    val tasks = rowChunks.map { columnChunk ->
        columnChunks.map { rowChunk ->
            { partialCopy(newMatrix, rowChunk, columnChunk) }
        }
    }.flatten()

    taskExecutor.execute(tasks)

    return newMatrix
}

private fun Matrix.partialCopy(newMatrix: Matrix, rowChunk: Iterable<Int>, columnChunk: Iterable<Int>) {
    rowChunk.forEach { row ->
        columnChunk.forEach { column ->
            newMatrix[row, column] = this[row, column]
        }
    }
}