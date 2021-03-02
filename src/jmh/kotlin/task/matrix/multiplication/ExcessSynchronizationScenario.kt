package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory

open class ExcessSynchronizationScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("4")
        override var threadCount: Int = 1,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("ATOMIC_ROW_MAJOR", "SYNCHRONIZED_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.ATOMIC_ROW_MAJOR
    ) : BaseExecutionPlan() {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun unsafeConcurrentMatrixMultiplication(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        execUnsafeConcurrentMultiplication(executionPlan, blackhole)
    }
}