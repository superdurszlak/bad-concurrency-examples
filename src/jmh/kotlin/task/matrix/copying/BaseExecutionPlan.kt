package task.matrix.copying

import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import task.executor.TaskExecutorFactory
import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.initializeRandomly
import kotlin.random.Random

@State(Scope.Benchmark)
open class BaseExecutionPlan(
    open var size: Int = 5000,
    open var columnBlockSize: Int = 5000,
    open var rowBlockSize: Int = 5000,
    open var threadCount: Int = 5000,
    open var copyingAlgorithm: CopyingAlgorithm = CopyingAlgorithm.SEQUENTIAL_COPY,
    open var sourceMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
    open var benchmarkContext: String = ""
) {
    lateinit var sourceMatrix: Matrix

    protected fun setUpIteration() {
        sourceMatrix = sourceMatrixFactory.create(size, size)
        initializeRandomly(sourceMatrix, Random.Default)
    }
}