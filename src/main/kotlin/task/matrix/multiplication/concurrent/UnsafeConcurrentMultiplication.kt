package task.matrix.multiplication.concurrent

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.TaskExecutor

fun unsafeConcurrentMultiplication(
    leftMatrix: Matrix,
    rightMatrix: Matrix,
    resultMatrixFactory: MatrixFactory,
    taskExecutor: TaskExecutor,
    rowBlockSize: Int,
    columnBlockSize: Int,
    dotProductBlockSize: Int
): Matrix {
    val resultMatrix = resultMatrixFactory.create(leftMatrix.rows, rightMatrix.columns)

    val subTasks =
        divideIntoSubtasks(leftMatrix, rightMatrix, resultMatrix, rowBlockSize, columnBlockSize, dotProductBlockSize)

    taskExecutor.execute(subTasks)

    return resultMatrix
}

