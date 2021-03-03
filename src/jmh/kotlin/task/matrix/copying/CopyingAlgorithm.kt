package task.matrix.copying

import task.matrix.Matrix
import task.matrix.copy
import task.matrix.copyConcurrently

enum class CopyingAlgorithm(
    private val algorithm: (executionPlan: BaseExecutionPlan) -> Matrix
) {
    SEQUENTIAL_COPY(::copySequentially),
    CONCURRENT_COPY(::copyConcurrently);

    operator fun invoke(executionPlan: BaseExecutionPlan): Matrix = algorithm(executionPlan)
}

private fun copySequentially(executionPlan: BaseExecutionPlan): Matrix {
    val sourceMatrix = executionPlan.sourceMatrix
    val matrixFactory = executionPlan.resultMatrixFactory

    return sourceMatrix.copy(matrixFactory)
}

private fun copyConcurrently(executionPlan: BaseExecutionPlan): Matrix {
    val sourceMatrix = executionPlan.sourceMatrix
    val matrixFactory = executionPlan.resultMatrixFactory
    val taskExecutor = executionPlan.taskExecutorFactory.create(executionPlan.threadCount)

    return sourceMatrix.copyConcurrently(
        matrixFactory,
        executionPlan.columnBlockSize,
        executionPlan.rowBlockSize,
        taskExecutor
    )
}