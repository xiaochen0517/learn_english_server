package `fun`.mochen.learn.english.service.dict.impl

import com.alibaba.fastjson.JSON
import `fun`.mochen.learn.english.core.domain.dict.*
import `fun`.mochen.learn.english.core.domain.model.LoginUser
import `fun`.mochen.learn.english.dict.finder.WordFinderFactory
import `fun`.mochen.learn.english.entity.dict.DictWordInfo
import `fun`.mochen.learn.english.mapper.dict.DictWordInfoMapper
import `fun`.mochen.learn.english.service.dict.DictInfoService
import `fun`.mochen.learn.english.system.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
@Transactional(rollbackFor = [Exception::class])
class DictInfoServiceImpl : DictInfoService {

    @Autowired
    private lateinit var wordFinderFactory: WordFinderFactory

    @Autowired
    private lateinit var dictWordInfoMapper: DictWordInfoMapper

    @Autowired
    private lateinit var tokenService: TokenService

    override fun queryWordInfo(word: String, request: HttpServletRequest): WordInfo {
        val loginUser = tokenService.getLoginUser(request);
        var dictWordInfo: DictWordInfo? = dictWordInfoMapper.selectByWord(word)
        // 检查数据库中是否存在该单词
        if (dictWordInfo?.word != null) {
            return dictWordInfo2FindWordInfo(dictWordInfo)
        }
        // 从网络查询单词
        val findWordInfo = wordFinderFactory.getWordFinder(WordFinderType.YOUDAO).queryWord(word)
        dictWordInfo = findWordInfo2DictWordInfo(findWordInfo, loginUser!!)
        // 保存到数据库
        dictWordInfoMapper.insert(dictWordInfo)
        return findWordInfo;
    }

    private fun dictWordInfo2FindWordInfo(dictWordInfo: DictWordInfo): WordInfo {
        val wordPhonetic = WordPhonetic(dictWordInfo.defPhonetic, dictWordInfo.usPhonetic, dictWordInfo.ukPhonetic)
        val wordVoice = WordVoice(
            dictWordInfo.queryVoiceUrl, dictWordInfo.transVoiceUrl, dictWordInfo.usVoiceUrl, dictWordInfo.ukVoiceUrl
        )
        val explainsList = JSON.parseArray(dictWordInfo.explains, String::class.java) ?: listOf()
        val derivedFormsList = JSON.parseArray(dictWordInfo.derivedForms, WordDerivedForms::class.java)
        return WordInfo(
            id = dictWordInfo.id!!,
            word = dictWordInfo.word!!,
            phonetic = wordPhonetic,
            voice = wordVoice,
            explains = explainsList,
            derivedForms = derivedFormsList
        )
    }

    private fun findWordInfo2DictWordInfo(findWordInfo: WordInfo, loginUser: LoginUser): DictWordInfo {
        val dictWordInfo = DictWordInfo()
        dictWordInfo.id = null
        dictWordInfo.word = findWordInfo.word
        dictWordInfo.defPhonetic = findWordInfo.phonetic.defPhonetic
        dictWordInfo.usPhonetic = findWordInfo.phonetic.usPhonetic
        dictWordInfo.ukPhonetic = findWordInfo.phonetic.ukPhonetic
        dictWordInfo.queryVoiceUrl = findWordInfo.voice.queryVoiceUrl
        dictWordInfo.transVoiceUrl = findWordInfo.voice.transVoiceUrl
        dictWordInfo.usVoiceUrl = findWordInfo.voice.usVoiceUrl
        dictWordInfo.ukVoiceUrl = findWordInfo.voice.ukVoiceUrl
        dictWordInfo.explains = JSON.toJSONString(findWordInfo.explains)
        dictWordInfo.derivedForms = JSON.toJSONString(findWordInfo.derivedForms)
        dictWordInfo.createTime = Date()
        dictWordInfo.createrId = loginUser.userId
        dictWordInfo.createrName = loginUser.username
        return dictWordInfo
    }

}
