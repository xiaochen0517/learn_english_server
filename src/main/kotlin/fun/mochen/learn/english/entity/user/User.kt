package `fun`.mochen.learn.english.entity.user

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import `fun`.mochen.learn.english.entity.BaseEntity

class User: BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var username: String? = null

    var password: String? = null

    var status: Int = 0

}
