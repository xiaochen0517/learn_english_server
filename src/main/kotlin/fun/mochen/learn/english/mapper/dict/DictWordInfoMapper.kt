package `fun`.mochen.learn.english.mapper.dict

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.dict.DictWordInfo
import org.springframework.stereotype.Repository

@Repository
interface DictWordInfoMapper : BaseMapper<DictWordInfo> {
    fun selectByWord(word: String): DictWordInfo?

}
