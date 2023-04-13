package `fun`.mochen.learn.english.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Data
@AllArgsConstructor
@NoArgsConstructor
class User: BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var username: String? = null

    var password: String? = null

    var status: Int = 0

}
