package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class ReferenceScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("100")
        override var size: Int = 100,
        @Param("UNSAFE_ROW_MAJOR")
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