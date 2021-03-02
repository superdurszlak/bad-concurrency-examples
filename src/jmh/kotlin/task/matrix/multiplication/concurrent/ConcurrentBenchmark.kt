package task.matrix.multiplication.concurrent

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole
import task.matrix.multiplication.sequential.cacheOptimizedMultiplication
import task.matrix.multiplication.sequential.naiveMultiplication

class ConcurrentBenchmark {

    @Benchmark
    fun safeMultiplication(executionPlan: SafeConcurrentExecutionPlan, blackhole: Blackhole) {
        val leftMatrix = executionPlan.leftMatrix
        val rightMatrix = executionPlan.rightMatrix
        val resultMatrixFactory = executionPlan.resultMatrixFactory
        val rowChunkSize = executionPlan.rowChunkSize
        val columnChunkSize = executionPlan.columnChunkSize
        val threadCount = executionPlan.rowChunkSize
        val taskExecutor = executionPlan.taskExecutorFactory.create(threadCount)

        val resultMatrix = safeConcurrentMultiplication(
            leftMatrix,
            rightMatrix,
            resultMatrixFactory,
            taskExecutor,
            rowChunkSize,
            columnChunkSize
        )
        blackhole.consume(resultMatrix)
    }

    @Benchmark
    fun unsafeMultiplication(executionPlan: UnsafeConcurrentExecutionPlan, blackhole: Blackhole) {
        val leftMatrix = executionPlan.leftMatrix
        val rightMatrix = executionPlan.rightMatrix
        val resultMatrixFactory = executionPlan.resultMatrixFactory
        val rowChunkSize = executionPlan.rowChunkSize
        val columnChunkSize = executionPlan.columnChunkSize
        val dotProductChunkSize = executionPlan.dotProductChunkSize
        val threadCount = executionPlan.rowChunkSize
        val taskExecutor = executionPlan.taskExecutorFactory.create(threadCount)

        val resultMatrix = unsafeConcurrentMultiplication(
            leftMatrix,
            rightMatrix,
            resultMatrixFactory,
            taskExecutor,
            rowChunkSize,
            columnChunkSize,
            dotProductChunkSize
        )
        blackhole.consume(resultMatrix)
    }
}