package seventeen.my.notes.model

data class Notes(
    var id:Int? = null,
    var title:String? = null,
    var subTitle:String? = null,
    var dateTime:String? = null,
    var noteText:String? = null,
    var imgPath:String? = null,
    var webLink:String? = null,
    var color:String? = null
)
