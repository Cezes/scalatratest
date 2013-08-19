
import org.scalatra.LifeCycle
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new DemoSwagger

  override def init(context: ServletContext) {
    context.mount(new DemoService, "/file/*")
    context.mount (new ResourcesApp, "/api-docs/*")
  }
}
