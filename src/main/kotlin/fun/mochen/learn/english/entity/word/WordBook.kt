package `fun`.mochen.learn.english.entity.word

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import `fun`.mochen.learn.english.entity.BaseEntity


class WordBook : BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var userId: Long? = null

    var name: String? = null

    var status: Int = 0

}
