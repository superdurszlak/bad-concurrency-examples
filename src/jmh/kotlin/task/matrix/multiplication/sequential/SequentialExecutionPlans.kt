package task.matrix.multiplication.sequential

import org.openjdk.jmh.annotations.*
import task.matrix.multiplication.ExecutionPlan
import task.matrix.MatrixFactory

@State(Scope.Benchmark)
open class NaiveExecutionPlan(
    @Param("100", "1500")
    override var size: Int = 1,
    @Param("UNSAFE_ROW_MAJOR")
    override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
    @Param("UNSAFE_ROW_MAJOR")
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
open class CacheOptimizedExecutionPlan(
    @Param("8", "16", "24")
    var blockSize: Int = 1,
    @Param("1500")
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