package task.matrix

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll


fun assertMatricesEquivalent(expected: Matrix, actual: Matrix, delta: Double) {
    val rows = expected.rows
    val columns = expected.columns
    Assertions.assertEquals(rows, actual.rows, "Matrix row count mismatch: expected $rows, was ${actual.rows}}")
    Assertions.assertEquals(
        columns,
        actual.columns,
        "Matrix column count mismatch: expected $columns, was ${actual.columns}}"
    )

    val assertions = (0 until rows).map { row ->
        (0 until columns).map { column ->
            {
                Assertions.assertEquals(
                    expected[row, column],
                    actual[row, column],
                    delta,
                    "Value mismatch at indices [$row, $column] exceeds delta $delta"
                )
            }
        }
    }
        .flatten()
        .toTypedArray()

    assertAll(*assertions)
}