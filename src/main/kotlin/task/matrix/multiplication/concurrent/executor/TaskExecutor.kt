package task.matrix.multiplication.concurrent.executor

interface TaskExecutor {
    fun execute(tasks: Collection<() -> Unit>)
}