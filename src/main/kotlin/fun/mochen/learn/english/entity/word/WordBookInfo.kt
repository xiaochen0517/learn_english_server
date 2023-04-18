package `fun`.mochen.learn.english.entity.word

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import `fun`.mochen.learn.english.entity.BaseEntity

class WordBookInfo : BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var word: String? = null

    var status: Int = 0

}
