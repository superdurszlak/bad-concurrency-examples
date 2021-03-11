package task.matrix.io

import org.openjdk.jmh.annotations.*
import task.executor.TaskExecutorFactory

open class LargeFilesConcurrentScenario: BaseFileWriteScenario() {

    open class ExecutionPlan(
        @Param("1000000")
        override var charactersPerFile: Int = 1000000,
        @Param("1")
        override var filesPerSubtask: Int = 1,
        @Param("1000", "1000000")
        override var fileBlockSize: Int = 1000000,
        @Param("100")
        override var filesToWrite: Int = 100,
        @Param("1", "2", "3", "4", "8", "64", "512")
        override var threadCount: Int = 1,
        @Param("SAFE_SERIAL_CONCURRENT_WRITE", "SAFE_PARALLEL_CONCURRENT_WRITE")
        override var fileWriteAlgorithm: FileWriteAlgorithm = FileWriteAlgorithm.SAFE_SERIAL_CONCURRENT_WRITE,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("writing some 1MiB files, files can be shared")
        override var benchmarkContext: String = ""
    ): BaseExecutionPlan(directoryPrefix = "large-files-concurrent") {

        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }

        @TearDown(Level.Iteration)
        fun tearDown() {
            tearDownIteration()
        }
    }

    @Benchmark
    fun execute(executionPlan: ExecutionPlan) {
        executeBenchmark(executionPlan)
    }
}