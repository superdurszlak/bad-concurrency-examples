package task.matrix.copying

import org.openjdk.jmh.infra.Blackhole

open class BaseMatrixCopyingScenario {
    protected fun executeBenchmark(baseExecutionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val algorithm = baseExecutionPlan.copyingAlgorithm

        val result = algorithm(baseExecutionPlan)

        blackhole.consume(result)
    }
}
