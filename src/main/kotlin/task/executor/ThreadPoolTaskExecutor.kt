package task.executor

import java.util.concurrent.*

class ThreadPoolTaskExecutor(private val threadCount: Int) : TaskExecutor {

    override fun execute(tasks: Collection<() -> Unit>) {
        val executorService = Executors.newFixedThreadPool(threadCount)

        val completableFutures = mutableListOf<CompletableFuture<Void>>()

        tasks.forEach { task ->
            val future = CompletableFuture.runAsync(task, executorService)
            completableFutures.add(future)
        }

        executorService.shutdown()

        CompletableFuture
            .allOf(*completableFutures.toTypedArray())
            .join()
    }
}