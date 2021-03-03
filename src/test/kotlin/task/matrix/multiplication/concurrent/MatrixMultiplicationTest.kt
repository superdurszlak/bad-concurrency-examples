package task.matrix.multiplication.concurrent

import org.junitpioneer.jupiter.CartesianEnumSource
import org.junitpioneer.jupiter.CartesianEnumSource.CartesianEnumSources
import org.junitpioneer.jupiter.CartesianEnumSource.Mode
import org.junitpioneer.jupiter.CartesianProductTest
import org.junitpioneer.jupiter.CartesianValueSource
import org.junitpioneer.jupiter.CartesianValueSource.CartesianValueSources
import task.matrix.MatrixFactory
import task.matrix.assertMatricesEquivalent
import task.matrix.initializeRandomly
import task.executor.TaskExecutorFactory
import task.matrix.multiplication.sequential.naiveMultiplication
import kotlin.random.Random

class MatrixMultiplicationTest {

    @CartesianProductTest
    @CartesianValueSources(value = [
        CartesianValueSource(doubles = [1e-5, 1e-10]),
        CartesianValueSource(ints = [1, 4]),
        CartesianValueSource(ints = [1, 4]),
        CartesianValueSource(ints = [1, 10])
    ])
    @CartesianEnumSources(value = [
        CartesianEnumSource(MatrixFactory::class, names = ["UNSAFE_COLUMN_MAJOR", "UNSAFE_ROW_MAJOR"], mode = Mode.INCLUDE),
        CartesianEnumSource(TaskExecutorFactory::class),
    ])
    fun `safeMultiplication should yield same results as sequential multiplication`(
        delta: Double,
        columnChunkSize: Int,
        rowChunkSize: Int,
        threadCount: Int,
        resultMatrixFactory: MatrixFactory,
        taskExecutorFactory: TaskExecutorFactory
    ) {
        val leftMatrix = MatrixFactory.UNSAFE_ROW_MAJOR.create(10, 10000)
        val rightMatrix = MatrixFactory.UNSAFE_ROW_MAJOR.create(10000, 10)

        initializeRandomly(leftMatrix, Random.Default)
        initializeRandomly(rightMatrix, Random.Default)

        val taskExecutor = taskExecutorFactory.create(threadCount)

        val expectedResult = naiveMultiplication(leftMatrix, rightMatrix, resultMatrixFactory)

        val actualResult = safeConcurrentMultiplication(leftMatrix, rightMatrix, resultMatrixFactory, taskExecutor, rowChunkSize, columnChunkSize)

        assertMatricesEquivalent(expectedResult, actualResult, delta)
    }

    @CartesianProductTest
    @CartesianValueSources(value = [
        CartesianValueSource(doubles = [1e-5, 1e-10]),
        CartesianValueSource(ints = [1, 4]),
        CartesianValueSource(ints = [1, 4]),
        CartesianValueSource(ints = [1, 4]),
        CartesianValueSource(ints = [1, 10])
    ])
    @CartesianEnumSources(value = [
        CartesianEnumSource(MatrixFactory::class, names = ["UNSAFE_COLUMN_MAJOR", "UNSAFE_ROW_MAJOR"], mode = Mode.EXCLUDE),
        CartesianEnumSource(TaskExecutorFactory::class),
    ])
    fun `unsafeMultiplication should yield same results as sequential multiplication for thread safe target matrix`(
        delta: Double,
        columnChunkSize: Int,
        rowChunkSize: Int,
        dotProductChunkSize: Int,
        threadCount: Int,
        resultMatrixFactory: MatrixFactory,
        taskExecutorFactory: TaskExecutorFactory
    ) {
        val leftMatrix = MatrixFactory.UNSAFE_ROW_MAJOR.create(10, 10000)
        val rightMatrix = MatrixFactory.UNSAFE_ROW_MAJOR.create(10000, 10)

        initializeRandomly(leftMatrix, Random.Default)
        initializeRandomly(rightMatrix, Random.Default)

        val taskExecutor = taskExecutorFactory.create(threadCount)

        val expectedResult = naiveMultiplication(leftMatrix, rightMatrix, resultMatrixFactory)

        val actualResult = safeConcurrentMultiplication(leftMatrix, rightMatrix, resultMatrixFactory, taskExecutor, rowChunkSize, columnChunkSize)

        assertMatricesEquivalent(expectedResult, actualResult, delta)
    }
}