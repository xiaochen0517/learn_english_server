package `fun`.mochen.learn.english.service.dict.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import `fun`.mochen.learn.english.core.common.utils.BeanUtil
import `fun`.mochen.learn.english.core.common.utils.EntityUtil
import `fun`.mochen.learn.english.core.common.utils.StrUtil
import `fun`.mochen.learn.english.core.domain.dict.*
import `fun`.mochen.learn.english.service.finder.WordFinderFactory
import `fun`.mochen.learn.english.entity.dict.DictExampleSentence
import `fun`.mochen.learn.english.entity.dict.DictWordInfo
import `fun`.mochen.learn.english.mapper.dict.DictExampleSentenceMapper
import `fun`.mochen.learn.english.mapper.dict.DictWordInfoMapper
import `fun`.mochen.learn.english.service.dict.DictExampleSentenceService
import `fun`.mochen.learn.english.service.dict.DictInfoService
import `fun`.mochen.learn.english.service.openai.OpenAiService
import `fun`.mochen.learn.english.system.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.InvalidParameterException

@Service
@Transactional(rollbackFor = [Exception::class])
class DictInfoServiceImpl : ServiceImpl<DictWordInfoMapper, DictWordInfo>(), DictInfoService {

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(DictInfoServiceImpl::class.java)
    }

    @Autowired
    private lateinit var wordFinderFactory: WordFinderFactory

    @Autowired
    private lateinit var dictWordInfoMapper: DictWordInfoMapper

    @Autowired
    private lateinit var dictExampleSentenceMapper: DictExampleSentenceMapper

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var dictExampleSentenceService: DictExampleSentenceService

    @Autowired
    private lateinit var openAiService: OpenAiService

    override fun queryWordInfo(wordQueryBody: WordQueryBody): WordInfo {
        val (word) = wordQueryBody
        StrUtil.checkWord(word)
        val dictWordInfo: DictWordInfo? = dictWordInfoMapper.selectByWord(word)
        // 检查数据库中是否存在该单词
        if (dictWordInfo?.word != null) {
            return EntityUtil.dictWordInfo2FindWordInfo(dictWordInfo)
        }
        // 从网络查询单词
        val findWordInfo = wordFinderFactory.getWordFinder(WordFinderType.YOUDAO).queryWord(word)
        // 保存到数据库
        val findDictWordInfo = EntityUtil.findWordInfo2DictWordInfo(findWordInfo)
        dictWordInfoMapper.insert(findDictWordInfo)
        // 将保存之后的id设置到查询结果中
        findWordInfo.id = findDictWordInfo.id
        // 返回查询结果
        return findWordInfo;
    }

    override fun queryWordExampleSentence(wordQueryBody: WordQueryBody): List<WordExampleSentence> {
        val (word) = wordQueryBody
        StrUtil.checkWord(word)
        // 从数据库查询单词
        val dictWordInfo: DictWordInfo? = dictWordInfoMapper.selectByWord(word)
        // 检查数据库中是否存在该单词
        if (dictWordInfo?.word == null) {
            throw InvalidParameterException("This word {${word}} is not exist in database.")
        }
        // 检查例句数据库中是否存在该单词
        var examSentListInDB: List<DictExampleSentence> = dictExampleSentenceMapper.selectByWordId(dictWordInfo.id!!)
        // 检查数据库中是否存在例句
        if (examSentListInDB.isNotEmpty()) {
            // 返回例句
            return BeanUtil.copyList(examSentListInDB, WordExampleSentence::class.java)
        }
        // 从OpenAi生成例句
        val examSentListInOpenAi = openAiService.queryWordExampleSentence(word)
        // 将OpenAi的例句转换为数据库实体
        examSentListInDB = BeanUtil.copyList(examSentListInOpenAi, DictExampleSentence::class.java)
        examSentListInDB.forEach { examSent ->
            examSent.wordId = dictWordInfo.id
        }
        // 将OpenAi的例句保存到数据库
        dictExampleSentenceService.saveBatch(examSentListInDB)
        // 将保存之后的id设置到查询结果中
        examSentListInOpenAi.forEachIndexed { index, wordExampleSentence ->
            wordExampleSentence.id = examSentListInDB[index].id
        }
        return examSentListInOpenAi
    }

}
