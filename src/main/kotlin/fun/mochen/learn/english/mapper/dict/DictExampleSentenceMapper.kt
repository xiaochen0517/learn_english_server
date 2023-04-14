package `fun`.mochen.learn.english.mapper.dict

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.dict.DictExampleSentence
import org.springframework.stereotype.Repository

@Repository
interface DictExampleSentenceMapper : BaseMapper<DictExampleSentence> {
    fun selectByWordId(id: Long): List<DictExampleSentence>
}
