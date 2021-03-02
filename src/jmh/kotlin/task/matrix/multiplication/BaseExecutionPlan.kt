package task.matrix.multiplication

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.initializeRandomly
import task.matrix.multiplication.concurrent.executor.TaskExecutorFactory
import kotlin.random.Random


open class BaseExecutionPlan(
    open var size: Int = 100,
    open var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
    open var columnBlockSize: Int = 1000,
    open var rowBlockSize: Int = 1000,
    open var dotProductBlockSize: Int = 1000,
    open var threadCount: Int = 1000,
) {
    lateinit var leftMatrix: Matrix
    lateinit var rightMatrix: Matrix

    protected fun setUpIteration() {
        leftMatrix = leftMatrixFactory.create(size, size)
        rightMatrix = rightMatrixFactory.create(size, size)
        initializeRandomly(leftMatrix, Random)
        initializeRandomly(rightMatrix, Random)
    }
}

