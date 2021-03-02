package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class ShowAmdahlLawScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("1", "2", "3", "4", "64", "256")
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
}