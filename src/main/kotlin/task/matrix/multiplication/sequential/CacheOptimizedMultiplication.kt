package task.matrix.multiplication.sequential

import task.matrix.*
import task.matrix.multiplication.partialMultiplication

fun cacheOptimizedMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory,
    blockSize: Int
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)

    val dotProductChunks = (0 until leftMatrix.columns).chunked(blockSize)
    val chunkedColumnChunks = (0 until leftMatrix.rows).chunked(blockSize)
    val chunkedRowChunks = (0 until rightMatrix.columns).chunked(blockSize)


    chunkedColumnChunks.forEach { columnChunk ->
        chunkedRowChunks.forEach { rowChunk ->
            dotProductChunks.forEach { dotProductChunk ->
                partialMultiplication(leftMatrix, rightMatrix, resultMatrix, rowChunk, columnChunk, dotProductChunk)
            }
        }
    }
    return resultMatrix
}