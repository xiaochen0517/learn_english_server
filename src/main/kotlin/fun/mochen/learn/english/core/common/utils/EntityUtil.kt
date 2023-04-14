package `fun`.mochen.learn.english.core.common.utils

import com.alibaba.fastjson.JSON
import `fun`.mochen.learn.english.core.domain.dict.WordDerivedForms
import `fun`.mochen.learn.english.core.domain.dict.WordInfo
import `fun`.mochen.learn.english.core.domain.dict.WordPhonetic
import `fun`.mochen.learn.english.core.domain.dict.WordVoice
import `fun`.mochen.learn.english.entity.dict.DictWordInfo

class EntityUtil {

    companion object {


        fun dictWordInfo2FindWordInfo(dictWordInfo: DictWordInfo): WordInfo {
            // 检查当前参数是否为空
            if (dictWordInfo.id == null || dictWordInfo.word == null) {
                throw IllegalArgumentException("dictWordInfo.id or dictWordInfo.word is null")
            }
            val wordPhonetic = WordPhonetic(dictWordInfo.defPhonetic, dictWordInfo.usPhonetic, dictWordInfo.ukPhonetic)
            val wordVoice = WordVoice(
                dictWordInfo.queryVoiceUrl, dictWordInfo.transVoiceUrl, dictWordInfo.usVoiceUrl, dictWordInfo.ukVoiceUrl
            )
            val explainsList = JSON.parseArray(dictWordInfo.explains, String::class.java) ?: listOf()
            val derivedFormsList = JSON.parseArray(dictWordInfo.derivedForms, WordDerivedForms::class.java)
            return WordInfo(
                id = dictWordInfo.id!!,
                word = dictWordInfo.word!!,
                phonetic = wordPhonetic,
                voice = wordVoice,
                explains = explainsList,
                derivedForms = derivedFormsList
            )
        }

        fun findWordInfo2DictWordInfo(findWordInfo: WordInfo): DictWordInfo {
            // 检查当前参数是否为空
            val dictWordInfo = DictWordInfo()
            dictWordInfo.id = null
            dictWordInfo.word = findWordInfo.word
            dictWordInfo.defPhonetic = findWordInfo.phonetic.defPhonetic
            dictWordInfo.usPhonetic = findWordInfo.phonetic.usPhonetic
            dictWordInfo.ukPhonetic = findWordInfo.phonetic.ukPhonetic
            dictWordInfo.queryVoiceUrl = findWordInfo.voice.queryVoiceUrl
            dictWordInfo.transVoiceUrl = findWordInfo.voice.transVoiceUrl
            dictWordInfo.usVoiceUrl = findWordInfo.voice.usVoiceUrl
            dictWordInfo.ukVoiceUrl = findWordInfo.voice.ukVoiceUrl
            dictWordInfo.explains = JSON.toJSONString(findWordInfo.explains)
            dictWordInfo.derivedForms = JSON.toJSONString(findWordInfo.derivedForms)
            return dictWordInfo
        }
    }

}
