package task.matrix.multiplication.concurrent

import task.matrix.Matrix
import task.matrix.multiplication.partialMultiplication

fun divideIntoSubtasks(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrix: Matrix,
    rowBlockSize: Int,
    columnBlockSize: Int,
    dotProductBlockSize: Int
): List<() -> Unit> {

    val chunkedColumnRange = (0 until rightMatrix.columns).chunked(columnBlockSize)
    val chunkedRowRange = (0 until leftMatrix.rows).chunked(rowBlockSize)
    val chunkedDotProductRange = (0 until leftMatrix.columns).chunked(dotProductBlockSize)

    return chunkedColumnRange.map { columnChunk ->
        chunkedRowRange.map { rowChunk ->
            chunkedDotProductRange.map { dotProductChunk ->
                {
                    partialMultiplication(leftMatrix, rightMatrix, resultMatrix, columnChunk, rowChunk, dotProductChunk)
                }
            }
        }
    }
        .flatten()
        .flatten()

}