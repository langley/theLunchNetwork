package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.Play.current
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models._
import views._


// case class UserProfile(id: Long, name: String, email: String, password: String, url: String, desc: String, phone: String, org: String)

object UserProfileController extends Controller {
  
  // ---------------------------------------------
  // Form object for user profiles
  val userProfileForm = Form(
    mapping( "id" -> ignored("1234"),
      "name" -> text.verifying("Really? This field must be > 4 chars", userName => userName.length > 4),
      "email" -> text.verifying(nonEmpty),
      "password" -> text.verifying(nonEmpty),
      "url" -> text,
      "desc" -> text.verifying("Oh, don't be so modest! This field must be > 5 characters", desc => desc.length() > 4),
      "phone" -> text, 
      "org" -> text.verifying(nonEmpty), 
      "nextDateBegin" -> text,
      "nextDateEnd" -> text
     )(UserProfile.apply)(UserProfile.unapply)
  )

  def view = Action { implicit request => 
    val origUri = session.get("origUri").getOrElse("/")
    Ok(html.userProfileForm(userProfileForm))
  }
  
  def update = Action { implicit request => 
    userProfileForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.userProfileForm(formWithErrors)),
        goodForm => { saveUserProfile(goodForm); Ok(views.html.userProfileForm(userProfileForm.fill(goodForm)))} 
        )
  }
  
  def saveUserProfile(userProfileData: UserProfile)  = { 
    // TODO 

  }
}