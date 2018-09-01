package controllers 

object TodoForm {  
    import play.api.data.Forms._ 
    import play.api.data.Form

    case class Data(name: String, description: String) 

   val form = Form(
    mapping(
      "name" -> text,
      "description" -> text
    )(Data.apply)(Data.unapply)
  )

} 