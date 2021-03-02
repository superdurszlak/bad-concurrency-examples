package task.matrix.multiplication

import task.matrix.Matrix
import task.matrix.MatrixFactory
import task.matrix.initializeRandomly
import kotlin.random.Random


open class ExecutionPlan(
    open var size: Int,
    open var leftMatrixFactory: MatrixFactory,
    open var rightMatrixFactory: MatrixFactory,
    open var resultMatrixFactory: MatrixFactory
) {
    lateinit var leftMatrix: Matrix
    lateinit var rightMatrix: Matrix
    lateinit var resultMatrix: Matrix

    protected fun setUpIteration() {
        leftMatrix = leftMatrixFactory.create(size, size)
        rightMatrix = rightMatrixFactory.create(size, size)
        initializeRandomly(leftMatrix, Random)
        initializeRandomly(rightMatrix, Random)
    }
}

