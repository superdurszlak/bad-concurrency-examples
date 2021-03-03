package task.matrix.multiplication

import task.matrix.Matrix
import task.matrix.multiplication.concurrent.safeConcurrentMultiplication
import task.matrix.multiplication.concurrent.unsafeConcurrentMultiplication
import task.matrix.multiplication.sequential.cacheOptimizedMultiplication
import task.matrix.multiplication.sequential.naiveMultiplication

enum class MultiplicationAlgorithm(
    private val algorithm: (executionPlan: BaseExecutionPlan) -> Matrix
) {
    SEQUENTIAL_NAIVE(::execNaiveSequentialMultiplication),
    SEQUENTIAL_CACHE_OPTIMIZED(::execCacheOptimizedSequentialMultiplication),
    CONCURRENT_SAFE(::execSafeConcurrentMultiplication),
    CONCURRENT_UNSAFE(::execUnsafeConcurrentMultiplication);

    operator fun invoke(executionPlan: BaseExecutionPlan) : Matrix = algorithm(executionPlan)
}

private fun execNaiveSequentialMultiplication(executionPlan: BaseExecutionPlan) =
    naiveMultiplication(
        executionPlan.leftMatrix,
        executionPlan.rightMatrix,
        executionPlan.resultMatrixFactory
    )

private fun execCacheOptimizedSequentialMultiplication(executionPlan: BaseExecutionPlan) =
    cacheOptimizedMultiplication(
        executionPlan.leftMatrix,
        executionPlan.rightMatrix,
        executionPlan.resultMatrixFactory,
        executionPlan.columnBlockSize,
        executionPlan.rowBlockSize,
        executionPlan.dotProductBlockSize
    )

private fun execSafeConcurrentMultiplication(executionPlan: BaseExecutionPlan) =
    safeConcurrentMultiplication(
        executionPlan.leftMatrix,
        executionPlan.rightMatrix,
        executionPlan.resultMatrixFactory,
        executionPlan.taskExecutorFactory.create(
            executionPlan.threadCount
        ),
        executionPlan.columnBlockSize,
        executionPlan.rowBlockSize
    )

private fun execUnsafeConcurrentMultiplication(executionPlan: BaseExecutionPlan) =
    unsafeConcurrentMultiplication(
        executionPlan.leftMatrix,
        executionPlan.rightMatrix,
        executionPlan.resultMatrixFactory,
        executionPlan.taskExecutorFactory.create(
            executionPlan.threadCount
        ),
        executionPlan.columnBlockSize,
        executionPlan.rowBlockSize,
        executionPlan.dotProductBlockSize
    )