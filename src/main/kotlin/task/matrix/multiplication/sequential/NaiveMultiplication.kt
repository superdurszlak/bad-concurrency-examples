package task.matrix.multiplication.sequential

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.dotProduct

fun naiveMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)
    (0 until leftMatrix.rows).forEach { row ->
        (0 until rightMatrix.columns).forEach { column ->
            resultMatrix[row, column] = dotProduct(leftMatrix, rightMatrix, row, column)
        }
    }
    return resultMatrix
}