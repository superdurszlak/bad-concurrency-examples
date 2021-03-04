package task.matrix.multiplication

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import task.matrix.MatrixFactory
import task.executor.TaskExecutorFactory

open class ReferenceScenario: BaseMatrixMultiplicationScenario() {
    @State(Scope.Benchmark)
    open class ExecutionPlan(
        @Param("200", "500", "2000")
        override var size: Int = 200,
        @Param("1")
        override var threadCount: Int = 1,
        @Param("UNSAFE_ROW_MAJOR")
        override var leftMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var rightMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("UNSAFE_ROW_MAJOR")
        override var resultMatrixFactory: MatrixFactory = MatrixFactory.UNSAFE_ROW_MAJOR,
        @Param("THREAD_POOL_EXECUTOR")
        override var taskExecutorFactory: TaskExecutorFactory = TaskExecutorFactory.THREAD_POOL_EXECUTOR,
        @Param("SEQUENTIAL_NAIVE")
        override var multiplicationAlgorithm: MultiplicationAlgorithm = MultiplicationAlgorithm.SEQUENTIAL_NAIVE,
        @Param("matrix multiplication baseline scenario")
        override var benchmarkContext: String = ""
    ) : BaseExecutionPlan(
        columnBlockSize = size,
        dotProductBlockSize = size,
        rowBlockSize = size
    ) {
        @Setup(Level.Iteration)
        fun setUp() {
            setUpIteration()
        }
    }

    @Benchmark
    fun execute(executionPlan: ExecutionPlan, blackhole: Blackhole) {
        executeBenchmark(executionPlan, blackhole)
    }
}