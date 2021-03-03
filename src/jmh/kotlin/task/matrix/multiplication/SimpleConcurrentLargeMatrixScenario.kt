package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.TaskExecutorFactory

open class SimpleConcurrentLargeMatrixScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("2000")
        override var size: Int = 2000,
        @Param("2000")
        override var columnBlockSize: Int = 2000,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("2000")
        override var dotProductBlockSize: Int = 2000,
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
        @Param("CONCURRENT_SAFE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.CONCURRENT_SAFE,
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