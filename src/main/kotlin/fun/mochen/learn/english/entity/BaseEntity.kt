package `fun`.mochen.learn.english.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

open class BaseEntity{

    var creatorName: String? = null

    var creatorId: Long? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    var updaterName: String? = null

    var updaterId: Long? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updateTime: Date? = null

    var remark: String? = null

}
