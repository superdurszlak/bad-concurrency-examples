package task.matrix.multiplication.sequential

import org.junit.jupiter.api.BeforeAll
import org.junitpioneer.jupiter.CartesianEnumSource
import org.junitpioneer.jupiter.CartesianProductTest
import org.junitpioneer.jupiter.CartesianValueSource
import task.matrix.*
import task.matrix.rowmajor.UnsafeRowMajorMatrix
import kotlin.random.Random


internal class MatrixMultiplicationTest {

    companion object {
        private val leftMatrix = UnsafeRowMajorMatrix(2, 3)
        private val rightMatrix = UnsafeRowMajorMatrix(3, 2)
        private val expectedResultMatrix = UnsafeRowMajorMatrix(2, 2)

        @BeforeAll
        fun setUp() {
            leftMatrix[0, 0] = 1.0; leftMatrix[0, 1] = 0.0; leftMatrix[0, 2] = 1.0
            leftMatrix[1, 0] = 0.0; leftMatrix[1, 1] = 2.0; leftMatrix[1, 2] = 0.0

            rightMatrix[0, 0] = 2.0; rightMatrix[0, 1] = 0.0
            rightMatrix[1, 0] = 0.0; rightMatrix[1, 1] = 1.0
            rightMatrix[0, 0] = 3.0; rightMatrix[0, 1] = 0.0

            expectedResultMatrix[0, 0] = 5.0; expectedResultMatrix[0, 1] = 0.0
            expectedResultMatrix[1, 0] = 0.0; expectedResultMatrix[1, 1] = 2.0
        }
    }

    @CartesianProductTest
    @CartesianValueSource(doubles = [1e-30, 1e-60, 1e-90])
    @CartesianEnumSource(MatrixFactory::class)
    fun `naiveMultiplication should yield correct result regardless of target matrix kind`(
        delta: Double,
        resultMatrixFactory: MatrixFactory
    ) {
        val resultMatrix = naiveMultiplication(leftMatrix, rightMatrix, resultMatrixFactory)

        assertMatricesEquivalent(expectedResultMatrix, resultMatrix, delta)
    }

    @CartesianProductTest
    @CartesianValueSource(doubles = [1e-30, 1e-60, 1e-90])
    @CartesianEnumSource(MatrixFactory::class)
    fun `cacheOptimizedMultiplication should yield correct result regardless of target matrix kind`(
        delta: Double,
        resultMatrixFactory: MatrixFactory
    ) {
        val resultMatrix = cacheOptimizedMultiplication(leftMatrix, rightMatrix, resultMatrixFactory, 16)

        assertMatricesEquivalent(expectedResultMatrix, resultMatrix, delta)
    }

    @CartesianProductTest
    @CartesianValueSource(doubles = [1e-30, 1e-60, 1e-90])
    @CartesianEnumSource.CartesianEnumSources(
        value = [
            CartesianEnumSource(MatrixFactory::class),
            CartesianEnumSource(MatrixFactory::class)
        ]
    )
    fun `naiveMultiplication should yield correct result regardless of source matrices kinds`(
        delta: Double,
        leftMatrixFactory: MatrixFactory,
        rightMatrixFactory: MatrixFactory
    ) {
        val leftMatrixCopy = leftMatrix.copy(leftMatrixFactory)
        val rightMatrixCopy = rightMatrix.copy(rightMatrixFactory)

        val resultMatrix = naiveMultiplication(leftMatrixCopy, rightMatrixCopy, MatrixFactory.UNSAFE_ROW_MAJOR)

        assertMatricesEquivalent(expectedResultMatrix, resultMatrix, delta)
    }

    @CartesianProductTest
    @CartesianValueSource(doubles = [1e-30, 1e-60, 1e-90])
    @CartesianEnumSource.CartesianEnumSources(
        value = [
            CartesianEnumSource(MatrixFactory::class),
            CartesianEnumSource(MatrixFactory::class)
        ]
    )
    fun `cacheOptimizedMultiplication should yield correct result regardless of source matrices kind`(
        delta: Double,
        leftMatrixFactory: MatrixFactory,
        rightMatrixFactory: MatrixFactory
    ) {
        val leftMatrixCopy = leftMatrix.copy(leftMatrixFactory)
        val rightMatrixCopy = rightMatrix.copy(rightMatrixFactory)

        val resultMatrix = cacheOptimizedMultiplication(leftMatrixCopy, rightMatrixCopy, MatrixFactory.UNSAFE_ROW_MAJOR, 16)

        assertMatricesEquivalent(expectedResultMatrix, resultMatrix, delta)
    }

    @CartesianProductTest
    @CartesianValueSource(doubles = [1e-5, 1e-10])
    @CartesianEnumSource(MatrixFactory::class)
    fun `cacheOptimizedMultiplication should yield same result as naiveMultiplication`(
        delta: Double,
        resultMatrixFactory: MatrixFactory
    ) {
        val bigLeftMatrix = UnsafeRowMajorMatrix(10, 10000)
        val bigRightMatrix = UnsafeRowMajorMatrix(10000, 10)

        initializeRandomly(bigLeftMatrix, Random)
        initializeRandomly(bigRightMatrix, Random)

        val naiveResultMatrix = naiveMultiplication(bigLeftMatrix, bigRightMatrix, resultMatrixFactory)
        val optimizedResultMatrix = cacheOptimizedMultiplication(bigLeftMatrix, bigRightMatrix, resultMatrixFactory, 16)

        assertMatricesEquivalent(naiveResultMatrix, optimizedResultMatrix, delta)
    }
}