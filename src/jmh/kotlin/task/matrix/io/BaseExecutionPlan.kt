package task.matrix.io

import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import task.executor.TaskExecutorFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@State(Scope.Benchmark)
open class BaseExecutionPlan(
    open var charactersPerFile: Int = 1,
    open var filesToWrite: Int = 100,
    open var filesPerSubtask: Int = 10,
    open var fileBlockSize: Int = 1,
    open var threadCount: Int = 1,
    open var fileWriteAlgorithm: FileWriteAlgorithm = FileWriteAlgorithm.SERIAL_SEQUENTIAL_WRITE,
    open var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
    open var benchmarkContext: String = "",
    private val directoryPrefix: String = "base",
) {

    lateinit var tempDir: Path

    lateinit var fileContent: String

    protected fun setUpIteration() {
        tempDir = Files.createTempDirectory(directoryPrefix)
        fileContent = "0".repeat(charactersPerFile)
    }

    protected fun tearDownIteration() {
        tempDir
            .toFile()
            .listFiles()
            .orEmpty()
            .map(File::toPath)
            .forEach { path ->
                Files.deleteIfExists(path)
            }
        Files.deleteIfExists(tempDir)
    }
}