#!/usr/bin/env groovy
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


ExecutorService pool=Executors.newFixedThreadPool(3)
map=["ubuntu","sbms-server","sbms-web"]
x=0
map.each { var->
    x++
    Thread.sleep(444);
    pool.execute{
        println "start thread $x: $var"
        Thread.sleep(3000+x*100);
        println "end thread  $x: $var"
    }
}

println "done cycle"
pool.shutdown()                 //all tasks submitted
while (!pool.isTerminated()){}  //waitfor termination
println 'Finished all threads'
