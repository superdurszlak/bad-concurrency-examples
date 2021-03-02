package task.matrix.multiplication.concurrent

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.TaskExecutor

fun safeConcurrentMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory,
    taskExecutor: TaskExecutor,
    columnBlockSize: Int,
    rowBlockSize: Int
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)

    val subTasks =
        divideIntoSubtasks(leftMatrix, rightMatrix, resultMatrix, rowBlockSize, columnBlockSize, Int.MAX_VALUE)

    taskExecutor.execute(subTasks)

    return resultMatrix
}