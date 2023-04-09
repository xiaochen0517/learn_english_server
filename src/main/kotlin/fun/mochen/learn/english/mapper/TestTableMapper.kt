package `fun`.mochen.learn.english.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.TestTable
import org.springframework.stereotype.Repository

@Repository
interface TestTableMapper: BaseMapper<TestTable> {
}
