package `fun`.mochen.learn.english.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

open class BaseEntity{

    var createrName: String? = null

    var createrId: Long? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    var updaterName: String? = null

    var updaterId: Long? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updateTime: Date? = null

    var remark: String? = null
}
