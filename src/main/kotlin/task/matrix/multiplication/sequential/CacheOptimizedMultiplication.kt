package task.matrix.multiplication.sequential

import task.matrix.*
import task.matrix.multiplication.partialMultiplication

fun cacheOptimizedMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory,
    columnBlockSize: Int,
    rowBlockSize: Int,
    dotProductBlockSize: Int
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)

    val columnChunks = (0 until leftMatrix.rows).chunked(columnBlockSize)
    val rowChunks = (0 until rightMatrix.columns).chunked(rowBlockSize)
    val dotProductChunks = (0 until leftMatrix.columns).chunked(dotProductBlockSize)


    columnChunks.forEach { columnChunk ->
        rowChunks.forEach { rowChunk ->
            dotProductChunks.forEach { dotProductChunk ->
                partialMultiplication(leftMatrix, rightMatrix, resultMatrix, rowChunk, columnChunk, dotProductChunk)
            }
        }
    }
    return resultMatrix
}