package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.executor.TaskExecutorFactory

open class MemoryOptimizedLargeMatrixScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("2000")
        override var size: Int = 2000,
        @Param("16")
        override var columnBlockSize: Int = 16,
        @Param("16")
        override var rowBlockSize: Int = 16,
        @Param("16")
        override var dotProductBlockSize: Int = 16,
        @Param("4")
        override var threadCount: Int = 4,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("SEQUENTIAL_CACHE_OPTIMIZED")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE,
        @Param("optimizing for cache may boost execution significantly")
        override var benchmarkContext: String = ""
    ) : BaseExecutionPlan(
    ) {
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