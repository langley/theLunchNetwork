package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Task

object TaskController extends Controller {
  
  // ----------------------------------------
  // Form object for tasks. This is as simple
  // as it gets. 
  val taskForm = Form(
	"label" -> nonEmptyText
  )  
  
  // ---------------------------------------
  // Controller defs
  
  def taskIndex = Action {
    Ok("Hello world from TasksController")
  }
  
  def tasks = Action {
	Ok(views.html.tasks(Task.all(), taskForm))
  }
  
  def newTask = Action { implicit request =>
	taskForm.bindFromRequest.fold(
		errors => BadRequest(views.html.tasks(Task.all(), errors)),
		label => {
			Task.create(label)
			Redirect(routes.TaskController.tasks)
		}
	)
  }
  
  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.TaskController.tasks)
  }
  
}
