package `fun`.mochen.learn.english.service.word.impl

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import `fun`.mochen.learn.english.core.domain.word.WordBookRequest
import `fun`.mochen.learn.english.core.domain.word.WordBookResponse
import `fun`.mochen.learn.english.entity.word.WordBook
import `fun`.mochen.learn.english.mapper.word.WordBookMapper
import `fun`.mochen.learn.english.service.user.UserService
import `fun`.mochen.learn.english.service.word.WordBookService
import `fun`.mochen.learn.english.system.exception.service.LoginUserNotNullException
import `fun`.mochen.learn.english.system.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

@Service
@Transactional(rollbackFor = [Exception::class])
class WordBookServiceImpl : ServiceImpl<WordBookMapper, WordBook>(), WordBookService {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userService: UserService

    override fun createWordBook(wordBookRequest: WordBookRequest, request: HttpServletRequest): WordBookResponse {
        // 获取用户信息
        val user = tokenService.getLoginUser(request) ?: throw LoginUserNotNullException()
        // 创建单词本
        val wordBook = WordBook()
        wordBook.userId = user.userId
        wordBook.name = wordBookRequest.name
        this.baseMapper.insert(wordBook)
        // 将持久层对象转换为响应对象
        val wordBookResponse = WordBookResponse()
        BeanUtil.copyProperties(wordBook, wordBookResponse)
        return wordBookResponse
    }

    override fun listWordBookByUser(
        wordBookRequest: WordBookRequest,
        request: HttpServletRequest
    ): IPage<WordBookResponse> {
        // 获取用户信息
        val user = tokenService.getLoginUser(request) ?: throw LoginUserNotNullException()
        // 查询条件
        val wrapper = KtQueryWrapper(WordBook::class.java)
            .eq(WordBook::userId, user.userId)
            .like(StrUtil.isNotEmpty(wordBookRequest.name), WordBook::name, wordBookRequest.name)
        // 获取分页列表信息
        val page = Page<WordBook>(wordBookRequest.page, wordBookRequest.count)
        val wordBookPage = baseMapper.selectPage(page, wrapper)
        // 将持久层对象page转为响应层page
        val wordBookResponseIPage = wordBookPage.convert {
            val wordBookResponse = WordBookResponse()
            BeanUtil.copyProperties(it, wordBookResponse)
            wordBookResponse
        }
        return wordBookResponseIPage
    }
}
