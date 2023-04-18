package `fun`.mochen.learn.english.service.word.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import `fun`.mochen.learn.english.entity.word.WordBookInfo
import `fun`.mochen.learn.english.mapper.word.WordBookInfoMapper
import `fun`.mochen.learn.english.service.word.WordBookInfoService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WordBookInfoServiceImpl: ServiceImpl<WordBookInfoMapper, WordBookInfo>(), WordBookInfoService {
}
