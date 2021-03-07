package task.matrix.io

import java.io.File
import java.nio.file.Files

internal val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

internal fun writeSequentially(
    executionPlan: BaseExecutionPlan,
    iterateTasks: (
        List<File>,
        List<Int>,
        (File, Int) -> Unit
    ) -> List<() -> Unit>,
    writeTask: (File, Int) -> Unit
) {
    val fileChunksSizes = (0 until executionPlan.charactersPerFile)
        .chunked(executionPlan.fileBlockSize)
        .map(Collection<*>::size)

    val fileList = mutableListOf<File>()

    repeat(executionPlan.filesToWrite) { counter ->
        val file = Files.createTempFile(executionPlan.tempDir, "$counter", null)
            .toFile()

        fileList.add(file)
    }

    iterateTasks(fileList, fileChunksSizes, writeTask).forEach {
        task -> task()
    }
}

internal fun writeConcurrently(
    executionPlan: BaseExecutionPlan,
    iterateTasks: (
        List<File>,
        List<Int>,
        (File, Int) -> Unit
    ) -> List<() -> Unit>,
    writeTask: (File, Int) -> Unit
) {
    val taskExecutor = executionPlan.taskExecutorFactory.create(executionPlan.threadCount)

    val fileChunksSizes = (0 until executionPlan.charactersPerFile)
        .chunked(executionPlan.fileBlockSize)
        .map(Collection<*>::size)

    val fileList = mutableListOf<File>()

    repeat(executionPlan.filesToWrite) { counter ->
        val file = Files.createTempFile(executionPlan.tempDir, "$counter", null)
            .toFile()

        fileList.add(file)
    }

    val tasks = iterateTasks(fileList, fileChunksSizes, writeTask)

    taskExecutor.execute(tasks)
}

internal fun writeRandomString(file: File, charactersToWrite: Int) {
    val printWriter = file.printWriter()

    val text = (0 until charactersToWrite)
        .map { alphabet.random() }
        .joinToString()

    printWriter.print(text)

    printWriter.close()
}

internal fun iterateTasksSerial(
    files: List<File>,
    chunkSizes: Iterable<Int>,
    task: (File, Int) -> Unit
): List<() -> Unit> =
    files.map { file ->
        chunkSizes.map { chunkSize ->
            {
                task(file, chunkSize)
            }
        }
    }.flatten()

internal fun iterateTasksParallel(
    files: List<File>,
    chunkSizes: Iterable<Int>,
    task: (File, Int) -> Unit
): List<() -> Unit> =
    chunkSizes.map { chunkSize ->
        files.map { file ->
            {
                task(file, chunkSize)
            }
        }
    }.flatten()