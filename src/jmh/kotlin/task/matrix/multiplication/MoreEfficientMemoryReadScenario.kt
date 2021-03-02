package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class MoreEfficientMemoryReadScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("UNSAFE_COLUMN_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_COLUMN_MAJOR
    ) : BaseExecutionPlan() {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun naiveSequentialMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execNaiveSequentialMultiplication(executionPlan, blackhole)
    }
}