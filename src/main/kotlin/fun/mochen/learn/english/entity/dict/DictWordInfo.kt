package `fun`.mochen.learn.english.entity.dict

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import `fun`.mochen.learn.english.entity.BaseEntity

class DictWordInfo : BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var word: String? = null

    var defPhonetic: String? = null

    var usPhonetic: String? = null

    var ukPhonetic: String? = null

    var queryVoiceUrl: String? = null

    var transVoiceUrl: String? = null

    var usVoiceUrl: String? = null

    var ukVoiceUrl: String? = null

    var explains: String? = null

    var derivedForms: String? = null

}
