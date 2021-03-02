package task.matrix.multiplication

import org.openjdk.jmh.infra.Blackhole
import task.matrix.multiplication.concurrent.safeConcurrentMultiplication
import task.matrix.multiplication.concurrent.unsafeConcurrentMultiplication
import task.matrix.multiplication.sequential.cacheOptimizedMultiplication
import task.matrix.multiplication.sequential.naiveMultiplication

open class BaseMatrixMultiplicationScenario {
    protected fun execNaiveSequentialMultiplication(executionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val result = naiveMultiplication(
            executionPlan.leftMatrix,
            executionPlan.rightMatrix,
            executionPlan.resultMatrixFactory
        )
        blackhole.consume(result)
    }

    protected fun execCacheOptimizedSequentialMultiplication(executionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val result = cacheOptimizedMultiplication(
            executionPlan.leftMatrix,
            executionPlan.rightMatrix,
            executionPlan.resultMatrixFactory,
            executionPlan.columnBlockSize,
            executionPlan.rowBlockSize,
            executionPlan.dotProductBlockSize
        )
        blackhole.consume(result)
    }

    protected fun execSafeConcurrentMultiplication(executionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val result = safeConcurrentMultiplication(
            executionPlan.leftMatrix,
            executionPlan.rightMatrix,
            executionPlan.resultMatrixFactory,
            executionPlan.taskExecutorFactory.create(
                executionPlan.threadCount
            ),
            executionPlan.columnBlockSize,
            executionPlan.rowBlockSize
        )
        blackhole.consume(result)
    }

    protected fun execUnsafeConcurrentMultiplication(executionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val result = unsafeConcurrentMultiplication(
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
        blackhole.consume(result)
    }
}