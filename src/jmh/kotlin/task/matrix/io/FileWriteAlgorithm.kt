package task.matrix.io

enum class FileWriteAlgorithm(
    private val algorithm: (executionPlan: BaseExecutionPlan) -> Unit
) {
    SERIAL_SEQUENTIAL_WRITE(::serialWriteSequentially),
    SAFE_SERIAL_CONCURRENT_WRITE(::safeSerialWriteConcurrently),
    SAFE_PARALLEL_CONCURRENT_WRITE(::safeParallelWriteConcurrently),
    UNSAFE_SERIAL_CONCURRENT_WRITE(::unsafeSerialWriteConcurrently);

    operator fun invoke(executionPlan: BaseExecutionPlan): Unit = algorithm(executionPlan)
}

private fun serialWriteSequentially(executionPlan: BaseExecutionPlan) {
    writeSequentially(executionPlan, ::iterateTasksSerial, ::writeStringTo)
}

private fun safeParallelWriteConcurrently(executionPlan: BaseExecutionPlan) {
    writeConcurrently(executionPlan, ::iterateTasksParallel) { file, charactersToWrite ->
        synchronized(file) {
            writeStringTo(file, charactersToWrite)
        }
    }
}

private fun safeSerialWriteConcurrently(executionPlan: BaseExecutionPlan) {
    writeConcurrently(executionPlan, ::iterateTasksSerial) { file, charactersToWrite ->
        synchronized(file) {
            writeStringTo(file, charactersToWrite)
        }
    }
}

private fun unsafeSerialWriteConcurrently(executionPlan: BaseExecutionPlan) {
    writeConcurrently(executionPlan, ::iterateTasksSerial, ::writeStringTo)
}

