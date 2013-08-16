

//mport DefaultJsonProtocol._

import org.json4s.{Formats, DefaultFormats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.ScalatraServlet
import scalax.io.WriterResource
import org.json4s.JsonDSL._

import java.io.File


/*case class Address(no: String, street: String, city: String)*/





case class Person(name: String, age: Int, sex: String, address: String)

case class ApiResponse(code: String, msg: String)

object ApiResponseType{
  val ERROR = "error"
  val WARNING = "warning"
  val INFO = "info"
  val OK ="ok"
  val TOO_BUSY = "too busy"
}

object Person extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats



}

/*

trait MyJsonProtocol extends DefaultJsonProtocol {
  //implicit val PersonFormat = jsonFormat4(Person.apply)
  implicit val PersonFormat = jsonFormat(Person, "name", "age", "sex", "address")
}

*/



/*object Person {
  val `application/x-www-form-urlencoded` = MediaTypes.register(MediaType.custom("application/x-www-form-urlencoded"))


  implicit val PersonMarshaller = Marshaller.of[Person](`application/x-www-form-urlencoded`) {(value, contentType, ctx) =>
    val Person(name, age, sex, address) = value
    val string = " {\"firstname\" : \"%s\", \"age\" : %s,  \"sex\" : \"%s\" , \"address\" : \"%s\"} ".format(name , age, sex, address)
    //val string = "firstname=%s&age=%s&sex=%s&address=%s".format(name , age, sex, address)
    ctx.marshalTo(HttpEntity(contentType, string))

  }


}
*/