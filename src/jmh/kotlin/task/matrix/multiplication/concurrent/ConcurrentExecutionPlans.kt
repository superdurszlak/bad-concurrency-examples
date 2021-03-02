package task.matrix.multiplication.concurrent

import org.openjdk.jmh.annotations.*
import task.matrix.multiplication.ExecutionPlan
import task.matrix.MatrixFactory
import task.matrix.multiplication.concurrent.executor.BareThreadTaskExecutor
import task.matrix.multiplication.concurrent.executor.TaskExecutorFactory

@State(Scope.Benchmark)
open class SafeConcurrentExecutionPlan(
    @Param
    var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.BARE_THREAD_EXECUTOR,
    @Param("1", "8")
    var columnChunkSize :Int,
    @Param("1", "8")
    var rowChunkSize :Int,
    @Param("2", "4", "16", "64", "512")
    var threadCount :Int,
    @Param("500")
    override var size: Int = 1,
    @Param("UNSAFE_ROW_MAJOR")
    override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    @Param("UNSAFE_COLUMN_MAJOR")
    override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    @Param("UNSAFE_ROW_MAJOR")
    override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,

) : ExecutionPlan(size, leftMatrixFactory, rightMatrixFactory, resultMatrixFactory) {

    @Setup(Level.Iteration)
    fun setUp() {
        setUpIteration()
    }
}

@State(Scope.Benchmark)
open class UnsafeConcurrentExecutionPlan(
    @Param
    var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.BARE_THREAD_EXECUTOR,
    @Param("1", "8")
    var columnChunkSize :Int,
    @Param("1", "8")
    var rowChunkSize :Int,
    @Param("1", "8")
    var dotProductChunkSize :Int,
    @Param("2", "4", "16", "64", "512")
    var threadCount :Int,
    @Param("500")
    override var size: Int = 1,
    @Param("UNSAFE_ROW_MAJOR")
    override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    @Param("UNSAFE_COLUMN_MAJOR")
    override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    @Param("SYNCHRONIZED_ROW_MAJOR", "ATOMIC_ROW_MAJOR")
    override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
) : ExecutionPlan(size, leftMatrixFactory, rightMatrixFactory, resultMatrixFactory) {

    @Setup(Level.Iteration)
    fun setUp() {
        setUpIteration()
    }
}