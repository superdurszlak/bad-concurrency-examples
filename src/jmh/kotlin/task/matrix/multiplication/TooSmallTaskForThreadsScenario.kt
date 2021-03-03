package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.TaskExecutorFactory

open class TooSmallTaskForThreadsScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("10")
        override var size: Int = 10,
        @Param("10")
        override var columnBlockSize: Int = 10,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("10")
        override var dotProductBlockSize: Int = 10,
        @Param("4")
        override var threadCount: Int = 1,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("SEQUENTIAL_NAIVE", "CONCURRENT_SAFE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE,
        @Param("concurrent execution may be detrimental if tasks are too small")
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