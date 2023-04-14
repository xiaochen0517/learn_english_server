package `fun`.mochen.learn.english.service.user.impl

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import `fun`.mochen.learn.english.entity.TestTable
import `fun`.mochen.learn.english.mapper.TestTableMapper
import `fun`.mochen.learn.english.service.user.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestServiceImpl : TestService {

    @Autowired
    private lateinit var testTableMapper: TestTableMapper

    override fun getTest(): List<TestTable> {
        val lambdaQueryWrapper = LambdaQueryWrapper<TestTable>()
        return testTableMapper.selectList(lambdaQueryWrapper)
    }

}
