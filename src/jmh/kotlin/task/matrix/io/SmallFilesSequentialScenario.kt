package task.matrix.io

import org.openjdk.jmh.annotations.*
import task.executor.TaskExecutorFactory

open class SmallFilesSequentialScenario: BaseFileWriteScenario() {

    open class ExecutionPlan(
        @Param("10000")
        override var charactersPerFile: Int = 10000,
        @Param("10000")
        override var filesPerSubtask: Int = 10000,
        @Param("10000")
        override var fileBlockSize: Int = 10000,
        @Param("10000")
        override var filesToWrite: Int = 10000,
        @Param("1")
        override var threadCount: Int = 1,
        @Param("SERIAL_SEQUENTIAL_WRITE")
        override var fileWriteAlgorithm: FileWriteAlgorithm = FileWriteAlgorithm.SERIAL_SEQUENTIAL_WRITE,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("copying many 10KiB files, files not shared")
        override var benchmarkContext: String = ""
    ): BaseExecutionPlan(directoryPrefix = "small-files") {

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