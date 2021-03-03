package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.executor.TaskExecutorFactory

open class ExcessSynchronizationScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("200")
        override var size: Int = 200,
        @Param("200")
        override var columnBlockSize: Int = 200,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("10")
        override var dotProductBlockSize: Int = 10,
        @Param("4")
        override var threadCount: Int = 4,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("ATOMIC_ROW_MAJOR", "SYNCHRONIZED_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.SYNCHRONIZED_ROW_MAJOR,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("CONCURRENT_UNSAFE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.CONCURRENT_UNSAFE,
        @Param("compare synchronizing entire matrix with using atomic elements for thread safety")
        override var benchmarkContext: String = ""
    ) : BaseExecutionPlan() {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun execute(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        executeBenchmark(executionPlan, blackhole)
    }
}