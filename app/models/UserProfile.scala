package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

import play.api.db._
import play.api.cache.Cache
import play.api.Play.current


import anorm._
import anorm.SqlParser._


case class UserProfile(id: String, name: String, email: String, password: String, url: String, desc: String, phone: String, org: String, nextDateBegin: String, nextDateEnd: String)

object UserProfile {
  // Constructor for UserProfile value objects that creates the sequence number
  def apply(name: String, email: String, password: String, url: String, desc: String, phone: String, org: String, nextDateBegin: String, nextDateEnd: String) = { 
    new UserProfile(java.util.UUID.randomUUID().toString, name, email, password, url, desc, phone, org, nextDateBegin, nextDateEnd)
  }
  // Parser for getting into and out of the RDBMS
  val userProfileParser = { 
    get[String]("user.id") ~
    get[String]("user.name") ~
    get[String]("user.email") ~
    get[String]("user.password") ~
    get[String]("user.desc") ~
    get[String]("user.phone") ~
    get[String]("user.org") ~ 
    get[String]("user.nextDateBegin") ~
    get[String]("user.nextDateEnd") map { 
      case id~name~email~password~desc~phone~org~nextDateBegin~nextDateEnd => UserProfile(id, name, email, password, desc, phone, org, nextDateBegin, nextDateEnd)
    }
  }
  
  // -- Queries
  
  /**
   * Retrieve a User by id by going directly to the database.
   */
  def findById(id: String): Option[UserProfile] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where id = {id}").on(
        'id-> id
      ).as(UserProfile.userProfileParser.singleOpt)
    }
  }  
  
  /**
   * Retrieve all Users
   */
  def findAll: Seq[UserProfile] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user").as(UserProfile.userProfileParser *)
    }
  }
  
  def countUsers() = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select count(*) from user
        """
          ).as(scalar[Long].single)
    }
  }
  
  def add(id: String, name: String, email: String, password: String, url: String, desc: String, phone: String, org: String) = { 
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user values (
              {id}, {name}, {email}, {password}, {url}, {desc}, {phone}, {org}
		  )
    	"""
      ).on(
        'id -> id,
        'name -> name,
        'email -> email,
        'password -> password,
        'url -> url, 
        'desc -> desc, 
        'phone -> phone, 
        'org -> org
      ).execute()
    }
  }  
  
  def update(user: UserProfile): UserProfile = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update user
    		set name = {name}, email = {email}, password = {password}, url = {url}, desc = {desc}, phone = {phone}, org = {org} 
    		where id = {id}
        """
      ).on(
        'id -> user.id,
        'name-> user.name,
        'email -> user.email,
        'password -> user.password,
        'url -> user.url, 
        'desc -> user.desc, 
        'phone -> user.phone, 
        'org -> user.org          
      ).executeUpdate()
      user
    }
  }  
}