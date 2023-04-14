package `fun`.mochen.learn.english.service.dict.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import `fun`.mochen.learn.english.entity.dict.DictExampleSentence
import `fun`.mochen.learn.english.mapper.dict.DictExampleSentenceMapper
import `fun`.mochen.learn.english.service.dict.DictExampleSentenceService
import org.springframework.stereotype.Service

@Service
class DictExampleSentenceServiceImpl : ServiceImpl<DictExampleSentenceMapper, DictExampleSentence>(),
    DictExampleSentenceService {
}
