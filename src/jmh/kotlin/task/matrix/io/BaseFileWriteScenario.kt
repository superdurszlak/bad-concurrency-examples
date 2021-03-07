package task.matrix.io

open class BaseFileWriteScenario {
    protected fun executeBenchmark(baseExecutionPlan: BaseExecutionPlan) {
        val algorithm = baseExecutionPlan.fileWriteAlgorithm

        algorithm(baseExecutionPlan)
    }
}
