package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.TaskExecutorFactory

open class UnnecessaryLowLevelThreadHandlingScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("4")
        override var threadCount: Int = 1,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR
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