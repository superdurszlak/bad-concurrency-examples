package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class TooSmallTaskForThreadsScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("10")
        override var size: Int = 10,
        @Param("4")
        override var threadCount: Int = 1,
        @Param("1")
        override var rowBlockSize: Int = 1
    ) : BaseExecutionPlan() {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun safeConcurrentMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execSafeConcurrentMultiplication(executionPlan, blackhole)
    }

    @Benchmark
    fun naiveSequentialMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execNaiveSequentialMultiplication(executionPlan, blackhole)
    }
}