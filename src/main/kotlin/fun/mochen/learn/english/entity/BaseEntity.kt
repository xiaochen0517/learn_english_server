package `fun`.mochen.learn.english.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

open class BaseEntity{

    @TableField(fill = FieldFill.INSERT)
    var creatorName: String? = null

    @TableField(fill = FieldFill.INSERT)
    var creatorId: Long? = null

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: Date? = null

    @TableField(fill = FieldFill.UPDATE)
    var updaterName: String? = null

    @TableField(fill = FieldFill.UPDATE)
    var updaterId: Long? = null

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updateTime: Date? = null

    var remark: String? = null

}
