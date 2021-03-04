package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.executor.TaskExecutorFactory

open class MoreEfficientMemoryReadScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("500")
        override var size: Int = 500,
        @Param("500")
        override var columnBlockSize: Int = 500,
        @Param("500")
        override var rowBlockSize: Int = 500,
        @Param("500")
        override var dotProductBlockSize: Int = 500,
        @Param("1")
        override var threadCount: Int = 1,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_COLUMN_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("SEQUENTIAL_NAIVE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE,
        @Param("more efficient arrangement for reads may boost execution")
        override var benchmarkContext: String = ""
    ) : BaseExecutionPlan(multiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE) {
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