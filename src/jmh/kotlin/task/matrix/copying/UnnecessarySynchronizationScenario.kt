package task.matrix.copying

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole
import task.executor.TaskExecutorFactory
import task.matrix.MatrixFactory

open class UnnecessarySynchronizationScenario: BaseMatrixCopyingScenario() {

    open class ExecutionPlan(
        @Param("5000")
        override var size: Int = 5000,
        @Param("5000")
        override var columnBlockSize: Int = 5000,
        @Param("1")
        override var rowBlockSize: Int = 1,
        @Param("4")
        override var threadCount: Int = 1,
        @Param("UNSAFE_ROW_MAJOR")
        override var sourceMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("SYNCHRONIZED_ROW_MAJOR", "UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("CONCURRENT_COPY")
        override var copyingAlgorithm: CopyingAlgorithm = CopyingAlgorithm.SEQUENTIAL_COPY,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("some operations do not require synchronization, and synchronization can be costly")
        override var benchmarkContext: String = ""
    ): BaseExecutionPlan() {

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