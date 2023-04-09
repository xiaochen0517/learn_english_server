package `fun`.mochen.learn.english

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("fun.mochen.learn.english.mapper")
class LearnEnglishServerApplication

fun main(args: Array<String>) {
    runApplication<LearnEnglishServerApplication>(*args)
}
