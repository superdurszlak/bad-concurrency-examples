package task.executor

interface TaskExecutor {
    fun execute(tasks: Collection<() -> Unit>)
}