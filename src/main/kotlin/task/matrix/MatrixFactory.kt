package task.matrix

import task.matrix.Matrix
import task.matrix.columnmajor.AtomicColumnMajorMatrix
import task.matrix.columnmajor.SynchronizedColumnMajorMatrix
import task.matrix.columnmajor.UnsafeColumnMajorMatrix
import task.matrix.rowmajor.AtomicRowMajorMatrix
import task.matrix.rowmajor.SynchronizedRowMajorMatrix
import task.matrix.rowmajor.UnsafeRowMajorMatrix

enum class MatrixFactory(val create: (Int, Int) -> Matrix) {
    ATOMIC_COLUMN_MAJOR({ rows: Int, columns: Int -> AtomicColumnMajorMatrix(rows, columns) }),
    ATOMIC_ROW_MAJOR({ rows: Int, columns: Int -> AtomicRowMajorMatrix(rows, columns) }),
    SYNCHRONIZED_COLUMN_MAJOR({ rows: Int, columns: Int -> SynchronizedColumnMajorMatrix(rows, columns) }),
    SYNCHRONIZED_ROW_MAJOR({ rows: Int, columns: Int -> SynchronizedRowMajorMatrix(rows, columns) }),
    UNSAFE_COLUMN_MAJOR({ rows: Int, columns: Int -> UnsafeColumnMajorMatrix(rows, columns) }),
    UNSAFE_ROW_MAJOR({ rows: Int, columns: Int -> UnsafeRowMajorMatrix(rows, columns) }),
}