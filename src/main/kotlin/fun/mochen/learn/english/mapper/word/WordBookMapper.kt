package `fun`.mochen.learn.english.mapper.word

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import `fun`.mochen.learn.english.entity.word.WordBook
import org.springframework.stereotype.Repository

@Repository
interface WordBookMapper : BaseMapper<WordBook> {
}
