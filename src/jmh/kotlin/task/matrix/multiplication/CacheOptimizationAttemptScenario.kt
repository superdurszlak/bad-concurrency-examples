package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class CacheOptimizationAttemptScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("1500")
        override var size: Int = 1500,
        @Param("16")
        var blockSize: Int = 16,
    ) : BaseExecutionPlan(columnBlockSize = blockSize, rowBlockSize = blockSize, dotProductBlockSize = blockSize) {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun cacheOptimizedMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execCacheOptimizedSequentialMultiplication(executionPlan, blackhole)
    }

    @Benchmark
    fun naiveMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execNaiveSequentialMultiplication(executionPlan, blackhole)
    }
}