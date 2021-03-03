package task.executor

enum class TaskExecutorFactory(
    val create: (threads: Int) -> TaskExecutor
) {
    BARE_THREAD_EXECUTOR({ threads -> BareThreadTaskExecutor(threads) }),
    THREAD_POOL_EXECUTOR({ threads -> ThreadPoolTaskExecutor(threads) })
}