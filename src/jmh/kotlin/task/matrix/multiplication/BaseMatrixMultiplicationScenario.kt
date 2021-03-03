package task.matrix.multiplication

import org.openjdk.jmh.infra.Blackhole
import task.matrix.multiplication.concurrent.safeConcurrentMultiplication
import task.matrix.multiplication.concurrent.unsafeConcurrentMultiplication
import task.matrix.multiplication.sequential.cacheOptimizedMultiplication
import task.matrix.multiplication.sequential.naiveMultiplication

open class BaseMatrixMultiplicationScenario {

    protected fun executeBenchmark(baseExecutionPlan: BaseExecutionPlan, blackhole: Blackhole) {
        val algorithm = baseExecutionPlan.multiplicationAlgorithm

        val result = algorithm(baseExecutionPlan)

        blackhole.consume(result)
    }
}