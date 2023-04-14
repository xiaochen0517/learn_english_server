package `fun`.mochen.learn.english.service.dict.impl

import com.alibaba.fastjson.JSON
import `fun`.mochen.learn.english.core.common.utils.EntityUtil
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

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(DictInfoServiceImpl::class.java)
    }

    @Autowired
    private lateinit var wordFinderFactory: WordFinderFactory

    @Autowired
    private lateinit var dictWordInfoMapper: DictWordInfoMapper

    @Autowired
    private lateinit var tokenService: TokenService

    override fun queryWordInfo(word: String, request: HttpServletRequest): WordInfo {
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

}
