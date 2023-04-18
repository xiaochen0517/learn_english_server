package `fun`.mochen.learn.english.service.word

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import `fun`.mochen.learn.english.core.domain.word.WordBookRequest
import `fun`.mochen.learn.english.core.domain.word.WordBookResponse
import `fun`.mochen.learn.english.entity.word.WordBook
import javax.servlet.http.HttpServletRequest

interface WordBookService: IService<WordBook> {
    fun createWordBook(wordBookRequest: WordBookRequest, request: HttpServletRequest): WordBookResponse
    fun listWordBookByUser(wordBookRequest: WordBookRequest, request: HttpServletRequest): IPage<WordBookResponse>
}
