package task.matrix.multiplication.sequential

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

open class SequentialBenchmark {

    @Benchmark
    fun cacheOptimized(executionPlan: CacheOptimizedExecutionPlan, blackhole: Blackhole) {
        val leftMatrix = executionPlan.leftMatrix
        val rightMatrix = executionPlan.rightMatrix
        val resultMatrixFactory = executionPlan.resultMatrixFactory
        val blockSize = executionPlan.blockSize
        val resultMatrix = cacheOptimizedMultiplication(leftMatrix, rightMatrix, resultMatrixFactory, blockSize)
        blackhole.consume(resultMatrix)
    }

    @Benchmark
    fun naive(executionPlan: NaiveExecutionPlan, blackhole: Blackhole) {
        val leftMatrix = executionPlan.leftMatrix
        val rightMatrix = executionPlan.rightMatrix
        val resultMatrixFactory = executionPlan.resultMatrixFactory
        val resultMatrix = naiveMultiplication(leftMatrix, rightMatrix, resultMatrixFactory)
        blackhole.consume(resultMatrix)
    }
}

