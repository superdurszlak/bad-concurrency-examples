package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.executor.TaskExecutorFactory

open class UnnecessaryLowLevelThreadHandlingScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("200")
        override var size: Int = 200,
        @Param("200")
        override var columnBlockSize: Int = 200,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("200")
        override var dotProductBlockSize: Int = 200,
        @Param("4")
        override var threadCount: Int = 1,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("CONCURRENT_SAFE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.CONCURRENT_SAFE,
        @Param("low-level thread handling does not mean faster execution")
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