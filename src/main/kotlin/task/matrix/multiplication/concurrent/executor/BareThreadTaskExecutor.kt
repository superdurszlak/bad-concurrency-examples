package task.matrix.multiplication.concurrent.executor

import task.matrix.multiplication.divideAndRoundUp
import kotlin.concurrent.thread

class BareThreadTaskExecutor(private val threadCount: Int) : TaskExecutor {
    override fun execute(tasks: Collection<() -> Unit>) {

        val chunkSize = divideAndRoundUp(tasks.size, threadCount)

        val taskBundles = tasks.chunked(chunkSize)

        val threads = mutableListOf<Thread>()

        taskBundles.forEach { bundle ->
            val thread = thread {
                bundle.forEach { task ->
                    task()
                }
            }

            threads.add(thread)
        }

        threads.forEach(Thread::join)
    }

}