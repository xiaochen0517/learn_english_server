package `fun`.mochen.learn.english.entity

import org.springframework.data.annotation.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

class TestTable : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String? = null

    var age: Int? = null

}
