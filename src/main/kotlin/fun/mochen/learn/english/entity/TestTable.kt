package `fun`.mochen.learn.english.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId

class TestTable : BaseEntity() {

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var name: String? = null

    var age: Int? = null

}
