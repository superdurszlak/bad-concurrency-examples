package task.matrix.io

import java.io.File
import java.nio.file.Files

internal fun writeSequentially(
    executionPlan: BaseExecutionPlan,
    iterateTasks: (
        List<File>,
        List<String>,
        (File, String) -> Unit
    ) -> List<() -> Unit>,
    writeTask: (File, String) -> Unit
) {
    val fileChunks = executionPlan.fileContent
        .chunked(executionPlan.fileBlockSize)

    val fileList = mutableListOf<File>()

    repeat(executionPlan.filesToWrite) { counter ->
        val file = Files.createTempFile(executionPlan.tempDir, "$counter", null)
            .toFile()

        fileList.add(file)
    }

    iterateTasks(fileList, fileChunks, writeTask).forEach {
        task -> task()
    }
}

internal fun writeConcurrently(
    executionPlan: BaseExecutionPlan,
    iterateTasks: (
        List<File>,
        List<String>,
        (File, String) -> Unit
    ) -> List<() -> Unit>,
    writeTask: (File, String) -> Unit
) {
    val taskExecutor = executionPlan.taskExecutorFactory.create(executionPlan.threadCount)

    val fileChunks = executionPlan.fileContent
        .chunked(executionPlan.fileBlockSize)

    val fileList = mutableListOf<File>()

    repeat(executionPlan.filesToWrite) { counter ->
        val file = Files.createTempFile(executionPlan.tempDir, "$counter", null)
            .toFile()

        fileList.add(file)
    }

    val tasks = iterateTasks(fileList, fileChunks, writeTask)

    taskExecutor.execute(tasks)
}

internal fun writeStringTo(file: File, string: String) {
    val printWriter = file.printWriter()

    printWriter.print(string)

    printWriter.close()
}

internal fun iterateTasksSerial(
    files: List<File>,
    chunks: Iterable<String>,
    task: (File, String) -> Unit
): List<() -> Unit> =
    files.map { file ->
        chunks.map { chunk ->
            {
                task(file, chunk)
            }
        }
    }.flatten()

internal fun iterateTasksParallel(
    files: List<File>,
    chunks: Iterable<String>,
    task: (File, String) -> Unit
): List<() -> Unit> =
    chunks.map { chunk ->
        files.map { file ->
            {
                task(file, chunk)
            }
        }
    }.flatten()