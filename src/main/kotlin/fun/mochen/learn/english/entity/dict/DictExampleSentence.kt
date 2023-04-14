package `fun`.mochen.learn.english.entity.dict

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import `fun`.mochen.learn.english.entity.BaseEntity

class DictExampleSentence : BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var wordId: Long? = null

    var en: String? = null

    var zh: String? = null

}
