package controllers

import javax.inject.Inject 

import models.Todo

import play.api.data._
import play.api.i18n._
import play.api.mvc._

/** 
* This controller creates an 'Action' to hanlde HTTP requests that 
* allow the application to create and return "todos" 
*/ 
// @Singleton
class TodoController  @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/todos`.
   */ 

    import TodoForm._ 

    private val todos = scala.collection.mutable.ArrayBuffer(
        Todo("Add more items", "Add more items to this to do list")
    ) 

    private val postUrl = routes.TodoController.createTodo() 

    def index = Action { 
        Ok(views.html.index())
    } 

    def getTodos = Action { implicit request: MessagesRequest[AnyContent] => 
        Ok(views.html.getTodos(todos, form, postUrl)) 
    } 

    def createTodo = Action { implicit request: MessagesRequest[AnyContent] => 
         val errorFunction = { formWithErrors: Form[Data] =>
            BadRequest(views.html.getTodos(todos, formWithErrors, postUrl))
        }

        val successFunction = { data: Data => 
        val todo = Todo(name = data.name, description = data.description) 
            todos.append(todo)
            Redirect(routes.TodoController.getTodos()).flashing("info" -> "Item added") 
        } 

        val formValidationResult = form.bindFromRequest
        formValidationResult.fold(errorFunction, successFunction) 
    } 
} 