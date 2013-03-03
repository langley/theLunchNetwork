package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Welcome to The Lunch Network"))
  }
  
  def about = Action { 
    Ok(views.html.about("About The Lunch Network"))
  }
  
}
