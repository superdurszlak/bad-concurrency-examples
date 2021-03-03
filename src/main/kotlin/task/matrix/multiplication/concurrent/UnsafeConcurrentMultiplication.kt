package task.matrix.multiplication.concurrent

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.executor.TaskExecutor

fun unsafeConcurrentMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory,
    taskExecutor: TaskExecutor,
    columnBlockSize: Int,
    rowBlockSize: Int,
    dotProductBlockSize: Int
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)

    val subTasks =
        divideIntoSubtasks(leftMatrix, rightMatrix, resultMatrix, rowBlockSize, columnBlockSize, dotProductBlockSize)

    taskExecutor.execute(subTasks)

    return resultMatrix
}

