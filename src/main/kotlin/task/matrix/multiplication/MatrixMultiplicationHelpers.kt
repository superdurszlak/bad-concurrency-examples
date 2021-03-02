package task.matrix.multiplication

import task.matrix.Matrix
import task.matrix.partialDotProduct

fun divideAndRoundUp(nominator: Int, denominator: Int): Int {
    val nominator = nominator + denominator - 1
    return nominator / denominator
}

fun partialMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrix: Matrix,
    rowRange: Iterable<Int>,
    columnRange: Iterable<Int>,
    dotProductRange: Iterable<Int>
) {
    rowRange.forEach { row ->
        columnRange.forEach { column ->
            resultMatrix[row, column] += partialDotProduct(leftMatrix, rightMatrix, row, column, dotProductRange)
        }
    }
}