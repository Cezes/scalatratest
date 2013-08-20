
import java.io.File
import org.codehaus.jackson.map.ObjectMapper
import scala.concurrent.duration._
import scala.Some


import org.scalatra._
import org.scalatra.scalate.ScalateSupport
import org.scalatra.swagger._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import scala.util.parsing.json.JSONObject
import scalax.io._

class DemoService (implicit val swagger: Swagger) extends ScalatraServlet
with SwaggerSupport
with ScalateSupport
with NativeJsonSupport
with MethodOverride{

  protected implicit val jsonFormats: Formats = DefaultFormats
  override protected val applicationName: Option[String] = Some("file")
  protected val applicationDescription: String = "File Processing Api. It allows to use file to store data about client, process and browse this data "


  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }


  val showIndex =
    (apiOperation[String]("showIndex")
      .summary("Show available pages")
      .notes("Show all possibilities that user can use"))


  get("/index", operation(showIndex)) {
    contentType="text/html"
    <html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl" >
      <body>
        <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
        <p>Defined resources:</p>
        <ul>
          <li><a href="/file/plik">/plik</a></li>
          <li><a href="/file/stats">/stats</a></li>
          <li><a href="/file/timeout">/timeout</a></li>
          <li><a href="/file/crash">/crash</a></li>
          <li><a href="/file/fail">/fail</a></li>
          <li><a href="/file/stop?method=post">/stop</a></li>
        </ul>
      </body>
    </html>
  }

  val showFileOperations =
    (apiOperation[String]("showFileOperations")
      summary "Show possible actions which can be used on file"
      notes   "Displays page with links leading to diffrent forms which grant various results, such as" +
      "displaying file, adding entries, finding entry, removing records and obviously adding one")

  get("/plik", operation(showFileOperations)) {
    contentType="text/html"
    <html >

      <body>
        <h1>Say hello to <i>spray-can</i>!</h1>
        <p>Defined operations:</p>
        <ul>
          <li><a href="/file/plik/open">/Display file</a></li>
          <li><a href="/file/plik/addingName">/Add record</a></li>
          <li><a href="/file/plik/findBy">/Find by</a></li>
          <li><a href="/file/plik/edit">/Edit record</a></li>
          <li><a href="/file/plik/removeName">/Remove record</a></li>
        </ul>
      </body>
    </html>
  }

  val showFileContents =
    (apiOperation[String]("showFileContents")
      summary "Shows json contents"
      notes   "Displays whole json content in form of String")

  get("/plik/open", operation(showFileContents)) {
    val source = scala.io.Source.fromFile("file.txt")
    var lines = source.mkString
    source.close()
    lines
  }

  val showAddingEntryForm =
    (apiOperation[String]("showAddingEntryForm")
      summary "Show form for adding entries"
      notes   "Shows form which asks for Name, Age, Sex and Address of client")
  get("/plik/addingName", operation(showAddingEntryForm)) {
    contentType="text/html"
    <html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl" >
      <head>
        <link rel="stylesheet" type="text/css" href="/file/mystyle.css" ></link>
      </head>
      <body>
        <div class="centre">
        <h1>Add to file</h1>
        <form name="input" action="/file/plik/append" method="post">
          <div id ="formWrapper">
            <label for="firstname">First name</label>
            <input type ="text" placeholder="First name" name="firstname"></input>
            <br/>

            <label for="age">Age</label>
            <input type ="text" placeholder="Age" name="age" ></input>
            <br/>

            <label for="sex">Sex</label>
            <input type ="text" placeholder="Male" name="sex" ></input>
            <br/>

            <label for="address">Address</label>
            <input type ="text" placeholder="Address" name="address" ></input>
            <br/>

            <input type="submit" value="Submit"></input>

            <br/>

          </div>
        </form>
        </div>
      </body>
    </html>
  }
  val showRemovingEntryForm =
    (apiOperation[String]("showRemovingEntryForm")
      summary "Show form for deleting entries"
      notes   "Shows form which asks for Username")
  get("/plik/removeName", operation(showRemovingEntryForm)){
    contentType="text/html"
    <html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl" >
      <head>
        <link type="text/css" href="/file/mystyle.css" rel="stylesheet"></link>
      </head>
      <body>
        <div class="centre">
        <h1>Remove from file</h1>
        <form name="input" action="/file/plik/remove" method="post" >
          <div id ="formWrapper">

            <label for="user"> Username: </label>
            <input type="text" placeholder ="Username" name="user" />
            <br/>

            <input type="submit" value="Remove" />
          </div>
        </form>
        </div>
      </body>
    </html>
  }
  val showFindingEntryForm =
    (apiOperation[String]("showFindingEntryForm")
      summary "Shows form for finding entries"
      notes   "Shows form which asks for Name, Age, Sex and Address of client")
  get("/plik/findBy", operation(showFindingEntryForm)){
    contentType="text/html"
    <html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl" >
      <head>
        <link type="text/css" href="/file/mystyle.css" rel="stylesheet" ></link>
      </head>
      <body>
        <div class ="centre">
        <h1>Find by </h1>
        <form name="input" action="/file/plik/find" method="get">
          <div id ="formWrapper">

            <label for="name">Name:</label>
            <input type="text" placeholder="Name" name="name" />
            <br/>

            <label for="age">Age:</label>
            <input type="text" placeholder="age" name="age" />
            <br/>

            <label for="sex">Sex:</label>
            <input type="text" placeholder="sex" name="sex" />
            <br/>

            <label for="address">Address:</label>
            <input type="text" placeholder="address" name="address" />
            <br/>

            <input type="submit" value="Find" />
            <br/>
          </div>
        </form>
        </div>
      </body>
    </html>
  }
  val showEditingEntryForm =
    (apiOperation[String]("showEditingEntryForm")
      summary "Shows form for editing entries"
      notes   "Shows form which asks for Name, Age, Sex and Address of client")
  get("/plik/edit", operation(showEditingEntryForm)){
    contentType="text/html"
    <html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl" >
      <head>
        <link type ="text/css" href="/file/mystyle2.css" rel="stylesheet"></link>
      </head>
      <body>
        <div class ="centre">
          <h1>Find record you want to edit </h1>
          <form name="input" action="/file/plik/edite" method="post" align="center">
            <div id="formWrapper">
              <table align="center">
                <tr>
                  <td>
                    <label for="name">Name:</label>
                    <input type="text" placeholder="name" name="name" />
                  </td>
                  <td>
                    <label for="newName" > New Name:</label>
                    <input type="text" placeholder="new Name" name="newName" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <label for="age" > Age:</label>
                    <input type="text" placeholder="age" name="age" />
                  </td>
                  <td>
                    <label for="newAge"  > New Age  :</label>
                    <input type="text" placeholder="new age" name="newAge" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <label for="sex">Sex:</label>
                    <input type="text" placeholder="sex" name="sex" />
                  </td>
                  <td>
                    <label for="newSex">New sex:</label>
                    <input type="text" placeholder="new sex" name="newSex" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <label for="address">Address:</label>
                    <input type="text" placeholder="address" name="address" />
                  </td>
                  <td>
                    <label for="newAddress">New Address:</label>
                    <input type="text" placeholder="new Address" name="newAddress" />
                  </td>
                </tr>
                <tr>
                  <td>  <input type="submit" value="Edit" />   </td>
                </tr>
              </table>
            </div>
          </form>
        </div>
      </body>
    </html>
  }
  val appendEntry =
    (apiOperation[Person]("appendEntry")
      summary "Append entry based on form input"
      parameters(
      pathParam[String]("firstname").description("Name of customer"),
      pathParam[String]("age").description("Age of customer"),
      pathParam[String]("sex").description("Sex of customer"),
      pathParam[String]("address").description("Address of customer")
      ))
  post("/plik/append", operation(appendEntry)){
    var name =params.get("firstname").get
    var age =params.get("age").get
    var sex = params.get("sex").get
    var address = params.get("address").get

    val gender = sex.toLowerCase
    //Sprawdzenie poprawnosci wprowadzonych do formularza danych
    if( !(gender.equals("male") | gender.equals("female")) | name.isEmpty | age.isEmpty | address.isEmpty )
      NotFound("Bad parameters used.")
    else{
      var personAge = 0
      try {
        personAge =age.toInt
      } catch {
        case ex: NumberFormatException =>{
          personAge = -1
        }
      }
      if(personAge < 0 )
        NotAcceptable("Age must be a number equal or higher than 0")
      else{
        val person: Person = Person(name.toLowerCase , personAge, gender, address)
        //sprawdzenie czy imie jest unikalne
        if (findIfNameIsUnique(Person(name,-1,"",""))){
          var source = scala.io.Source.fromFile("file.txt")
          var lines = source.mkString
          source.close()
          val file: Seekable =  Resource.fromFile("file.txt")
          //Sprawdzenie czy nie dopisujemy juz do isteniejacych danych , jesli tak to dajemy znak mowej lini na poczatek
          if (lines != "")
            file.append("\n" + toJson(person))
          else
            file.append(toJson(person))
          source = scala.io.Source.fromFile("file.txt")
          lines = source.mkString
          source.close
          lines.toString
        } else
          NotAcceptable("Name must be unique")
      }
    }
  }
  val findEntry =
    (apiOperation[Person]("findEntry")
      summary "Find entry based on form input"
      parameters(
      pathParam[String]("name").description("Name of customer"),
      pathParam[String]("age").description("Age of customer"),
      pathParam[String]("sex").description("Sex of customer"),
      pathParam[String]("address").description("Address of customer")
      ))
  get("/plik/find", operation(findEntry)){
    var name = params.get("name").get
    var age = params.get("age").get
    var sex = params.get("sex").get.toLowerCase
    var address = params.get("address").get

    var temp = 0
    if (age.isEmpty ){
      temp = -1
    }
    else  { temp = age.toInt }
    var person = Person(name,temp,sex,address)
    var result = ""
    try{
      var source = scala.io.Source.fromFile("file.txt")
      for ( line <- source.getLines()){
        var currentLineResult = findMatch( line, person)
        result = result + currentLineResult
        currentLineResult =""
      }

      source.close()
    }
    result
  }

  val removeEntry =
    (apiOperation[Person]("removeEntry")
      summary "Removing entry based on form input"
      parameters(
      pathParam[String]("user").description("Name of customer")
      ))

  delete("/plik/remove", operation(removeEntry)){
    val nameToRemove = params.get("user").get
    val file: Seekable =  Resource.fromFile(new File("file.txt"))
    var position = 0
    try{
      for ( line <- file.lines()){
        if (parse(line , true).extract[Person].name.equals(nameToRemove)){
          file.patch(position, "", OverwriteSome(line.length+2))
          println(line.length)
          println(line)
        }  else {
          position = position + line.length + 2
        }
      }
    }
    val source = scala.io.Source.fromFile("file.txt")
    val lines = source.mkString
    source.close()
    lines.toString
  }
  val editEntry =
    (apiOperation[String]("editEntry")
      summary "Finds and edits entry based on form input"
      parameters(
      pathParam[String]("name").description("Name of customer"),
      pathParam[String]("newName").description("NEW name of customer"),
      pathParam[String]("age").description("Age of customer"),
      pathParam[String]("newAge").description("New age of customer"),
      pathParam[String]("sex").description("Sex of customer"),
      pathParam[String]("newSex").description("NEW Sex of customer"),
      pathParam[String]("address").description("Address of customer"),
      pathParam[String]("newAddress").description("New Address of customer")
      ))

  put("/plik/edite", operation(editEntry)){

    var name = params.get("name").get
    var newName = params.get("newName").get
    var age = params.get("age").get
    var newAge = params.get("newAge").get
    var sex = params.get("sex").get.toLowerCase
    var newSex = params.get("newSex").get.toLowerCase
    var address = params.get("address").get
    var newAddress = params.get("newAddress").get

    var temp = 0
    var personAge = 0

    //Sprawdzamy poprawnosc danych
    if (age.isEmpty ){
      temp = -1
    }
    else  {
      try {
        personAge =age.toInt
      } catch {
        case ex: NumberFormatException =>{
          personAge = -1
        }
      }
    }
    val gender = sex.toLowerCase
    val newGender = newSex.toLowerCase
    if(personAge < 0 | !(gender.equals("male") | gender.equals("female") | gender.isEmpty) | !(newGender.equals("male") | newGender.equals("female") | newGender.isEmpty))
      if (personAge < 0)
        NotAcceptable("Age must be a number higher or equal 0")
      else
        NotAcceptable("Wrong sex parameter use \"male\" or \"female\"")
    else if (newName == "" | findIfNameIsUnique(Person(newName,-1, "", "")) ){
      if (temp == 0){
        temp = age.toInt
      }
      var temporary = 0
      val file: Seekable =  Resource.fromFile("file.txt")
      var position = 0
      val personToEdit = Person(name, temp, gender, address)
      try{
        val fileLenght = file.lines().mkString.length
        var offset = 0
        for( line <- file.lines()){
          var currentLineResult = ""
          println(line)
          if ( position + line.length <= fileLenght){
            currentLineResult = findMatch( line, personToEdit)}
          else{
            //offset wprowadzony aby pozbyc sie buga w ktorym jesli nowa linijka była krotsza to w ostatniej linijsce pliku bylo przesuniecie
            val linesubstring = line.substring(0, (line.length - offset))
            if ( linesubstring.isEmpty == false)
              currentLineResult = findMatch ( line.substring(0, (line.length-offset)), personToEdit )}
          if ( currentLineResult.isEmpty){
            position = position + line.length + 1
          }
          else {

            if (newAge.isEmpty ){
              temporary = -1
            }
            else  {
              try {
                personAge =newAge.toInt
              } catch {
                case ex: NumberFormatException =>{
                  personAge = -1
                }
              }
            }
            if (personAge < 0 ){
              NotAcceptable("new Age parameter must be a number higher or equal 0")
            } else {
              if (temporary == 0){
                temporary = newAge.toInt
              }
              val newLine =  editPerson(line , Person(newName, temporary, newGender, newAddress))
              if ( newLine.length > line.length)
                offset = offset + (newLine.length - line.length)
              file.patch(position  , newLine , OverwriteSome(line.length))
              file.string
              println("offset:" + offset)
              position = position + newLine.length + 1
            }
          }
        }
        val source = scala.io.Source.fromFile("file.txt")
        val lines = source.mkString
        source.close()
        lines.toString
      }
    }  else{
      NotAcceptable("New name must be unique")
    }
  }

  get("/mystyle.css"){
    val source = scala.io.Source.fromFile("mystyle.css")
    val lines = source.mkString
    source.close
    lines
  }

  get("/mystyle2.css"){
    val source = scala.io.Source.fromFile("mystyle2.css")
    val lines = source.mkString
    source.close
    lines
  }


  def findMatch(line : String ,  personToFind : Person ) : String = {

    var currentLine =  parse(line , true).extract[Person]
    var currentLineResult =  line + "\n"
    if (personToFind.name == "" ){
    }
    else if (currentLine.name != personToFind.name)
      currentLineResult =""
    if (personToFind.age ==  -1 ) {
    }
    else if(currentLine.age != personToFind.age)
      currentLineResult =""
    if (personToFind.sex == ""  ){
    }
    else if (currentLine.sex != personToFind.sex)
      currentLineResult = ""
    if (personToFind.address == ""  ){
    }
    else if (currentLine.address != personToFind.address)
      currentLineResult =""

    currentLineResult

  }

  def toJson(person :Person) : String = {
    "{\"name\":\"%s\",\"age\":%s,\"sex\":\"%s\",\"address\":\"%s\"}".format(person.name , person.age, person.sex, person.address)
  }

  def editPerson(line : String ,  personToEdit : Person ) : String = {
    var currentLine =  parse(line , true).extract[Person]
    println(personToEdit)
    var name = personToEdit.name
    var age = personToEdit.age
    var sex = personToEdit.sex
    var address = personToEdit.address
    //var string =""
    println(personToEdit)
    if (personToEdit.name.isEmpty ){
      name = currentLine.name
    }
    if (personToEdit.age ==  -1 ) {
      age = currentLine.age
    }
    if (personToEdit.sex.isEmpty  ){
      sex = currentLine.sex
      println("Edit person" + currentLine)
    }
    if (personToEdit.address.isEmpty  ){
      address = currentLine.address
    }
    toJson(Person(name, age, sex, address))
  }

  def findIfNameIsUnique (personToFind: Person) : Boolean = {
    var isUnique = true
    val source = scala.io.Source.fromFile("file.txt")
    for( line <- source.getLines()){
      if ( !(findMatch(line, personToFind).isEmpty))
        isUnique = false
    }
    isUnique
  }
}

