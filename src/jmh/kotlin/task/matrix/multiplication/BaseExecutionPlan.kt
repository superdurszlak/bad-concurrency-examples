package task.matrix.multiplication

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.initializeRandomly
import task.executor.TaskExecutorFactory
import kotlin.random.Random


open class BaseExecutionPlan(
    open var size: Int = 200,
    open var columnBlockSize: Int = 200,
    open var rowBlockSize: Int = 200,
    open var dotProductBlockSize: Int = 200,
    open var threadCount: Int = 1,
    open var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    open var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
    open var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE,
    open var benchmarkContext: String = ""
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

